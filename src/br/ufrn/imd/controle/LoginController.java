package br.ufrn.imd.controle;

import java.io.IOException;

import br.ufrn.imd.modelo.ControllerUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Classe controladora para a tela de login.
 */
public class LoginController extends ControllerUtils {

    @FXML
    private PasswordField textSenha;
    @FXML
    private TextField textNome;
    @FXML
    private Button logoutButton;
    @FXML
    private AnchorPane scenePane;
    
    /**
     * Carrega a tela de cadastro.
     * @param event.
     * @throws IOException Se a tela não puder ser carregada.
     */
    public void MenuCadastrar(ActionEvent event) throws IOException {
        loadScene(event, "/br/ufrn/imd/visao/TelaCadastro.fxml");
    }

    /**
     * Carrega a tela principal.
     * @param event.
     * @throws IOException Se a tela não puder ser carregada.
     */
    public void TelaPrincipal(ActionEvent event) throws IOException {
        loadScene(event, "/br/ufrn/imd/visao/TelaPrincipal.fxml");
    }
    
    /**
     * Realiza o login do usuário.
     * @param event.
     * @throws IOException Se a tela não puder ser carregada.
     */
    public void Entrar(ActionEvent event) throws IOException {
        String nome = textNome.getText();
        String senha = textSenha.getText();
        ServicoAutenticacao servicoAutenticacao = ServicoAutenticacao.getInstance();
        
        if(servicoAutenticacao.realizarLogin(nome, senha)) {
            TelaPrincipal(event);
        }
    }
    
    /**
     * Encerra o programa.
     * @param event.
     */
    public void logout(ActionEvent event) {
        fecharPrograma(event);
    }
}
