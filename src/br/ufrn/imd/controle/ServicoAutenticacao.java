package br.ufrn.imd.controle;

import br.ufrn.imd.dao.BancoDeDados;
import br.ufrn.imd.modelo.Usuario;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.StageStyle;

/**
 * Classe que gerencia a autenticação de usuários.
 */
public class ServicoAutenticacao {
	private static ServicoAutenticacao instance;
	private Usuario usuarioLogado;
	
	/**
	 * Construtor privado para a classe ServicoAutenticacao.
	 */
	private ServicoAutenticacao() {
		this.usuarioLogado = null;
	}
	
	/**
	 * Retorna a instância única do serviço de autenticação.
	 * @return instance.
	 */
	public static ServicoAutenticacao getInstance() {
		if(instance==null) {
			instance = new ServicoAutenticacao();
		}
		
		return instance;
	}
	
	/**
	 * Realiza o login do usuário.
	 * @param nomeUsario.
	 * @param senha.
	 * @return true se o login for bem-sucedido, false caso contrário.
	 */
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
	
	/**
	 * Retorna o usuário que está atualmente logado.
	 * @return usuarioLogado.
	 */
	public Usuario getUsuarioLogado() {
		return this.usuarioLogado;
	}
}
