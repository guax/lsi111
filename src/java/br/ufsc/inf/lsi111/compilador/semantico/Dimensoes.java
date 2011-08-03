package br.ufsc.inf.lsi111.compilador.semantico;

import br.ufsc.inf.lsi111.compilador.semantico.id.Constante;

public class Dimensoes {

	private Constante limiteInferior;
	private Constante limiteSuperior;

	public Constante getLimiteInferior() {
		return limiteInferior;
	}

	public void setLimiteInferior(Constante limiteInferior) {
		this.limiteInferior = limiteInferior;
	}

	public Constante getLimiteSuperior() {
		return limiteSuperior;
	}

	public void setLimiteSuperior(Constante limiteSuperior) {
		this.limiteSuperior = limiteSuperior;
	}

}
