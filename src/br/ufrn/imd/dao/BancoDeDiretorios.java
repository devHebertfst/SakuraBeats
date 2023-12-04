package br.ufrn.imd.dao;

import br.ufrn.imd.modelo.Diretorio;
import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.controle.ServicoAutenticacao;
import br.ufrn.imd.modelo.Usuario;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Classe que representa um banco de dados de diretórios.
 */
public class BancoDeDiretorios {
    private static BancoDeDiretorios instancia;
    private Map<String, Diretorio> diretorios;

    /**
     * Construtor privado para a classe BancoDeDiretorios.
     */
    private BancoDeDiretorios() {
        diretorios = new HashMap<String, Diretorio>();
    }

    /**
     * Retorna a instância única do banco de dados de diretórios.
     * @return instancia.
     */
    public static BancoDeDiretorios getInstancia() {
        if (instancia == null) {
            instancia = new BancoDeDiretorios();
        }
        return instancia;
    }

    /**
     * Retorna um mapa de todos os diretórios no banco de dados.
     * @return Map
     */
    public Map<String, Diretorio> getDiretorios() {
        return diretorios;
    }

    /**
     * Retorna um diretório do banco de dados com base em seu caminho.
     * @param caminho
     * @return O diretório, ou null se o diretório não existir.
     */
    public Diretorio getDiretorio(String caminho){
        return this.diretorios.get(caminho);
    }

    /**
     * Adiciona um diretório ao banco de dados.
     * @param diretorio.
     */
    public void adicionarDiretorio(Diretorio diretorio) {
        diretorios.put(diretorio.getCaminho(), diretorio);
        Usuario usuarioLogado = ServicoAutenticacao.getInstance().getUsuarioLogado();
        usuarioLogado.addDiretorio(diretorio);
        salvarDiretoriosEmArquivo();
    }

    /**
     * Remove um diretório do banco de dados.
     * @param diretorio.
     */
    public void removerDiretorio(Diretorio diretorio) {
        diretorios.remove(diretorio.getCaminho());
        salvarDiretoriosEmArquivo();
    }
    
    /**
     * Verifica se um diretório existe no banco de dados.
     * @param caminho.
     * @return O diretório, ou null se o diretório não existir.
     */
    public Diretorio verificarDiretorio(String caminho) {
        return diretorios.get(caminho);
    }
    
    /**
     * Salva os diretórios do banco de dados em um arquivo.
     */
    public void salvarDiretoriosEmArquivo() {
        try (PrintWriter writer = new PrintWriter("dados/diretorios.txt")) {
            for (Usuario usuario : BancoDeDados.getInstance().getUsuarios().values()) {
                if (!usuario.getDiretorios().isEmpty()) { // Verifica se o usuário tem algum diretório
                    writer.println("ID: " + usuario.getId());
                    for(Diretorio diretorio : usuario.getDiretorios()) {
                        writer.println(diretorio.getCaminho());
                        for(Musica musica : diretorio.getMusicas()) {
                            BancoDeMusicas.getInstance().addMusica(musica);
                        }
                    }
                    writer.println();
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar diretórios em arquivo: " + e.getMessage());
        }
    }

    /**
     * Carrega os diretórios de um arquivo para o banco de dados.
     */
    public void carregarDiretoriosDeArquivo() {
        try (Scanner scanner = new Scanner(new File("dados/diretorios.txt"))) {
            while (scanner.hasNext()) {
                String idLine = scanner.nextLine();
                int id = Integer.parseInt(idLine.split(": ")[1]);
                Usuario usuario = BancoDeDados.getInstance().getUsuarioPorId(id);
                if (usuario != null) {
                    String diretorioLine = scanner.nextLine();
                    while(!diretorioLine.isEmpty()) {
                        Diretorio diretorio = new Diretorio(diretorioLine);
                        usuario.addDiretorio(diretorio);
                        diretorios.put(diretorio.getCaminho(), diretorio);
                        if (scanner.hasNextLine()) {
                            diretorioLine = scanner.nextLine();
                        } else {
                            break;
                        }
                    }
                } else {
                    System.out.println("Usuário com ID " + id + " não encontrado.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao carregar diretórios de arquivo: " + e.getMessage());
        }
    }

}
