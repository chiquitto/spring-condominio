package br.edu.ifms.springcondominio.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import br.edu.ifms.springcondominio.dtos.VagaDto;
import br.edu.ifms.springcondominio.models.Apartamento;
import br.edu.ifms.springcondominio.models.Vaga;
import br.edu.ifms.springcondominio.services.VagaService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/vaga")
public class VagaController {
	
	final VagaService vagaService;

	public VagaController(VagaService vagaService) {
		this.vagaService = vagaService;
	}
	
	@PostMapping
	public ResponseEntity<Object> novaVaga(@RequestBody @Valid VagaDto vagaDto) {
		if (vagaService.existsByNumero( vagaDto.getNumero() )) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "O numero já existe para outra vaga");
		}
		
		var vagaModel = new Vaga();
		BeanUtils.copyProperties(vagaDto, vagaModel);
		vagaModel.setDataCadastro( LocalDateTime.now(ZoneId.of("UTC")) );
		
		vagaService.save(vagaModel);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body( vagaModel );
	}

//	@GetMapping
//	public ResponseEntity< List<Vaga> > pegarTodos() {
//		return ResponseEntity.status(HttpStatus.OK)
//				.body( vagaService.findAll() );
//	}
	
	@GetMapping
	public ResponseEntity< Page<Vaga> > pegarTodosPaginacao(
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
			) {
		return ResponseEntity.status(HttpStatus.OK)
				.body( vagaService.findAll(pageable) );
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> pegarUm( @PathVariable(value = "id") UUID id ) {
		Vaga vaga = testExistsById(id);
		return ResponseEntity.status(HttpStatus.OK)
				.body( vaga );
	}

	private Vaga testExistsById(UUID id) {
		Optional<Vaga> vagaOptional = vagaService.findById( id );
		if (!vagaOptional.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A vaga não existe");
		}
		return vagaOptional.get();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> apagarVaga( @PathVariable(value = "id") UUID id ) {
		Vaga vaga = testExistsById(id);
		
		vagaService.delete( vaga );
		
		return ResponseEntity.status(HttpStatus.OK)
				.body( "A vaga foi apagada" );
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarVaga( @PathVariable(value = "id") UUID id,
			@RequestBody @Valid VagaDto vagaDto) {
		
		Vaga vaga = testExistsById(id);
		vaga.setNumero( vagaDto.getNumero() );
		
		vagaService.save(vaga);
		
		return ResponseEntity.ok()
				.body( vaga );
	}

}
