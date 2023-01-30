package br.edu.ifms.springcondominio.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
		Optional<Apartamento> apartamentoOptional = apartamentoService.findById( id );
		if (!apartamentoOptional.isPresent()) {
			return ResponseEntity.status( HttpStatus.NOT_FOUND )
					.body( "Erro: O apartamento não existe" );
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body( apartamentoOptional.get() );
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> apagarApartamento( @PathVariable(value = "id") UUID id ) {
		Optional<Apartamento> apartamentoOptional = apartamentoService.findById( id );
		if (!apartamentoOptional.isPresent()) {
			return ResponseEntity.status( HttpStatus.NOT_FOUND )
					.body( "Erro: O apartamento não existe" );
		}
		apartamentoService.delete( apartamentoOptional.get() );
		return ResponseEntity.status( HttpStatus.OK )
				.body( "O apartamento foi apagado" );
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarApartamento( @PathVariable(value = "id") UUID id,
			@RequestBody @Valid ApartamentoDto apartamentoDto) {
		Optional<Apartamento> apartamentoOptional = apartamentoService.findById( id );
		if (!apartamentoOptional.isPresent()) {
			return ResponseEntity.status( HttpStatus.NOT_FOUND )
					.body( "Erro: O apartamento não existe" );
		}
		
//		var apartamentoModel = new ApartamentoModel();
//		BeanUtils.copyProperties(apartamentoDto, apartamentoModel);
//		apartamentoModel.setId( apartamentoOptional.get().getId() );
//		apartamentoModel.setDataCadastro( apartamentoOptional.get().getDataCadastro() );
		
		var apartamentoModel = apartamentoOptional.get();
		apartamentoModel.setNumero( apartamentoDto.getNumero() );
		apartamentoModel.setBloco( apartamentoDto.getBloco() );
		apartamentoModel.setResponsavel( apartamentoDto.getResponsavel() );
		apartamentoModel.setContato( apartamentoDto.getContato() );
		
		return ResponseEntity.status( HttpStatus.OK )
				.body( apartamentoService.save(apartamentoModel) );
	}
	
}
