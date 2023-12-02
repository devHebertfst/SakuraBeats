package br.ufrn.imd.controle;

import java.io.IOException;

import br.ufrn.imd.dao.BancoDeDados;
import br.ufrn.imd.modelo.ControllerUtils;
import br.ufrn.imd.modelo.UsuarioComum;
import br.ufrn.imd.modelo.UsuarioVip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

public class CadastroController extends ControllerUtils {

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
	
	public void menuLogin(ActionEvent event) throws IOException {
		loadScene(event, "/br/ufrn/imd/visao/TelaLogin.fxml");
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
		fecharPrograma(event);
	}
}