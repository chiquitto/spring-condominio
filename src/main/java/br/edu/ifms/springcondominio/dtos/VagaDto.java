package br.edu.ifms.springcondominio.dtos;

import br.edu.ifms.springcondominio.models.Apartamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class VagaDto {

	@NotBlank
	@Size(max = 10)
	private String numero;
	private Apartamento apartamento;
	
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public Apartamento getApartamento() {
		return apartamento;
	}
	public void setApartamento(Apartamento apartamento) {
		this.apartamento = apartamento;
	}
	
	
}
