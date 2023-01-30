package br.edu.ifms.springcondominio.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.edu.ifms.springcondominio.models.Vaga;
import br.edu.ifms.springcondominio.repositories.VagaRepository;
import jakarta.transaction.Transactional;

@Service
public class VagaService {
	
	final VagaRepository vagaRepository;

    VagaService(VagaRepository vagaRepository) {
        this.vagaRepository = vagaRepository;
    }
    
    public List<Vaga> findAll() {
		return vagaRepository.findAll();
	}
    
    public Page<Vaga> findAll(Pageable pageable) {
    	return vagaRepository.findAll(pageable);
	}
    
    public Optional<Vaga> findById(UUID id) {
		return vagaRepository.findById(id);
	}

    @Transactional
	public Vaga save(Vaga vagaModel) {
		return vagaRepository.save(vagaModel);
	}

    @Transactional
	public void delete(Vaga vaga) {
		vagaRepository.delete(vaga);
	}

	public boolean existsByNumero(String numero) {
		return vagaRepository.existsByNumero(numero);
	}

}
