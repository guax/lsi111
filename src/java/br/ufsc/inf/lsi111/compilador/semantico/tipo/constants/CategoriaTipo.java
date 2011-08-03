package br.ufsc.inf.lsi111.compilador.semantico.tipo.constants;

public interface CategoriaTipo {

	public boolean isInteger();

	public boolean isReal();

	public boolean isChar();

	public boolean isBoolean();

	public boolean isCadeia();

	public boolean isVetor();

	public boolean isNumerico();

	public boolean isPreDefinido();

}
