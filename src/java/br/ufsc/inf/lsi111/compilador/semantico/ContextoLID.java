package br.ufsc.inf.lsi111.compilador.semantico;

public enum ContextoLID {
	PAR_FORMAL, DECLARACAO, LEITURA;

	public boolean isParFormal() {
		return this.equals(PAR_FORMAL);
	}

	public boolean isDeclaracao() {
		return this.equals(DECLARACAO);
	}

	public boolean isLeitura() {
		return this.equals(LEITURA);
	}
}
