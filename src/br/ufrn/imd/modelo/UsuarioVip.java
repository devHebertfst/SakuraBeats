package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe que representa um usuário VIP do sistema de tocador de MP3.
 * Herda da classe abstrata Usuario.
 */
public class UsuarioVip extends Usuario {
    private Map<String, Playlist> playlists;

    /**
     * Construtor para a classe UsuarioVip.
     * Inicializa o atributo playlists e define o tipo do usuário como "Vip".
     */
    public UsuarioVip() {
        tipo = "Vip";
        playlists = new HashMap<>();
    }

    /**
     * Método para criar uma playlist com um nome dado e associá-la ao usuário.
     * @param nomePlaylist.
     */
    public void criarPlaylist(String nomePlaylist) {
        Playlist playlist = new Playlist(nomePlaylist);
        playlist.setIdUsuario(getId());
        playlists.put(nomePlaylist, playlist);
    }

    /**
     * Método para adicionar uma música a uma playlist existente do usuário.
     * @param nomePlayList.
     * @param musica.
     */
    public void adicionarMusica(String nomePlayList, Musica musica) {
        Playlist playlist = buscaPlaylist(nomePlayList);
        if(playlist!=null) {
            playlist.addMusica(musica);
        }
    }

    /**
     * Método para verificar se o usuário possui uma playlist com um nome dado.
     * @param play.
     * @return Verdadeiro se o usuário possuir a playlist, falso caso contrário.
     */
    public boolean playlistExiste(String play) {
        return playlists.containsKey(play);
    }

    /**
     * Método para retornar a playlist do usuário com um nome dado, se ela existir.
     * @param nomePlaylist.
     * @return A playlist com o nome dado, ou null se não existir.
     */
    public Playlist buscaPlaylist(String nomePlaylist) {
        return playlists.get(nomePlaylist);
    }

    /**
     * Método para adicionar uma playlist já existente ao usuário.
     * @param playlist.
     */
    public void adicionarPlaylist(Playlist playlist) {
        this.playlists.put(playlist.getNome(), playlist);
    }

    /**
     * Método para retornar uma lista com todas as playlists do usuário.
     * @return Uma lista com todas as playlists do usuário.
     */
    public List<Playlist> getPlaylists() {
        return new ArrayList<Playlist>(playlists.values());
    }
}
