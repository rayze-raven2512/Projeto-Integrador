import java.io.Serializable;
import java.time.LocalDate;

public class Tarefa implements Serializable {
    private String titulo;
    private String descricao;
    private LocalDate dataVencimento;
    private boolean concluida;
    private String status; // Adicionamos este campo

    public Tarefa(String titulo, String descricao, LocalDate dataVencimento, boolean concluida) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataVencimento = dataVencimento;
        this.concluida = concluida;
        this.status = "Pendente"; // Valor padrão
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(LocalDate dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
