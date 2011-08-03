package br.ufsc.inf.lsi111.compilador.semantico.id;

import br.ufsc.inf.lsi111.compilador.semantico.tipo.Tipo;

public class Funcao extends Procedimento {

	private Tipo tipoRetorno;

	private boolean utilizouRetorno;

	public Funcao(String nome) {
		super(nome);
	}

	public Tipo getTipoRetorno() {
		return tipoRetorno;
	}

	public void setTipoRetorno(Tipo tipoRetorno) {
		this.tipoRetorno = tipoRetorno;
	}

	public boolean isUtilizouRetorno() {
		return utilizouRetorno;
	}

	public void setUtilizouRetorno(boolean utilizouRetorno) {
		this.utilizouRetorno = utilizouRetorno;
	}

	/**
	 * @see Identificador#getCategoria()
	 */
	public Categoria getCategoria() {
		return Categoria.FUNCAO;
	}

}
