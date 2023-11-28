package br.ufrn.imd.modelo;

import java.util.HashMap;
import java.util.Map;

public class Playlist {
	private Integer id;
	private String nome;
	private Map<String, Musica> musicas;
	private Integer idUsuario;
	
	public Playlist() {
		musicas = new HashMap<>();
	}
}
