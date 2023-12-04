package application;

/**
 * 
 * 
 * @author Hebert França da Silva Torres
 * @author José Victor do Nascimento Ferreira
 * @version 1.0
 * 
 */

import br.ufrn.imd.dao.BancoDeDados;
import br.ufrn.imd.dao.BancoDeDiretorios;
import br.ufrn.imd.dao.BancoDeMusicas;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Classe principal da aplicação do sistema de media player.
 * Herda da classe Application do JavaFX.
 */
public class Main extends Application {
    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * Método para iniciar a interface gráfica da aplicação.
     * Carrega a tela de login a partir de um arquivo FXML.
     * Permite que o usuário arraste a janela da aplicação usando o mouse.
     * Trata qualquer exceção que possa ocorrer durante o carregamento da interface.
     * @param stage.
     */
    @Override
    public void start(Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/ufrn/imd/visao/TelaLogin.fxml"));
            Scene scene = new Scene(root);
            root.setOnMousePressed(event -> {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            });
            root.setOnMouseDragged(event -> {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            });
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método principal da aplicação, que é executado quando a aplicação é iniciada.
     * Carrega os dados dos usuários, diretórios, músicas e playlists a partir de arquivos de texto.
     * Invoca o método launch da classe Application para iniciar a interface gráfica.
     * @param args.
     */
    public static void main(String[] args) {
        BancoDeDados.getInstance().carregarUsuariosDeArquivo();
        BancoDeDiretorios.getInstancia().carregarDiretoriosDeArquivo();
        BancoDeMusicas.getInstance().carregarMusicasDeArquivo();
        launch(args);
    }
}
