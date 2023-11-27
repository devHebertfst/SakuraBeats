package br.ufrn.imd.dao;

import java.util.HashMap;
import java.util.Map;

import br.ufrn.imd.modelo.Playlist;

public class BancoDePlaylists {
	private static BancoDePlaylists instance;
	private Map<Integer,Playlist> playlists;
	
	private BancoDePlaylists() {
		playlists = new HashMap<>();
	}
	
	public static BancoDePlaylists getInstance() {
		if(instance==null) {
			instance = new BancoDePlaylists();
		}
		return instance;
	}
	
	public void adicionarPlaylist(Playlist play) {
		playlists.put(play.getIdUsuario(), play);
	}
	
	public void removerPlaylist(Playlist play) {
		playlists.remove(play);
	}
}
