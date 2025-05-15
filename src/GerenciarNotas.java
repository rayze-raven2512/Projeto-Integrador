import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatDarkLaf;

public class GerenciarNotas extends JFrame {
    private DefaultListModel<Nota> listModel;
    private JList<Nota> listaNotas;
    private JButton btnAdicionar, btnEditar, btnExcluir, btnSalvar, btnModoEscuro;
    private List<Nota> notas;

    public GerenciarNotas() {
        super("Gerenciador de Notas");
        notas = DataService.carregarNotas();

        listModel = new DefaultListModel<>();
        notas.forEach(listModel::addElement);

        listaNotas = new JList<>(listModel);
        listaNotas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        listaNotas.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Nota) {
                    Nota nota = (Nota) value;
                    label.setText("<html><b>" + nota.getTitulo() + "</b><br/><small>" + resumo(nota.getConteudo()) + "</small></html>");
                    label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                }
                return label;
            }

            private String resumo(String texto) {
                return texto.length() > 30 ? texto.substring(0, 30) + "..." : texto;
            }
        });

        btnAdicionar = new JButton("Adicionar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnSalvar = new JButton("Salvar");

        btnModoEscuro = new JButton("Modo Escuro");
        btnModoEscuro.addActionListener(e -> alternarModoEscuro());

        btnAdicionar.addActionListener(e -> adicionarNota());
        btnEditar.addActionListener(e -> editarNota());
        btnExcluir.addActionListener(e -> excluirNota());
        btnSalvar.addActionListener(e -> salvarNotas());

        JPanel botoesPanel = new JPanel(new FlowLayout());
        botoesPanel.add(btnAdicionar);
        botoesPanel.add(btnEditar);
        botoesPanel.add(btnExcluir);
        botoesPanel.add(btnSalvar);
        botoesPanel.add(btnModoEscuro);

        JTabbedPane abas = new JTabbedPane();

        JPanel painelNotas = new JPanel(new BorderLayout());
        painelNotas.add(new JScrollPane(listaNotas), BorderLayout.CENTER);
        painelNotas.add(botoesPanel, BorderLayout.SOUTH);

        JPanel painelTarefas = criarPainelTarefas();
        JPanel painelConcluidas = criarPainelConcluidas();

        abas.addTab("Notas", painelNotas);
        abas.addTab("Tarefas", painelTarefas);
        abas.addTab("Concluídas", painelConcluidas);

        add(abas, BorderLayout.CENTER);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void adicionarNota() {
        EditorNotaDialog dialog = new EditorNotaDialog(this, null);
        Nota novaNota = dialog.getNotaResultado();
        if (novaNota != null) {
            listModel.addElement(novaNota);
        }
    }

    private void editarNota() {
        Nota notaSelecionada = listaNotas.getSelectedValue();
        if (notaSelecionada != null) {
            EditorNotaDialog dialog = new EditorNotaDialog(this, notaSelecionada);
            Nota notaEditada = dialog.getNotaResultado();
            if (notaEditada != null) {
                notaSelecionada.setTitulo(notaEditada.getTitulo());
                notaSelecionada.setConteudo(notaEditada.getConteudo());
                listaNotas.repaint();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma nota para editar.");
        }
    }

    private void excluirNota() {
        Nota notaSelecionada = listaNotas.getSelectedValue();
        if (notaSelecionada != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                listModel.removeElement(notaSelecionada);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma nota para excluir.");
        }
    }

    private void salvarNotas() {
        List<Nota> notasParaSalvar = new ArrayList<>();
        for (int i = 0; i < listModel.size(); i++) {
            notasParaSalvar.add(listModel.getElementAt(i));
        }
        DataService.salvarNotas(notasParaSalvar);
        JOptionPane.showMessageDialog(this, "Notas salvas com sucesso!");
    }

    private void alternarModoEscuro() {
        try {
            if (UIManager.getLookAndFeel().getClass().getName().equals("com.formdev.flatlaf.FlatLightLaf")) {
                UIManager.setLookAndFeel(new FlatDarkLaf());
                btnModoEscuro.setText("Modo Claro");
            } else {
                UIManager.setLookAndFeel(new FlatLightLaf());
                btnModoEscuro.setText("Modo Escuro");
            }
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    private DefaultListModel<Tarefa> tarefasModel = new DefaultListModel<>();
    private DefaultListModel<Tarefa> concluidasModel = new DefaultListModel<>();
    private JList<Tarefa> listaTarefas = new JList<>(tarefasModel);
    private JList<Tarefa> listaConcluidas = new JList<>(concluidasModel);
    private JButton btnAddTarefa = new JButton("Adicionar");
    private JButton btnEditTarefa = new JButton("Editar");
    private JButton btnDelTarefa = new JButton("Excluir");
    private JButton btnSalvarTarefas = new JButton("Salvar");

    private JPanel criarPainelTarefas() {
        listaTarefas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaTarefas.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Tarefa tarefa) {
                    boolean vencida = tarefa.getDataVencimento().isBefore(LocalDate.now()) && !tarefa.isConcluida();
                    label.setText("<html><b>" + (tarefa.isConcluida() ? "✔️ " : "⬜ ") + tarefa.getTitulo() + "</b> - <i>" + tarefa.getStatus() + "</i><br/>" +
                            "<small>Vence: " + tarefa.getDataVencimento() + "</small></html>");
                    label.setForeground(vencida ? Color.RED : Color.BLACK);
                }
                return label;
            }
        });

        btnAddTarefa.addActionListener(e -> adicionarTarefa());
        btnEditTarefa.addActionListener(e -> editarTarefa());
        btnDelTarefa.addActionListener(e -> excluirTarefa());
        btnSalvarTarefas.addActionListener(e -> salvarTarefas());

        JPanel botoesPanel = new JPanel(new FlowLayout());
        botoesPanel.add(btnAddTarefa);
        botoesPanel.add(btnEditTarefa);
        botoesPanel.add(btnDelTarefa);
        botoesPanel.add(btnSalvarTarefas);

        JPanel painel = new JPanel(new BorderLayout());
        painel.add(new JScrollPane(listaTarefas), BorderLayout.CENTER);
        painel.add(botoesPanel, BorderLayout.SOUTH);
        return painel;
    }

    private JPanel criarPainelConcluidas() {
        listaConcluidas.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Tarefa tarefa) {
                    label.setText("<html><b>✔️ " + tarefa.getTitulo() + "</b> - <i>" + tarefa.getStatus() + "</i><br/>" +
                            "<small>Venceu: " + tarefa.getDataVencimento() + "</small></html>");
                }
                return label;
            }
        });

        JPanel painel = new JPanel(new BorderLayout());
        painel.add(new JScrollPane(listaConcluidas), BorderLayout.CENTER);
        return painel;
    }

    private void adicionarTarefa() {
        EditorTarefaDialog dialog = new EditorTarefaDialog(this, null);
        Tarefa novaTarefa = dialog.getTarefaResultado();
        if (novaTarefa != null) {
            if (novaTarefa.isConcluida()) {
                concluidasModel.addElement(novaTarefa);
            } else {
                tarefasModel.addElement(novaTarefa);
            }
        }
    }

    private void editarTarefa() {
        Tarefa selecionada = listaTarefas.getSelectedValue();
        if (selecionada != null) {
            EditorTarefaDialog dialog = new EditorTarefaDialog(this, selecionada);
            Tarefa tarefaEditada = dialog.getTarefaResultado();
            if (tarefaEditada != null) {
                tarefasModel.removeElement(selecionada);
                if (tarefaEditada.isConcluida()) {
                    concluidasModel.addElement(tarefaEditada);
                } else {
                    tarefasModel.addElement(tarefaEditada);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma tarefa para editar.");
        }
    }

    private void excluirTarefa() {
        Tarefa selecionada = listaTarefas.getSelectedValue();
        if (selecionada != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Excluir tarefa?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                tarefasModel.removeElement(selecionada);
            }
        }
    }

    private void salvarTarefas() {
        JOptionPane.showMessageDialog(this, "Tarefas salvas (temporariamente apenas na memória).");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Erro ao aplicar o tema.");
        }

        SwingUtilities.invokeLater(() -> {
            new GerenciarNotas().setVisible(true);
        });
    }
}
