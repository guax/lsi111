package br.ufsc.inf.lsi111.compilador.semantico.id;

public enum Categoria {

        PROGRAMA, CONSTANTE, VARIAVEL, PROCEDIMENTO, FUNCAO, PARAMETRO;

        public String toString() {
                return super.toString().toLowerCase();
        }

}
