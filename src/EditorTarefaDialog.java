import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EditorTarefaDialog extends JDialog {
    private JTextField campoTitulo;
    private JTextArea campoDescricao;
    private JTextField campoData;
    private JComboBox<String> comboStatus;
    private boolean confirmado = false;
    private Tarefa tarefaResultado;

    public EditorTarefaDialog(JFrame parent, Tarefa tarefa) {
        super(parent, "Adicionar Tarefa", true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(new JLabel("Título:"), gbc);
        gbc.gridy++;
        campoTitulo = new JTextField(20);
        add(campoTitulo, gbc);

        gbc.gridy++;
        add(new JLabel("Descrição:"), gbc);
        gbc.gridy++;
        campoDescricao = new JTextArea(4, 20);
        campoDescricao.setLineWrap(true);
        campoDescricao.setWrapStyleWord(true);
        JScrollPane scrollDescricao = new JScrollPane(campoDescricao);
        add(scrollDescricao, gbc);

        gbc.gridy++;
        add(new JLabel("Data de Vencimento (dd/MM/yyyy):"), gbc);
        gbc.gridy++;
        campoData = new JTextField(20);
        add(campoData, gbc);

        gbc.gridy++;
        add(new JLabel("Status:"), gbc);
        gbc.gridy++;
        comboStatus = new JComboBox<>(new String[]{"Pendente", "Em andamento", "Mapeado", "Concluído"});
        add(comboStatus, gbc);

        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel botoesPanel = new JPanel(new FlowLayout());
        JButton btnOK = new JButton("OK");
        JButton btnCancel = new JButton("Cancel");
        botoesPanel.add(btnOK);
        botoesPanel.add(btnCancel);
        add(botoesPanel, gbc);

        btnOK.addActionListener(e -> {
            try {
                String titulo = campoTitulo.getText().trim();
                String descricao = campoDescricao.getText().trim();
                LocalDate data = LocalDate.parse(campoData.getText().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String status = (String) comboStatus.getSelectedItem();
                boolean concluida = status.equals("Concluído");

                if (tarefa == null) {
                    tarefaResultado = new Tarefa(titulo, descricao, data, concluida);
                } else {
                    tarefa.setTitulo(titulo);
                    tarefa.setDescricao(descricao);
                    tarefa.setDataVencimento(data);
                    tarefa.setConcluida(concluida);
                    tarefa.setStatus(status);
                    tarefaResultado = tarefa;
                }

                confirmado = true;
                dispose();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Data inválida. Use o formato dd/MM/yyyy.");
            }
        });

        btnCancel.addActionListener(e -> dispose());

        if (tarefa != null) {
            campoTitulo.setText(tarefa.getTitulo());
            campoDescricao.setText(tarefa.getDescricao());
            campoData.setText(tarefa.getDataVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            comboStatus.setSelectedItem(tarefa.getStatus());
        } else {
            campoData.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }

        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    public Tarefa getTarefaResultado() {
        return confirmado ? tarefaResultado : null;
    }
}

