package br.ufrn.imd.controle;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
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
}
