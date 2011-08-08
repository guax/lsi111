package br.ufsc.inf.lsi111.compilador.semantico;

public enum OperadorRelacional {

	NENHUM(""), MULTIPLICACAO("*"), DIVISAO("/"), ADICAO("+"), SUBTRACAO(
			"-"), OU("ou"), E("e"),

	IGUAL("="), MENOR("<"), MAIOR(">"), MAIOR_IGUAL(">="), MENOR_IGUAL("<="), DIFERENTE(
			"<>");

	private String nome;

	private OperadorRelacional(String nome) {
		this.nome = nome;
	}

	public boolean isMultiplicacao() {
		return equals(MULTIPLICACAO);
	}

	public boolean isDivisao() {
		return equals(DIVISAO);
	}

	public boolean isE() {
		return equals(E);
	}

	public boolean isAdicao() {
		return equals(ADICAO);
	}

	public boolean isSubtracao() {
		return equals(SUBTRACAO);
	}

	public boolean isOu() {
		return equals(OU);
	}

	public static OperadorRelacional getTipoPorNome(String nome) {
		OperadorRelacional[] values = OperadorRelacional.values();
		for (OperadorRelacional tipoPreDefinido : values) {
			if (tipoPreDefinido.getNome().equals(nome)) {
				return tipoPreDefinido;
			}
		}
		return null;
	}

	public String getNome() {
		return nome;
	}

	@Override
	public String toString() {
		return getNome();
	}
}
