package br.ufrn.imd.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import br.ufrn.imd.modelo.Usuario;
import br.ufrn.imd.modelo.UsuarioComum;
import br.ufrn.imd.modelo.UsuarioVip;

/**
 * Classe que representa um banco de dados de usuários.
 */
public class BancoDeDados {
	private static BancoDeDados instance;
	private Map<String, Usuario> usuarios;
	private int idCounter;
	
	/**
	 * Construtor privado para a classe BancoDeDados.
	 */
	private BancoDeDados() {
		usuarios = new HashMap<>();
		idCounter = 1;
	}
	
	/**
	 * Retorna a instância única do banco de dados.
	 * @return instance.
	 */
	public static BancoDeDados getInstance() {
		if(instance==null) {
			instance = new BancoDeDados();
		}
		return instance;
	}
	
	/**
	 * Adiciona um usuário ao banco de dados.
	 * @param usuario
	 */
	public void adicionarUsuario(Usuario usuario) {
		usuario.setId(idCounter++);
		usuarios.put(usuario.getNome(), usuario);
		salvarUsuariosEmArquivo();
	}
	
	/**
	 * Verifica se um usuário existe no banco de dados.
	 * @param nome
	 * @return true se o usuário existir, false caso contrário.
	 */
	public boolean usuarioExiste(String nome) {
        return usuarios.containsKey(nome);
    }
	
	/**
	 * Retorna um usuário do banco de dados.
	 * @param nome
	 * @return O usuário, ou null se o usuário não existir.
	 */
	public Usuario getUsuario(String nome) {
        return usuarios.get(nome);
    }
	
	/**
	 * Salva os usuários do banco de dados em um arquivo.
	 */
	public void salvarUsuariosEmArquivo() {
        try (PrintWriter writer = new PrintWriter("dados/usuarios.txt")) {
            for (Usuario usuario : usuarios.values()) {
                writer.println("ID: " + usuario.getId());
                writer.println("Nome: " + usuario.getNome());
                writer.println("Senha: " + usuario.getSenha());
                writer.println("Tipo: " + usuario.getTipo());
                writer.println("Avatar: " + usuario.getAvatar());
                writer.println();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários em arquivo: " + e.getMessage());
        }
    }
	
	/**
	 * Carrega os usuários de um arquivo para o banco de dados.
	 */
	public void carregarUsuariosDeArquivo() {
	    try (Scanner scanner = new Scanner(new File("dados/usuarios.txt"))) {
	        while (scanner.hasNext()) {
	            String idLine = scanner.nextLine();
	            String nomeLine = scanner.nextLine();
	            String senhaLine = scanner.nextLine();
	            String tipoLine = scanner.nextLine();
	            String avatarLine = scanner.nextLine();
	            
	            if (!idLine.startsWith("ID: ") || !nomeLine.startsWith("Nome: ") || !senhaLine.startsWith("Senha: ") || !tipoLine.startsWith("Tipo: ") || !avatarLine.startsWith("Avatar: "))  {
	                throw new IllegalArgumentException("Formato de arquivo inválido");
	            }
	            String tipo = tipoLine.split(": ")[1];
	            Usuario usuario;
	            if (tipo.equals("Vip")) {
	                usuario = new UsuarioVip();
	            } else if (tipo.equals("Comum")) {
	                usuario = new UsuarioComum();
	            } else {
	                throw new IllegalArgumentException("Tipo desconhecido: " + tipo);
	            }

	            usuario.setId(Integer.parseInt(idLine.split(": ")[1]));
	            usuario.setNome(nomeLine.split(": ")[1]);
	            usuario.setSenha(senhaLine.split(": ")[1]);
	            usuario.setTipo(tipo);
	            usuario.setAvatar(avatarLine.split(": ")[1]);
	            adicionarUsuario(usuario);
	            if (scanner.hasNext()) {
	                scanner.nextLine();
	            }
	        }
	    } catch (FileNotFoundException e) {
	        System.out.println("Erro ao carregar usuários de arquivo: " + e.getMessage());
	    }
	}

	/**
	 * Retorna um mapa de todos os usuários no banco de dados.
	 * @return Map
	 */
	public Map<String, Usuario> getUsuarios() {
		return usuarios;
	}
	
	/**
	 * Retorna um usuário do banco de dados com base em seu ID.
	 * @param id
	 * @return O usuário, ou null se o usuário não existir.
	 */
	public Usuario getUsuarioPorId(int id) {
	    for (Usuario usuario : usuarios.values()) {
	        if (usuario.getId() == id) {
	            return usuario;
	        }
	    }
	    return null;
	}
	
	/**
	 * Define o mapa de usuários do banco de dados.
	 * @param usuarios
	 */
	public void setUsuarios(Map<String, Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	/**
	 * Retorna o contador de ID do banco de dados.
	 * @return
	 */
	public int getIdCounter() {
		return idCounter;
	}

	/**
	 * Define o contador de ID do banco de dados.
	 * @param
	 */
	public void setIdCounter(int idCounter) {
		this.idCounter = idCounter;
	}
	
	/**
	 * Define a instância única do banco de dados.
	 * @param instance
	 */
	public static void setInstance(BancoDeDados instance) {
		BancoDeDados.instance = instance;
	}
}
