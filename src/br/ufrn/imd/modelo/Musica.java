package br.ufrn.imd.modelo;

import java.util.UUID;

/**
 * Classe para representar uma música.
 */
public class Musica {
    private UUID id;
    private String nome;
    private String caminho;

    /**
     * Construtor para a classe Musica.
     * Gera um UUID aleatório para a música e inicializa o nome e o caminho como strings vazias.
     */
    public Musica() {
        UUID uuid = UUID.randomUUID();
        this.id = uuid;

        this.nome = "";
        this.caminho = "";
    }

    /**
     * Método para obter o ID da música.
     * @return id.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Método para definir o ID da música.
     * @param id.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Método para obter o nome da música.
     * @return nome.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método para definir o nome da música.
     * @param nome.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Método para obter o caminho da música.
     * @return caminho.
     */
    public String getCaminho() {
        return caminho;
    }

    /**
     * Método para definir o caminho da música.
     * @param caminho.
     */
    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }
}
