import com.sun.source.doctree.AttributeTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Frame extends JFrame implements ActionListener {


    /**
     * NÃO FUNCIONOU, DESISTO
     */
  /*   private KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            // Ctrl+F foi apertado
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F) {
                //abrir mensagem na tela
                JOptionPane.showMessageDialog(Frame.this, "Criação cancelada.");
                Procura procura = new Procura();
            }
            if(KeyEvent.VK_J==e.getKeyCode()){
                JOptionPane.showMessageDialog(Frame.this, "Criação cancelada.");
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F) {
                JOptionPane.showMessageDialog(Frame.this, "Criação cancelada.");
                Procura procura = new Procura();
            }

            if(KeyEvent.VK_J==e.getKeyCode()){
                JOptionPane.showMessageDialog(Frame.this, "Criação cancelada.");
            }
        }
    };*/

    private JPanel painelCentral = new JPanel();
    private BorderLayout estiloPainelCentral = new BorderLayout();

    private JPanel painelSuperior= new JPanel();
    private FlowLayout estiloPainelSuperior= new FlowLayout();

    private JPanel painelInferior = new JPanel();
    private FlowLayout estiloPainelInferior = new FlowLayout();

    private JTextArea txtArea = new JTextArea();
    private JButton botaoAdicionar = new JButton();
    private JTextField txtLinha = new JTextField();

    private Procura janelaLocaliza = null;



    public Frame() {

        super("Exercicio 4, BLOCO DE NOTAS FALSO");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        //comandos do teclado
        //this.addKeyListener(keyListener); //LARGUEI GURIZADA


        //ESTILO PAINEL PRINCIPAL
        estiloPainelCentral = new BorderLayout();
        painelCentral = new JPanel(estiloPainelCentral);

        this.add(painelCentral, BorderLayout.CENTER); //ADICIONA COMO PRINCIPAL


        //DECLARA variaveis
        txtArea = new JTextArea(30,100);
        txtArea.setEditable(true);
        botaoAdicionar.setText("Adicionar");
        txtLinha = new JTextField();
        txtLinha.setPreferredSize(new Dimension(1000, 25));

        //PAINEL SUPERIOR
        estiloPainelSuperior = new FlowLayout(); //puxa para esquerda
        painelSuperior.setLayout(estiloPainelInferior);
        //adiciona no painel superior
        painelSuperior.add(txtArea);
        this.add(painelSuperior);//add no principal


        //PAINEL INFERIOR
        estiloPainelInferior = new FlowLayout(FlowLayout.LEFT); //puxa para esquerda
        painelInferior.setLayout(estiloPainelInferior);
        //add botao e campo nele
        painelInferior.add(botaoAdicionar);
        painelInferior.add(txtLinha);

        add(painelInferior, BorderLayout.SOUTH); //add no principal


        //CONFIG TECLAS - TROCADO EM INVES DAQUELE OUTRO QUE DA CERTO NEM COM REZA
        setupKeyBindings();

        //Adicionando Listener no "butão"
        botaoAdicionar.addActionListener(this);


        this.setResizable(false);
        this.setSize(500, 500);
        this.pack();
        this.setVisible(true);

        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    //coloca os componentes no trabalho
    @Override
    public void actionPerformed(ActionEvent e) {

        //SE PRESSIONAR "Adicionar"
        if (e.getSource() == botaoAdicionar) {
            String textoLinha = txtLinha.getText(); // Pega texto da txtLinha

            // da uma olhada se o texto não está vazio
            if (textoLinha != null && !textoLinha.trim().isEmpty()) {

                // Add o texto na txtArea com uma espaco para ficar mais melhor de bom
                txtArea.append(textoLinha + "\n");
                txtLinha.setText("");  // limpa o campo txtLinha após add
            } else {
                JOptionPane.showMessageDialog(this, "Digite algo para adicionar!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



        /// SUBSTITUDO PARA O KeyListiner
    private void setupKeyBindings() {
        // adicionando comando para Ctrl+F
        InputMap inputMap = txtArea.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);  // tem que ta no foco a janela e tem a opcao na parte principal
        ActionMap actionMap = txtArea.getActionMap();

        // mapeia Ctrl+F
        KeyStroke ctrlFKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK);
        inputMap.put(ctrlFKeyStroke, "showFindDialog");

        // Define a ação para a tecla Ctrl+F
        actionMap.put("showFindDialog", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (janelaLocaliza == null || !janelaLocaliza.isVisible()) {
                    janelaLocaliza = new Procura(txtArea);  // Passando a referência de txtArea para a janela de Procura
                }
            }
        });
    }


}
