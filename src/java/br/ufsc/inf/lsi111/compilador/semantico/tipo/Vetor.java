package br.ufsc.inf.lsi111.compilador.semantico.tipo;

import br.ufsc.inf.lsi111.compilador.semantico.id.Constante;
import br.ufsc.inf.lsi111.compilador.semantico.tipo.constants.CategoriaTipo;
import br.ufsc.inf.lsi111.compilador.semantico.tipo.constants.CategoriaTipoEstruturado;

public class Vetor extends Tipo {

    private PreDefinido tipo;
    private Constante tamanho;

    public PreDefinido getTipo() {
        return tipo;
    }

    public void setTipo(PreDefinido tipo) {
        this.tipo = tipo;
    }

    public Constante getTamanho() {
        return tamanho;
    }

    public void setTamanho(Constante tamanho) {
        this.tamanho = tamanho;
    }

    public CategoriaTipo getCategoria() {
        return CategoriaTipoEstruturado.VETOR;
    }

    public boolean isCompativelAtribuicao(Tipo tipo) {
        return getCategoria().equals(tipo.getCategoria());
    }

    public boolean isCompativelOperacao(Tipo tipo) {
        return tipo.getCategoria().equals(getCategoria())
                || getCategoria().equals(tipo.getCategoria());
    }
}
