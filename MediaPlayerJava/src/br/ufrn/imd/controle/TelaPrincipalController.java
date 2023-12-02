package br.ufrn.imd.controle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import br.ufrn.imd.dao.BancoDeDados;
import br.ufrn.imd.dao.BancoDeDiretorios;
import br.ufrn.imd.modelo.Diretorio;
import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.modelo.Playlist;
import br.ufrn.imd.modelo.Usuario;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

public class TelaPrincipalController implements Initializable {
	@FXML
	private Label musica;
	
	@FXML
	private Button btnVoltar, btnAvancar,btnTocarPausar;
	
	@FXML
	private ListView<Playlist> listPlaylists;
	
	@FXML
	private ListView<Musica> listMusicas;
	
	@FXML
	private ListView<Diretorio> listDiretorios;
	
	@FXML
	private Label lbTempo;
	
	@FXML
	private MenuItem sair;
	
	@FXML
	private Label lbNome;
	
	@FXML
	private Label lbTipo;
	
	@FXML
	private Label lbMusica;
	
	@FXML
	private Label lbPlaylist;
	
	@FXML
	private Menu mnPlaylists;
	
	@FXML
	private ImageView imgAvatar;
	
	@FXML
	private Slider sliderMusica;
	
	@FXML
	private ProgressBar proSlider;
	
	private ObservableList<Musica> musicasVisiveis;
	private ObservableList<Playlist> playlistsVisiveis;
	private ObservableList<Diretorio> diretoriosVisiveis;
	
	private String musicaSelecionada;
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
		playlistsVisiveis = FXCollections.observableArrayList();
	    progressao();
	    lbNome.setText(getUsuarioLogado().getNome());
	    lbTipo.setText(getUsuarioLogado().getTipo());
	    desligarPlayer();
	    if(ServicoAutenticacao.getInstance().getUsuarioLogado().getTipo().equals("Comum")) {
	    	lbPlaylist.setVisible(false);
	    	listPlaylists.setVisible(false);
	    	listPlaylists.setDisable(true);
	    	mnPlaylists.setDisable(true);
	    	mnPlaylists.setVisible(false);
	    	
	    }
	    else {
	    	lbPlaylist.setVisible(true);
	    	listPlaylists.setVisible(true);
	    	listPlaylists.setDisable(false);
	    	mnPlaylists.setDisable(false);
	    	mnPlaylists.setVisible(true);
	    }

	    try {
	        for (int i = 1; i <= 7; i++) {
	            images.add(new Image(new FileInputStream("avatares/avatar" + i + ".png")));
	        }
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    }
	    if(!getUsuarioLogado().getAvatar().equals("0")) {
	        try {
	            imgAvatar.setImage(new Image(new FileInputStream("avatares/avatar" + getUsuarioLogado().getAvatar()+".png")));
	            imageIndex = Integer.parseInt(getUsuarioLogado().getAvatar()) - 1;
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }
	    } else {
	        imageIndex = (imageIndex + 1) % images.size();
	        imgAvatar.setImage(images.get(Integer.parseInt(getUsuarioLogado().getAvatar())));
	    }

	    imgAvatar.setOnMouseClicked(event -> {
	        imageIndex = (imageIndex + 1) % images.size();
	        imgAvatar.setImage(images.get(imageIndex));
	        getUsuarioLogado().setAvatar(Integer.toString(imageIndex+1));
	    });
	    BancoDeDados.getInstance().salvarUsuariosEmArquivo();
	    
	    exibirNomesMusicas();
	    exibirNomesDiretorios();
	    exibirNomesPlaylists();

	}
	
	private void desligarPlayer() {
		sliderMusica.setDisable(true);
	    lbMusica.setVisible(false);
	    btnTocarPausar.setDisable(true);
	    btnVoltar.setDisable(true);
	    btnAvancar.setDisable(true);
	}
	
	private void ligarPlayer() {
		sliderMusica.setDisable(false);
	    lbMusica.setVisible(true);
	    btnTocarPausar.setDisable(false);
	    btnVoltar.setDisable(false);
	    btnAvancar.setDisable(false);
	}
	private void exibirNomesMusicas() {
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
	}
	
	private void exibirNomesPlaylists() {
		playlistsVisiveis = FXCollections.observableArrayList();
		listPlaylists.setItems(playlistsVisiveis);
		listPlaylists.setCellFactory(new Callback<ListView<Playlist>, ListCell<Playlist>>() {
		    @Override
		    public ListCell<Playlist> call(ListView<Playlist> param) {
		    	return new ListCell<Playlist>() {
		    		@Override
		    		protected void updateItem(Playlist play, boolean empty) {
		    			super.updateItem(play, empty);
		    			if (empty || play == null) {
		    				setText(null);
		    			} else {
		    				play.getNome();
		                    int indice = play.getNome().lastIndexOf("\\");
		                    String nome = play.getNome().substring(indice + 1);
		                    setText(nome);
		    			}
		    		}
		    	};
		    }
		    });
	}
	
	private void exibirNomesDiretorios() {
		diretoriosVisiveis = FXCollections.observableArrayList();
		diretoriosVisiveis.addAll(BancoDeDiretorios.getInstancia().getDiretorios());
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

	@FXML
	private void pausarTocar(MouseEvent event) {
		tocarMusica(listMusicas.getSelectionModel().getSelectedItem());
	}
	
	private void tocarMusica(Musica musica) {
		ligarPlayer();	
	    if (musica == null) {
	        return;
	    }
	    if (musica.getNome().equals(musicaSelecionada)) {
	        mediaPlayer.stop();
	        mediaPlayer.play();
	    } else {
	        if (mediaPlayer != null) {
	            mediaPlayer.stop();
	        }
	        File f = new File(musica.getCaminho());
	        URI u = f.toURI();
	        Media media = new Media(u.toString());
	        mediaPlayer = new MediaPlayer(media);
	        mediaPlayer.setOnEndOfMedia(() -> {
	            int index = listMusicas.getSelectionModel().getSelectedIndex();
	            if (index < listMusicas.getItems().size() - 1) {
	                listMusicas.getSelectionModel().select(index + 1);
	            } else {
	                listMusicas.getSelectionModel().select(0);
	            }
	            tocarMusica(listMusicas.getSelectionModel().getSelectedItem());
	        });
	        mediaPlayer.play();
	        updateTempoMusica();
	        updateSlider();
	        btnTocarPausar.setText("| |");
	        tocando = true;
	        musicaSelecionada = musica.getNome();
	    }
	    lbMusica.setText(musica.getNome());
	}

	private boolean tocando = false;
	public void tocarPausar(ActionEvent event) {
		if(mediaPlayer == null) {
			return;
		}
		if(tocando) {
			mediaPlayer.pause();
			btnTocarPausar.setText("|>");
			
		}
		else {
			mediaPlayer.play();
			btnTocarPausar.setText("| |");
		}
		tocando = !tocando;
		
	}
	
	private int currentSongIndex = 0;
	@FXML
	private void voltarMusica(ActionEvent event) {
	    if (mediaPlayer == null) {
	        return;
	    }

	    Duration currentTime = mediaPlayer.getCurrentTime();
	    if (currentTime.toSeconds() > 5) {
	        mediaPlayer.seek(Duration.ZERO);
	    } else {
	        int index = listMusicas.getSelectionModel().getSelectedIndex();
	        if (index == 0) {
	            currentSongIndex = listMusicas.getItems().size() - 1; // seleciona a última música da lista
	        } else {
	            currentSongIndex = index - 1;
	        }
	        listMusicas.getSelectionModel().select(currentSongIndex);
	        tocarMusica(listMusicas.getItems().get(currentSongIndex));
	    }
	}
	
	@FXML
	private void avancarMusica(ActionEvent event) {
	    if (mediaPlayer == null) {
	        return;
	    }

	    int index = listMusicas.getSelectionModel().getSelectedIndex();
	    if (index == listMusicas.getItems().size() - 1) {
	        currentSongIndex = 0;
	    } else {
	        currentSongIndex = index + 1;
	    }
	    listMusicas.getSelectionModel().select(currentSongIndex);
	    tocarMusica(listMusicas.getItems().get(currentSongIndex));
	}

	
	public void updateTempoMusica() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Duration currentTime = mediaPlayer.getCurrentTime();
                String formattedTime = String.format("%02d:%02d:%02d",
                        (int) currentTime.toHours(),
                        (int) currentTime.toMinutes() % 60,
                        (int) currentTime.toSeconds() % 60);
                lbTempo.setText(formattedTime);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
	
	public void updateSlider() {
        mediaPlayer.setOnReady(() -> {
            Duration duration = mediaPlayer.getMedia().getDuration();
            sliderMusica.setMax(duration.toSeconds());
        });

        sliderMusica.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (sliderMusica.isValueChanging()) {
                mediaPlayer.seek(Duration.seconds(newValue.doubleValue()));
            }
        });

        mediaPlayer.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (!sliderMusica.isValueChanging()) {
                sliderMusica.setValue(newValue.toSeconds());
            }
        });
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
    
    @FXML
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
            if(BancoDeDiretorios.getInstancia().verificarDiretorio(diretorio.getCaminho())== null){
            	BancoDeDiretorios.getInstancia().adicionarDiretorio(diretorio);
            	diretoriosVisiveis.add(diretorio);
            }
            else {
            	Alert alert = new Alert(AlertType.INFORMATION);
    	    	alert.initStyle(StageStyle.UNDECORATED);
    	    	alert.setHeaderText("Diretorio já existe");
    	    	alert.showAndWait();
            }
        }
    }
    
    @FXML
    public void exibirMusicas(MouseEvent event) {
        Diretorio diretorio = listDiretorios.getSelectionModel().getSelectedItem();
        Playlist play = listPlaylists.getSelectionModel().getSelectedItem();

        listDiretorios.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                listPlaylists.getSelectionModel().clearSelection();
            }
        });

        listPlaylists.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                listDiretorios.getSelectionModel().clearSelection();
            }
        });
        // Seu código existente
        if (diretorio != null) {
        	if(mediaPlayer!=null) {
        		mediaPlayer.stop();
        	}
        	desligarPlayer();
            musicasVisiveis.clear();
            musicasVisiveis.addAll(diretorio.getMusicas());
        }
        
        if(play!=null) {
        	if(mediaPlayer!=null) {
        		mediaPlayer.stop();
        	}
        	desligarPlayer();
        	musicasVisiveis.clear();
            musicasVisiveis.addAll(play.getMusicas());
        }
    }

    
    @FXML
    public void exibirMusicasPlaylist(MouseEvent event) {
    	listDiretorios.getSelectionModel().clearSelection();
    	Diretorio diretorio = listDiretorios.getSelectionModel().getSelectedItem();
    	Playlist play = listPlaylists.getSelectionModel().getSelectedItem();
        if (play != null) {
            // Limpar o ObservableList das músicas
            musicasVisiveis.clear();
            // Adicionar as músicas do diretório ao ObservableList
            musicasVisiveis.addAll(diretorio.getMusicas());
        }
    }
    
    @FXML
    public void criarPlaylist(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Criacao de playlists");
        dialog.setHeaderText(null);
        dialog.setContentText("Digite o nome da playlist:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            // Salve a entrada do usuário em uma string
            String userInput = input;
            Playlist play = new Playlist(userInput);
            playlistsVisiveis.add(play);
            System.out.println("Entrada do usuário: " + userInput);
        });
        result.ifPresent(input -> System.out.println("Entrada do usuário: " + input));
    }
    @FXML
    public void selecionarMusica(MouseEvent event) {
        Musica musica = listMusicas.getSelectionModel().getSelectedItem();
        Dragboard db = listMusicas.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        String musicaString = musica.getNome() + ";" + musica.getCaminho() + ";" + musica.getId();
        content.putString(musicaString);
        db.setContent(content);
        event.consume();
    }
    
    @FXML
    public void arrastarMusica(DragEvent event) {
    	 if (event.getGestureSource() != listPlaylists && event.getDragboard().hasString()) {
    	        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
    	    }
    	    event.consume();
    }
    @FXML
    public void receberMusica(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            // Obtenha a playlist selecionada
            Playlist selectedPlaylist = listPlaylists.getSelectionModel().getSelectedItem();

            // Separe a String de música de volta em suas propriedades
            String[] musicaProperties = db.getString().split(";");

            // Crie uma nova música com as propriedades
            Musica musica = new Musica();
            musica.setNome(musicaProperties[0]);
            musica.setCaminho(musicaProperties[1]);
            musica.setId
            (musicaProperties[2]); // Adicione mais propriedades conforme necessário

            // Adicione a música à playlist selecionada
            selectedPlaylist.getMusicas().add(musica);

            // Atualize a ListView da playlist
            listPlaylists.refresh();

            success = true;
        }
        // Desmarque a seleção
        listPlaylists.getSelectionModel().clearSelection();

        event.setDropCompleted(success);
        event.consume();
    }

    
    @FXML
    public void hoverPlaylist(DragEvent dragEvent) {
        listPlaylists.setCellFactory(lv -> {
            ListCell<Playlist> cell = new ListCell<>();

            cell.setOnDragEntered(event -> {
                if (event.getGestureSource() != cell && event.getDragboard().hasString()) {
                    listPlaylists.getSelectionModel().select(cell.getIndex());
                }
            });

            cell.itemProperty().addListener((obs, oldItem, newItem) -> {
                if (newItem == null) {
                    cell.setText(null);
                } else {
                    cell.setText(newItem.getNome());
                }
            });
            return cell;
        });
    }
	
	private Stage estado;
	public void logout(ActionEvent event) {
		BancoDeDados.getInstance().salvarUsuariosEmArquivo();
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
		BancoDeDados.getInstance().salvarUsuariosEmArquivo();
	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	    alert.setTitle("Confirmação");
	    alert.setHeaderText("Você tem certeza que deseja sair?");
	    alert.setContentText("Clique em OK para confirmar, ou Cancelar para voltar.");
	    if (alert.showAndWait().get() == ButtonType.OK){
	    	if(mediaPlayer!=null) {
	    		mediaPlayer.stop();
	    	}
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
