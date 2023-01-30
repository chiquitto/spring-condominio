package br.edu.ifms.springcondominio.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class VeiculoDto {

	@NotBlank
	@Size(min = 7, max = 7)
	private String placa;
	
	@NotBlank
	@Size(max = 50)
	private String descricao;

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
