package br.ufrn.imd.controle;

import java.io.IOException;
import java.util.Optional;

import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.modelo.Playlist;
import br.ufrn.imd.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.stage.Stage;

public class TelaPrincipalController {
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
	
	private Parent root;
	private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
	private Scene scene;
	private Usuario usuarioLogado;
	private MediaPlayer mediaPlayer;
	private Media media;
	
	
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
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
