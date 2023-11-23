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

public class CadastroController {

	@FXML
	private PasswordField senhaa;
	@FXML
	private TextField nomee;
	@FXML
	private Button logoutButton;
	@FXML
	private AnchorPane scenePane;	
	@FXML
	private RadioButton vip;
	private Stage estado;
	private double xOffset = 0;
    private double yOffset = 0;
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void MenuLogin(ActionEvent event) throws IOException {
		
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
		
		//root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));	
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	
	}
	
	public void Cadastrar(ActionEvent event) {
	    String nome = nomee.getText();
	    String senha = senhaa.getText();
	    
	    if(vip.isSelected()) {
	    	BancoDeDados usuarioDao = BancoDeDados.getInstance();
	 	    if (usuarioDao.usuarioExiste(nome)) {
	 	        Alert alert = new Alert(AlertType.INFORMATION);
	 	    	alert.initStyle(StageStyle.UNDECORATED);
	 			alert.setHeaderText("Usuário já existe");
	 			alert.setContentText("Insira outro nome");
	 			if(alert.showAndWait().get() == ButtonType.OK){
	 	    	  System.out.println("Alerta fechado");
	 			}
	 	    } else {
	 	        UsuarioVip usuario = new UsuarioVip();
	 	        usuario.setNome(nome);
	 	        usuario.setSenha(senha);
	 	        usuario.setTipo("Vip");
	 	        usuarioDao.adicionarUsuario(usuario);
	 	        Alert alert = new Alert(AlertType.INFORMATION);
	 	    	alert.initStyle(StageStyle.UNDECORATED);
	 			alert.setHeaderText("Usuário cadastrado com sucesso!");
	 			if(alert.showAndWait().get() == ButtonType.OK){
	 	    	  System.out.println("Alerta fechado");
	 			}
	 	    }
	    }
	    
	    else {
	    	BancoDeDados usuarioDao = BancoDeDados.getInstance();
	 	    if (usuarioDao.usuarioExiste(nome)) {
	 	    	Alert alert = new Alert(AlertType.INFORMATION);
	 	    	alert.initStyle(StageStyle.UNDECORATED);
	 			alert.setHeaderText("Usuário já existe");
	 			alert.setContentText("Insira outro nome");
	 			if(alert.showAndWait().get() == ButtonType.OK){
	 	    	  System.out.println("Alerta fechado");
	 			}
	 	    } else {
	 	        UsuarioComum usuario = new UsuarioComum();
	 	        usuario.setNome(nome);
	 	        usuario.setSenha(senha);
	 	        usuario.setTipo("Comum");
	 	        usuarioDao.adicionarUsuario(usuario);
	 	        Alert alert = new Alert(AlertType.INFORMATION);
	 	    	alert.initStyle(StageStyle.UNDECORATED);
	 			alert.setHeaderText("Usuário cadastrado com sucesso!");
	 			if(alert.showAndWait().get() == ButtonType.OK){
	 				System.out.println("Alerta fechado");
		 		}
	 	    }
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