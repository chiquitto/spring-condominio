package br.edu.ifms.springcondominio.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.ifms.springcondominio.models.ApartamentoModel;
import br.edu.ifms.springcondominio.repositories.ApartamentoRepository;
import jakarta.transaction.Transactional;

@Service
public class ApartamentoService {
	
	final ApartamentoRepository apartamentoRepository;
	
	public ApartamentoService(ApartamentoRepository apartamentoRepository) {
		this.apartamentoRepository = apartamentoRepository;
	}

	@Transactional
	public ApartamentoModel save(ApartamentoModel apartamentoModel) {
		return apartamentoRepository.save(apartamentoModel);
	}

	public boolean existsByNumeroAndBloco(String numero, String bloco) {
		return apartamentoRepository.existsByNumeroAndBloco( numero, bloco );
	}
	
	public boolean existsByResponsavel(String responsavel) {
		return apartamentoRepository.existsByResponsavel(responsavel);
	}

	public List<ApartamentoModel> findAll() {
		return apartamentoRepository.findAll();
	}

	public Optional<ApartamentoModel> findById(UUID id) {
		return apartamentoRepository.findById(id);
	}

	@Transactional
	public void delete(ApartamentoModel apartamentoModel) {
		apartamentoRepository.delete(apartamentoModel);
	}

}
