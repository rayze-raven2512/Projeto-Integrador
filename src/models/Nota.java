package models;

import java.time.LocalDateTime;

public class Nota {
    private String id;
    private String titulo;
    private String conteudo;
    private LocalDateTime dataCriacao;
    private String prioridade; // "baixa", "media", "alta"
    private String cor; // Exemplo: "amarelo", "azul"

    public Nota(String id, String titulo, String conteudo, LocalDateTime dataCriacao, String prioridade, String cor) {
        this.id = id;
        this.titulo = titulo;
        this.conteudo = conteudo;
        this.dataCriacao = dataCriacao;
        this.prioridade = prioridade;
        this.cor = cor;
    }


}
