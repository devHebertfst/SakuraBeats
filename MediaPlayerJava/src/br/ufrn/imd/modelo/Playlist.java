package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Playlist {
	private UUID id;
	private String nome;
	private List<Musica> musicas;
	private Integer idUsuario;
	
	public Playlist(String nome) {
		UUID uuid = UUID.randomUUID();
		this.id = uuid;
		this.nome = nome;
		this.musicas = new ArrayList<>();
	}


	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	
	public void addMusica(Musica musica) {
		musicas.add(musica);
	}
	
	public void removerMusica(Musica musica) {
		musicas.remove(musica);
	}
	
	public boolean musicaExiste(String musica) {
		for(Musica m: musicas) {
			if(m.getNome().equals(musica)) {
				System.out.println("cheguei aqui!");
				return true;
			}
		}
		return false;
	}
}
