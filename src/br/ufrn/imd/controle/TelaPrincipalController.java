package br.ufrn.imd.controle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import br.ufrn.imd.dao.BancoDeMusicas;
import br.ufrn.imd.dao.BancoDePlaylists;
import javafx.util.Duration;

import java.util.*;

import br.ufrn.imd.dao.BancoDeDados;
import br.ufrn.imd.dao.BancoDeDiretorios;
import br.ufrn.imd.modelo.ControllerUtils;
import br.ufrn.imd.modelo.Diretorio;
import br.ufrn.imd.modelo.Musica;
import br.ufrn.imd.modelo.Playlist;
import br.ufrn.imd.modelo.Usuario;
import br.ufrn.imd.modelo.UsuarioVip;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;


/**
 * Classe controladora para a tela principal.
 */
public class TelaPrincipalController extends ControllerUtils implements Initializable {
	@FXML
	private Button btnVoltar, btnAvancar,btnTocarPausar;
	
	@FXML
	private ListView<Playlist> listPlaylists;
	
	@FXML
	private ListView<Musica> listMusicas;
	
	@FXML
	private ListView<Diretorio> listDiretorios;
	
	@FXML
	private Label lbTempo, lbNome, lbTipo, lbPlaylist, lbMusica;
	
	@FXML
	private MenuItem sair;
	
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
	private MediaPlayer mediaPlayer;
	
	@Override
	/**
	 * Método responsável por inicializar os valores da tela principal.
	 */
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
	    	BancoDePlaylists.getInstance().carregarPlaylistDeArquivo();
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
	    BancoDeDiretorios.getInstancia().salvarDiretoriosEmArquivo();
		BancoDeMusicas.getInstance().carregarMusicasDeArquivo();
		BancoDePlaylists.getInstance().salvarPlaylistEmArquivo();
	    exibirNomesMusicas();
	    exibirNomesDiretorios();
	    exibirNomesPlaylists();

	}
	/**
	 * Método para desabilitar os elementos do player.
	 */
	private void desligarPlayer() {
		sliderMusica.setDisable(true);
	    lbMusica.setVisible(false);
	    btnTocarPausar.setDisable(true);
	    btnVoltar.setDisable(true);
	    btnAvancar.setDisable(true);
	}
	/**
	 * Método para habilitar os elementos do player.
	 */
	private void ligarPlayer() {
		sliderMusica.setDisable(false);
	    lbMusica.setVisible(true);
	    btnTocarPausar.setDisable(false);
	    btnVoltar.setDisable(false);
	    btnAvancar.setDisable(false);
	}
	/**
     * Método para exibir os nomes das músicas na interface do usuário.
     */
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
		                        setText(musica.getNome());
		                    }
		                }
		            };
		        }
		    });
	}
	/**
     * Método para exibir os nomes das playlists na interface do usuário.
     */
	private void exibirNomesPlaylists() {
		playlistsVisiveis = FXCollections.observableArrayList();
		if(getUsuarioLogado() instanceof UsuarioVip) {
			playlistsVisiveis.addAll(((UsuarioVip) getUsuarioLogado()).getPlaylists());
		}
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
	/**
     * Método para exibir os nomes dos diretóriso na interface do usuário.
     */
	private void exibirNomesDiretorios() {
		diretoriosVisiveis = FXCollections.observableArrayList();
		diretoriosVisiveis.addAll(getUsuarioLogado().getDiretorios());
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
						int indice = diretorio.getCaminho().lastIndexOf("\\");
						String nome = diretorio.getCaminho().substring(indice + 1);
						setText(nome);
					}
					}
				};
			}
		});
	}
	/**
	 * Método para unir a barra de progressão com o slider.
	 */
	private void progressao() {
		sliderMusica.valueProperty().addListener((obs, oldValue, newValue)->{
			proSlider.setProgress(newValue.doubleValue()/sliderMusica.getMax());
		});
	}
	/**
	 * Retorna o usúario logado.
	 * @return Usúario logado.
	 */
	public Usuario getUsuarioLogado() {
		ServicoAutenticacao servicoAutenticacao = ServicoAutenticacao.getInstance();
		return servicoAutenticacao.getUsuarioLogado();
	}

	@FXML
	/**
	 * Método para tocar uma música quando selecionada.
	 * @param event.
	 */
	public void pausarTocar(MouseEvent event) {
		tocarMusica(listMusicas.getSelectionModel().getSelectedItem());
	}
	/**
	 * Método para gerenciar o estado da música.
	 * @param musica.
	 */
	
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
	@FXML
	/**
	 * Método para controlar o botão de pausar e tocar.
	 * @param event. 
	 */
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
	/**
	 * Método para voltar uma música.
	 * @param event.
	 */
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
	            currentSongIndex = listMusicas.getItems().size() - 1;
	        } else {
	            currentSongIndex = index - 1;
	        }
	        listMusicas.getSelectionModel().select(currentSongIndex);
	        tocarMusica(listMusicas.getItems().get(currentSongIndex));
	    }
	}
	
	@FXML
	/**
	 * Método para avançar uma música.
	 * @param event.
	 */
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

	/**
	 * Método para atualizar o tempo da música
	 */
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
	/**
	 * Método para atualizar o slider do tocador
	 */
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
    private MenuItem mnItemFileAddDirectory;
    
    @FXML
    /**
     * Método para abrir a tela de seleção de diretório e adicionar o diretório selecionado.
     * @param event.
     */
    public void abrirTelaAddDirectory(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Escolha um diretório");
        File selectedDirectory = directoryChooser.showDialog(estado);
        if(selectedDirectory != null) {
            Diretorio diretorio = new Diretorio(selectedDirectory.getAbsolutePath());
            diretorio.carregarMusicas();
            if(!getUsuarioLogado().diretorioExiste(diretorio.getCaminho())){
            	BancoDeDiretorios.getInstancia().adicionarDiretorio(diretorio);
				BancoDeDados.getInstance().salvarUsuariosEmArquivo();
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
    /**
     * Método para exibir as músicas de um diretório ou playlist selecionados.
     * @param event.
     */
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
    /**
     * Método para criar uma playlist
     * @param event
     */
    public void criarPlaylist(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Criacao de playlists");
        dialog.setHeaderText(null);
        dialog.setContentText("Digite o nome da playlist:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(input -> {
            String userInput = input;
            Playlist play = new Playlist(userInput);
			play.setIdUsuario(getUsuarioLogado().getId());

			BancoDePlaylists.getInstance().adicionarPlaylist(play);
			BancoDePlaylists.getInstance().salvarPlaylistEmArquivo();
            playlistsVisiveis.add(play);
        });
    }
    
    @FXML
    /**
     * Método para selecionar uma música e fazer uma cópia
     * @param event
     */
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
    /**
     * Método para iniciar o arrasto de uma música.
     * @param event.
     */
    public void arrastarMusica(DragEvent event) {
		 if (event.getGestureSource() != listPlaylists && event.getDragboard().hasString()) {
			 event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
		 }
		 event.consume();
    }
    /**
     * Método para receber uma música arrastada para uma playlist.
     * @param event.
     */
    @FXML
    public void receberMusica(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            Playlist selectedPlaylist = listPlaylists.getSelectionModel().getSelectedItem();
            String[] musicaProperties = db.getString().split(";");
            Musica musica = new Musica();
            musica.setNome(musicaProperties[0]);
            musica.setCaminho(musicaProperties[1]);
            musica.setId
            (UUID.fromString(musicaProperties[2]));
            if(selectedPlaylist.musicaExiste(musica.getNome())) {
            	 Alert alert = new Alert(AlertType.INFORMATION);
                 alert.initStyle(StageStyle.UNDECORATED);
                 alert.setHeaderText("Musica já está na playlist!");
                 alert.showAndWait();
                 success = false;
            }
            else {

            	selectedPlaylist.getMusicas().add(musica);
				BancoDePlaylists.getInstance().salvarPlaylistEmArquivo();
				BancoDeMusicas.getInstance().carregarMusicasDeArquivo();
                listPlaylists.refresh();
                success = true;
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setHeaderText("Musica adicionada com sucesso!");
                alert.showAndWait();
            }
            
        }
        listPlaylists.getSelectionModel().clearSelection();
        event.setDropCompleted(success);
        event.consume();
    }
    
    @FXML
    /**
     * Método para destacar uma playlist quando uma música é arrastada sobre ela.
     * @param dragEvent.
     */
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
    
    /**
     * Método para exibir informações pertinentes
     * @param event
     */
    public void readme(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.initStyle(StageStyle.UNDECORATED);
    	alert.setHeaderText("Para adicionar uma música a uma playlist, apenas arraste a música");
    	alert.setContentText("Autores: Hebert França e José Victor");
    	alert.showAndWait();
    }
	
	private Stage estado;
	/**
     * Método para fechar o programa
     */
	public void logout(ActionEvent event) {
		BancoDeDados.getInstance().salvarUsuariosEmArquivo();
		fecharPrograma(event);
		
	}
	/**
	 * Método para deslogar do sistema.
	 * @param event.
	 * @throws IOException.
	 */
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
