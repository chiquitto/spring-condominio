package br.edu.ifms.springcondominio.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifms.springcondominio.models.Vaga;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, UUID> {

	boolean existsByNumero(String numero);
	
}
