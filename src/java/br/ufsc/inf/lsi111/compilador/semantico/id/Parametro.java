package br.ufsc.inf.lsi111.compilador.semantico.id;

import br.ufsc.inf.lsi111.compilador.semantico.tipo.Tipo;

public class Parametro extends Identificador {
    
	private MecanismoPassagem mecanismoPassagem;

	private Tipo tipo;

	private boolean variavelControle = false;

	public Parametro(String nome) {
		super(nome);
	}

	public MecanismoPassagem getMecanismoPassagem() {
		return mecanismoPassagem;
	}

	public void setMecanismoPassagem(MecanismoPassagem mecanismoPassagem) {
		this.mecanismoPassagem = mecanismoPassagem;
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
		return Categoria.PARAMETRO;
	}

}
