package br.ufrn.imd.controle;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller {
	
	@FXML
	private Button logoutButton;
	@FXML
	private AnchorPane scenePane;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private double xOffset = 0;
    private double yOffset = 0;
	
	public void logout(ActionEvent event) {
		if(confirmLogout()){
			System.out.println("Programa encerrado");
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.close();
		}
	}
	
	public void MenuCadastrar(ActionEvent event) throws IOException {
		MudarCena("/br/ufrn/imd/visao/TelaCadastro.fxml", event);
	}
	
	public void MenuLogin(ActionEvent event) throws IOException {
		MudarCena("/br/ufrn/imd/visao/TelaLogin.fxml", event);
	}
	
	private boolean confirmLogout(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Saindo");
		alert.setHeaderText("Tem certeza de que quer sair?");
		alert.setContentText("O progresso não será salvo!");
		return alert.showAndWait().get() == ButtonType.OK;
	}
	
	private void MudarCena(String fxmlPath, ActionEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));	
		root = loader.load();	
		
		root.setOnMousePressed(e -> {
	        xOffset = e.getSceneX();
	        yOffset = e.getSceneY();
	    });
	    root.setOnMouseDragged(e -> {
	        stage.setX(e.getScreenX() - xOffset);
	        stage.setY(e.getScreenY() - yOffset);
	    });
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
