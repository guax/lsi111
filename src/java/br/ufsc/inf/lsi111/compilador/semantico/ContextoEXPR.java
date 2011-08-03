package br.ufsc.inf.lsi111.compilador.semantico;

public enum ContextoEXPR {
	PAR_ATUAL, IMPRESSAO;

	public boolean isParAtual() {
		return this.equals(PAR_ATUAL);
	}

	public boolean isImpressao() {
		return this.equals(IMPRESSAO);
	}
}
