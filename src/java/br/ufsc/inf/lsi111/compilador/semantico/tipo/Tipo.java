package br.ufsc.inf.lsi111.compilador.semantico.tipo;

import br.ufsc.inf.lsi111.compilador.semantico.tipo.constants.CategoriaTipo;

public abstract class Tipo {

	/**
	 * Obriga as subclasses implementarem para especificar a categoria a que
	 * pertencem.
	 * 
	 * @return Retorna a categoria na qual o identificador pertence.
	 */
	public abstract CategoriaTipo getCategoria();

	public abstract boolean isCompativelAtribuicao(Tipo tipo);

	public abstract boolean isCompativelOperacao(Tipo tipo);

}
