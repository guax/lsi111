package br.ufsc.inf.lsi111.compilador.semantico.id;

import java.util.ArrayList;
import java.util.List;

public class Procedimento extends Identificador {

	private Integer enderecoPrimeiraInstrucao;

	private List<Parametro> listaParametros = new ArrayList<Parametro>();

	public Procedimento(String nome) {
		super(nome);
	}

	public Integer getEnderecoPrimeiraInstrucao() {
		return enderecoPrimeiraInstrucao;
	}

	public void setEnderecoPrimeiraInstrucao(Integer enderecoPrimeiraInstrucao) {
		this.enderecoPrimeiraInstrucao = enderecoPrimeiraInstrucao;
	}

	public List<Parametro> getListaParametros() {
		return listaParametros;
	}

	public void setListaParametros(List<Parametro> listaParametros) {
		this.listaParametros = listaParametros;
	}

	/**
	 * @see Identificador#getCategoria()
	 */
	public Categoria getCategoria() {
		return Categoria.PROCEDIMENTO;
	}

}
