package br.edu.ifms.springcondominio.controllers;

import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifms.springcondominio.dtos.VagaVeiculoDto;
import br.edu.ifms.springcondominio.models.Vaga;
import br.edu.ifms.springcondominio.models.Veiculo;
import br.edu.ifms.springcondominio.services.VagaService;
import br.edu.ifms.springcondominio.services.VeiculoService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/vaga-veiculo")
public class VagaVeiculoController {

	final VagaService vagaService;
	final VeiculoService veiculoService;

	public VagaVeiculoController(VagaService vagaService, VeiculoService veiculoService) {
		this.vagaService = vagaService;
		this.veiculoService = veiculoService;
	}

	@PostMapping()
	public ResponseEntity<Object> adicionarVagaVeiculo( @RequestBody @Valid VagaVeiculoDto vagaVeiculoDto ) {
		Optional<Vaga> vagaOptional = vagaService.findById( vagaVeiculoDto.getVaga().getId() );
		if (vagaOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body( "Erro: Vaga inexistente" );
		}
		
		Optional<Veiculo> veiculoOptional = veiculoService.findById( vagaVeiculoDto.getVeiculo().getId() );
		if (veiculoOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body( "Erro: Veiculo inexistente" );
		}
		
		Vaga vaga = vagaOptional.get();
		Veiculo veiculo = veiculoOptional.get();
		
		vaga.getVeiculos().add( veiculo );
		vagaService.save( vaga );
		
		return ResponseEntity.status(HttpStatus.OK)
				.body( vaga );
	}
	
	@DeleteMapping("/{id_vaga}/{id_veiculo}")
	public ResponseEntity<String> apagarVagaVeiculo(
			@PathVariable(name = "id_vaga") UUID idvaga,
			@PathVariable(name = "id_veiculo") UUID idveiculo
			) {
		Optional<Vaga> vagaOptional = vagaService.findById( idvaga );
		if (vagaOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body( "Erro: Vaga inexistente" );
		}
		
		Vaga vaga = vagaOptional.get();
		Iterator<Veiculo> veicIterator = vaga.getVeiculos().iterator();
		while(veicIterator.hasNext()) {
			Veiculo veiculo = veicIterator.next();
			
			if (veiculo.getId().equals( idveiculo )) {
				vaga.getVeiculos().remove(veiculo);
				vagaService.save( vaga );
				
				return ResponseEntity.status(HttpStatus.OK)
						.body( "Veiculo removido da vaga" );
			}
		}
		
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body( "Erro: Veiculo não encontrado na vaga" );
	}
	
}
