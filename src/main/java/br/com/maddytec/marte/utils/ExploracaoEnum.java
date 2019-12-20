package br.com.maddytec.marte.utils;

public enum ExploracaoEnum {

	EXPLORACAO_SIM("SIM"), 
	EXPLORACAO_NAO("NÃO");

	private String descricao;

	private ExploracaoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
