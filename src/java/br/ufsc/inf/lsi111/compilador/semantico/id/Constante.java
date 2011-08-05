package br.ufsc.inf.lsi111.compilador.semantico.id;

import br.ufsc.inf.lsi111.compilador.semantico.tipo.Tipo;

public class Constante extends Identificador {

    private Tipo tipo;
    private Object valor;

    public Constante(String nome) {
        super(nome);
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public Integer getValorInteiro() throws Exception {
        if (!tipo.getCategoria().isInteger()) {
            throw new Exception(
                    "Contante nao eh inteira para usar getValorInteiro");
        }
        try {
            return Integer.valueOf(valor.toString().trim());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public char getValorChar() throws Exception {
        if (!tipo.getCategoria().isChar()) {
            throw new Exception(
                    "Contante nao eh caracter para usar getValorChar");
        }
        return valor.toString().charAt(0);
    }

    /**
     * @see Identificador#getCategoria()
     */
    public Categoria getCategoria() {
        return Categoria.CONSTANTE;
    }
}
