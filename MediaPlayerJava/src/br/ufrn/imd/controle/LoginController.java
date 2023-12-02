package br.ufrn.imd.controle;

import java.io.IOException;

import br.ufrn.imd.modelo.ControllerUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController extends ControllerUtils {

	@FXML
    private PasswordField textSenha;
    @FXML
    private TextField textNome;
    @FXML
    private Button logoutButton;
    @FXML
    private AnchorPane scenePane;
	
	public void MenuCadastrar(ActionEvent event) throws IOException {
        loadScene(event, "/br/ufrn/imd/visao/TelaCadastro.fxml");
    }

    public void TelaPrincipal(ActionEvent event) throws IOException {
        loadScene(event, "/br/ufrn/imd/visao/TelaPrincipal.fxml");
    }
	
	public void Entrar(ActionEvent event) throws IOException {
	    String nome = textNome.getText();
	    String senha = textSenha.getText();
	    ServicoAutenticacao servicoAutenticacao = ServicoAutenticacao.getInstance();
	    
	    if(servicoAutenticacao.realizarLogin(nome, senha)) {
	    	TelaPrincipal(event);
	    }
	}
	
	public void logout(ActionEvent event) {
		fecharPrograma(event);
	}
}
