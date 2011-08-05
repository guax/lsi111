package br.ufsc.inf.lsi111.compilador.semantico;

import java.util.Stack;

import br.ufsc.inf.lsi111.compilador.semantico.id.Constante;
import br.ufsc.inf.lsi111.compilador.semantico.id.Identificador;
import br.ufsc.inf.lsi111.compilador.semantico.id.Procedimento;
import br.ufsc.inf.lsi111.compilador.semantico.tipo.Tipo;
import br.ufsc.inf.lsi111.compilador.semantico.id.MecanismoPassagem;

public class VariaveisDoContexto {

    /**
     * Armazena a pilha de identificadores declarados.
     */
    private Stack<String> identificador = new Stack<String>();

    public String popIdentificador() {
        return identificador.pop();
    }

    public String peekIdentificador() {
        return identificador.peek();
    }

    public void pushIdentificador(String identificador) {
        this.identificador.push(identificador);
    }
    /**
     * Armazena a pilha de procedimentos declarados.
     */
    private Stack<Procedimento> procedimento = new Stack<Procedimento>();

    public Procedimento popProcedimento() {
        return procedimento.pop();
    }

    public void pushProcedimento(Procedimento metodoAtual) {
        procedimento.push(metodoAtual);
    }

    public Procedimento peekProcedimento() {
        return procedimento.peek();
    }
    /**
     * Armazena a pilha de identificadores declarados e utilizados como
     * variaveis de controle de uma estrutura para/faca.
     */
    private Stack<Identificador> variavelControle = new Stack<Identificador>();

    public Identificador popVariavelControle() {
        return variavelControle.pop();
    }

    public Identificador peekVariavelControle() {
        return variavelControle.peek();
    }

    public void pushVariavelControle(Identificador identificador) {
        variavelControle.push(identificador);
    }
    /**
     * Armazena as informacoes da constante que esta sendo analisada no momento.
     */
    private Constante tipoConstante;

    public Constante getTipoConstante() {
        return tipoConstante;
    }

    public void setTipoConstante(Constante tipoConstante) {
        this.tipoConstante = tipoConstante;
    }
    /**
     * TODO COMENTAR
     */
    private ContextoLID contextoLID;

    public ContextoLID getContextoLID() {
        return contextoLID;
    }

    public void setContextoLID(ContextoLID contextoLID) {
        this.contextoLID = contextoLID;
    }
    /**
     * TODO COMENTAR
     */
    private Stack<ContextoEXPR> contextoEXPR = new Stack<ContextoEXPR>();

    public ContextoEXPR popContextoEXPR() {
        return contextoEXPR.pop();
    }

    public void pushContextoEXPR(ContextoEXPR contextoEXPR) {
        this.contextoEXPR.push(contextoEXPR);
    }

    public ContextoEXPR peekContextoEXPR() {
        return contextoEXPR.peek();
    }
    /**
     * TODO COMENTAR
     */
    private int posInicial = 0;
    private int posFinal = 0;
    private int numParametros = 0;

    public int getPosInicial() {
        return posInicial;
    }

    public void setPosInicial(int posInicial) {
        this.posInicial = posInicial;
    }

    public int getPosFinal() {
        return posFinal;
    }

    public void setPosFinal(int posFinal) {
        this.posFinal = posFinal;
    }

    public int getNrParametros() {
        return numParametros;
    }

    public void setNrParametros(int nrParametros) {
        numParametros = nrParametros;
    }
    /**
     * Armazena as informacoes do tipo da variavel ou parametro analisado no
     * momento.
     */
    private Tipo tipoAtual;

    public Tipo getTipoAtual() {
        return tipoAtual;
    }

    public void setTipoAtual(Tipo tipoAtual) {
        this.tipoAtual = tipoAtual;
    }
    /**
     * Armazena as informacoes do tipo do lado esquerdo de um comando atribuicao
     * analisado no momento.
     */
    private Tipo tipoLadoEsquerdo;

    public Tipo getTipoLadoEsquerdo() {
        return tipoLadoEsquerdo;
    }

    public void setTipoLadoEsquerdo(Tipo tipoLadoEsquerdo) {
        this.tipoLadoEsquerdo = tipoLadoEsquerdo;
    }
    /**
     * TODO
     */
    private Tipo tipoFator;

    public Tipo getTipoFator() {
        return tipoFator;
    }

    public void setTipoFator(Tipo tipoFator) {
        this.tipoFator = tipoFator;
    }
    /**
     * Armazena as informacoes do tipo atual da expressao analisada.
     */
    private Tipo tipoExpressao;

    public Tipo getTipoExpressao() {
        return tipoExpressao;
    }

    public void setTipoExpressao(Tipo tipoExpressao) {
        this.tipoExpressao = tipoExpressao;
    }
    /**
     * TODO
     */
    private Tipo tipoVar;

    public Tipo getTipoVar() {
        return tipoVar;
    }

    public void setTipoVar(Tipo tipoVar) {
        this.tipoVar = tipoVar;
    }
    /**
     * TODO
     */
    private Stack<Tipo> tipoTermo = new Stack<Tipo>();

    public Tipo popTipoTermo() {
        return tipoTermo.pop();
    }

    public void pushTipoTermo(Tipo tipo) {
        tipoTermo.push(tipo);
    }

    public Tipo peekTipoTermo() {
        return tipoTermo.peek();
    }
    /**
     * TODO
     */
    private Tipo tipoExpSimples;

    public Tipo getTipoExpSimples() {
        return tipoExpSimples;
    }

    public void setTipoExpSimples(Tipo tipoExpSimples) {
        this.tipoExpSimples = tipoExpSimples;
    }
    /**
     * TODO
     */
    private Stack<Tipo> tipoVarIndexada = new Stack<Tipo>();

    public Tipo popTipoVarIndexada() {
        return tipoVarIndexada.pop();
    }

    public Tipo peekTipoVarIndexada() {
        return tipoVarIndexada.peek();
    }

    public void pushTipoVarIndexada(Tipo tipo) {
        tipoVarIndexada.push(tipo);
    }
    /**
     * Armazena informação de limite inferior para uso de declaração de
     * intervalo e tamanho de vetor.
     */
    private Constante limite;

    public Constante getLimite() {
        return limite;
    }

    public void setLimite(Constante limiteInferior) {
        this.limite = limiteInferior;
    }

    /**
     * TODO
     */
    private int nrIndices = 0;

    public int getNrIndices() {
        return nrIndices;
    }

    public void setNrIndices(int nrIndices) {
        this.nrIndices = nrIndices;
    }
    /**
     * Utilizado para armazenar o mecanismo de passagem.
     */
    private MecanismoPassagem mecanismoPassagem;

    public MecanismoPassagem getMecanismoPassagem() {
        return mecanismoPassagem;
    }

    public void setMecanismoPassagem(MecanismoPassagem mecanismoPassagem) {
        this.mecanismoPassagem = mecanismoPassagem;
    }
    /**
     * TODO
     */
    private boolean operadorNegacao;

    public boolean isOperadorNegacao() {
        return operadorNegacao;
    }

    public void setOperadorNegacao(boolean operadorNegacao) {
        this.operadorNegacao = operadorNegacao;
    }
    /**
     * TODO
     */
    private boolean operadorUnario;

    public boolean isOperadorUnario() {
        return operadorUnario;
    }

    public void setOperadorUnario(boolean operadorUnario) {
        this.operadorUnario = operadorUnario;
    }
    /**
     * TODO
     */
    private OperadorRelacional operadorRelacional;

    public OperadorRelacional getOperadorRelacional() {
        return operadorRelacional;
    }

    public void setOperadorRelacional(OperadorRelacional operadorRelacional) {
        this.operadorRelacional = operadorRelacional;
    }
    private boolean expressaoComposta;

    public boolean isExpressaoComposta() {
        return expressaoComposta;
    }

    public void setExpressaoComposta(boolean expressaoComposta) {
        this.expressaoComposta = expressaoComposta;
    }
}
