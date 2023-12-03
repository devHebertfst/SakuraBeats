package br.ufrn.imd.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.modelo.Playlist;
import br.ufrn.imd.controle.ServicoAutenticacao;
import br.ufrn.imd.modelo.Usuario;
import br.ufrn.imd.modelo.UsuarioVip;

public class BancoDePlaylists {
	private static BancoDePlaylists instance;
	private Map<Integer,Playlist> playlists;
	
	private BancoDePlaylists() {
		playlists = new HashMap<>();
	}
	
	public static BancoDePlaylists getInstance() {
		if(instance==null) {
			instance = new BancoDePlaylists();
		}
		return instance;
	}
	
	public void adicionarPlaylist(Playlist play) {
		playlists.put(play.getIdUsuario(), play);
		salvarPlaylistEmArquivo();
	}
	
	public void removerPlaylist(Playlist play) {
		playlists.remove(play);
	}

	public void salvarPlaylistEmArquivo() {
		try (PrintWriter writer = new PrintWriter("dados/playlists.txt")) {
			playlists.forEach((key, value) -> {
				writer.println("ID: " + value.getId());
				writer.println("Nome: " + value.getNome());
				writer.println("Musicas: [");
				for(Musica musica: value.getMusicas()) {
					if(musica != null) {
						writer.println(musica.getNome());
					}
				}
				writer.println("]");
				writer.println("Usuário ID: " + value.getIdUsuario());
				writer.println();
			});
		} catch (IOException e) {
			System.out.println("Erro ao salvar playlists em arquivo: " + e.getMessage());
		}
	}

	public void carregarPlaylistDeArquivo() {
		try (Scanner scanner = new Scanner(new File("dados/playlists.txt"))) {
			while(scanner.hasNext()) {
				String idLine = scanner.nextLine();
				UUID id = UUID.fromString(idLine.split(": ")[1]);
				String nomeLine = scanner.nextLine();
				String nome = nomeLine.split(": ")[1];
				scanner.nextLine();
				String musicasLine = scanner.nextLine();

				List<Musica> musicas = new ArrayList<Musica>();
				while(!musicasLine.equals("]")) {
					musicas.add(BancoDeMusicas.getInstance().getMusica(musicasLine));
					musicasLine = scanner.nextLine();
				}
				String usuarioIdLine = scanner.nextLine();
				Integer usuarioId = Integer.parseInt(usuarioIdLine.split(": ")[1]);

				Playlist playlist = new Playlist(nome);
				playlist.setId(id);
				playlist.setMusicas(musicas);
				playlist.setIdUsuario(usuarioId);
				Usuario usuario = BancoDeDados.getInstance().getUsuarioPorId(usuarioId);
				if(usuario instanceof UsuarioVip) {
					((UsuarioVip) usuario).adicionarPlaylist(playlist);
				}
				adicionarPlaylist(playlist);

				if (scanner.hasNext()) {
					scanner.nextLine();
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("Erro ao carregar diretórios de arquivo: " + e.getMessage());
		}
	}
}
