package br.ufrn.imd.controle;

import java.io.File;

import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.modelo.ServicoAutenticacao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TelaPrincipalController {
	
	
	Stage estado;
	public void logout(ActionEvent event) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Saindo");
		alert.setHeaderText("Tem certeza de que quer sair?");
		alert.setContentText("Sentiremos sua falta!");
		
		if(alert.showAndWait().get() == ButtonType.OK){
			estado = (Stage)((Node)event.getSource()).getScene().getWindow();
			System.out.println("Programa encerrado");
			estado.close();
		}
		
	}
	
	@FXML
    private MenuItem mnItemFileAddFile;

    @FXML
    void abrirTelaAddFile(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Escolha um arquivo MP3");
    	fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo MP3", "*.mp3"));
    	
    	var selectedFile = fileChooser.showOpenDialog(estado);
    	if(selectedFile != null) {
    		ServicoAutenticacao servicoAutenticacao = ServicoAutenticacao.getInstance();
    		
    		Musica musica = new Musica();
    		musica.setCaminho(selectedFile.getAbsolutePath());
    		musica.setNome(selectedFile.getName());
    		
    		servicoAutenticacao.getUsuarioLogado().getDiretorio().addMusic(musica);
    		System.out.println("Arquivo selecionado: " + selectedFile.getName());
    	}
    }
    
    @FXML
    private MenuItem mnItemFileAddDirectory;
    
    @FXML
    void abrirTelaAddDirectory(ActionEvent event) {
    	DirectoryChooser directoryChooser = new DirectoryChooser();
    	directoryChooser.setTitle("Escolha um diretório");
    	
    	File selectedDirectory = directoryChooser.showDialog(estado);
    	if(selectedDirectory != null) {
    		File[] arquivos = selectedDirectory.listFiles((dir, nome) -> nome.toLowerCase().endsWith(".mp3"));
    		
    		if(arquivos != null) {
    			System.out.println("Arquivos MP3 encontrados:");
                for (File file : arquivos) {
                	ServicoAutenticacao servicoAutenticacao = ServicoAutenticacao.getInstance();
            		
            		Musica musica = new Musica();
            		musica.setCaminho(file.getAbsolutePath());
            		musica.setNome(file.getName());
            		
            		servicoAutenticacao.getUsuarioLogado().getDiretorio().addMusic(musica);
            		System.out.println("Arquivo selecionado: " + file.getName());  
                }
    		} else {
    			System.out.println("Nenhum arquivo MP3 encontrado neste diretório.");
    		}
    	}
    }
}
