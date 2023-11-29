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

public class BancoDeDados {
	private static BancoDeDados instance;
	private Map<String, Usuario> usuarios;
	private int idCounter;
	
	private BancoDeDados() {
		usuarios = new HashMap<>();
		idCounter = 1;
	}
	
	public static BancoDeDados getInstance() {
		if(instance==null) {
			instance = new BancoDeDados();
		}
		return instance;
	}
	
	public void adicionarUsuario(Usuario usuario) {
		usuario.setId(idCounter++);
		usuarios.put(usuario.getNome(), usuario);
		salvarUsuariosEmArquivo();
	}
	
	public boolean usuarioExiste(String nome) {
        return usuarios.containsKey(nome);
    }
	
	public Usuario getUsuario(String nome) {
        return usuarios.get(nome);
    }
	
	public void salvarUsuariosEmArquivo() {
        try (PrintWriter writer = new PrintWriter("dados/usuarios.txt")) {
            for (Usuario usuario : usuarios.values()) {
                writer.println("ID: " + usuario.getId());
                writer.println("Nome: " + usuario.getNome());
                writer.println("Senha: " + usuario.getSenha());
                writer.println("Tipo: " + usuario.getTipo());
                writer.println("Diretorio: " + usuario.getDiretorio());
                writer.println();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuários em arquivo: " + e.getMessage());
        }
    }
	
	public void carregarUsuariosDeArquivo() {
	    try (Scanner scanner = new Scanner(new File("dados/usuarios.txt"))) {
	        while (scanner.hasNext()) {
	            String idLine = scanner.nextLine();
	            String nomeLine = scanner.nextLine();
	            String senhaLine = scanner.nextLine();
	            String tipoLine = scanner.nextLine();
	            String diretorioLine = scanner.nextLine();

	            if (!idLine.startsWith("ID: ") || !nomeLine.startsWith("Nome: ") || !senhaLine.startsWith("Senha: ") || !tipoLine.startsWith("Tipo: ") || !diretorioLine.startsWith("Diretorio: ")) {
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
	            usuario.setDiretorio(diretorioLine.split(": ")[1]);

	            // Adicione o usuário ao banco de dados
	            adicionarUsuario(usuario);

	            // Pule a linha em branco entre os usuários
	            if (scanner.hasNext()) {
	                scanner.nextLine();
	            }
	        }
	    } catch (FileNotFoundException e) {
	        System.out.println("Erro ao carregar usuários de arquivo: " + e.getMessage());
	    }
	}





}
