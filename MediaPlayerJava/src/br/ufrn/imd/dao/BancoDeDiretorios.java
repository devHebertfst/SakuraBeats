package br.ufrn.imd.dao;

import br.ufrn.imd.modelo.Diretorio;
import br.ufrn.imd.controle.ServicoAutenticacao;
import br.ufrn.imd.modelo.Usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class BancoDeDiretorios {
    private static BancoDeDiretorios instancia;
    private Map<String, Diretorio> diretorios;
    private String arquivo;

    private BancoDeDiretorios() {
        diretorios = new HashMap<String, Diretorio>();
        arquivo = "diretorios.txt";
    }

    public static BancoDeDiretorios getInstancia() {
        if (instancia == null) {
            instancia = new BancoDeDiretorios();
        }
        return instancia;
    }

    public Map<String, Diretorio> getDiretorios() {
        return diretorios;
    }

    public Diretorio getDiretorio(String caminho){
        return this.diretorios.get(caminho);
    }

    public void adicionarDiretorio(Diretorio diretorio) {
        diretorios.put(diretorio.getCaminho(), diretorio);
        Usuario usuarioLogado = ServicoAutenticacao.getInstance().getUsuarioLogado();
        usuarioLogado.addDiretorio(diretorio);
        salvarDiretoriosEmArquivo();
    }

    public void removerDiretorio(Diretorio diretorio) {
        diretorios.remove(diretorio);

        salvarDiretoriosEmArquivo();
    }
    
    public Diretorio verificarDiretorio(String caminho) {
        return diretorios.get(caminho);
    }
    public void salvarDiretoriosEmArquivo() {
        try (PrintWriter writer = new PrintWriter("dados/diretorios.txt")) {
            for (Usuario usuario : BancoDeDados.getInstance().getUsuarios().values()) {
                if (!usuario.getDiretorios().isEmpty()) { // Verifica se o usuário tem algum diretório
                    writer.println("ID: " + usuario.getId());
                    for(Diretorio diretorio : usuario.getDiretorios()) {
                        writer.println(diretorio.getCaminho());
                    }
                    writer.println();
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar diretórios em arquivo: " + e.getMessage());
        }
    }

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
