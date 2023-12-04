package br.ufrn.imd.dao;

import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.modelo.Playlist;
import br.ufrn.imd.modelo.Usuario;
import br.ufrn.imd.modelo.UsuarioVip;
import br.ufrn.imd.controle.ServicoAutenticacao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Classe para gerenciar as playlists no banco de dados.
 */
public class BancoDePlaylists {
    private static BancoDePlaylists instance;
    private Map<Integer,Playlist> playlists;

    /**
     * Construtor privado para a classe BancoDePlaylists.
     */
    private BancoDePlaylists() {
        playlists = new HashMap<>();
    }

    /**
     * Método para obter a instância do BancoDePlaylists.
     * @return instance.
     */
    public static BancoDePlaylists getInstance() {
        if(instance==null) {
            instance = new BancoDePlaylists();
        }
        return instance;
    }

    /**
     * Método para adicionar uma playlist.
     * @param play.
     */
    public void adicionarPlaylist(Playlist play) {
        playlists.put(play.getIdUsuario(), play);
        salvarPlaylistEmArquivo();
    }

    /**
     * Método para remover uma playlist.
     * @param play.
     */
    public void removerPlaylist(Playlist play) {
        playlists.remove(play);
    }

    /**
     * Método para salvar as playlists em um arquivo.
     */
    public void salvarPlaylistEmArquivo() {
        Usuario usuario = ServicoAutenticacao.getInstance().getUsuarioLogado();
        playlists.forEach((key, value) -> {
            String fileName = "dados/playlist_" + usuario.getNome() + "_" + usuario.getId() + "_" + value.getNome() + ".txt";
            try (PrintWriter writer = new PrintWriter(fileName)) {
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
            } catch (IOException e) {
                System.out.println("Erro ao salvar playlist em arquivo: " + e.getMessage());
            }
        });
    }

    /**
     * Método para carregar as playlists de um arquivo.
     */
    public void carregarPlaylistDeArquivo() {
        Usuario usuario = ServicoAutenticacao.getInstance().getUsuarioLogado();
        File dir = new File("dados/");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (child.getName().startsWith("playlist_" + usuario.getNome() + "_" + usuario.getId())) {
                    try (Scanner scanner = new Scanner(child)) {
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
        }
    }
}
