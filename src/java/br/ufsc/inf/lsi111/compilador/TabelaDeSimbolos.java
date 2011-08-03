package br.ufsc.inf.lsi111.compilador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufsc.inf.lsi111.compilador.semantico.id.Identificador;
import br.ufsc.inf.lsi111.compilador.semantico.id.Parametro;
import br.ufsc.inf.lsi111.compilador.semantico.id.Variavel;

public class TabelaDeSimbolos {

    /**
     * Armazena a estrutura de simbolos, onde a chave é o nível e o valor é um
     * outro map que possui o como chave o nome do identificador e como valor o
     * objeto identificador com suas respectivas informações.
     */
    private Map<Integer, Map<String, Identificador>> mapaDeSimbolos = new HashMap<Integer, Map<String, Identificador>>();
    /**
     * Armazena o nivel atual.
     */
    private int nivel = 0;

    public int getNivel() {
        return nivel;
    }

    public void incrementaNivel() {
        nivel++;
        setDeslocamento(0);
    }

    public void decrementaNivel() {
        nivel--;
        setDeslocamento(0);
    }
    /**
     * Armazena o deslocamento atual no nivel atual.
     */
    private int deslocamento = 0;

    public int getDeslocamento() {
        return deslocamento;
    }

    public void setDeslocamento(int deslocamento) {
        this.deslocamento = deslocamento;
    }

    public void incrementaDeslocamento() {
        deslocamento++;
    }

    public void decrementaDeslocamento() {
        deslocamento--;
    }

    /**
     * Adiciona um novo simbolo no mapa de simbolos do nível atual.
     *
     * @param simbolo
     */
    public void addSimbolo(Identificador simbolo) {
        Map<String, Identificador> mapaDeSimbolosDoNivel = getMapaDeSimbolosDoNivel();
        mapaDeSimbolosDoNivel.put(simbolo.getNome(), simbolo);
    }

    /**
     * Recupera o simbolo a partir do seu identificador.
     *
     * @param id
     * @return
     */
    public Identificador getSimbolo(String id) {
        return this.getSimbolo(id, nivel);
    }

    /**
     * Recupera o simbolo a partir do seu identificador no nivel passado por
     * parametro. Caso nao encontre no nivel passado por parametro, decrementa o
     * nivel e procura o simbolo novamente num nivel "acima" (de maior escopo),
     * caso nao ache novamente, repeteo processo recursivamente.
     *
     * @param id
     * @param nivel
     * @return
     */
    private Identificador getSimbolo(String id, int nivel) {
        // Recupera o simbolo no nivel desejado
        Identificador simbolo = getMapaDeSimbolos(nivel).get(id);

        // Se encontrar, retorna. Caso nao encontre, decrementa o nivel para
        // procurar o simbolo em um nivel "acima" (de maior escopo).
        if (simbolo != null) {
            return simbolo;
        } else if (nivel >= 0) {
            // Decrementa o nivel e procura recursivamente em niveis de maior
            // escopo.
            return this.getSimbolo(id, --nivel);
        } else {
            return null;
        }
    }

    /**
     * Recupera o simbolo no nivel atual a partir do seu identificador.
     *
     * @param id
     * @return
     */
    public Identificador getSimboloDoNivel(String id) {
        return getMapaDeSimbolosDoNivel().get(id);
    }

    /**
     * Recupera o mapa de simbolos do nivel atual.
     *
     * @return
     */
    public Map<String, Identificador> getMapaDeSimbolosDoNivel() {
        return getMapaDeSimbolos(nivel);
    }

    /**
     * Recupera o mapa de simbolos do nível passado como parametro.
     *
     * @param nivel
     * @return
     */
    private Map<String, Identificador> getMapaDeSimbolos(int nivel) {
        Map<String, Identificador> mapaDeSimbolosDoNivel = mapaDeSimbolos.get(nivel);
        // Caso nao exista esse nivel, cria.
        if (mapaDeSimbolosDoNivel == null) {
            mapaDeSimbolosDoNivel = new HashMap<String, Identificador>();
            mapaDeSimbolos.put(nivel, mapaDeSimbolosDoNivel);
            return mapaDeSimbolosDoNivel;
        } else {
            return mapaDeSimbolosDoNivel;
        }
    }

    /**
     * Recupera as variaveis entre o deslocamento inical e final no nivel atual.
     *
     * @param posInicial
     * @param posFinal
     * @return
     */
    public List<Variavel> getVariaveisDaPosicao(int posInicial, int posFinal) {

        List<Variavel> listaVariaveisRetorno = new ArrayList<Variavel>();

        Map<String, Identificador> simbolosDoNivel = getMapaDeSimbolosDoNivel();
        for (String nomeSimbolo : simbolosDoNivel.keySet()) {

            Identificador simbolo = simbolosDoNivel.get(nomeSimbolo);
            if (simbolo instanceof Variavel) {
                Variavel var = (Variavel) simbolo;
                int deslocamento = var.getDeslocamento();
                if (deslocamento >= posInicial && deslocamento <= posFinal) {
                    listaVariaveisRetorno.add(var);
                }
            }
        }
        return listaVariaveisRetorno;
    }

    /**
     * Recupera as variaveis entre o deslocamento inical e final no nivel atual.
     *
     * @param posInicial
     * @param posFinal
     * @return
     */
    public List<Parametro> getParametrosDaPosicao(int posInicial, int posFinal) {

        List<Parametro> listaVariaveisRetorno = new ArrayList<Parametro>();

        Map<String, Identificador> simbolosDoNivel = getMapaDeSimbolosDoNivel();
        for (String nomeSimbolo : simbolosDoNivel.keySet()) {

            Identificador simbolo = simbolosDoNivel.get(nomeSimbolo);
            // Verifica se o simbolo localizado é instancia da classe Variavel.
            // A classe Parametro estende a classe Variavel, então entrara nesse
            // if e tambem sera recuperada.
            if (simbolo instanceof Parametro) {
                Parametro param = (Parametro) simbolo;
                int deslocamento = param.getDeslocamento();
                if (deslocamento >= posInicial && deslocamento <= posFinal) {
                    listaVariaveisRetorno.add(param);
                }
            }
        }
        return listaVariaveisRetorno;
    }

    /**
     * Método remove do mapa de niveis o nivel atual afim de limpar todas as
     * variaveis declaradas no mesmo.
     */
    public void limpaNivelAtual() {
        mapaDeSimbolos.remove(this.nivel);
    }
}
