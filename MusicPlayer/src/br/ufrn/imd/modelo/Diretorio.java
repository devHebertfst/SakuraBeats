package br.ufrn.imd.modelo;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class Diretorio {
	private String id;
	private ArrayList<Musica> musicas;
	
	public Diretorio() {
		UUID uuid = UUID.randomUUID();
		this.id = uuid.toString();
		musicas = new ArrayList<Musica>();
	}

	public String getId() {
		return id;
	}
	
	//Não permitiremos que o ID seja alterado. Esse ID deve ser gerado pelo sistema.
	/*public void setId(String id) {
		this.id = id;
	}*/

	public ArrayList<Musica> getMusicas() {
		return musicas;
	}

	public void addMusic(Musica musica) {
		this.musicas.add(musica);
	}
	
	public void removeMusic(Musica musica) {
		this.musicas.remove(musica);
	}
}
