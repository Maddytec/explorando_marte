package br.com.maddytec.marte.utils;

public enum DirecaoEnum {

	N("Norte"),
	E("Leste"),
	S("Sul"),
	W("Oeste");

	private String descricao;

	private DirecaoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
