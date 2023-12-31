package br.ufrn.imd.modelo;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Classe abstrata que fornece métodos utilitários para controladores.
 */
public abstract class ControllerUtils {

    protected Stage stage;
    private Stage estado;
    protected Scene scene;
    protected Parent root;
    protected double xOffset = 0;
    protected double yOffset = 0;

    /**
     * Carrega uma nova cena.
     * @param event
     * @param fxmlPath O caminho para o arquivo FXML da cena.
     * @throws IOException Se houver um erro ao carregar o arquivo FXML.
     */
    protected void loadScene(ActionEvent event, String fxmlPath) throws IOException {
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
    
    /**
     * Método para fechar o programa após a confirmação do usuário.
     * @param event
     */
    public void fecharPrograma(ActionEvent event) {
		
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
