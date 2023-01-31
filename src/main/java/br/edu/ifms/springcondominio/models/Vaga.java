package br.edu.ifms.springcondominio.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_VAGA")
public class Vaga implements Serializable {

private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(nullable = false, length = 10)
	private String numero;
	
	@Column(nullable = false)
	private LocalDateTime dataCadastro;
	
	@ManyToOne
	@JoinColumn(name = "id_apto")
	@JsonIncludeProperties(value = {"id"})
	private Apartamento apartamento;
	
	@ManyToMany
	@JoinTable(name = "TB_VAGA_VEICULO",
				joinColumns = @JoinColumn(name = "id_vaga"),
				inverseJoinColumns = @JoinColumn(name = "id_veiculo"))
	@JsonIncludeProperties(value = {"id"})
	Set<Veiculo> veiculos = new HashSet<>();

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Apartamento getApartamento() {
		return apartamento;
	}

	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}

	public Set<Veiculo> getVeiculos() {
		return veiculos;
	}
	
}
