package br.ufrn.imd.controle;

import java.io.IOException;

import br.ufrn.imd.dao.BancoDeDados;
import br.ufrn.imd.modelo.UsuarioComum;
import br.ufrn.imd.modelo.UsuarioVip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController {

	@FXML
	private PasswordField textSenha;
	@FXML
	private TextField textNome;
	@FXML
	private Button logoutButton;
	@FXML
	private AnchorPane scenePane;
	private Stage estado;
	private double xOffset = 0;
    private double yOffset = 0;
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void MenuCadastrar(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/visao/TelaCadastro.fxml"));	
		root = loader.load();	
		
		root.setOnMousePressed(e -> {
	        xOffset = e.getSceneX();
	        yOffset = e.getSceneY();
	    });
	    root.setOnMouseDragged(e -> {
	        stage.setX(e.getScreenX() - xOffset);
	        stage.setY(e.getScreenY() - yOffset);
	    });
		
		//root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));	
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

		
	}
	
	public void TelaPrincipal(ActionEvent event) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/ufrn/imd/visao/TelaPrincipal.fxml"));	
		root = loader.load();	
		
		root.setOnMousePressed(e -> {
	        xOffset = e.getSceneX();
	        yOffset = e.getSceneY();
	    });
	    root.setOnMouseDragged(e -> {
	        stage.setX(e.getScreenX() - xOffset);
	        stage.setY(e.getScreenY() - yOffset);
	    });
		
		//root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));	
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void Entrar(ActionEvent event) throws IOException {
	    String nome = textNome.getText();
	    String senha = textSenha.getText();
	    BancoDeDados usuarioDao = BancoDeDados.getInstance();
	 	if (usuarioDao.usuarioExiste(nome)) {
	 		if(usuarioDao.getUsuario(nome).getSenha().equals(senha)) {
	 	 	   	TelaPrincipal(event);
	 		}
	 		else {
	 			Alert alert = new Alert(AlertType.INFORMATION);
	 	    	alert.initStyle(StageStyle.UNDECORATED);
	 			alert.setHeaderText("Senha incorreta");
	 			alert.setContentText("Tente novamente");
	 			alert.showAndWait();
	 		}   
	 	} 
	 	else{ 
	 		Alert alert = new Alert(AlertType.INFORMATION);
 	    	alert.initStyle(StageStyle.UNDECORATED);
 			alert.setHeaderText("Usuário não existe");
 			alert.setContentText("Realize um novo cadastro");
 			alert.showAndWait();
	 	}
	    
	}
	
	
	public void logout(ActionEvent event) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Saindo");
		alert.setHeaderText("Tem certeza de que quer sair?");
		alert.setContentText("O progresso não será salvo!");
		
		if(alert.showAndWait().get() == ButtonType.OK){
			estado = (Stage)((Node)event.getSource()).getScene().getWindow();
			System.out.println("Programa encerrado");
			estado.close();
		}
		
	}
}
