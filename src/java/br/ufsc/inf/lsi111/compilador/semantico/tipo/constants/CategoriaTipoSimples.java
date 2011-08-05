package br.ufsc.inf.lsi111.compilador.semantico.tipo.constants;

public enum CategoriaTipoSimples implements CategoriaTipo {

	// Tipo simples pré-definido:
	INTEGER, REAL, CHAR, BOOLEAN,

	// Tipo simples definido pelo usuário:
	CADEIA, INTERVALO;

	public boolean isInteger() {
		return equals(INTEGER);
	}

	public boolean isReal() {
		return equals(REAL);
	}

	public boolean isChar() {
		return equals(CHAR);
	}

	public boolean isBoolean() {
		return equals(BOOLEAN);
	}

	public boolean isCadeia() {
		return equals(CADEIA);
	}

        public boolean isIntervalo() {
		return equals(INTERVALO);
	}

	public boolean isVetor() {
		return false;
	}

	public boolean isNumerico() {
		return isInteger() || isReal();
	}

	public boolean isPreDefinido() {
		return true;
	}

}
