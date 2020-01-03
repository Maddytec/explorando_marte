package br.com.maddytec.marte.utils;

public enum ComandoEnum {

	L("Esquerda"),
	R("Direita"),
	M("Frente");
	
	private String comando;
	
	ComandoEnum(String comando){
		this.comando = comando;
	}
	
	public String getComando() {
		return comando;
	}
	
}
