package models;

import java.time.LocalDateTime;

public class Tarefa {
    private String id;
    private String titulo;
    private String descricao;
    private boolean concluida;
    private LocalDateTime dataCriacao;
    private String prioridade;
    private String cor;

    public Tarefa(String id, String titulo, String descricao, boolean concluida, LocalDateTime dataCriacao, String prioridade, String cor) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.concluida = concluida;
        this.dataCriacao = dataCriacao;
        this.prioridade = prioridade;
        this.cor = cor;
    }

}

