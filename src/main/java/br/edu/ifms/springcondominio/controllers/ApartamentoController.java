package br.edu.ifms.springcondominio.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifms.springcondominio.dtos.ApartamentoDto;
import br.edu.ifms.springcondominio.models.Apartamento;
import br.edu.ifms.springcondominio.services.ApartamentoService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/apartamento")
public class ApartamentoController {
	
	final ApartamentoService apartamentoService;

	public ApartamentoController(ApartamentoService apartamentoService) {
		this.apartamentoService = apartamentoService;
	}
	
	@PostMapping
	public ResponseEntity<Object> novoApartamento(@RequestBody @Valid ApartamentoDto apartamentoDto) {
		if (apartamentoService.existsByNumeroAndBloco( apartamentoDto.getNumero(), apartamentoDto.getBloco() )) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body( "Conflito: O bloco/numero já existe para outro apartamento" );
		}
		if (apartamentoService.existsByResponsavel( apartamentoDto.getResponsavel() )) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body( "Conflito: O resposável já existe para outro apartamento" );
		}
		
		var apartamentoModel = new Apartamento();
		BeanUtils.copyProperties(apartamentoDto, apartamentoModel);
		apartamentoModel.setDataCadastro( LocalDateTime.now(ZoneId.of("UTC")) );
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body( apartamentoService.save(apartamentoModel) );
	}
	
	@GetMapping
	public ResponseEntity< List<Apartamento> > pegarTodos() {
		return ResponseEntity.status(HttpStatus.OK)
				.body( apartamentoService.findAll() );
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> pegarUm( @PathVariable(value = "id") UUID id ) {
		Apartamento apartamento = testExistsById(id);
		
		return ResponseEntity.status(HttpStatus.OK)
				.body( apartamento );
	}

	private Apartamento testExistsById(UUID id) {
		Optional<Apartamento> apartamentoOptional = apartamentoService.findById( id );
		if (!apartamentoOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O apartamento não existe");
		}
		return apartamentoOptional.get();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> apagarApartamento( @PathVariable(value = "id") UUID id ) {
		Apartamento apartamento = testExistsById(id);
		
		apartamentoService.delete( apartamento );
		return ResponseEntity.status( HttpStatus.OK )
				.body( "O apartamento foi apagado" );
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarApartamento( @PathVariable(value = "id") UUID id,
			@RequestBody @Valid ApartamentoDto apartamentoDto) {
		Apartamento apartamento = testExistsById(id);
		
		apartamento.setNumero( apartamentoDto.getNumero() );
		apartamento.setBloco( apartamentoDto.getBloco() );
		apartamento.setResponsavel( apartamentoDto.getResponsavel() );
		apartamento.setContato( apartamentoDto.getContato() );
		
		return ResponseEntity.status( HttpStatus.OK )
				.body( apartamentoService.save(apartamento) );
	}
	
}
