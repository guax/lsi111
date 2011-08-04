package br.ufsc.inf.lsi111.compilador.semantico.tipo;

import br.ufsc.inf.lsi111.compilador.semantico.tipo.constants.CategoriaTipo;
import br.ufsc.inf.lsi111.compilador.semantico.tipo.constants.CategoriaTipoSimples;

public class Cadeia extends Tipo {

	public static final int TAMANHO_MAXIMO = 255;

	private int tamanho;

	public Cadeia(int tamanho) {
		this.tamanho = tamanho;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	/**
	 * @see Tipo#getCategoria()
	 */
	public CategoriaTipo getCategoria() {
		return CategoriaTipoSimples.CADEIA;
	}

	public boolean isCompativelAtribuicao(Tipo tipo) {
		return tipo.getCategoria().isChar() || tipo.getCategoria().isCadeia();
	}

	public boolean isCompativelOperacao(Tipo tipo) {
		return isCompativelAtribuicao(tipo);
	}

}
