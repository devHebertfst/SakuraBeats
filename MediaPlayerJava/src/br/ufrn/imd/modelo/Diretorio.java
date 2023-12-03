package br.ufrn.imd.modelo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Diretorio {
    private final UUID id; // o identificador único do diretório no sistema
    private String caminho; // o caminho da pasta no computador
    private List<Musica> musicas; // a lista de músicas que estão na pasta

    public Diretorio(String caminho) {
        this.caminho = caminho;
        this.id = UUID.randomUUID();
        this.musicas = new ArrayList<>();
        carregarMusicas();
    }

    public UUID getId() {
        return id;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

    // este método lê os arquivos da pasta e cria objetos Musica para cada um
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