package br.ufsc.inf.lsi111.compilador;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Highlighter;
import javax.swing.text.Utilities;

import br.ufsc.inf.lsi111.compilador.gals.LexicalError;
import br.ufsc.inf.lsi111.compilador.gals.Lexico;
import br.ufsc.inf.lsi111.compilador.gals.SemanticError;
import br.ufsc.inf.lsi111.compilador.gals.Semantico;
import br.ufsc.inf.lsi111.compilador.gals.Sintatico;
import br.ufsc.inf.lsi111.compilador.gals.SyntaticError;
import br.ufsc.inf.lsi111.compilador.gals.Token;

/**
 * Classe que serve como fachada para executar a invocação dos analisadores
 * léxico, sintático e futuramente semântica.
 * 
 * Em padrões de projeto de software, um façade (fachada em francês) é um objeto
 * que disponibiliza uma interface simplificada para as funcionalidades de
 * uma API.
 * 
 */
public class CompiladorFacade {

	public String executaAnalisadorLexico(
			Highlighter.HighlightPainter myHighlightPainter,
			Highlighter hilite, JTextArea sourceText) throws LexicalError,
			BadLocationException, Exception {
		Lexico lexico = new Lexico();
		lexico.setInput(sourceText.getText());
		Token tk = null;
		do {
			tk = lexico.nextToken();
			if (tk != null) {
				if (tk.getId() == Lexico.t_programa
						|| tk.getId() == Lexico.t_proc
						|| tk.getId() == Lexico.t_funcao) {
					int start = tk.getPosition();
					int end = Utilities.getParagraphElement(sourceText, start)
							.getEndOffset();
					hilite.addHighlight(tk.getPosition(), end,
							myHighlightPainter);
				}
			}
		} while (tk != null);

		return "O programa fonte está lexicamente correto.";
	}

	public String executaAnalisadorSintatico(String text) throws LexicalError,
			SyntaticError, SemanticError, Exception {
		Lexico lexico = new Lexico();
		lexico.setInput(text);
		Sintatico syntactic = new Sintatico();
		Semantico semantic = new Semantico();
		// Executa análise
		syntactic.parse(lexico, semantic);
		return "O programa fonte está sintaticamente correto.";
	}
}
