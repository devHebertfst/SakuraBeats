package br.ufrn.imd.dao;

import java.util.HashMap;
import java.util.Map;

import br.ufrn.imd.modelo.UsuarioComum;

public class UsuarioComumDao {
	private static UsuarioComumDao instance;
	private Map<String, UsuarioComum> usuarios;
	
	private UsuarioComumDao() {
		usuarios = new HashMap<>();
	}
	
	public static UsuarioComumDao getInstance() {
		if(instance==null) {
			instance = new UsuarioComumDao();
		}
		return instance;
	}
	
	public void adicionarUsuario(UsuarioComum usuario) {
		usuarios.put(usuario.getNome(), usuario);
	}
	
	public boolean usuarioExiste(String nome) {
        return usuarios.containsKey(nome);
    }
	
	public UsuarioComum getUsuario(String nome) {
        return usuarios.get(nome);
    }
	
	
}
