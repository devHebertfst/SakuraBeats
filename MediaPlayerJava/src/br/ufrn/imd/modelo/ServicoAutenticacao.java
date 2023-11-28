package br.ufrn.imd.modelo;

import br.ufrn.imd.dao.BancoDeDados;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	 			alert.showAndWait();
	 			return false;
	 		}
	 	} else {
	 		Alert alert = new Alert(AlertType.INFORMATION);
 	    	alert.initStyle(StageStyle.UNDECORATED);
 			alert.setHeaderText("Usuario nao existe");
 			alert.setContentText("Realize um novo cadastro");
 			alert.showAndWait();
 			return false;
	 	}
	}
	
	public Usuario getUsuarioLogado() {
		return this.usuarioLogado;
	}
}