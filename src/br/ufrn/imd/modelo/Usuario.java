package br.ufrn.imd.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata que representa um usuário.
 */
public abstract class Usuario {
	protected Integer id;
	protected String nome;
	protected String senha;
	protected String tipo;
	protected String avatar;
	protected List<Diretorio> diretorios;
	
	/**
	 * Construtor para a classe Usuario.
	 */
	public Usuario() {
		this.diretorios = new ArrayList<>();
		avatar = "0";
	}
	
	/**
	 * Retorna o ID do usuário.
	 * @return id.
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Define o ID do usuário.
	 * @param id.
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Retorna o nome do usuário.
	 * @return nome.
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Define o nome do usuário.
	 * @param nome.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Retorna a senha do usuário.
	 * @return senha.
	 */
	public String getSenha() {
		return senha;
	}
	
	/**
	 * Define a senha do usuário.
	 * @param senha.
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	/**
	 * Retorna o tipo do usuário.
	 * @return tipo.
	 */
	public String getTipo() {
		return tipo;
	}
	
	/**
	 * Define o tipo do usuário.
	 * @param tipo.
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * Retorna a lista de diretórios do usuário.
	 * @return diretorios.
	 */
	public List<Diretorio> getDiretorios() {
		return this.diretorios;
	}
	
	/**
	 * Adiciona um diretório à lista de diretórios do usuário.
	 * @param diretorio.
	 */
	public void addDiretorio(Diretorio diretorio) {
		this.diretorios.add(diretorio);
	}
	
	/**
	 * Retorna o avatar do usuário.
	 * @return avatar.
	 */
	public String getAvatar() {
		return avatar;
	}
	
	/**
	 * Verifica se um diretório existe na lista de diretórios do usuário.
	 * @param diretorio.
	 * @return true se o diretório existir, false caso contrário.
	 */
	public boolean diretorioExiste(String diretorio) {
		for(Diretorio d: diretorios) {
			if(d.getCaminho().equals(diretorio)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Define o avatar do usuário.
	 * @param avatar.
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
}
