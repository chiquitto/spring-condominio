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
import org.springframework.web.server.ResponseStatusException;

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
			throw new ResponseStatusException(HttpStatus.CONFLICT, "A placa já existe para outro veiculo");
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
		Veiculo veiculo = testExistsById(id);
		
		return ResponseEntity.status( HttpStatus.OK )
				.body( veiculo );
	}

	private Veiculo testExistsById(UUID id) {
		Optional<Veiculo> veiculoOptional = veiculoService.findById( id );
		if (veiculoOptional.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "O veiculo não existe");
		}
		return veiculoOptional.get();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizarVeiculo( @PathVariable(name = "id") UUID id,
			@RequestBody @Valid VeiculoDto veiculoDto) {
		Veiculo veiculo = testExistsById(id);
		
		if ( veiculoService.existsByIdNotAndPlaca(id, veiculoDto.getPlaca()) ) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "A placa já existe para outro veiculo");
		}
		
		veiculo.setPlaca( veiculoDto.getPlaca() );
		veiculo.setDescricao( veiculoDto.getDescricao() );
		veiculoService.save( veiculo );
		
		return ResponseEntity.status(HttpStatus.OK)
				.body( veiculo );
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> apagarVeiculo( @PathVariable(name = "id") UUID id ) {
		Veiculo veiculo = testExistsById(id);
		
		veiculoService.delete( veiculo );
		
		return ResponseEntity.status(HttpStatus.OK)
				.body( "Veiculo apagado" );
	}
	
}
