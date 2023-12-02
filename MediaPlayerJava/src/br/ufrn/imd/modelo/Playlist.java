package br.ufrn.imd.modelo;

import java.util.HashMap;
import java.util.Map;

public class Playlist {
	private Integer id;
	private String nome;
	private Map<String, Musica> musicas;
	private Integer idUsuario;
	
	public Playlist(String nome) {
		this.nome = nome;
		musicas = new HashMap<>();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Map<String, Musica> getMusicas() {
		return musicas;
	}

	public void setMusicas(Map<String, Musica> musicas) {
		this.musicas = musicas;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public void addMusica(Musica musica) {
		musicas.put(musica.getNome(), musica);
	}
	
	public void removerMusica(Musica musica) {
		musicas.remove(musica.getNome());
	}
}
