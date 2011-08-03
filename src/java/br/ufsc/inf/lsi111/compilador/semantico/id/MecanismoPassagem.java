package br.ufsc.inf.lsi111.compilador.semantico.id;

public enum MecanismoPassagem {

	REFERENCIA(), VALOR();

	public boolean isReferencia() {
		return this.equals(REFERENCIA);
	}

	public boolean isValor() {
		return this.equals(VALOR);
	}

}
