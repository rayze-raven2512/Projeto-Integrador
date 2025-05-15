import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditorNotaDialog extends JDialog {
    private JTextField campoTitulo;
    private JTextArea areaConteudo;
    private JButton btnSalvar, btnCancelar;
    private Nota notaResultado;

    public EditorNotaDialog(JFrame parent, Nota notaExistente) {
        super(parent, "Editor de Nota", true);
        setLayout(new BorderLayout(10, 10));

        // Título
        campoTitulo = new JTextField();
        campoTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        campoTitulo.setBorder(BorderFactory.createTitledBorder("Título"));

        // Conteúdo
        areaConteudo = new JTextArea(15, 30);
        areaConteudo.setFont(new Font("Arial", Font.PLAIN, 14));
        areaConteudo.setLineWrap(true);
        areaConteudo.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(areaConteudo);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Conteúdo"));

        // Botões
        btnSalvar = new JButton("Salvar");
        btnCancelar = new JButton("Cancelar");

        JPanel botoesPanel = new JPanel();
        botoesPanel.add(btnSalvar);
        botoesPanel.add(btnCancelar);

        // Preencher campos se for edição
        if (notaExistente != null) {
            campoTitulo.setText(notaExistente.getTitulo());
            areaConteudo.setText(notaExistente.getConteudo());
        }

        // Ações dos botões
        btnSalvar.addActionListener(e -> {
            String titulo = campoTitulo.getText().trim();
            String conteudo = areaConteudo.getText();
            if (titulo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "O título não pode estar vazio.");
            } else {
                notaResultado = new Nota(titulo, conteudo);
                dispose();
            }
        });

        btnCancelar.addActionListener(e -> {
            notaResultado = null;
            dispose();
        });

        add(campoTitulo, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(botoesPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    public Nota getNotaResultado() {
        return notaResultado;
    }
}

