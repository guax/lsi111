package br.ufsc.inf.lsi111.compilador.semantico.tipo.constants;

public enum CategoriaTipoEstruturado implements CategoriaTipo {

	// Tipo Estruturado
	VETOR;

	public boolean isVetor() {
		return equals(VETOR);
	}

	public boolean isBoolean() {
		return false;
	}

	public boolean isChar() {
		return false;
	}

	public boolean isInteger() {
		return false;
	}

	public boolean isCadeia() {
		return false;
	}

	public boolean isReal() {
		return false;
	}

	public boolean isNumerico() {
		return false;
	}

	public boolean isPreDefinido() {
		return false;
	}
}
