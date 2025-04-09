import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;

public class Frame extends JFrame {

    private JTextArea txtArea = new JTextArea();
    private JButton botaoAdicionar = new JButton("Adicionar");
    private JTextField txtLinha = new JTextField();
    private Procura janelaLocaliza = null;




    public Frame() {
        super("Exercicio 5, BLOCO DE NOTAS FALSO");


        // pergunta o nome
        String nomeUsuario = JOptionPane.showInputDialog(this, "Qual seu nome?", "Nome do Usuário", JOptionPane.QUESTION_MESSAGE);
        this.setTitle("Bloco de Notas - " + nomeUsuario);

        //config da janela
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        //painel principal
        JPanel painelCentral = new JPanel(new BorderLayout());
        this.add(painelCentral, BorderLayout.CENTER);

        //txtArea bloco maior
        txtArea = new JTextArea(30,100);
        txtArea.setEditable(true);
        painelCentral.add(new JScrollPane(txtArea), BorderLayout.CENTER); //adiciona no painel principal

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelInferior.add(botaoAdicionar);
        painelInferior.add(txtLinha);
        txtLinha.setPreferredSize(new Dimension(1000, 25));
        this.add(painelInferior, BorderLayout.SOUTH);

        botaoAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoLinha = txtLinha.getText();
                if (textoLinha != null && !textoLinha.trim().isEmpty()) {
                    txtArea.append(textoLinha + "\n");
                    txtLinha.setText("");
                } else {
                    JOptionPane.showMessageDialog(Frame.this, "Digite algo para adicionar!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setupKeyBindings();

        //interceptar o fechamento da janela
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Verifica se há alterações não salvas
                if (!txtArea.getText().isEmpty()) {
                    int resposta = JOptionPane.showConfirmDialog(Frame.this, "Deseja salvar as alterações?", "Salvar antes de sair", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (resposta == JOptionPane.YES_OPTION) {
                        salvarTexto();
                    } else if (resposta == JOptionPane.NO_OPTION) {
                        System.exit(0);
                    }
                } else {
                    System.exit(0);  //se não houver alterações
                }
            }
        });

        this.setResizable(false);
        this.setSize(500, 500);
        this.pack();
        this.setVisible(true);
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    private void setupKeyBindings() {
        InputMap inputMap = txtArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = txtArea.getActionMap();

        //abrir
        KeyStroke ctrlFKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK);
        inputMap.put(ctrlFKeyStroke, "showFindDialog");

        actionMap.put("showFindDialog", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (janelaLocaliza == null || !janelaLocaliza.isVisible()) {
                    janelaLocaliza = new Procura(Frame.this, txtArea);
                }
            }
        });

        //salvar
        KeyStroke ctrlSKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
        inputMap.put(ctrlSKeyStroke, "saveText");

        actionMap.put("saveText", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarTexto();
            }
        });

        // Ctrl + O para abrir
        KeyStroke ctrlOKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
        inputMap.put(ctrlOKeyStroke, "openFile");

        actionMap.put("openFile", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirArquivo();
            }
        });
    }

    private void salvarTexto() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showSaveDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {
                writer.write(txtArea.getText());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void abrirArquivo() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();
            try {
                String conteudo = new String(Files.readAllBytes(arquivo.toPath()));
                txtArea.setText(conteudo);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}