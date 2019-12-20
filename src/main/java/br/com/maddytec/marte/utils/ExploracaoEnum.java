package br.com.maddytec.marte.utils;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ExploracaoEnum {

	EXPLORACAO_SIM("SIM"), 
	EXPLORACAO_NAO("NÃO");

	@JsonValue
	private String descricao;

	private ExploracaoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
