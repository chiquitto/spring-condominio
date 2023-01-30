package br.edu.ifms.springcondominio.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifms.springcondominio.models.Veiculo;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, UUID> {

	boolean existsByPlaca(String placa);

	boolean existsByIdNotAndPlaca(UUID id, String placa);

}
