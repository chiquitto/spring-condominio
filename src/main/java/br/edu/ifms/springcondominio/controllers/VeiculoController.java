package br.edu.ifms.springcondominio.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import br.edu.ifms.springcondominio.dtos.VeiculoDto;
import br.edu.ifms.springcondominio.models.Veiculo;
import br.edu.ifms.springcondominio.services.VeiculoService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/veiculo")
public class VeiculoController {
	
	final VeiculoService veiculoService;

	public VeiculoController(VeiculoService veiculoService) {
		this.veiculoService = veiculoService;
	}
	
	@PostMapping
	public ResponseEntity<Object> novoVeiculo( @RequestBody @Valid VeiculoDto veiculoDto ) {
		
		if ( veiculoService.existsByPlaca( veiculoDto.getPlaca() ) ) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body( "Erro: A placa já existe para outro veiculo" );
		}
		
		var veiculo = new Veiculo();
		BeanUtils.copyProperties(veiculoDto, veiculo);
		veiculo.setDataCadastro( LocalDateTime.now( ZoneId.of("UTC") ) );
		
		veiculoService.save( veiculo );
		
		return ResponseEntity.status(HttpStatus.OK)
				.body( veiculo );
	}
	
	@GetMapping
	public ResponseEntity<List<Veiculo>> pegarTodos() {
		return ResponseEntity.status(HttpStatus.OK)
				.body( veiculoService.findAll() );
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Object> pegarUm( @PathVariable(name = "id") UUID id ) {
		Optional<Veiculo> veiculoOptional = veiculoService.findById( id );
		if (veiculoOptional.isEmpty()) {
			return ResponseEntity.status( HttpStatus.NOT_FOUND )
					.body( "Erro: O veiculo não existe" );
		}
		return ResponseEntity.status( HttpStatus.OK )
				.body( veiculoOptional.get() );
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarVeiculo( @PathVariable(name = "id") UUID id,
			@RequestBody @Valid VeiculoDto veiculoDto) {
		Optional<Veiculo> veiculoOptional = veiculoService.findById(id);
		if (veiculoOptional.isEmpty()) {
			return ResponseEntity.status( HttpStatus.NOT_FOUND )
					.body( "Erro: O veiculo não existe" );
		}
		
		if ( veiculoService.existsByIdNotAndPlaca(id, veiculoDto.getPlaca()) ) {
			return ResponseEntity.status( HttpStatus.NOT_FOUND )
					.body( "Erro" );
		}
		
		Veiculo veiculo = veiculoOptional.get();
		veiculo.setPlaca( veiculoDto.getPlaca() );
		veiculo.setDescricao( veiculoDto.getDescricao() );
		veiculoService.save( veiculo );
		
		return ResponseEntity.status(HttpStatus.OK)
				.body( veiculo );
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> apagarVeiculo( @PathVariable(name = "id") UUID id ) {
		Optional<Veiculo> veiculoOptional = veiculoService.findById(id);
		if (veiculoOptional.isEmpty()) {
			return ResponseEntity.status( HttpStatus.NOT_FOUND )
					.body( "Erro: O veiculo não existe" );
		}
		
		veiculoService.delete( veiculoOptional.get() );
		
		return ResponseEntity.status(HttpStatus.OK)
				.body( "Veiculo apagado" );
	}
	
}
