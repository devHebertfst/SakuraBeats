package br.ufrn.imd.modelo;

import java.util.HashMap;
import java.util.Map;

public class UsuarioVip extends Usuario {
	private Map<String, Playlist> playlists;
	
	public UsuarioVip() {
		tipo = "Vip";
		playlists = new HashMap<>();
	}
	
	public void criarPlaylist(String nomePlaylist) {
		Playlist playlist = new Playlist(nomePlaylist);
		playlist.setIdUsuario(getId());
		playlists.put(nomePlaylist, playlist);
	}
	
	public void adicionarMusica(String nomePlayList, Musica musica) {
		Playlist playlist = buscaPlaylist(nomePlayList);
		if(playlist!=null) {
			playlist.addMusica(musica);
		}
	}
	
	public boolean playlistExiste(String play) {
		return playlists.containsKey(play);
	}
	
	public Playlist buscaPlaylist(String nomePlaylist) {
	    return playlists.get(nomePlaylist);
	}
	
}
