package br.ufsc.inf.lsi111.compilador.view;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Utilities;

import br.ufsc.inf.lsi111.compilador.CompiladorFacade;
import br.ufsc.inf.lsi111.compilador.gals.AnalysisError;
import br.ufsc.inf.lsi111.compilador.gals.LexicalError;
import br.ufsc.inf.lsi111.compilador.gals.Lexico;
import br.ufsc.inf.lsi111.compilador.gals.SemanticError;
import br.ufsc.inf.lsi111.compilador.gals.SyntaticError;
import br.ufsc.inf.lsi111.compilador.gals.Token;

/**
 * Classe com a implementação das interfaces do compilador.
 * 
 */
public class CompiladorMainView extends javax.swing.JFrame {

    private CompiladorFacade model;

    class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {

        public MyHighlightPainter(Color color) {
            super(color);
        }
    }
    Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(
            new Color(0, 0, 0, 15));

    public CompiladorMainView() {
        initComponents();
        model = new CompiladorFacade();
        sourceText.setCaretPosition(0);
        lexicalHighlight();
    }

    private void exceptionHandling(AnalysisError error) {
        if (error instanceof LexicalError) {
            outputText.setText("Erro lexico encontrado: ");
        } else if (error instanceof SyntaticError) {
            outputText.setText("Erro sintatico encontrado: ");
        } else if (error instanceof SemanticError) {
            outputText.setText("Erro semantico encontrado: ");
        } else {
            outputText.setText("Erro inesperado!");
        }
        outputText.append(error.getMessage() + " (posicao "
                + error.getPosition() + ")\n");
        sourceText.setCaretPosition(error.getPosition());
    }

    private void lexicalHighlight() {
        if (!sourceText.getText().isEmpty()) {
            Highlighter hilite = sourceText.getHighlighter();
            hilite.removeAllHighlights();
            Lexico lexico = new Lexico();
            lexico.setInput(sourceText.getText());
            try {
                Token tk = null;
                do {
                    tk = lexico.nextToken();
                    if (tk != null) {
                        if (tk.getId() == Lexico.t_proc || tk.getId() == Lexico.t_funcao) {
                            int start = tk.getPosition();
                            int end = Utilities.getParagraphElement(sourceText,
                                    start).getEndOffset();
                            hilite.addHighlight(tk.getPosition(), end,
                                    myHighlightPainter);
                        }
                    }
                } while (tk != null);
            } catch (AnalysisError error) {
                exceptionHandling(error);
            } catch (BadLocationException e) {
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        sourceText = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        outputText = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        openFileMenu = new javax.swing.JMenuItem();
        SaveFileMenu = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        SyntheticAnalysisMenu = new javax.swing.JMenuItem();
        LexicalAnalysisMenu = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Código Fonte"));

        sourceText.setColumns(20);
        sourceText.setFont(new java.awt.Font("Monospaced", 0, 13));
        sourceText.setRows(5);
        jScrollPane1.setViewportView(sourceText);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Output"));

        outputText.setColumns(20);
        outputText.setRows(5);
        jScrollPane2.setViewportView(outputText);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
                jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                jPanel2Layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane2,
                javax.swing.GroupLayout.DEFAULT_SIZE, 820,
                Short.MAX_VALUE).addContainerGap()));
        jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                jPanel2Layout.createSequentialGroup().addComponent(
                jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE,
                108, Short.MAX_VALUE).addContainerGap()));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
                jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                jPanel1Layout.createSequentialGroup().addGroup(
                jPanel1Layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.TRAILING).addGroup(
                javax.swing.GroupLayout.Alignment.LEADING,
                jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(
                jScrollPane1,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                850,
                Short.MAX_VALUE)).addGroup(
                jPanel1Layout.createSequentialGroup().addGap(
                12,
                12,
                12).addComponent(
                jPanel2,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                Short.MAX_VALUE))).addContainerGap()));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                javax.swing.GroupLayout.Alignment.TRAILING,
                jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1,
                javax.swing.GroupLayout.DEFAULT_SIZE, 427,
                Short.MAX_VALUE).addGap(18, 18, 18).addComponent(jPanel2,
                javax.swing.GroupLayout.PREFERRED_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.PREFERRED_SIZE).addContainerGap()));

        jMenu1.setText("Arquivo");

        openFileMenu.setText("Abrir");
        openFileMenu.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openFileMenuActionPerformed(evt);
            }
        });
        jMenu1.add(openFileMenu);

        SaveFileMenu.setText("Salvar");
        SaveFileMenu.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFileMenuActionPerformed(evt);
            }
        });
        jMenu1.add(SaveFileMenu);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Analisador");

        LexicalAnalysisMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_X,
                java.awt.event.InputEvent.CTRL_MASK));
        LexicalAnalysisMenu.setText("Léxico");
        LexicalAnalysisMenu.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lexicalAnalysisMenuActionPerformed(evt);
            }
        });
        jMenu2.add(LexicalAnalysisMenu);

        SyntheticAnalysisMenu.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER,
                java.awt.event.InputEvent.CTRL_MASK));
        SyntheticAnalysisMenu.setText("Sintático/Semântico");
        SyntheticAnalysisMenu.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                syntacticAnalysisMenuActionPerformed(evt);
            }
        });
        jMenu2.add(SyntheticAnalysisMenu);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Ajuda");

        jMenuItem5.setText("Sobre");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
                getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup().addContainerGap().addComponent(
                jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup().addContainerGap().addComponent(
                jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addContainerGap()));

        pack();
    }

    private void lexicalAnalysisMenuActionPerformed(
            java.awt.event.ActionEvent evt) {
        if (!sourceText.getText().isEmpty()) {
            Highlighter hilite = sourceText.getHighlighter();
            hilite.removeAllHighlights();
            outputText.setText("");
            try {
                String retorno = model.executaAnalisadorLexico(
                        myHighlightPainter, hilite, sourceText);
                outputText.setText(retorno);
            } catch (AnalysisError error) {
                exceptionHandling(error);
            } catch (BadLocationException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            outputText.setText("Programa fonte vazio!");
        }
    }

    private void syntacticAnalysisMenuActionPerformed(java.awt.event.ActionEvent evt) {
        if (!sourceText.getText().isEmpty()) {
            System.out.println("Inicio da analise Sintatica/Semantica.");
            outputText.setText("");
            try {
                String retorno = model.executaAnalisadorSintatico(sourceText.getText());
                outputText.setText(retorno);
            } catch (AnalysisError error) {
                exceptionHandling(error);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Fim da analise Sintatica/Semantica.");
            System.out.println();
        } else {
            outputText.setText("Programa fonte vazio!");
        }
    }

    private void openFileMenuActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Abrir");
        if (!sourceText.getText().isEmpty()) {
            int selectedOption = JOptionPane.showConfirmDialog(this,
                    "Deseja salvar o arquivo atual?", "Abrir arquivo",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (selectedOption == JOptionPane.YES_OPTION) {
                saveFileMenuActionPerformed(null);
            }
        }
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        int fileChooserReturn = fileChooser.showOpenDialog(this);
        if (fileChooserReturn == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                String text = null;
                FileInputStream fileReader = new FileInputStream(file);
                DataInputStream dataInput = new DataInputStream(fileReader);
                sourceText.setText("");
                while ((text = dataInput.readLine()) != null) {
                    if (sourceText.getText().isEmpty()) {
                        sourceText.setText(text);
                    } else {
                        sourceText.setText(sourceText.getText()
                                + System.getProperty("line.separator") + text);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            sourceText.setCaretPosition(0);
            lexicalHighlight();
        }
    }

    private void saveFileMenuActionPerformed(java.awt.event.ActionEvent evt) {
        System.out.println("Salvar");

        if (!sourceText.getText().isEmpty()) {
            JFileChooser fileChooser = new JFileChooser();

            int fileChooserReturn = fileChooser.showSaveDialog(this);

            if (fileChooserReturn == JFileChooser.APPROVE_OPTION) {
                FileWriter fileWriter = null;
                try {
                    String text = sourceText.getText();
                    File file = addExtensionToFile(fileChooser.getSelectedFile());
                    fileWriter = new FileWriter(file);
                    fileWriter.write(text);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    try {
                        fileWriter.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Nao ha codigo para ser salvo.", "Salvar arquivo",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {
        StringBuilder sb = new StringBuilder(
                "INE5622 - Trabalho de Compiladores\n");
        sb.append("Semestre: 2011/1\n\n");
        sb.append("Jonathan Kuntz Fornari\n");
        sb.append("Henrique Grolli Basotto");

        JOptionPane.showMessageDialog(this, sb, "Sobre",
                JOptionPane.PLAIN_MESSAGE);
    }

    private File addExtensionToFile(File pArquivo) {
        if (!pArquivo.getName().endsWith(".lsi")
                && !pArquivo.getName().endsWith(".txt")) {
            return new File(pArquivo.getAbsolutePath() + ".txt");
        } else {
            return pArquivo;
        }
    }

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.setProperty("apple.laf.useScreenMenuBar", "true");

                CompiladorMainView window = new CompiladorMainView();
                window.setTitle("LSI-111 Compiler");
                window.setVisible(true);

            }
        });
    }
    
    private javax.swing.JMenuItem LexicalAnalysisMenu;
    private javax.swing.JMenuItem SaveFileMenu;
    private javax.swing.JMenuItem SyntheticAnalysisMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JMenuItem openFileMenu;
    private javax.swing.JTextArea outputText;
    private javax.swing.JTextArea sourceText;
}
