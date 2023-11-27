package br.ufrn.imd.controle;

import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.modelo.Playlist;
import br.ufrn.imd.modelo.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
}
