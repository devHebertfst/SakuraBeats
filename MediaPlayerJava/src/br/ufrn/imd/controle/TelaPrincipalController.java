package br.ufrn.imd.controle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.ufrn.imd.dao.BancoDeDiretorios;
import br.ufrn.imd.modelo.Diretorio;
import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.modelo.Playlist;
import br.ufrn.imd.modelo.ServicoAutenticacao;
import br.ufrn.imd.modelo.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class TelaPrincipalController implements Initializable {
	@FXML
	private Label musica;
	
	@FXML
	private Button btnVoltar, btnAvancar, btnPausar;
	
	@FXML
	private ListView<Playlist> listPlaylists;
	
	@FXML
	private ListView<Musica> listMusicas;
	
	@FXML
	private ListView<Diretorio> listDiretorios;
	
	@FXML
	private MenuItem sair;
	
	@FXML
	private Label lbNome;
	
	@FXML
	private Label lbTipo;
	
	@FXML
	private ImageView imgAvatar;
	
	@FXML
	private Slider sliderMusica;
	
	@FXML
	private ProgressBar proSlider;
	
	private ObservableList<Musica> musicasVisiveis;
	private ObservableList<Playlist> playlistsVisiveis;
	private ObservableList<Diretorio> diretoriosVisiveis;
	
	
	private List<Image> images = new ArrayList<>();
    private int imageIndex = 0;
	private Parent root;
	private double xOffset = 0;
    private double yOffset = 0;
    private Stage stage;
	private Scene scene;
	private Usuario usuarioLogado;
	private MediaPlayer mediaPlayer;
	private Media media;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	    progressao();
	    lbNome.setText(getUsuarioLogado().getNome());
	    lbTipo.setText(getUsuarioLogado().getTipo());
	    try {
	        for (int i = 1; i <= 7; i++) {
	            images.add(new Image(new FileInputStream("avatares/avatar" + i + ".png")));
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }

	    imgAvatar.setImage(images.get(imageIndex));

	    imgAvatar.setOnMouseClicked(event -> {
	        imageIndex = (imageIndex + 1) % images.size();
	        imgAvatar.setImage(images.get(imageIndex));
	    });
	    musicasVisiveis = FXCollections.observableArrayList();
	    
	    listMusicas.setItems(musicasVisiveis);
	    listMusicas.setCellFactory((Callback<ListView<Musica>, ListCell<Musica>>) new Callback<ListView<Musica>, ListCell<Musica>>() {
	        @Override
	        public ListCell<Musica> call(ListView<Musica> param) {
	            return new ListCell<Musica>() {
	                @Override 
	                protected void updateItem(Musica musica, boolean empty) {
	                    super.updateItem(musica, empty);
	                    if (empty || musica == null) {
	                        setText(null);
	                    } else {
	                        // Mostrar apenas o nome da música
	                        setText(musica.getNome());
	                    }
	                }
	            };
	        }
	    });
	    
	    diretoriosVisiveis = FXCollections.observableArrayList();
        diretoriosVisiveis.addAll(BancoDeDiretorios.getInstancia().getDiretorios());
        // Vincular o ObservableList ao ListView dos diretórios
        listDiretorios.setItems(diretoriosVisiveis);
	    
        listDiretorios.setCellFactory(new Callback<ListView<Diretorio>, ListCell<Diretorio>>() {
            @Override
            public ListCell<Diretorio> call(ListView<Diretorio> param) {
                return new ListCell<Diretorio>() {
                    @Override
                    protected void updateItem(Diretorio diretorio, boolean empty) {
                        super.updateItem(diretorio, empty);
                        if (empty || diretorio == null) {
                            setText(null);
                        } else {
                        	diretorio.getCaminho();
                        	int indice = diretorio.getCaminho().lastIndexOf("\\");
                        	String nome = diretorio.getCaminho().substring(indice + 1);
                            setText(nome);
                        }
                    }
                };
            }
        });



	}

	
	private void progressao() {
		sliderMusica.valueProperty().addListener((obs, oldValue, newValue)->{
			proSlider.setProgress(newValue.doubleValue()/sliderMusica.getMax());
		});
	}
	
	public Usuario getUsuarioLogado() {
		ServicoAutenticacao servicoAutenticacao = ServicoAutenticacao.getInstance();
		return servicoAutenticacao.getUsuarioLogado();
	}

	public void tocarMusica() {
		mediaPlayer.play();
	}
	
	public void pausarMusica() {
		mediaPlayer.pause();
	}
	
	public void voltarMusica() {
		
	}
	
	public void avancarMusica() {
		
	}
	
	
	@FXML
    private MenuItem mnItemFileAddFile;

    @FXML
    void abrirTelaAddFile(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Escolha um arquivo MP3");
    	fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo MP3", "*.mp3"));
    	
    	var selectedFile = fileChooser.showOpenDialog(estado);
    	if(selectedFile != null) {
    		ServicoAutenticacao servicoAutenticacao = ServicoAutenticacao.getInstance();
    		
    		Musica musica = new Musica();
    		musica.setCaminho(selectedFile.getAbsolutePath());
    		musica.setNome(selectedFile.getName());
    		
    		//servicoAutenticacao.getUsuarioLogado().getDiretorio().addMusic(musica);
    		System.out.println("Arquivo selecionado: " + selectedFile.getName());
    	}
    }
    
    @FXML
    private MenuItem mnItemFileAddDirectory;
    
    public void abrirTelaAddDirectory(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Escolha um diretório");
        
        File selectedDirectory = directoryChooser.showDialog(estado);
        if(selectedDirectory != null) {
            // Criar um objeto Diretorio a partir do selectedDirectory
            Diretorio diretorio = new Diretorio(selectedDirectory.getAbsolutePath());
            // Carregar as músicas do diretório
            diretorio.carregarMusicas();
            // Adicionar o diretório ao usuário logado
            BancoDeDiretorios.getInstancia().adicionarDiretorio(diretorio);
            // Adicionar as novas músicas ao ObservableList, evitando duplicação
            // Adicionar o novo diretório ao ObservableList dos diretórios
            diretoriosVisiveis.add(diretorio);
        }
    }
    
    public void exibirMusicas(MouseEvent event) {
    	Diretorio diretorio = listDiretorios.getSelectionModel().getSelectedItem();
        if (diretorio != null) {
            // Limpar o ObservableList das músicas
            musicasVisiveis.clear();
            // Adicionar as músicas do diretório ao ObservableList
            musicasVisiveis.addAll(diretorio.getMusicas());
        }
    }
	
	
	private Stage estado;
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
	
	public void sair(ActionEvent event) throws IOException {
	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.setTitle("Confirmação");
	    alert.setHeaderText("Você tem certeza que deseja sair?");
	    alert.setContentText("Clique em OK para confirmar, ou Cancelar para voltar.");
	    if (alert.showAndWait().get() == ButtonType.OK){
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
	        scene = new Scene(root);
	        stage = (Stage)sair.getParentPopup().getOwnerWindow();
	        stage.setScene(scene);
	        stage.show();
	    }
	    else {
	    	alert.close();
	    }
	}

}
