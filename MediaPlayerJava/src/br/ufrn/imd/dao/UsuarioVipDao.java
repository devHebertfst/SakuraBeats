package br.ufrn.imd.dao;

import java.util.HashMap;
import java.util.Map;

import br.ufrn.imd.modelo.UsuarioComum;
import br.ufrn.imd.modelo.UsuarioVip;

public class UsuarioVipDao {
	private static UsuarioVipDao instance;
	private Map<String, UsuarioVip> usuarios;
	
	private UsuarioVipDao() {
		usuarios = new HashMap<>();
	}
	
	public static UsuarioVipDao getInstance() {
		if(instance==null) {
			instance = new UsuarioVipDao();
		}
		return instance;
	}
	
	public void adicionarUsuario(UsuarioVip usuario) {
		usuarios.put(usuario.getNome(), usuario);
	}
	
	public boolean usuarioExiste(String nome) {
        return usuarios.containsKey(nome);
    }
	
	public UsuarioVip getUsuario(String nome) {
        return usuarios.get(nome);
    }
	
}
