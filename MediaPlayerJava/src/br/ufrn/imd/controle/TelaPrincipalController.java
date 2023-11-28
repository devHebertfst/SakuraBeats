package br.ufrn.imd.controle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.modelo.Playlist;
import br.ufrn.imd.modelo.ServicoAutenticacao;
import br.ufrn.imd.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TelaPrincipalController implements Initializable {
	@FXML
	private Label musica;
	
	@FXML
	private Button btnVoltar, btnAvancar, btnPausar, btnAdicionarPlaylist;
	
	@FXML
	private ListView<Playlist> playlists;
	
	@FXML
	private ListView<Musica> musicas;
	
	@FXML
	private MenuItem sair;
	
	@FXML
	private Label lbNome;
	
	@FXML
	private Label lbTipo;
	
	private Parent root;
	private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
	private Scene scene;
	private Usuario usuarioLogado;
	private MediaPlayer mediaPlayer;
	private Media media;
	
	@Override
    public void initialize(URL url, ResourceBundle rb) {
        lbNome.setText(getUsuarioLogado().getNome());
        lbTipo.setText(getUsuarioLogado().getTipo());
    }
	
	public Usuario getUsuarioLogado() {
		ServicoAutenticacao servicoAutenticacao = ServicoAutenticacao.getInstance();
		return servicoAutenticacao.getUsuarioLogado();
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public void tocarMusica() {
		mediaPlayer.play();
	}
	
	public void pausarMusica() {
		mediaPlayer.pause();
	}
	
	public void voltarMusica() {
		
	}
	
	public void avancarMusica() {
		
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
    		
    		//servicoAutenticacao.getUsuarioLogado().getDiretorio().addMusic(musica);
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
            		
            		//servicoAutenticacao.getUsuarioLogado().getDiretorio().addMusic(musica);
            		System.out.println("Arquivo selecionado: " + file.getName());  
                }
    		} else {
    			System.out.println("Nenhum arquivo MP3 encontrado neste diretório.");
    		}
    	}
    }
	
	
	private Stage estado;
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
	
	public void sair(ActionEvent event) throws IOException {
	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.setTitle("Confirmação");
	    alert.setHeaderText("Você tem certeza que deseja sair?");
	    alert.setContentText("Clique em OK para confirmar, ou Cancelar para voltar.");
	    if (alert.showAndWait().get() == ButtonType.OK){
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/visao/TelaLogin.fxml"));	
	        root = loader.load();	

	        root.setOnMousePressed(e -> {
	            xOffset = e.getSceneX();
	            yOffset = e.getSceneY();
	        });
	        root.setOnMouseDragged(e -> {
	            stage.setX(e.getScreenX() - xOffset);
	            stage.setY(e.getScreenY() - yOffset);
	        });
	        scene = new Scene(root);
	        stage = (Stage)sair.getParentPopup().getOwnerWindow();
	        stage.setScene(scene);
	        stage.show();
	    }
	    else {
	    	alert.close();
	    }
	}

}
