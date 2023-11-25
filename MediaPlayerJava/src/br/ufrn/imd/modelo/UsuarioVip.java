package br.ufrn.imd.modelo;

import java.util.HashMap;
import java.util.Map;

public class UsuarioVip extends Usuario {
	private Map<String, Playlist> playlists;
	
	public UsuarioVip() {
		playlists = new HashMap<>();
	}
}
