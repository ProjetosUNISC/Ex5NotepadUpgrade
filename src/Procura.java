import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Procura extends JFrame implements ActionListener {


    //lista onde fica salvo as palavras procuradas da comboBOX
    ArrayList<String> palavras = new ArrayList<>();


    private JTextArea txtArea;


    //variavel titulo
    private JLabel tituloJanela;

    //variavel painel texto
    private JPanel painelCampo;
    private FlowLayout flowCampo;
    private JComboBox<String> comboBox;


    //variaveis painel botoes
    private JPanel painelBotoes;
    private FlowLayout gradeBotoes;
    private JButton btnOK;
    private JButton btnCancelar;

    //função dos botoes
    @Override
    public void actionPerformed(ActionEvent e) {

        //STRING LOCAL
        String textoSelecionado;

        //BOTÃO DE CANCELAR
        if (e.getSource() == btnOK){

            //checa se alguém digitou alguma coisa na caixa
            if(comboBox.isEditable()) {
                textoSelecionado = comboBox.getEditor().getItem().toString();
                // se não tiver na lista ele coloca pra dentro
                if (!palavras.contains(textoSelecionado)) {
                    palavras.add(textoSelecionado);

                    // cria novo model ComboBOX e atualiza a ComboBOX
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(palavras.toArray(new String[0]));
                    comboBox.setModel(model);

                }

                //funcao ja diz o que faz
                encontrarTexto();
            }
            }else { // quando for item selecionado da lista
                textoSelecionado = comboBox.getSelectedItem().toString();

                // Verifica se o texto não é vazio
                if (textoSelecionado != null && !textoSelecionado.trim().isEmpty()) {

                    //funcao ja diz o que faz
                    encontrarTexto();
                }

        }

        //BOTÃO DE CANCELAR
        if (e.getSource() == btnCancelar){
            dispose();//fecha apenas a janela sem interromper o processo
        }
    }

        //FUNCAO PARA ENCONTRAR TEXTO NA AREA DE TEXTO
        private void encontrarTexto() {
            String textoParaProcurar = comboBox.getEditor().getItem().toString();

            // encontra a posição do texto dentro de txtArea
            int posicao = txtArea.getText().indexOf(textoParaProcurar);

            if (posicao >= 0) {
                // Se o texto for encontrado, selecione a parte encontrada
                txtArea.setSelectionStart(posicao);
                txtArea.setSelectionEnd(posicao + textoParaProcurar.length());
                txtArea.setSelectedTextColor(Color.BLUE); // cor do texto selecionado
            } else {
                JOptionPane.showMessageDialog(this, "Texto não encontrado!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    public Procura(JTextArea txtArea){

        super("Localizar");
        this.setLocationRelativeTo(null);

        this.txtArea = txtArea;


        //CRIA TITULO
        tituloJanela = new JLabel("Localizar:");
        tituloJanela.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(tituloJanela, BorderLayout.NORTH); //INSERE ELE EM CIMA


        //CRIA CAMPO TEXTO PROCURA
        comboBox = new JComboBox<>(palavras.toArray(new String[0]));
        comboBox.setFont(new Font("Arial", Font.BOLD, 16));
        comboBox.setEditable(true);
        comboBox.setPreferredSize(new Dimension(250, 30));

        flowCampo = new FlowLayout(); //instancia layout
        painelCampo = new JPanel(flowCampo); //joga ele no panel

        painelCampo.add(comboBox);

        this.add(painelCampo, BorderLayout.CENTER);


        //PAINEL DE BOTOES em flow PARA POR NO SOUTH DA BORDER(mais bonito)
        gradeBotoes = new FlowLayout(); //da o estilo para a variveel
        painelBotoes = new JPanel(gradeBotoes); //e coloca ele num painel

        //botoes e conf
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.PLAIN, 15));
        btnOK = new JButton("OK");
        btnOK.setFont(new Font("Arial", Font.PLAIN, 15));

        //adiciono botoes na grade
        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnOK);

        this.add(painelBotoes, BorderLayout.SOUTH); //adiciona painel na base

        //adiciona ação nos botoes conforme conf
        btnOK.addActionListener(this);
        btnCancelar.addActionListener(this);


        this.setResizable(false);
        this.setSize(300, 150);
        this.setVisible(true);

    }



}
