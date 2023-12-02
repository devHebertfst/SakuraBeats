package br.ufrn.imd.modelo;

import java.util.UUID;

public class Musica {
	private String id;
	private String nome;
	private String caminho;
	
	public Musica() {
		UUID uuid = UUID.randomUUID();
		this.id = uuid.toString();
		
		this.nome = "";
		this.caminho = "";
	}
	
	public String getId() {
		return id;
	}
	//N�o permitiremos que o ID seja alterado. Esse ID deve ser gerado pelo sistema.
	/*public void setId(String id) {
		this.id = id;
	}*/
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCaminho() {
		return caminho;
	}
	
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
}