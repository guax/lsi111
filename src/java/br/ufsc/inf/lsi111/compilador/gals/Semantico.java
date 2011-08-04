package br.ufsc.inf.lsi111.compilador.gals;

import br.ufsc.inf.lsi111.compilador.TabelaDeSimbolos;
import br.ufsc.inf.lsi111.compilador.semantico.VariaveisDoContexto;
import br.ufsc.inf.lsi111.compilador.semantico.id.Constante;
import br.ufsc.inf.lsi111.compilador.semantico.id.Identificador;
import br.ufsc.inf.lsi111.compilador.semantico.tipo.Cadeia;
import br.ufsc.inf.lsi111.compilador.semantico.tipo.PreDefinido;
import br.ufsc.inf.lsi111.compilador.semantico.tipo.Tipo;
import br.ufsc.inf.lsi111.compilador.semantico.tipo.constants.CategoriaTipoSimples;
import java.lang.reflect.InvocationTargetException;

public class Semantico implements Constants {
    
    TabelaDeSimbolos tabelaDeSimbolos;

    public void executeAction(int action, Token token) throws SemanticError {
        //System.out.printf("Acao #%d, Token: %s\n", action, token); msg chata.
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

    private void definirTipoPreDefinido(CategoriaTipoSimples categoriaTipo) {
        PreDefinido tipo = new PreDefinido(categoriaTipo);
        variaveisDoContexto.setTipoAtual(tipo);
    }
    VariaveisDoContexto variaveisDoContexto;

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
     * <dcl_const> ::= const <tipo_pre_def> id #101 "=" <constante> #102 ";" <dcl_const> | î;
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
     * <dcl_const> ::= const <tipo_pre_def> id #101 "=" <constante> #102 ";" <dcl_const> | î;
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
        }
        else {
            throw new SemanticError("Tipo de constante invalido.");
        }
    }

    /**
     * <constante> ::= id  #116 | <constante_explicita>;
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

        if(id == null) {
            throw new SemanticError("Id '" + lexeme + "' não declarado.");
        }
        else if(!(id instanceof Constante)) {
            throw new SemanticError("'" + lexeme + "' deve ser uma constante.");
        }
        else {
            Constante constante = (Constante) id;
            // Ignorando tipo declarado e usando tipo da constante do id.
            this.variaveisDoContexto.setTipoAtual(constante.getTipo());
            this.definirConstanteDoContexto(constante.getTipo(), (String) constante.getValor());
        }
    }

    /**
     * <tipo_pre_def> ::= inteiro #122 | real #123 | booleano #124 | caracter #125;
     *
     * #122 – TipoAtual := “inteiro”
     *
     * @param token
     */
    public void action122(Token token) {
        definirTipoPreDefinido(CategoriaTipoSimples.INTEGER);
    }

    /**
     * <tipo_pre_def> ::= inteiro #122 | real #123 | booleano #124 | caracter #125;
     *
     * #123 – TipoAtual := “real”
     *
     * @param token
     */
    public void action123(Token token) {
        definirTipoPreDefinido(CategoriaTipoSimples.REAL);
    }

    /**
     * <tipo_pre_def> ::= inteiro #122 | real #123 | booleano #124 | caracter #125;
     *
     * #124 – TipoAtual := “booleano”
     *
     * @param token
     */
    public void action124(Token token) {
        definirTipoPreDefinido(CategoriaTipoSimples.BOOLEAN);
    }

    /**
     * <tipo_pre_def> ::= inteiro #122 | real #123 | booleano #124 | caracter #125;
     *
     * #125 – TipoAtual := “caracter”
     * 
     * @param token
     */
    public void action125(Token token) {
        definirTipoPreDefinido(CategoriaTipoSimples.CHAR);
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

    /**
     * <constante_explicita> ::= num_int #171 | num_real #172 | falso #173
     *                         | verdadeiro #174 | literal #175;
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
     * <constante_explicita> ::= num_int #171 | num_real #172 | falso #173
     *                         | verdadeiro #174 | literal #175;
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
     * <constante_explicita> ::= num_int #171 | num_real #172 | falso #173
     *                         | verdadeiro #174 | literal #175;
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
     * <constante_explicita> ::= num_int #171 | num_real #172 | falso #173
     *                         | verdadeiro #174 | literal #175;
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
     * <constante_explicita> ::= num_int #171 | num_real #172 | falso #173
     *                         | verdadeiro #174 | literal #175;
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
        }
        else { // Char
            definirConstanteDoContexto(new PreDefinido(CategoriaTipoSimples.CHAR), valor);
        }
    }
}
