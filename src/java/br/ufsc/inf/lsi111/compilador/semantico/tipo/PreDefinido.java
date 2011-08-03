package br.ufsc.inf.lsi111.compilador.semantico.tipo;

import br.ufsc.inf.lsi111.compilador.semantico.tipo.constants.CategoriaTipo;
import br.ufsc.inf.lsi111.compilador.semantico.tipo.constants.CategoriaTipoSimples;

public class PreDefinido extends Tipo {

	private CategoriaTipoSimples categoria;

	public PreDefinido(CategoriaTipoSimples categoria) {
		if (categoria.isCadeia()) {
			// TODO Verificar se deve lancar erro.
		}
		this.categoria = categoria;
	}

	/**
	 * @see Tipo#getCategoria()
	 */
	public CategoriaTipo getCategoria() {
		return categoria;
	}

	public boolean isCompativelAtribuicao(Tipo tipo) {
		boolean ehNumerico = categoria.isReal()
				&& tipo.getCategoria().isInteger();

		boolean ehCaracter = categoria.isCadeia()
				&& tipo.getCategoria().isChar();

		boolean ehMesmoTipo = getCategoria().equals(tipo.getCategoria());

		return ehNumerico || ehCaracter || ehMesmoTipo;
	}

	public boolean isCompativelOperacao(Tipo tipo) {
		boolean mesmoTipoPredefinido = false;

		if (tipo.getCategoria().isPreDefinido()) {
			mesmoTipoPredefinido = getCategoria().equals(tipo.getCategoria());
		}

		return mesmoTipoPredefinido
				|| (getCategoria().isNumerico() && tipo.getCategoria()
						.isNumerico())
				|| (getCategoria().isChar() && tipo.getCategoria().isCadeia() || (getCategoria()
						.isCadeia() && tipo.getCategoria().isChar()));

	}
}
