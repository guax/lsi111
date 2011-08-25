package br.ufsc.inf.lsi111.compilador.gals;

import br.ufsc.inf.lsi111.compilador.TabelaDeSimbolos;
import br.ufsc.inf.lsi111.compilador.semantico.ContextoEXPR;
import br.ufsc.inf.lsi111.compilador.semantico.ContextoLID;
import br.ufsc.inf.lsi111.compilador.semantico.OperadorRelacional;
import br.ufsc.inf.lsi111.compilador.semantico.VariaveisDoContexto;
import br.ufsc.inf.lsi111.compilador.semantico.id.Constante;
import br.ufsc.inf.lsi111.compilador.semantico.id.Funcao;
import br.ufsc.inf.lsi111.compilador.semantico.id.Identificador;
import br.ufsc.inf.lsi111.compilador.semantico.id.MecanismoPassagem;
import br.ufsc.inf.lsi111.compilador.semantico.id.Parametro;
import br.ufsc.inf.lsi111.compilador.semantico.id.Procedimento;
import br.ufsc.inf.lsi111.compilador.semantico.id.Variavel;
import br.ufsc.inf.lsi111.compilador.semantico.tipo.Cadeia;
import br.ufsc.inf.lsi111.compilador.semantico.tipo.Intervalo;
import br.ufsc.inf.lsi111.compilador.semantico.tipo.PreDefinido;
import br.ufsc.inf.lsi111.compilador.semantico.tipo.Tipo;
import br.ufsc.inf.lsi111.compilador.semantico.tipo.Vetor;
import br.ufsc.inf.lsi111.compilador.semantico.tipo.constants.CategoriaTipoSimples;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

public class Semantico implements Constants {

    TabelaDeSimbolos tabelaDeSimbolos;
    VariaveisDoContexto variaveisDoContexto;

    public void executeAction(int action, Token token) throws SemanticError {
        //System.out.printf("Acao #%d, Token: %s\n", action, token); //msg chata.
        try {
            this.getClass().getMethod("action" + action, Token.class).invoke(this, token);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            SemanticError semanticError;
            if (targetException instanceof SemanticError) {
                SemanticError s = (SemanticError) targetException;
                semanticError = new SemanticError(String.format("#%d - %s", action, s.getMessage()), token.getPosition());
            } else {
                semanticError = new SemanticError(
                        "Erro desconhecido ao executar acao semantica #" + action + ": " + targetException);
            }
            throw semanticError;
        } catch (Exception e) {
            System.err.printf("Warning: Acao semantica #%d nao implementada\n", action);
            //throw new SemanticError("Acao semantica #" + action + " nao implementada");
        }
    }

    /**
     * <programa> ::=   #100  <declaracoes>  <comandos>  "." ; 
     *
     * #100 Efetua INICIALIZAÇÃO das variáveis de contexto:
     * NA (Nível Atual):= 0; DESLOC (deslocamento):=0;
     *
     * @param token
     */
    public void action100(Token token) {
        variaveisDoContexto = new VariaveisDoContexto();
        tabelaDeSimbolos = new TabelaDeSimbolos();
    }

    /**
     * <dcl_const> ::= const <tipo_pre_def> id #101 "=" <constante> #102 ";" <dcl_const>;
     *
     * #101  – Se id já está declarado no NA então ERRO(“Id já declarado”) senão
     * insere id na TS, junto com seus atributos (categoria = constante e
     * nível = NA)
     *
     * @param token
     * @throws SemanticError
     */
    public void action101(Token token) throws SemanticError {
        String lexeme = token.getLexeme();
        Identificador simbolo = tabelaDeSimbolos.getSimboloDoNivel(lexeme);
        if (simbolo != null) {
            throw new SemanticError("Identificador '" + lexeme
                    + "' ja foi declarado neste escopo.");
        } else {
            Constante constante = new Constante(lexeme);
            constante.setNivel(tabelaDeSimbolos.getNivel());
            tabelaDeSimbolos.addSimbolo(constante);
            // Adiciona o identificador na pilha de identificadores declarados
            // nas variaveis de contexto.
            variaveisDoContexto.pushIdentificador(lexeme);
        }
    }

    /**
     * <dcl_const> ::= const <tipo_pre_def> id #101 "=" <constante> #102 ";" <dcl_const>;
     *
     * #102 – se Tipo-Const = TipoAtual então Insere atrib. Tipo-Const e
     * Val-Const na TS senão ERRO (“Tipo de constante inválido”)
     *
     * @param token
     * @throws SemanticError
     */
    public void action102(Token token) throws SemanticError {
        // Pega o primeiro identificador da pilha de identificadores declarados
        // nas variaveis de contexto.
        String nomeIdentificador = variaveisDoContexto.popIdentificador();

        // Recupera a constante da tabela de simbolos atraves do nome do
        // identificador que estava na pilha.
        Constante simboloConstante = (Constante) tabelaDeSimbolos.getSimboloDoNivel(nomeIdentificador);

        // Recupera a constante (contendo tipo e valor) que estava no contexto.
        Constante constanteDoContexto = variaveisDoContexto.getTipoConstante();

        if (constanteDoContexto.getTipo().getCategoria() == variaveisDoContexto.getTipoAtual().getCategoria()) {
            // Seta na constante da tabela de simbolos o valor e tipo declarados.
            simboloConstante.setTipo(constanteDoContexto.getTipo());
            simboloConstante.setValor(constanteDoContexto.getValor());
        } else {
            throw new SemanticError("Tipo de constante invalido.");
        }
    }

    /**
     * <dcl_var> ::= var #103 <lid> #104 ":" <tipo> #105 ";" <dcl_var>;
     *
     * #103 – contextoLID := “decl” salva pos.na TS do primeiro id da lista.
     *
     * @param token
     */
    public void action103(Token token) {
        variaveisDoContexto.setContextoLID(ContextoLID.DECLARACAO);
        variaveisDoContexto.setPosInicial(tabelaDeSimbolos.getDeslocamento());
    }

    /**
     * <dcl_var> ::= var #103 <lid> #104 ":" <tipo> #105 ";" <dcl_var>;
     *
     * #104 – salva pos.na TS do último id da lista.
     *
     * @param token
     */
    public void action104(Token token) {
        variaveisDoContexto.setPosFinal(tabelaDeSimbolos.getDeslocamento());
    }

    /**
     * <dcl_var> ::= var #103 <lid> #104 ":" <tipo> #105 ";" <dcl_var>;
     *
     * #105 - Preenche atributos na TS dos id’s da lista, considerando categoria
     * “variável” e TipoAtual
     *
     * @param token
     */
    public void action105(Token token) {
        // Recupera a posicao (deslocamento no nivel) do primeiro id da lista de
        // identificadores de variaveis ou parametros
        int posInicial = variaveisDoContexto.getPosInicial();
        // Recupera a posicao (deslocamento no nivel) do ultimo id da lista de
        // identificadores de variaveis ou parametros
        int posFinal = variaveisDoContexto.getPosFinal();

        List<Variavel> listaVar = tabelaDeSimbolos.getVariaveisDaPosicao(
                posInicial, posFinal);

        for (Variavel var : listaVar) {
            var.setTipo(variaveisDoContexto.getTipoAtual());
        }
    }

    /**
     * <dcl_proc> ::= proc id #106 <parametros> #107 ";" <declaracoes> <comandos> #108
     *
     * #106 – Se id do procedimento já está declarado no NA,
     * então ERRO(“Id já declarado”)
     * senão insere id na TS, junto com seus atributos
     * zera número de parâmetros Formais (NPF)
     * incrementa nível atual (NA := NA + 1)
     *
     * @param token
     * @throws SemanticError
     */
    public void action106(Token token) throws SemanticError {
        Identificador simbolo = tabelaDeSimbolos.getSimboloDoNivel(token.getLexeme());

        if (simbolo != null) {
            throw new SemanticError("Identificador '" + token.getLexeme()
                    + "' ja foi declarado como '" + simbolo.getCategoria()
                    + "'.");
        }

        Procedimento procedimento = new Procedimento(token.getLexeme());
        procedimento.setNivel(tabelaDeSimbolos.getNivel());
        tabelaDeSimbolos.addSimbolo(procedimento);

        tabelaDeSimbolos.incrementaNivel();

        variaveisDoContexto.setPosInicial(0);
        variaveisDoContexto.setPosFinal(0);

        variaveisDoContexto.setContextoLID(ContextoLID.PAR_FORMAL);
        variaveisDoContexto.pushProcedimento(procedimento);
    }

    /**
     *<dcl_proc> ::= proc id #106 <parametros> #107 ";" <declaracoes> <comandos> #108
     *
     * #107 – Atualiza num. de par. Formais (NPF) na TS
     *
     * @param token
     * @throws SemanticError
     */
    public void action107(Token token) {
        // A classe procedimento ja armezena uma lista de parametros e assim o
        // devido numero de parametros.
    }

    /**
     * <dcl_proc> ::= proc  id #106 <parametros> #107 ";" <declaracoes> <comandos> #108;
     * <dcl_proc> ::= funcao id #109 <parametros> #107 ":" <tipo_pre_def> #110 ";" <declaracoes> <comandos> #108;
     *
     * #108 – Retira da TS as variáveis declaradas localmenteAtualiza nível
     * atual ( NA := NA – 1 )
     *
     * @param token
     * @throws SemanticError
     */
    public void action108(Token token) throws SemanticError {
        tabelaDeSimbolos.limpaNivelAtual();

        tabelaDeSimbolos.decrementaNivel();
        variaveisDoContexto.setPosInicial(0);
        variaveisDoContexto.setPosFinal(0);

        Procedimento procedimento = variaveisDoContexto.popProcedimento();

        if (procedimento instanceof Funcao) {
            Funcao funcao = (Funcao) procedimento;

            if (!funcao.isUtilizouRetorno()) {
                throw new SemanticError("Funcao '" + funcao.getNome()
                        + "' possui tipo e deve retornar expressao do tipo "
                        + funcao.getTipoRetorno().getCategoria());
            }
            variaveisDoContexto.popFuncaoEscopo();
        }

    }

    /**
     * <dcl_proc> ::= funcao id #109 <parametros> #107 ":" <tipo_pre_def> #110 ";" <declaracoes> <comandos> #108;
     *
     * #109 – idem a ação #106, para função.
     *
     * @param token
     * @throws SemanticError
     */
    public void action109(Token token) throws SemanticError {
        Identificador simbolo = tabelaDeSimbolos.getSimboloDoNivel(token.getLexeme());

        if (simbolo != null) {
            throw new SemanticError("Identificador '" + token.getLexeme()
                    + "' ja foi declarado como '" + simbolo.getCategoria()
                    + "'.");
        }
        Funcao funcao = new Funcao(token.getLexeme());
        funcao.setNivel(tabelaDeSimbolos.getNivel());
        tabelaDeSimbolos.addSimbolo(funcao);

        tabelaDeSimbolos.incrementaNivel();

        variaveisDoContexto.setPosInicial(0);
        variaveisDoContexto.setPosFinal(0);

        variaveisDoContexto.setContextoLID(ContextoLID.PAR_FORMAL);
        variaveisDoContexto.pushProcedimento(funcao);

        variaveisDoContexto.pushFuncaoEscopo(token.getLexeme());
    }

    /**
     * <dcl_proc> ::= funcao id #109 <parametros> #107 ":" <tipo_pre_def> #110 ";" <declaracoes> <comandos> #108;
     *
     * #111 - contextoLID := “par-formal”
     * MPP := “valor”
     * salva pos.na TS do primeiro id da lista
     *
     * #110 – Atualiza tipo do resultado da função na TS
     *
     * @param token
     */
    public void action110(Token token) {
        Funcao funcaoAtual = (Funcao) variaveisDoContexto.peekProcedimento();
        funcaoAtual.setTipoRetorno(variaveisDoContexto.getTipoAtual());
    }

    /**
     * <listapar> ::= ref #111 <lid> #112 ":" <tipo_pre_def> #113 <rep_listapar>;
     *
     * #111 - contextoLID := “par-formal”
     * MPP := “referência”
     * salva pos.na TS do primeiro id da lista
     *
     * @param token
     */
    public void action111(Token token) {
        variaveisDoContexto.setMecanismoPassagem(MecanismoPassagem.REFERENCIA);
        variaveisDoContexto.setContextoLID(ContextoLID.PAR_FORMAL);
        variaveisDoContexto.setPosInicial(tabelaDeSimbolos.getDeslocamento());
    }

    /**
     * <listapar> ::= ref #111 <lid> #112 ":" <tipo_pre_def> #113 <rep_listapar>;
     * <listapar> ::= val #114 <lid> #112 ":" <tipo_pre_def> #113 <rep_listapar>;
     *
     * #112 – salva pos.na TS do último id da lista
     *
     * @param token
     */
    public void action112(Token token) {
        variaveisDoContexto.setPosFinal(tabelaDeSimbolos.getDeslocamento());
    }

    /**
     * <listapar> ::= ref #111 <lid> #112 ":" <tipo_pre_def> #113 <rep_listapar>;
     * <listapar> ::= val #114 <lid> #112 ":" <tipo_pre_def> #113 <rep_listapar>;
     *
     * #113 – Preenche atributos dos id’s da lista de 
     * parâmetros, considerando categoria = “Parâmetro”, TipoAtual e MPP.
     * Insere todos os parâmetros em uma lista auxiliar 
     * ( ListaPar), a ser usada na chamada de proc.
     *
     * @param token
     */
    public void action113(Token token) {
        List<Parametro> parametrosProcedimento = variaveisDoContexto.peekProcedimento().getListaParametros();

        int posInicial = variaveisDoContexto.getPosInicial();
        int posFinal = variaveisDoContexto.getPosFinal();

        List<Parametro> paramDeclarados = tabelaDeSimbolos.getParametrosDaPosicao(posInicial, posFinal);

        for (Parametro parametro : paramDeclarados) {
            parametro.setTipo(variaveisDoContexto.getTipoAtual());
            parametro.setMecanismoPassagem(variaveisDoContexto.getMecanismoPassagem());
            parametrosProcedimento.add(parametro);
        }

    }

    /**
     * <listapar> ::= val #111 <lid> #112 ":" <tipo_pre_def> #113 <rep_listapar>;
     *
     * @param token
     */
    public void action114(Token token) {
        variaveisDoContexto.setMecanismoPassagem(MecanismoPassagem.VALOR);
        variaveisDoContexto.setContextoLID(ContextoLID.PAR_FORMAL);
        variaveisDoContexto.setPosInicial(tabelaDeSimbolos.getDeslocamento());
    }

    /**
     * <lid>    ::= id #115 <rep_id>;
     * <rep_id> ::= "," id #115 <rep_id>;
     *
     * #115 – Trata “id” de acordo com contextoLID (decl, par-formal ou leitura)
     *
     * @TODO tratamento de par-forma e leitura.
     *
     * @param token
     */
    public void action115(Token token) throws SemanticError {
        String lexeme = token.getLexeme();

        switch (variaveisDoContexto.getContextoLID()) {
            case DECLARACAO: {
                Identificador simbolo = tabelaDeSimbolos.getSimboloDoNivel(lexeme);

                if (simbolo != null) {
                    throw new SemanticError("Identificador '" + lexeme
                            + "' ja foi declarado.");
                } else {
                    Variavel variavel = new Variavel(lexeme);
                    variavel.setNivel(tabelaDeSimbolos.getNivel());
                    variavel.setDeslocamento(tabelaDeSimbolos.getDeslocamento());
                    tabelaDeSimbolos.addSimbolo(variavel);
                    tabelaDeSimbolos.incrementaDeslocamento();
                }
                break;
            }
            case PAR_FORMAL: {
                Identificador simbolo = tabelaDeSimbolos.getSimboloDoNivel(lexeme);
                if (simbolo != null) {
                    throw new SemanticError("Identificador do parametro '" + lexeme
                            + "' repetido.");
                } else {
                    Parametro parametro = new Parametro(lexeme);
                    parametro.setNivel(tabelaDeSimbolos.getNivel());
                    parametro.setDeslocamento(tabelaDeSimbolos.getDeslocamento());
                    tabelaDeSimbolos.addSimbolo(parametro);
                    tabelaDeSimbolos.incrementaDeslocamento();
                }
                break;
            }
            case LEITURA: {
                Identificador simbolo = tabelaDeSimbolos.getSimbolo(lexeme);
                if (simbolo == null) {
                    throw new SemanticError("Identificador '" + lexeme
                            + "' nao foi declarado");
                }

                if (simbolo instanceof Variavel) {
                    Variavel var = (Variavel) simbolo;
                    if (!(var.getTipo() instanceof PreDefinido)) {
                        throw new SemanticError("O tipo '"
                                + var.getTipo().getCategoria() + "' de "
                                + simbolo.getNome() + " eh invalido para leitura.");
                    }
                    return;
                }
                if (simbolo instanceof Parametro) {
                    Parametro param = (Parametro) simbolo;
                    if (!(param.getTipo() instanceof PreDefinido)) {
                        throw new SemanticError("O tipo '"
                                + param.getTipo().getCategoria() + "' de "
                                + simbolo.getNome() + " eh invalido para leitura.");
                    }
                    return;
                }
                throw new SemanticError("O identificador '" + simbolo.getNome()
                        + "' deve ser uma variavel ou parametro e nao "
                        + simbolo.getCategoria());
            }
        }
    }

    /**
     * <constante> ::= id  #116;
     *
     * #116- Se id não está declarado então ERRO(“Id não declarado”) senão se
     * categoria de id <> constante entao ERRO (“Esperava-se um id de
     * Constante”) senão TipoConst := Tipo do id-constante ValConst := Valor da
     * constante id
     *
     * @param token
     * @throws SemanticError
     */
    public void action116(Token token) throws SemanticError {
        String lexeme = token.getLexeme();
        Identificador id = tabelaDeSimbolos.getSimbolo(lexeme);

        if (id == null) {
            throw new SemanticError("Id '" + lexeme + "' não declarado.");
        } else if (!(id instanceof Constante)) {
            throw new SemanticError("'" + lexeme + "' deve ser uma constante.");
        } else {
            Constante constante = (Constante) id;
            // Ignorando tipo declarado e usando tipo da constante do id.
            this.variaveisDoContexto.setTipoAtual(constante.getTipo());
            this.definirConstanteDoContexto(constante.getTipo(), (String) constante.getValor());
        }
    }

    /**
     * <tipo> ::= intervalo <constante> #117 ".." <constante> #118;
     *
     * #117  – Se tipo da constante é válido para intervalo
     * Então Guarda tipo e valor do Limite Inferior
     * Senão ERRO (“Const deveria ser int ou char”)
     *
     * @param token
     * @throws SemanticError
     */
    public void action117(Token token) throws SemanticError {
        Constante constanteDoContexto = variaveisDoContexto.getTipoConstante();

        if (!constanteDoContexto.getTipo().getCategoria().isInteger()
                && !constanteDoContexto.getTipo().getCategoria().isChar()) {
            throw new SemanticError(
                    "Esperava-se uma constante inteira ou caracter e nao "
                    + constanteDoContexto.getTipo().getCategoria());
        }

        variaveisDoContexto.setLimite(constanteDoContexto);
    }

    /**
     * <tipo> ::= intervalo <constante> #117 ".." <constante> #118;
     *
     * #118  – Se intervalo é válido
     * Então TipoAtual := “intervalo”
     * Senão ERRO (“Intervalo inválido”)
     *
     * @param token
     * @throws Exception
     * @throws SemanticError
     */
    public void action118(Token token) throws SemanticError, Exception {
        Constante constanteDoContexto = variaveisDoContexto.getTipoConstante();

        Constante limiteInferior = variaveisDoContexto.getLimite();

        if (!constanteDoContexto.getTipo().getCategoria().equals(
                limiteInferior.getTipo().getCategoria())) {
            throw new SemanticError(
                    "Constantes do intervalo devem ser do mesmo tipo.");
        }

        if (constanteDoContexto.getTipo().getCategoria().isInteger()) {
            if (constanteDoContexto.getValorInteiro() <= limiteInferior.getValorInteiro()) {
                throw new SemanticError(
                        "Limite superior deve ser > que limite inferior.");
            }
        }
        if (constanteDoContexto.getTipo().getCategoria().isChar()) {
            if (constanteDoContexto.getValorChar() <= limiteInferior.getValorChar()) {
                throw new SemanticError(
                        "Limite superior deve ser > que limite inferior.");
            }
        }

        Intervalo intervalo = new Intervalo();
        intervalo.setLimiteInferior(limiteInferior);
        intervalo.setLimiteSuperior(constanteDoContexto);
        variaveisDoContexto.setTipoAtual(intervalo);
    }

    /**
     * <tipo> ::= cadeia "[" <constante> #119 "]";
     *
     * #119 – Se TipoConst <> “inteiro”
     *     então ERRO(“esperava-se uma constante inteira”)
     * senão se ValConst > 255
     *     então ERRO(“tam.da cadeia > que o permitido”)
     * senão TipoAtual := “cadeia”
     *
     * @param token
     * @throws SemanticError
     * @throws Exception
     */
    public void action119(Token token) throws SemanticError, Exception {
        Constante constanteDoContexto = variaveisDoContexto.getTipoConstante();

        if (!constanteDoContexto.getTipo().getCategoria().isInteger()) {
            throw new SemanticError("Esperava-se uma constante inteira.");
        }

        if (constanteDoContexto.getValorInteiro() > Cadeia.TAMANHO_MAXIMO) {
            throw new SemanticError("Cadeia declarada com tamanho maior que o permitido ["
                    + Cadeia.TAMANHO_MAXIMO + "]");
        }

        Cadeia literal = new Cadeia(constanteDoContexto.getValorInteiro());
        variaveisDoContexto.setTipoAtual(literal);
    }

    /**
     * <tipo> ::= vetor "[" <constante> #120 "]" de <tipo_pre_def> #121;
     *
     * #120 – Se TipoConst <> “inteiro”
     * então ERRO(“Número de elementos deve ser int”)
     * senão Tam-Vetor := Val-Const
     *
     * @param token
     * @throws SemanticError
     */
    public void action120(Token token) throws SemanticError, Exception {
        Constante constanteDoContexto = variaveisDoContexto.getTipoConstante();

        if (!constanteDoContexto.getTipo().getCategoria().isInteger()) {
            throw new SemanticError("Número de elementos deve ser inteiro.");
        }

        variaveisDoContexto.setLimite(constanteDoContexto);
    }

    /**
     * <tipo> ::= vetor "[" <constante> #120 "]" de <tipo_pre_def> #121;
     *
     * #121 - TipoElementos  := TipoAtual
     * TipoAtual := “vetor”
     *
     * @param token
     */
    public void action121(Token token) throws Exception {
        PreDefinido tipo = (PreDefinido) variaveisDoContexto.getTipoAtual();

        Vetor vetor = new Vetor();
        vetor.setTipo(tipo);
        vetor.setTamanho(variaveisDoContexto.getTipoConstante());

        variaveisDoContexto.setTipoAtual(vetor);
    }

    /**
     * <tipo_pre_def> ::= inteiro #122;
     *
     * #122 – TipoAtual := “inteiro”
     *
     * @param token
     */
    public void action122(Token token) {
        definirTipoPreDefinido(CategoriaTipoSimples.INTEGER);
    }

    /**
     * <tipo_pre_def> ::= real #123;
     *
     * #123 – TipoAtual := “real”
     *
     * @param token
     */
    public void action123(Token token) {
        definirTipoPreDefinido(CategoriaTipoSimples.REAL);
    }

    /**
     * <tipo_pre_def> ::= booleano #124;
     *
     * #124 – TipoAtual := “booleano”
     *
     * @param token
     */
    public void action124(Token token) {
        definirTipoPreDefinido(CategoriaTipoSimples.BOOLEAN);
    }

    /**
     * <tipo_pre_def> ::= caracter #125;
     *
     * #125 – TipoAtual := “caracter”
     * 
     * @param token
     */
    public void action125(Token token) {
        definirTipoPreDefinido(CategoriaTipoSimples.CHAR);
    }

    /**
     * <comando> ::= id #126 <rcomid>;
     *
     * #126 – Se id não está declarado (não esta na TS)
     * então ERRO(“Identificador não declarado”)
     * senão guarda posição de id (POSID)
     *
     * @param token
     * @throws SemanticError
     */
    public void action126(Token token) throws SemanticError {
        Identificador id = tabelaDeSimbolos.getSimbolo(token.getLexeme());
        if (id == null) {
            throw new SemanticError("Identificador '" + token.getLexeme()
                    + "' nao foi declarado");
        } else {
            variaveisDoContexto.pushIdentificador(token.getLexeme());
        }
    }

    /**
     * <comando> ::= se <expressao> #127 entao <comando> <senaoparte>
     *             | enquanto <expressao> #127 faca <comando>
     *             | repita <comando> ate <expressao>  #127;
     *
     * #127 – Se TipoExpr <> “booleano” e <> “inteiro”
     *     então ERRO(“Tipo inválido da expressão”)
     * senao (* G. Código *)
     *
     * @param token
     * @throws SemanticError
     */
    public void action127(Token token) throws SemanticError {
        Tipo tipoExpressao = variaveisDoContexto.getTipoExpressao();

        if (!tipoExpressao.getCategoria().isInteger()
                && !tipoExpressao.getCategoria().isBoolean()) {
            throw new SemanticError(
                    "Expressao inválida: o tipo deve ser inteiro ou booleano e nao "
                    + tipoExpressao.getCategoria());
        }
    }

    /**
     * <comando> ::= leia '(' #128 <lid> ')';
     *
     * #128 - contextoLID := “leitura”
     *
     * @param token
     */
    public void action128(Token token) {
        variaveisDoContexto.setContextoLID(ContextoLID.LEITURA);
    }

    /**
     * #129 – contextoEXPR := “impressão”
     * se TipoExpr <> inteiro/real/caracter/cadeia
     *     então ERRO(“tipo invalido para impressão”)
     * senão (* G. Código *)
     *
     * @param token
     * @throws SemanticError
     */
    public void action129(Token token) throws SemanticError {
        variaveisDoContexto.pushContextoEXPR(ContextoEXPR.IMPRESSAO);

        Tipo tipoExpr = variaveisDoContexto.getTipoExpressao();

        if (!tipoExpr.getCategoria().isInteger()
                && !tipoExpr.getCategoria().isReal()
                && !tipoExpr.getCategoria().isChar()
                && !tipoExpr.getCategoria().isCadeia()) {
            throw new SemanticError("Tipo " + tipoExpr.getCategoria()
                    + " invalido para impressao.");
        }

    }

    /**
     * <rcomid> ::= #130 ":=" <expressao> #131;
     *
     * #130 –
     * Se categoria de id = “Variável” ou “Parâmetro”
     *     então se tipo de id = “vetor”
     *         então ERRO (“id. Deveria ser indexado”)
     *     senão TipoLadoEsq := tipo de id
     * senão se categoria de id = “função”
     *     então se fora do escopo da função id
     *         então ERRO(“fora do escopo da função”)
     *     senão TipoLadoEsq := tipo da função
     * senão ERRO (“id. deveria ser var/par/função”)
     *
     * @param token
     * @throws SemanticError
     */
    public void action130(Token token) throws SemanticError {
        String lexeme = token.getLexeme();

        Identificador id = tabelaDeSimbolos.getSimbolo(lexeme);

        if (id instanceof Variavel) {
            Variavel var = (Variavel) id;

            // se tipo de id = vetor
            if (var.getTipo().getCategoria().isVetor()) {
                throw new SemanticError("Identificador '" + lexeme
                        + "' e vetor e deve ser indexado");
            } else {
                variaveisDoContexto.setTipoLadoEsquerdo(var.getTipo());
            }
        } else if (id instanceof Parametro) {
            Parametro param = (Parametro) id;

            // se tipo de id = vetor
            if (param.getTipo().getCategoria().isVetor()) {
                throw new SemanticError("Identificador '" + lexeme
                        + "' e vetor e deve ser indexado");
            } else {
                variaveisDoContexto.setTipoLadoEsquerdo(param.getTipo());
            }
        } else if (id instanceof Funcao) {
            Funcao funcao = (Funcao) id;

            if (!funcao.getNome().equals(variaveisDoContexto.peekFuncaoEscopo())) {
                throw new SemanticError("atribuicao de retorno em "
                        + funcao.getNome() + " fora do escopo da funcao");
            }

            funcao.setUtilizouRetorno(true);
            variaveisDoContexto.setTipoLadoEsquerdo(funcao.getTipoRetorno());
        } else {
            throw new SemanticError("Identificador '" + lexeme
                    + "' deveria ser um parametro, funcao ou variavel e nao "
                    + id.getCategoria());
        }
    }

    /**
     * <rcomid> ::= #130 ":=" <expressao> #131;
     *
     * #131 – se TipoExpr não compatível com tipoLadoesq
     *     então ERRO (“tipos incompatíveis”)
     * senão (* G. Código *)
     *
     * @param token
     * @throws SemanticError
     */
    public void action131(Token token) throws SemanticError {
        Tipo tipoExpressao = variaveisDoContexto.getTipoExpressao();
        Tipo tipoLadoEsquerdo = variaveisDoContexto.getTipoLadoEsquerdo();

        if (!tipoLadoEsquerdo.isCompativelAtribuicao(tipoExpressao)) {
            throw new SemanticError("Tipo da expressao "
                    + tipoExpressao.getCategoria()
                    + " incompativel com lado esquerdo "
                    + tipoLadoEsquerdo.getCategoria());
        }
        variaveisDoContexto.popIdentificador();
    }

    /**
     * <rcomid> ::= "[" #132 <expressao> #133 "]" ":=" <expressao> #131;
     *
     * #132– se categoria de id <> “variável”
     *     então ERRO (“esperava-se uma variável”)
     * senao se tipo de id <> vetor e <> de cadeia
     *     então ERRO(“apenas vetores e cadeias podem ser indexados”)
     * senão TipoVarIndexada = tipo (vetor/cadeia)
     *
     * @param token
     * @throws SemanticError
     */
    public void action132(Token token) throws SemanticError {
        Identificador id = tabelaDeSimbolos.getSimbolo(variaveisDoContexto.peekIdentificador());

        if (!(id instanceof Variavel)) {
            throw new SemanticError("Identificador '" + id.getNome() + "' deveria ser uma variavel");
        }

        Variavel variavel = (Variavel) id;

        if (!variavel.getTipo().getCategoria().isVetor()
                && !variavel.getTipo().getCategoria().isCadeia()) {
            throw new SemanticError("Apenas vetores e cadeias podem ser indexados");
        }
        variaveisDoContexto.pushTipoVarIndexada(variavel.getTipo());
    }

    /**
     * <rcomid> ::= "[" #132 <expressao> #133 "]" ":=" <expressao> #131;
     *
     * #133 – se TipoExpr <> “inteiro”
     *     então ERRO(“tipo do índice inválido”)
     * senão se TipoVarIndexada = “vetor”
     *     então TipoLadoEsq := TipoElementos do vetor
     * senão TipoLadoEsq := caracter
     *
     * @param token
     * @throws SemanticError
     */
    public void action133(Token token) throws SemanticError {
        Tipo tipoVarIndexada = variaveisDoContexto.peekTipoVarIndexada();

        Tipo tipoExpressao = variaveisDoContexto.getTipoExpressao();
        if (!tipoExpressao.getCategoria().isInteger()) {
            throw new SemanticError("Índice de vetor e cadeia deve ser INTEIRO.");
        } else if (tipoVarIndexada.getCategoria().isVetor()) {
            Vetor vetor = (Vetor) tipoVarIndexada;
            variaveisDoContexto.setTipoLadoEsquerdo(vetor.getTipo());
        } else {
            variaveisDoContexto.setTipoLadoEsquerdo(new PreDefinido(CategoriaTipoSimples.CHAR));
        }
    }

    /**
     * <comando> ::= "(" #134 <expressao> #135 <rep_expressao> ")" #136;
     *
     * #134 – se categoria de id <> procedure
     * então ERRO(“id deveria ser uma procedure”)
     *
     * @param token
     * @throws SemanticError
     */
    public void action134(Token token) throws SemanticError {
        Identificador simbolo = tabelaDeSimbolos.getSimbolo(variaveisDoContexto.peekIdentificador());

        if (!(simbolo instanceof Procedimento) || simbolo instanceof Funcao) {
            throw new SemanticError(simbolo.getNome() + " deveria ser um procedimento.");
        }
    }

    /**
     * <comando> ::= "(" #134 <expressao> #135 <rep_expressao> ")" #136;
     *
     * #135 – NPA := 1 (Número de Parâmetros Atuais)
     * contextoEXPR := “par-atual”
     * Verifica se existe Parâmetro Formal correspondente
     * e se o tipo e o MPP são compatíveis.
     *
     * @param token
     * @throws SemanticError
     */
    public void action135(Token token) throws SemanticError {
        variaveisDoContexto.setNrParametros(1);
        variaveisDoContexto.pushContextoEXPR(ContextoEXPR.PAR_ATUAL);
        analisaParametros(token);
    }

    /**
     * <comando> ::= "(" #134 <expressao> #135 <rep_expressao> ")" #136;
     *
     * #136 – se NPA = NPF
     *     então (* G. Código para chamada de proc*)
     * senão ERRO(“Erro na quantidade de parâmetros”)
     *
     * @param token
     * @throws SemanticError
     */
    public void action136(Token token) throws SemanticError {
        Procedimento procedimento = (Procedimento) tabelaDeSimbolos.getSimbolo(variaveisDoContexto.popIdentificador());

        int npf = procedimento.getListaParametros().size();
        int npa = variaveisDoContexto.getNrParametros();
        if (npf != npa) {
            throw new SemanticError("Erro na quantidade de parametros");
        }
    }

    /**
     * <rcomid> ::= î;
     *
     * #137 - se categoria de id <> procedure
     * então ERRO(“id deveria ser uma procedure”)
     * senão se NPF <> 0
     * então ERRO(“Erro na quant.de parâmetros”)
     * senão (* G. Código p/ chamada de proc. *)
     *
     * @param token
     * @throws SemanticError
     */
    public void action137(Token token) throws SemanticError {
        Identificador identificador = tabelaDeSimbolos.getSimbolo(variaveisDoContexto.popIdentificador());

        // Verificar se pode ser funcao tambem
        if (!(identificador instanceof Procedimento)) {
            throw new SemanticError("Id " + identificador.getNome()
                    + " deveria ser um " + identificador.getCategoria());
        }
        Procedimento procedimento = (Procedimento) identificador;

        if (!procedimento.getListaParametros().isEmpty()) {
            throw new SemanticError("Erro na quantidade de parametros do "
                    + procedimento.getCategoria() + " "
                    + identificador.getNome() + ".");
        }

    }

    /**
     * <rep_expressao> ::= "," <expressao> #138 <rep_expressao> | î;
     *
     * #138 – Trata expressão de acordo com ContextoEXPR (“par-atual” ou “impressão”)
     *
     * @param token
     * @throws SemanticError
     */
    public void action138(Token token) throws SemanticError {
        switch (variaveisDoContexto.peekContextoEXPR()) {
            case PAR_ATUAL: {
                variaveisDoContexto.setNrParametros(variaveisDoContexto.getNrParametros() + 1);
                analisaParametros(token);
                break;
            }
            case IMPRESSAO: {
                Tipo tipoExpressao = variaveisDoContexto.getTipoExpressao();
                if (!tipoExpressao.getCategoria().isInteger()
                        && tipoExpressao.getCategoria().isReal()
                        && tipoExpressao.getCategoria().isChar()
                        && tipoExpressao.getCategoria().isCadeia()) {
                    throw new SemanticError("O tipo '"
                            + tipoExpressao.getCategoria()
                            + "' e invalido para impressao.");
                }
            }
        }
    }

    /**
     * <expressao> ::= <expsimp> #139 <resto_expressao>;
     *
     * #139 – TipoExpr := TipoExpSimples
     *
     * @param token
     */
    public void action139(Token token) {
        variaveisDoContexto.setTipoExpressao(variaveisDoContexto.getTipoExpSimples());
    }

    /**
     * <resto_expressao> ::= <oprel> <expsimp> #140;
     *
     * #140 – Se TipoExpSimples não compatível com TipoExpr
     * então ERRO (“Operandos incompatíveis”)
     * senão TipoExpr := “booleano”
     *
     * @param token
     * @throws SemanticError
     */
    public void action140(Token token) throws SemanticError {
        Tipo tipoExpressao = variaveisDoContexto.getTipoExpressao();
        Tipo tipoExpressaoSimples = variaveisDoContexto.getTipoExpSimples();

        if (!tipoExpressaoSimples.isCompativelOperacao(tipoExpressao)) {
            throw new SemanticError("Operandos incompativeis, e "
                    + tipoExpressao.getCategoria() + " e deveria ser "
                    + tipoExpressaoSimples.getCategoria());
        }
        variaveisDoContexto.setTipoExpressao(new PreDefinido(CategoriaTipoSimples.BOOLEAN));
    }

    /**
     * <oprel> ::= "=" #141 | "<" #142 | ">" #143 | ">=" #144 | "<=" #145 | "<>" #146;
     *
     * @param token
     */
    public void action141(Token token) {
        guardaOperadorRelacional(token);
    }

    /**
     * <oprel> ::= "=" #141 | "<" #142 | ">" #143 | ">=" #144 | "<=" #145 | "<>" #146;
     *
     * @param token
     */
    public void action142(Token token) {
        guardaOperadorRelacional(token);
    }

    /**
     * <oprel> ::= "=" #141 | "<" #142 | ">" #143 | ">=" #144 | "<=" #145 | "<>" #146;
     *
     * @param token
     */
    public void action143(Token token) {
        guardaOperadorRelacional(token);
    }

    /**
     * <oprel> ::= "=" #141 | "<" #142 | ">" #143 | ">=" #144 | "<=" #145 | "<>" #146;
     *
     * @param token
     */
    public void action144(Token token) {
        guardaOperadorRelacional(token);
    }

    /**
     * <oprel> ::= "=" #141 | "<" #142 | ">" #143 | ">=" #144 | "<=" #145 | "<>" #146;
     *
     * @param token
     */
    public void action145(Token token) {
        guardaOperadorRelacional(token);
    }

    /**
     * <oprel> ::= "=" #141 | "<" #142 | ">" #143 | ">=" #144 | "<=" #145 | "<>" #146;
     * 
     * @param token
     */
    public void action146(Token token) {
        guardaOperadorRelacional(token);
    }

    /**
     * <expsimp> ::= <termo> #147 <rep_expsimp>;
     *
     * #147 – TipoExpSimples := TipoTermo
     *
     * @param token
     */
    public void action147(Token token) {
        variaveisDoContexto.setTipoExpSimples(variaveisDoContexto.popTipoTermo());
    }

    /**
     * <rep_expsimp> ::= <op_add> #148 <termo> #149 <rep_expsimp>;
     *
     * #148 – Se operador não se aplica a TipoExpSimples
     * então ERRO(“Operador e Operando incompatíveis”)
     *
     * @param token
     * @throws SemanticError
     */
    public void action148(Token token) throws SemanticError {
        OperadorRelacional operador = OperadorRelacional.getTipoPorNome(token.getLexeme());

        Tipo tipoExpSimples = variaveisDoContexto.getTipoExpSimples();

        if (operador.isE() || operador.isOu()) {
            if (!tipoExpSimples.getCategoria().isBoolean()) {
                throw new SemanticError("Operando do tipo "
                        + tipoExpSimples.getCategoria()
                        + " incompativel com o operador '" + operador.getNome()
                        + "'.");
            }
            return;

        }
        if (operador.isAdicao()) {
            if (!tipoExpSimples.getCategoria().isNumerico()
                    && !tipoExpSimples.getCategoria().isCadeia()
                    && !tipoExpSimples.getCategoria().isChar()) {
                throw new SemanticError("Operando do tipo "
                        + tipoExpSimples.getCategoria()
                        + " incompativel com o operador '" + operador.getNome()
                        + "'.");
            }
            return;
        }

        if (operador.isDivisao() || operador.isMultiplicacao() || operador.isSubtracao()) {
            if (!tipoExpSimples.getCategoria().isNumerico()) {
                throw new SemanticError("Operando do tipo "
                        + tipoExpSimples.getCategoria()
                        + " incompativel com o operador '" + operador.getNome()
                        + "'.");
            }
            return;
        }
    }

    /**
     * <rep_expsimp> ::= <op_add> #148 <termo> #149 <rep_expsimp>;
     *
     * #149 -
     * Se TipoTermo incompatível com TipoExpSimples
     *     então ERRO (“Operandos incompatíveis”)
     * senão TipoExpSimples := tipo do res. da operação
     *     (* G. Código de acordo com oppad guardado *)
     *
     * @param token
     * @throws SemanticError
     */
    public void action149(Token token) throws SemanticError {
        Tipo tipoDoTermo = variaveisDoContexto.peekTipoTermo();
        Tipo tipoExpressaoSimples = variaveisDoContexto.getTipoExpSimples();

        if (!tipoExpressaoSimples.isCompativelOperacao(tipoDoTermo)) {
            throw new SemanticError("Operandos incompativeis ("
                    + tipoExpressaoSimples.getCategoria() + " com "
                    + tipoDoTermo.getCategoria() + ")");
        }

        if (tipoExpressaoSimples.getCategoria().isInteger()
                && tipoDoTermo.getCategoria().isReal()) {
            variaveisDoContexto.setTipoExpSimples(new PreDefinido(
                    CategoriaTipoSimples.REAL));
            return;
        }

        if (tipoExpressaoSimples.getCategoria().isChar()
                && (tipoDoTermo.getCategoria().isChar() || tipoDoTermo.getCategoria().isCadeia())) {
            variaveisDoContexto.setTipoExpSimples(new PreDefinido(
                    CategoriaTipoSimples.CADEIA));
            return;
        }
    }

    /**
     * <op_add> ::= "+" #150 | "-" #151 | ou #152;
     *
     * @param token
     */
    public void action150(Token token) {
        guardaOperadorRelacional(token);
    }

    /**
     * <op_add> ::= "+" #150 | "-" #151 | ou #152;
     *
     * @param token
     */
    public void action151(Token token) {
        guardaOperadorRelacional(token);
    }

    /**
     * <op_add> ::= "+" #150 | "-" #151 | ou #152;
     *
     * @param token
     */
    public void action152(Token token) {
        guardaOperadorRelacional(token);
    }

    /**
     * <termo> ::= <fator> #153 <rep_termo>;
     *
     * #153 – TipoTermo := TipoFator
     *
     * @param token
     * @throws SemanticError
     */
    public void action153(Token token) {
        variaveisDoContexto.pushTipoTermo(variaveisDoContexto.getTipoFator());
    }

    /**
     * <rep_termo> ::= <op_mult> #154 <fator> #155 <rep_termo>;
     *
     * #154 – Se operador não se aplica a TipoTermo então ERRO(“Operador e
     * Operando incompatíveis”)
     *
     * @param token
     * @throws SemanticError
     */
    public void action154(Token token) throws SemanticError, Exception {
        Tipo tipoDoTermo = variaveisDoContexto.peekTipoTermo();

        OperadorRelacional operador = OperadorRelacional.getTipoPorNome(token.getLexeme());

        if (operador.isMultiplicacao() || operador.isDivisao()) {
            if (!tipoDoTermo.getCategoria().isInteger()
                    && !tipoDoTermo.getCategoria().isReal()) {
                throw new SemanticError("Operador '" + operador
                        + "' e operando (" + tipoDoTermo.getCategoria()
                        + ") imcompativeis");
            }
        } else if (operador.isE()) {
            if (!tipoDoTermo.getCategoria().isBoolean()) {
                throw new SemanticError("Operador '" + operador
                        + "' e operando (" + tipoDoTermo.getCategoria()
                        + ") imcompativeis");
            }
        } else {
            throw new Exception("Operador desconhecido");
        }
    }

    /**
     * <rep_termo> ::= <op_mult> #154 <fator> #155 <rep_termo>;
     *
     * #155 - Se TipoFator incompatível com TipoTermo
     * então ERRO (“Operandos incompatíveis”)
     *     senão TipoTermo := tipo do res. da operação
     *         (* G. Código de acordo com opmult *)
     *
     * @param token
     * @throws SemanticError
     */
    public void action155(Token token) throws SemanticError {
        Tipo tipoDoTermo = variaveisDoContexto.peekTipoTermo();
        Tipo tipoDoFator = variaveisDoContexto.getTipoFator();

        if (!tipoDoTermo.isCompativelOperacao(tipoDoFator)) {
            throw new SemanticError("Operandos incompativeis ("
                    + tipoDoTermo.getCategoria() + " com "
                    + tipoDoFator.getCategoria() + ")");
        }

        if (tipoDoTermo.getCategoria().isInteger()
                && tipoDoFator.getCategoria().isReal()) {

            variaveisDoContexto.pushTipoTermo(new PreDefinido(
                    CategoriaTipoSimples.REAL));
            return;
        }

        if (variaveisDoContexto.getOperadorRelacional().isDivisao()) {
            variaveisDoContexto.pushTipoTermo(new PreDefinido(
                    CategoriaTipoSimples.REAL));
            return;
        }

    }

    /**
     * <op_mult> ::= "*" #156 | "/" #157 | e #158;
     *
     * @param token
     * @throws SemanticError
     */
    public void action156(Token token) {
        guardaOperadorRelacional(token);
    }

    /**
     * <op_mult> ::= "*" #156 | "/" #157 | e #158;
     *
     * @param token
     * @throws SemanticError
     */
    public void action157(Token token) {
        guardaOperadorRelacional(token);
    }

    /**
     * <op_mult> ::= "*" #156 | "/" #157 | e #158;
     *
     * @param token
     * @throws SemanticError
     */
    public void action158(Token token) {
        guardaOperadorRelacional(token);
    }

    /**
     * <fator> ::= não #159 <fator> #160;
     *
     * #159 – se OpNega
     * então ERRO(“Operadores “não” consecutivos”)
     * Senão OpNega := true
     *
     * @param token
     * @throws SemanticError
     */
    public void action159(Token token) throws SemanticError {
        if (variaveisDoContexto.isOperadorNegacao()) {
            throw new SemanticError("Operador 'nao' repetido");
        }
        variaveisDoContexto.setOperadorNegacao(true);
    }

    /**
     * <fator> ::= não #159 <fator> #160;
     *
     * #160 – Se TipoFator <> “booleano”
     * então ERRO(“Op. ‘não’ exige operando booleano”)
     *
     * @param token
     * @throws SemanticError
     */
    public void action160(Token token) throws SemanticError {
        Tipo tipoDoFator = variaveisDoContexto.getTipoFator();
        if (!tipoDoFator.getCategoria().isBoolean()) {
            throw new SemanticError(
                    "Operador 'nao' exige operando booleano e nao "
                    + tipoDoFator.getCategoria());
        }
    }

    /**
     * <fator> ::= "-" #161 <fator> #162;
     *
     * #161 – se OpUnario
     * então ERRO(“Ops. “unario” consecutivos”)
     * Senão OpUnario := true
     *
     * @param token
     * @throws SemanticError
     */
    public void action161(Token token) throws SemanticError {
        if (variaveisDoContexto.isOperadorUnario()) {
            throw new SemanticError("Operadores 'unarios' consecutivos");
        }
        variaveisDoContexto.setOperadorUnario(true);
    }

    /**
     * <fator> ::= "-" #161 <fator> #162;
     *
     * #162 - Se TipoFator <> “inteiro” ou de “real”
     * então ERRO(“Op. ‘-/+’ exige operando numérico”)
     *
     * @param token
     * @throws SemanticError
     */
    public void action162(Token token) throws SemanticError {
        if (!variaveisDoContexto.getTipoFator().getCategoria().isInteger()
                && !variaveisDoContexto.getTipoFator().getCategoria().isReal()) {
            throw new SemanticError(
                    "Operadores \"-/+\" exigem operando numerico");
        }
    }

    /**
     * <fator> ::= "(" #163 <expressao> ")" #164;
     *
     * #163 – OpNega := OpUnario := false
     *
     * @param token
     */
    public void action163(Token token) {
        variaveisDoContexto.setOperadorNegacao(false);
        variaveisDoContexto.setOperadorUnario(false);
    }

    /**
     * <fator> ::= "(" #163 <expressao> ")" #164;
     *
     * #164 – TipoFator := TipoExpr
     *
     * @param token
     */
    public void action164(Token token) {
        variaveisDoContexto.setTipoFator(variaveisDoContexto.getTipoExpressao());
    }

    /**
     * <fator> ::= <variavel> #165;
     *
     * #165 – TipoFator := TipoVar
     *
     * @param token
     */
    public void action165(Token token) {
        variaveisDoContexto.setTipoFator(variaveisDoContexto.getTipoVar());
        variaveisDoContexto.popIdentificador();
    }

    /**
     * <fator> ::= <constante_explicita> #166;
     *
     * #166 – TipoFator := TipoCte
     *
     * @param token
     */
    public void action166(Token token) {
        variaveisDoContexto.setTipoFator(variaveisDoContexto.getTipoConstante().getTipo());
    }

    /**
     * <rvar> ::= "(" #167 <expressao> #135 <rep_expressao> ")" #168;
     *
     * #167 – se categoria de id <> função
     *     então ERRO(“id deveria ser uma função”)
     *
     * @param token
     * @throws SemanticError
     */
    public void action167(Token token) throws SemanticError {
        Identificador id = tabelaDeSimbolos.getSimbolo(variaveisDoContexto.peekIdentificador());

        if (!(id instanceof Funcao)) {
            throw new SemanticError("ID de função era esperado.");
        }
    }

    /**
     * <rvar> ::= "(" #167 <expressao> #135 <rep_expressao> ")" #168;
     *
     * #168 – se NPA = NPF
     * então TipoVar := Tipo do resultado da função
     *     (* G. Código chamada de função *)
     * senão ERRO(“Erro na quantidade de parâmetros”)
     *
     * @param token
     * @throws SemanticError
     */
    public void action168(Token token) throws SemanticError {
        Funcao funcao = (Funcao) tabelaDeSimbolos.getSimbolo(variaveisDoContexto.peekIdentificador());

        int npf = funcao.getListaParametros().size();
        int npa = variaveisDoContexto.getNrParametros();
        if (npf != npa) {
            throw new SemanticError(
                    "Erro na quantidade de parametros, deveria ser " + npf + " e nao " + npa);
        }

        variaveisDoContexto.setTipoVar(funcao.getTipoRetorno());
        variaveisDoContexto.popContextoEXPR();
    }

    /**
     * <rvar> ::= "[" #132 <expressao> #169 "]";
     *
     * #169 – se TipoExpr <> “inteiro”
     *     então ERRO(“tipo do índice inválido”)
     * senão se TipoVarIndexada = “vetor”
     *     então TipoVar := TipoElementos do vetor
     * senão TipoVar := caracter
     *
     * @param token
     */
    public void action169(Token token) throws SemanticError {
        Tipo tipoVarIndexada = variaveisDoContexto.peekTipoVarIndexada();

        Tipo tipoExpressao = variaveisDoContexto.getTipoExpressao();
        if (!tipoExpressao.getCategoria().isInteger()) {
            throw new SemanticError("Índice de vetor e cadeia deve ser INTEIRO.");
        } else if (tipoVarIndexada.getCategoria().isVetor()) {
            Vetor vetor = (Vetor) tipoVarIndexada;
            variaveisDoContexto.setTipoLadoEsquerdo(vetor.getTipo());
        } else {
            variaveisDoContexto.setTipoLadoEsquerdo(new PreDefinido(CategoriaTipoSimples.CHAR));
        }
    }

    /**
     * <rvar> ::= î #170;
     *
     * #170  - se categoria de id = “variável” ou “Parâmetro”
     *     então se tipo de id = “vetor”
     *         então ERRO(“vetor deve ser indexado”)
     *     senão TipoVar := Tipo de id
     *     senão se categoria de id = “função”
     *         então se NPF <> 0
     *             então ERRO(“Erro na quant. de par.”)
     *         senão (* G. Código *)
     *         TipoVar := Tipo res. da função
     *     Senão se categoria de id = “constante”
     *         então TipoVar:= Tipo do id de Const.
     *     Senão ERRO(“esperava-se var, id-função ou constante”)
     *
     * @param token
     * @throws SemanticError
     */
    public void action170(Token token) throws SemanticError {
        Identificador id = tabelaDeSimbolos.getSimbolo(token.getLexeme());

        if (id instanceof Variavel) {
            Variavel var = (Variavel) id;
            if (var.getTipo().getCategoria().isVetor()) {
                throw new SemanticError("Vetor " + token.getLexeme()
                        + " deve ser indexado.");
            }
            variaveisDoContexto.setTipoVar(var.getTipo());
            return;
        }

        if (id instanceof Parametro) {
            Parametro param = (Parametro) id;
            if (param.getTipo().getCategoria().isVetor()) {
                throw new SemanticError("Vetor " + token.getLexeme()
                        + " deve ser indexado.");
            }
            variaveisDoContexto.setTipoVar(param.getTipo());
            return;
        }

        if (id instanceof Funcao) {
            Funcao funcao = (Funcao) id;

            if (!funcao.getListaParametros().isEmpty()) {
                throw new SemanticError("Erro na quantidade de parametros");
            }

            variaveisDoContexto.setTipoVar(funcao.getTipoRetorno());
            return;
        }

        if (id instanceof Constante) {
            Constante constante = (Constante) id;
            variaveisDoContexto.setTipoVar(constante.getTipo());
            return;
        }

        throw new SemanticError("Esperava-se variavel, funcao ou constante");
    }

    /**
     * <constante_explicita> ::= num_int #171;
     *
     * ACAO #171
     *
     * Define na constante de contexto o seu tipo como inteiro e o valor
     *
     * @param token
     */
    public void action171(Token token) {
        // No lexeme vem o valor da constante
        String valor = token.getLexeme();
        definirConstanteDoContexto(new PreDefinido(CategoriaTipoSimples.INTEGER), valor);
    }

    /**
     * <constante_explicita> ::= num_real #172;
     *
     * ACAO #172
     *
     * Define na constante de contexto o seu tipo como real e o valor
     *
     * @param token
     */
    public void action172(Token token) {
        // No lexeme vem o valor da constante
        String valor = token.getLexeme();
        definirConstanteDoContexto(new PreDefinido(CategoriaTipoSimples.REAL), valor);
    }

    /**
     * <constante_explicita> ::= falso #173;
     *
     * ACAO #173
     *
     * Define na constante de contexto o seu tipo como boolean e o valor
     *
     * @param token
     */
    public void action173(Token token) {
        // No lexeme vem o valor da constante
        String valor = token.getLexeme();
        definirConstanteDoContexto(new PreDefinido(CategoriaTipoSimples.BOOLEAN), valor);
    }

    /**
     * <constante_explicita> ::= verdadeiro #174;
     *
     * ACAO #174
     *
     * Define na constante de contexto o seu tipo como boolean e o valor.
     *
     * @param token
     */
    public void action174(Token token) {
        this.action173(token);
    }

    /**
     * <constante_explicita> ::= literal #175;
     * 
     * ACAO #175
     *
     * Define na constante de contexto o seu valor e tipo como literal caso
     * valor for > 1, caso o valor for 1 define o tipo como char.
     *
     * @param token
     */
    public void action175(Token token) {
        // Pega lexeme removendo aspas
        String valor = token.getLexeme().replaceAll("'(.*?)'", "$1");

        if (valor.length() > 1) { // Literal (cadeia de char)
            Cadeia literal = new Cadeia(valor.length());
            definirConstanteDoContexto(literal, valor);
        } else { // Char
            definirConstanteDoContexto(new PreDefinido(CategoriaTipoSimples.CHAR), valor);
        }
    }

    /**
     * <op_mult> ::= "*" #156 | "/" #157 | e #158;
     *
     * guarda operador para futura G. codigo
     *
     * @param token
     * @throws SemanticError
     */
    private void guardaOperadorRelacional(Token token) {
        variaveisDoContexto.setOperadorRelacional(OperadorRelacional.getTipoPorNome(token.getLexeme()));
    }

    /**
     * Define como constante de contexto a constante com o tipo passado como
     * parametro
     *
     * @param tipo
     * @param valor
     */
    public void definirConstanteDoContexto(Tipo tipo, String valor) {
        Constante constanteDoContexto = new Constante(valor);
        constanteDoContexto.setTipo(tipo);
        constanteDoContexto.setValor(valor);
        variaveisDoContexto.setTipoConstante(constanteDoContexto);
    }

    private void definirTipoPreDefinido(CategoriaTipoSimples categoriaTipo) {
        PreDefinido tipo = new PreDefinido(categoriaTipo);
        variaveisDoContexto.setTipoAtual(tipo);
    }

    /**
     * Analisa compactibilidade de parametro de função e procedure.
     *
     * @param token
     * @throws SemanticError
     */
    private void analisaParametros(Token token) throws SemanticError {

        Procedimento procedimento = (Procedimento) tabelaDeSimbolos.getSimbolo(variaveisDoContexto.peekIdentificador());

        List<Parametro> parametros = procedimento.getListaParametros();

        Collections.sort(parametros);

        if (parametros.isEmpty()) {
            throw new SemanticError(procedimento.getCategoria() + " "
                    + procedimento.getNome() + " nao possui parametros");
        }

        if (parametros.size() >= variaveisDoContexto.getNrParametros()) {
            Parametro parametro = parametros.get(variaveisDoContexto.getNrParametros() - 1);

            if (parametro.getMecanismoPassagem().isReferencia()) {

                Identificador identificador = tabelaDeSimbolos.getSimbolo(token.getLexeme());

                if (identificador == null) {
                    throw new SemanticError("Parametro passado para "
                            + procedimento.getCategoria() + " "
                            + procedimento.getNome()
                            + " nao possui referencia ou nao eh uma "
                            + "Variavel, Parametro ou Funcao. ");
                }

                if (identificador instanceof Parametro
                        && !((Parametro) identificador).getMecanismoPassagem().isReferencia()) {
                    throw new SemanticError("Identificador "
                            + identificador.getNome()
                            + " passado como parametro para "
                            + procedimento.getCategoria()
                            + " nao possui referencia. ");
                }

                if ((!(identificador instanceof Variavel)
                        && !(identificador instanceof Parametro) && !(identificador instanceof Funcao))) {
                    throw new SemanticError("Identificador "
                            + identificador.getNome()
                            + " passado como parametro " + parametro + " para "
                            + procedimento.getCategoria()
                            + " nao possui referencia. ");
                }
            }

            Tipo ctipoParametro = parametro.getTipo();

            Tipo ctipoExpressao = variaveisDoContexto.getTipoExpressao();
            // Verifica se o tipo do parametro eh compativel com o passado.
            if (!ctipoParametro.isCompativelAtribuicao(ctipoExpressao)) {
                throw new SemanticError(
                        "Tipo de parametro incompativel. Esperava-se "
                        + ctipoParametro.getCategoria() + " e veio "
                        + ctipoExpressao.getCategoria());
            }
        }
    }
}
