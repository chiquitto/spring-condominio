package br.edu.ifms.springcondominio.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.ifms.springcondominio.models.Apartamento;
import br.edu.ifms.springcondominio.repositories.ApartamentoRepository;
import jakarta.transaction.Transactional;

@Service
public class ApartamentoService {
	
	final ApartamentoRepository apartamentoRepository;
	
	public ApartamentoService(ApartamentoRepository apartamentoRepository) {
		this.apartamentoRepository = apartamentoRepository;
	}

	@Transactional
	public Apartamento save(Apartamento apartamentoModel) {
		return apartamentoRepository.save(apartamentoModel);
	}

	public boolean existsByNumeroAndBloco(String numero, String bloco) {
		return apartamentoRepository.existsByNumeroAndBloco( numero, bloco );
	}
	
	public boolean existsByResponsavel(String responsavel) {
		return apartamentoRepository.existsByResponsavel(responsavel);
	}

	public List<Apartamento> findAll() {
		return apartamentoRepository.findAll();
	}

	public Optional<Apartamento> findById(UUID id) {
		return apartamentoRepository.findById(id);
	}

	@Transactional
	public void delete(Apartamento apartamentoModel) {
		apartamentoRepository.delete(apartamentoModel);
	}

}
