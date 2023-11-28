package br.ufrn.imd.modelo;

import br.ufrn.imd.dao.BancoDeDados;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.StageStyle;

public class ServicoAutenticacao {
	private static ServicoAutenticacao instance;
	private Usuario usuarioLogado;
	
	private ServicoAutenticacao() {
		this.usuarioLogado = null;
	}
	
	public static ServicoAutenticacao getInstance() {
		if(instance==null) {
			instance = new ServicoAutenticacao();
		}
		
		return instance;
	}
	
	public boolean realizarLogin(String nomeUsario, String senha) {
		BancoDeDados usuarioDao = BancoDeDados.getInstance();
		
	 	if (usuarioDao.usuarioExiste(nomeUsario)) {
	 		if(usuarioDao.getUsuario(nomeUsario).getSenha().equals(senha)) {
	 			this.usuarioLogado = usuarioDao.getUsuario(nomeUsario);
	 			return true;
	 		} else {
	 			Alert alert = new Alert(AlertType.INFORMATION);
	 	    	alert.initStyle(StageStyle.UNDECORATED);
	 			alert.setHeaderText("Senha incorreta");
	 			alert.setContentText("Tente novamente");
	 			if(alert.showAndWait().get() == ButtonType.OK){
	 				System.out.println("Alerta fechado");
		 		}
	 			return false;
	 		}
	 	} else {
	 		Alert alert = new Alert(AlertType.INFORMATION);
 	    	alert.initStyle(StageStyle.UNDECORATED);
 			alert.setHeaderText("Usuário não existe");
 			alert.setContentText("Realize um novo cadastro");
 			if(alert.showAndWait().get() == ButtonType.OK){
 				System.out.println("Alerta fechado");
	 		}
 			return false;
	 	}
	}
	
	public Usuario getUsuarioLogado() {
		return this.usuarioLogado;
	}
}
