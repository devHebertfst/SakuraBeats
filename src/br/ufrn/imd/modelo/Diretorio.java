package br.ufrn.imd.modelo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Classe que representa um diretório de músicas.
 */
public class Diretorio {
    private final UUID id; 
    private String caminho;
    private List<Musica> musicas;

    /**
     * Construtor para a classe Diretorio.
     * @param caminho.
     */
    public Diretorio(String caminho) {
        this.caminho = caminho;
        this.id = UUID.randomUUID();
        this.musicas = new ArrayList<>();
        carregarMusicas();
    }

    /**
     * Retorna o ID do diretório.
     * @return id.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Retorna o caminho do diretório.
     * @return caminho.
     */
    public String getCaminho() {
        return caminho;
    }

    /**
     * Define o caminho do diretório.
     * @param caminho.
     */
    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    /**
     * Retorna a lista de músicas no diretório.
     * @return musicas.
     */
    public List<Musica> getMusicas() {
        return musicas;
    }

    /**
     * Define a lista de músicas no diretório.
     * @param musicas .
     */
    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

    /**
     * Carrega as músicas do diretório.
     * Este método lê os arquivos da pasta e cria objetos Musica para cada um.
     */
    public void carregarMusicas() {
        File pasta = new File(caminho);
        File[] arquivos = pasta.listFiles();
        if (arquivos != null) {
            for (File arquivo : arquivos) {
                if (arquivo.isFile() && arquivo.getName().endsWith(".mp3")) {
                    Musica musica = new Musica();
                    musica.setCaminho(arquivo.getAbsolutePath());
                    musica.setNome(arquivo.getName());
                    musicas.add(musica);
                }
            }
        }
    }
}
