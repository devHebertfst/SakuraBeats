module MediaPlayerJava {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.media;
	
	exports br.ufrn.imd.controle to javafx.fxml;
	opens br.ufrn.imd.controle to javafx.fxml;
	
	opens application to javafx.graphics, javafx.fxml;
}
