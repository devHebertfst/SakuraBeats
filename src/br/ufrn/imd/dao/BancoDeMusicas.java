package br.ufrn.imd.dao;

import br.ufrn.imd.modelo.Musica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

/**
 * Classe para gerenciar as músicas no banco de dados.
 */
public class BancoDeMusicas {
    private static BancoDeMusicas instancia;
    private Map<String, Musica> musicas;

    /**
     * Construtor para a classe BancoDeMusicas.
     */
    public BancoDeMusicas() {
        this.musicas = new HashMap<String, Musica>();
    }

    /**
     * Método para obter a instância do BancoDeMusicas.
     * @return instancia
     */
    public static BancoDeMusicas getInstance() {
        if(instancia == null) {
            instancia = new BancoDeMusicas();
        }

        return instancia;
    }

    /**
     * Método para obter as músicas.
     * @return Map.
     */
    public Map<String, Musica> getMusicas() {
        return musicas;
    }

    /**
     * Método para adicionar uma música.
     * @param musica.
     */
    public void addMusica(Musica musica) {
        this.musicas.put(musica.getNome(), musica);
        salvarMusicasEmArquivo();
    }

    /**
     * Método para salvar as músicas em um arquivo.
     */
    public void salvarMusicasEmArquivo() {
        try (PrintWriter writer = new PrintWriter("dados/musicas.txt")) {
            musicas.forEach((key, value) -> {
                writer.println("ID: " + value.getId());
                writer.println("Nome: " + value.getNome());
                writer.println("Caminho: " + value.getCaminho());
                writer.println();
            });
        } catch (IOException e) {
            System.out.println("Erro ao salvar diretórios em arquivo: " + e.getMessage());
        }
    }

    /**
     * Método para carregar as músicas de um arquivo.
     */
    public void carregarMusicasDeArquivo() {
        try (Scanner scanner = new Scanner(new File("dados/musicas.txt"))) {
            while(scanner.hasNext()) {
                String idLine = scanner.nextLine();
                UUID id = UUID.fromString(idLine.split(": ")[1]);
                String nomeLine = scanner.nextLine();
                String nome = nomeLine.split(": ")[1];
                String caminhoLine = scanner.nextLine();
                String caminho = caminhoLine.split(": ")[1];

                Musica musica = new Musica();
                musica.setId(id);
                musica.setNome(nome);
                musica.setCaminho(caminho);
                addMusica(musica);

                if (scanner.hasNext()) {
                    scanner.nextLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Erro ao carregar diretórios de arquivo: " + e.getMessage());
        }
    }

    /**
     * Método para obter uma música pelo nome.
     * @param nome.
     * @return Musica.
     */
    public Musica getMusica(String nome) {
        return this.musicas.get(nome);
    }
}
