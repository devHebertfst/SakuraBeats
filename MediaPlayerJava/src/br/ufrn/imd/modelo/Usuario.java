package br.ufrn.imd.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Usuario implements Serializable {
	protected Integer id;
	protected String nome;
	protected String senha;
	protected String tipo;
	protected List<Diretorio> diretorios;

	public Usuario() {
		this.diretorios = new ArrayList<>();
		this.senha = "";
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
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public List<Diretorio> getDiretorios() {
		return this.diretorios;
	}
	public void addDiretorio(Diretorio diretorio) {
		this.diretorios.add(diretorio);
	}
}
