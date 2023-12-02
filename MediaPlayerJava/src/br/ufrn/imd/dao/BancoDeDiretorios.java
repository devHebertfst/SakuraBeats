package br.ufrn.imd.dao;

import br.ufrn.imd.modelo.Diretorio;
import br.ufrn.imd.modelo.ServicoAutenticacao;
import br.ufrn.imd.modelo.Usuario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class BancoDeDiretorios {
    private static BancoDeDiretorios instancia; // a única instância da classe
    private Map<String, Diretorio> diretorios; // a lista de diretórios
    private String arquivo; // o nome do arquivo que salva os diretórios

    private BancoDeDiretorios() {
        diretorios = new HashMap<String, Diretorio>();
        arquivo = "diretorios.txt";
        carregarDiretorios();
    }

    // este método retorna a única instância da classe
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

    // este método adiciona um diretório à lista de diretórios e salva no arquivo
    public void adicionarDiretorio(Diretorio diretorio) {
        diretorios.put(diretorio.getCaminho(), diretorio);
        salvarDiretorios();

        Usuario usuarioLogado = ServicoAutenticacao.getInstance().getUsuarioLogado();
        usuarioLogado.addDiretorio(diretorio);
    }

    // este método remove um diretório da lista de diretórios e salva no arquivo
    public void removerDiretorio(Diretorio diretorio) {
        diretorios.remove(diretorio);

        salvarDiretorios();
    }
    
    public Diretorio verificarDiretorio(String caminho) {
        return diretorios.get(caminho);
        /* Percorrer a lista de diretórios
        for (Diretorio diretorio : diretorios) {
            // Se o caminho do diretório for igual ao parâmetro, retornar o diretório
            if (diretorio.getCaminho().equals(caminho)) {
                return diretorio;
            }
        }
        // Se nenhum diretório for encontrado, retornar null
        return null;*/
    }



    // este método lê os diretórios do arquivo e cria objetos Diretorio para cada um
    public void carregarDiretorios() {
        try {
        	Scanner scanner = new Scanner(new File("dados/diretorios.txt"));
            while (scanner.hasNextLine()) {
                String id = scanner.nextLine();
                String caminho = scanner.nextLine();
                Diretorio diretorio = new Diretorio(caminho);
                diretorios.put(diretorio.getCaminho(), diretorio);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // este método salva os diretórios no arquivo
    public void salvarDiretorios() {
        try {
            PrintWriter writer = new PrintWriter(new File("dados/diretorios.txt"));
            diretorios.forEach((key, value) -> {
                writer.println(value.getId());
                writer.println(value.getCaminho());
            });
            /*for (Diretorio diretorio : diretorios) {
                writer.println(diretorio.getId());
                writer.println(diretorio.getCaminho());
            }*/
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

