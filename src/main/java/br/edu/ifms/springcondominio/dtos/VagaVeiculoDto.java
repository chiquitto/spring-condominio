package br.edu.ifms.springcondominio.dtos;

import br.edu.ifms.springcondominio.models.Vaga;
import br.edu.ifms.springcondominio.models.Veiculo;

public class VagaVeiculoDto {

	private Veiculo veiculo;
	private Vaga vaga;
	
	public Veiculo getVeiculo() {
		return veiculo;
	}
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	public Vaga getVaga() {
		return vaga;
	}
	public void setVaga(Vaga vaga) {
		this.vaga = vaga;
	}
	
}
