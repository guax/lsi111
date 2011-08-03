package br.ufsc.inf.lsi111.compilador.semantico.id;

public abstract class Identificador implements Comparable<Identificador> {

	protected String nome;

	private Integer nivel;

	private Integer deslocamento;

	public Identificador(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public Integer getDeslocamento() {
		return deslocamento;
	}

	public void setDeslocamento(Integer deslocamento) {
		this.deslocamento = deslocamento;
	}

	/**
	 * Obriga as subclasses implementarem para especificar a categoria a que
	 * pertencem.
	 * 
	 * @return Retorna a categoria na qual o identificador pertence.
	 */
	public abstract Categoria getCategoria();

	public boolean equals(Object obj) {
		return nome.equals(obj);
	}

	public int hashCode() {
		return nome.hashCode();
	}

	public String toString() {
		return nome;
	}

	public int compareTo(Identificador o) {
		return deslocamento.compareTo(o.getDeslocamento());
	}

}
