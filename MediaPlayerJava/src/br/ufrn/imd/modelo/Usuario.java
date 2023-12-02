package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.List;

public abstract class Usuario {
	protected Integer id;
	protected String nome;
	protected String senha;
	protected String tipo;
	protected String avatar;
	protected List<Diretorio> diretorios;
	
	public Usuario() {
		this.diretorios = new ArrayList<>();
		avatar = "0";
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
	public String getAvatar() {
		return avatar;
	}
	
	public boolean diretorioExiste(String diretorio) {
		for(Diretorio d: diretorios) {
			if(d.getCaminho().equals(diretorio)) {
				return true;
			}
		}
		return false;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
}
