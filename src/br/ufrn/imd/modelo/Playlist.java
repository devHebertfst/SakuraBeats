package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Classe para representar uma playlist.
 */
public class Playlist {
    private UUID id;
    private String nome;
    private List<Musica> musicas;
    private Integer idUsuario;

    /**
     * Construtor para a classe Playlist.
     * Gera um UUID aleatório para a playlist e inicializa o nome e a lista de músicas.
     * @param nome.
     */
    public Playlist(String nome) {
        UUID uuid = UUID.randomUUID();
        this.id = uuid;
        this.nome = nome;
        this.musicas = new ArrayList<>();
    }

    /**
     * Método para obter o ID da playlist.
     * @return id.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Método para definir o ID da playlist.
     * @param id.
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Método para obter o nome da playlist.
     * @return nome.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Método para definir o nome da playlist.
     * @param nome.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Método para obter as músicas da playlist.
     * @return musicas.
     */
    public List<Musica> getMusicas() {
        return musicas;
    }

    /**
     * Método para definir as músicas da playlist.
     * @param musicas.
     */
    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

    /**
     * Método para obter o ID do usuário da playlist.
     * @return id.
     */
    public Integer getIdUsuario() {
        return idUsuario;
    }

    /**
     * Método para definir o ID do usuário da playlist.
     * @param idUsuario.
     */
    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * Método para adicionar uma música à playlist.
     * @param musica.
     */
    public void addMusica(Musica musica) {
        musicas.add(musica);
    }

    /**
     * Método para remover uma música da playlist.
     * @param musica.
     */
    public void removerMusica(Musica musica) {
        musicas.remove(musica);
    }

    /**
     * Método para verificar se uma música existe na playlist.
     * @param musica.
     * @return Verdadeiro se a música existir, falso caso contrário.
     */
    public boolean musicaExiste(String musica) {
        for(Musica m: musicas) {
            if(m.getNome().equals(musica)) {
                return true;
            }
        }
        return false;
    }
}
