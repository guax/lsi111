package br.ufsc.inf.lsi111.compilador.semantico.id;

import br.ufsc.inf.lsi111.compilador.semantico.tipo.Tipo;

public class Variavel extends Identificador {

	private Tipo tipo;

	private boolean variavelControle = false;

	public Variavel(String nome) {
		super(nome);
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public boolean isVariavelControle() {
		return variavelControle;
	}

	public void setVariavelControle(boolean variavelControle) {
		this.variavelControle = variavelControle;
	}

	/**
	 * @see Identificador#getCategoria()
	 */
	public Categoria getCategoria() {
		return Categoria.VARIAVEL;
	}

}
