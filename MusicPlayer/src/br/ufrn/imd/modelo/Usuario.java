package br.ufrn.imd.modelo;

public abstract class Usuario {
	protected Integer id;
	protected String nome;
	protected String senha;
	protected String tipo;
	protected Diretorio diretorio;
	
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
	public Diretorio getDiretorio() {
		return diretorio;
	}
	public void setDiretorio(Diretorio diretorio) {
		this.diretorio = diretorio;
	}
	
}
