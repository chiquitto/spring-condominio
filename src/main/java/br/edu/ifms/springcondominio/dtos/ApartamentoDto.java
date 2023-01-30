package br.edu.ifms.springcondominio.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ApartamentoDto {
	
	@NotBlank
	@Size(max = 10)
	private String numero;
	
	@NotBlank
	@Size(max = 10)
	private String bloco;
	
	@NotBlank
	@Size(max = 100)
	private String responsavel;
	
	@NotBlank
	@Size(max = 50)
	private String contato;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBloco() {
		return bloco;
	}

	public void setBloco(String bloco) {
		this.bloco = bloco;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	
	
}
