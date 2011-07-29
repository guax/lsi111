package br.ufsc.inf.lsi111.compilador.gals;

import java.lang.reflect.InvocationTargetException;

public class Semantico implements Constants {

    public void executeAction(int action, Token token) throws SemanticError {
        System.out.printf("Acao #%d, Token: %s", action, token);
        try {
            this.getClass().getMethod("action" + action, Token.class).invoke(this, token);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            SemanticError semanticError;
            if (targetException instanceof SemanticError) {
                SemanticError s = (SemanticError) targetException;
                semanticError = new SemanticError(String.format("#%d - %s", action, s.getMessage()), token.getPosition());
            }
            else {
                semanticError = new SemanticError(
                        "Erro desconhecido ao executar acao semantica #" + action + ": " + targetException);
            }
            throw semanticError;
        } catch (Exception e) {
            throw new SemanticError("Acao semantica #" + action + " nao implementada");
        }
    }
}
