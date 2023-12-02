package application;
	
import br.ufrn.imd.dao.BancoDeDados;
import br.ufrn.imd.dao.BancoDeDiretorios;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	private double xOffset = 0;
    private double yOffset = 0;
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
	
	
	public static void main(String[] args) {
		BancoDeDados.getInstance().carregarUsuariosDeArquivo();
		BancoDeDiretorios.getInstancia().carregarDiretoriosDeArquivo();
		launch(args);
	}
}
