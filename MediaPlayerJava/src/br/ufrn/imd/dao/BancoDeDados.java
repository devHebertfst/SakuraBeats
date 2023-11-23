package br.ufrn.imd.dao;

import java.util.HashMap;
import java.util.Map;

import br.ufrn.imd.modelo.Usuario;

public class BancoDeDados {
	private static BancoDeDados instance;
	private Map<String, Usuario> usuarios;
	
	private BancoDeDados() {
		usuarios = new HashMap<>();
	}
	
	public static BancoDeDados getInstance() {
		if(instance==null) {
			instance = new BancoDeDados();
		}
		return instance;
	}
	
	public void adicionarUsuario(Usuario usuario) {
		usuarios.put(usuario.getNome(), usuario);
	}
	
	public boolean usuarioExiste(String nome) {
        return usuarios.containsKey(nome);
    }
	
	public Usuario getUsuario(String nome) {
        return usuarios.get(nome);
    }
}
