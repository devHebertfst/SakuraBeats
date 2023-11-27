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
	private PasswordField textSenha;
	@FXML
	private TextField textNome;
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
	
	public void menuLogin(ActionEvent event) throws IOException {
		
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
			
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	
	}
	
	public void cadastrar(ActionEvent event) {
	    String nome = textNome.getText();
	    String senha = textSenha.getText();
	    
	    if(nome.isEmpty() || senha.isEmpty()) {
	    	Alert alert = new Alert(AlertType.INFORMATION);
	    	alert.initStyle(StageStyle.UNDECORATED);
	    	alert.setHeaderText("Nome ou senha nao podem estar vazios");
	    	alert.showAndWait();
	    } else {
	        BancoDeDados usuarioDao = BancoDeDados.getInstance();
	        if (usuarioDao.usuarioExiste(nome)) {
	            Alert alert = new Alert(AlertType.INFORMATION);
	            alert.initStyle(StageStyle.UNDECORATED);
	            alert.setHeaderText("Usuario ja existe");
	            alert.showAndWait();
	        } else {
	            if(vip.isSelected()) {
	                UsuarioVip usuario = new UsuarioVip();
	                usuario.setNome(nome);
	                usuario.setSenha(senha);
	                usuarioDao.adicionarUsuario(usuario);
	                Alert alert = new Alert(AlertType.INFORMATION);
	                alert.initStyle(StageStyle.UNDECORATED);
	                alert.setHeaderText("Usuario VIP cadastrado com sucesso!");
	                alert.showAndWait();
	            } else {
	                UsuarioComum usuario = new UsuarioComum();
	                usuario.setNome(nome);
	                usuario.setSenha(senha);
	                usuarioDao.adicionarUsuario(usuario);
	                Alert alert = new Alert(AlertType.INFORMATION);
	                alert.initStyle(StageStyle.UNDECORATED);
	                alert.setHeaderText("Usuario comum cadastrado com sucesso!");
	                alert.showAndWait();
	            }
	            usuarioDao.salvarUsuariosEmArquivo();
	        }
	    }
	    textNome.clear();
	    textSenha.clear();
	}
	
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