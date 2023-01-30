package br.edu.ifms.springcondominio.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.ifms.springcondominio.models.Veiculo;
import br.edu.ifms.springcondominio.repositories.VeiculoRepository;
import jakarta.transaction.Transactional;

@Service
public class VeiculoService {
	
	final VeiculoRepository veiculoRepository;

    VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    @Transactional
	public Veiculo save(Veiculo veiculo) {
		return veiculoRepository.save(veiculo);
	}

	public boolean existsByPlaca(String placa) {
		return veiculoRepository.existsByPlaca(placa);
	}

	public List<Veiculo> findAll() {
		return veiculoRepository.findAll();
	}

	public Optional<Veiculo> findById(UUID id) {
		return veiculoRepository.findById(id);
	}
	
	public boolean existsByIdNotAndPlaca(UUID id, String placa) {
		return veiculoRepository.existsByIdNotAndPlaca(id, placa);
	}

	public void delete(Veiculo veiculo) {
		veiculoRepository.delete(veiculo);	
	}
    
    
}
