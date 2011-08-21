package br.ufsc.inf.lsi111.compilador.semantico.tipo;

import br.ufsc.inf.lsi111.compilador.semantico.id.Constante;
import br.ufsc.inf.lsi111.compilador.semantico.tipo.constants.CategoriaTipo;
import br.ufsc.inf.lsi111.compilador.semantico.tipo.constants.CategoriaTipoSimples;

public class Intervalo extends Tipo {

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

    @Override
    public CategoriaTipo getCategoria() {
        return CategoriaTipoSimples.INTERVALO;
    }

    @Override
    public boolean isCompativelAtribuicao(Tipo tipo) {
        return this.limiteInferior.getTipo().isCompativelAtribuicao(tipo);
    }

    @Override
    public boolean isCompativelOperacao(Tipo tipo) {
        return this.limiteInferior.getTipo().isCompativelOperacao(tipo);
    }
}
