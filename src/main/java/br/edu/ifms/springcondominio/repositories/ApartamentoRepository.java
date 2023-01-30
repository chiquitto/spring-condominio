package br.edu.ifms.springcondominio.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifms.springcondominio.models.ApartamentoModel;

/**
 * 
 * @author alisson
 * 
 * JpaRepository traz varios metodos de manipulacao com BD.
 *
 */
@Repository
public interface ApartamentoRepository extends JpaRepository<ApartamentoModel, UUID> {

	boolean existsByNumeroAndBloco(String numero, String bloco);
	boolean existsByResponsavel(String responsavel);
	
}
