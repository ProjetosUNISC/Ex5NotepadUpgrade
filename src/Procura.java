import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Procura extends JDialog {

    ArrayList<String> palavras = new ArrayList<>();
    private JTextArea txtArea;
    private JComboBox<String> comboBox;
    private JButton btnOK;
    private JButton btnCancelar;

    public Procura(JFrame parent, JTextArea txtArea) {
        super(parent, "Localizar", true);  // JDialog com modal

        this.txtArea = txtArea;

        setLayout(new BorderLayout());

        // Título
        JLabel tituloJanela = new JLabel("Localizar:");
        tituloJanela.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(tituloJanela, BorderLayout.NORTH);

        // ComboBox de busca
        comboBox = new JComboBox<>(palavras.toArray(new String[0]));
        comboBox.setFont(new Font("Arial", Font.BOLD, 16));
        comboBox.setEditable(true);
        comboBox.setPreferredSize(new Dimension(250, 30));
        JPanel painelCampo = new JPanel(new FlowLayout());
        painelCampo.add(comboBox);
        this.add(painelCampo, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        btnCancelar = new JButton("Cancelar");
        btnOK = new JButton("OK");

        // Adicionando os botões ao painel
        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnOK);
        this.add(painelBotoes, BorderLayout.SOUTH);

        //usando classe anônima
        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String textoSelecionado = comboBox.getEditor().getItem().toString();
                if (!palavras.contains(textoSelecionado)) {
                    palavras.add(textoSelecionado);
                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(palavras.toArray(new String[0]));
                    comboBox.setModel(model);
                }
                encontrarTexto();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setResizable(false);
        setSize(300, 150);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void encontrarTexto() {
        String textoParaProcurar = comboBox.getEditor().getItem().toString();
        int posicao = txtArea.getText().indexOf(textoParaProcurar);
        if (posicao >= 0) {
            txtArea.setSelectionStart(posicao);
            txtArea.setSelectionEnd(posicao + textoParaProcurar.length());
            txtArea.setSelectedTextColor(Color.BLUE);
        } else {
            JOptionPane.showMessageDialog(this, "Texto não encontrado!", "Resultado", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
