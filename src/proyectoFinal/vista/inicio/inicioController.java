package proyectoFinal.vista.inicio;

import java.io.File;
import java.sql.Connection;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Callback;
import proyectoFinal.datos.bda.conexionDAO;
import proyectoFinal.datos.bda.usuarioDAO;
import proyectoFinal.modelo.Herramientas;
import proyectoFinal.modelo.Pack;
import proyectoFinal.vista.actividades.actividadesController;
import proyectoFinal.vista.menuAdmin.menuAdminController;

public class inicioController {

    private Connection conexion;
    private conexionDAO conectar;
    private Pack pack = new Pack(); //Por ahora
    private Herramientas herramientas;
    private usuarioDAO usuarioDAO;

    @FXML
    private Pane menu;
    @FXML
    private Button botonHosteleria;
    @FXML
    private Button botonOcio;
    @FXML
    private Button botonHoteles;
    @FXML
    private Button botonCrearActividad;
    @FXML
    private DatePicker fechaInicioPack;
    @FXML
    private DatePicker fechaFinPack;
    @FXML
    private TextField textNombrePack;
    @FXML
    private Button buttonAceptarNombre;
    @FXML
    private Label labelNombrePack;
    @FXML
    private Pane mainPane;
    @FXML
    private MediaView mediaView;

    MediaPlayer mediaPlayer;
    Button botonPausa;

    private String mailUsuario;
    private int idUsuario;

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public void setConectar(conexionDAO conectar) {
        this.conectar = conectar;
    }

    public void setHerramientas(Herramientas herramientas) {
        this.herramientas = herramientas;
    }

    public void setUsuarioDAO(usuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public void setMailUsuario(String mailUsuario) {
        this.mailUsuario = mailUsuario;
    }

    public void initialize() {
    }

    public void inicio() {
        try {
            idUsuario = usuarioDAO.getIdusuario(mailUsuario);
            pack.setIdUsuario(idUsuario);
            setDefault();
            pack.setFechaCreacion(LocalDate.now());
            pack.setTiempoCreacion(LocalTime.now());
        } catch (SQLException e) {
            herramientas.showAlertErr("Error " + e.getMessage());
        }
    }

    public void setDefault() {
        fechaInicioPack.setShowWeekNumbers(false);
        fechaFinPack.setShowWeekNumbers(false);
        fechaFinPack.setDisable(true);
        addVideo();
        Callback<DatePicker, DateCell> dayCellFactory = herramientas.getDayCellFactoryInicio(LocalDate.now());
        fechaInicioPack.setDayCellFactory(dayCellFactory);
    }

    //VENTANAS Y ESCENAS
    @FXML
    private void mostrarActividades(ActionEvent event) {

        if (labelNombrePack.getText().length() != 0 && fechaInicioPack.getValue() != null && fechaFinPack.getValue() != null) {
            if (checkFechas()) {
                mediaPlayer.stop();
                Object botonPulsado = event.getSource();
                int tipo;
                if (botonPulsado == botonHoteles) {
                    tipo = 1;
                } else if (botonPulsado == botonOcio) {
                    tipo = 2;
                } else {
                    tipo = 3;
                }

                FXMLLoader loader;
                Parent root = null;
                try {
                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/proyectoFinal/vista/actividades/actividades.fxml"));
                    root = loader.load(); //Se ejecuta el Initialize

                    actividadesController actividadesController = loader.getController();
                    actividadesController.setConexion(conexion);
                    actividadesController.setPack(pack);
                    actividadesController.setHerramientas(herramientas);
                    actividadesController.setTipo(tipo);
                    actividadesController.rellenarConPanes();

                    menu.getChildren().addAll(root);
                } catch (IOException ex) {
                    herramientas.showAlertErr(ex.getMessage());
                }

            } else {
                herramientas.showAlertErr("Fechas incorrectas");
            }

        } else {
            herramientas.showAlertErr("Configura tu pack primero");
        }
    }

    @FXML
    private void crearActividad(ActionEvent event) {
        try {
            if (usuarioDAO.getRol(pack.getIdUsuario()).equalsIgnoreCase("admin")) {
                mediaPlayer.stop();
                FXMLLoader loader;
                Parent root = null;
                try {

                    loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/proyectoFinal/vista/menuAdmin/menuAdmin.fxml"));
                    root = loader.load(); //Se ejecuta el Initialize

                    menuAdminController menuAdmin = loader.getController();
                    menuAdmin.setConex(conexion);
                    menuAdmin.setHerramientas(herramientas);

                    menu.getChildren().addAll(root);
                } catch (IOException ex) {
                    herramientas.showAlertErr("Error " + ex.getMessage());
                }
            }else{
                herramientas.showAlertErr("No eres administrador!");
            }
        } catch (SQLException e) {
            herramientas.showAlertErr("Error " + e.getMessage());
        }

    }

    //SETEAR CAMPOS
    private boolean changeFechas() { //REVISAR --------------------------
        if (pack.getListaActividades().isEmpty()) {
            return true;
        }
        fechaInicioPack.setValue(pack.getFechaInicio());
        fechaFinPack.setValue(pack.getFechaFinal());
        herramientas.showAlertErr("Lo siento, no es posible cambiar las fechas.\nPor favor, vacíe el carrito antes de realizar el cambio.");
        return false;
    }

    @FXML
    private void setFechaInicioPack(ActionEvent event) {
        if (changeFechas()) {
            if (fechaInicioPack != null) {
                pack.setFechaInicio(fechaInicioPack.getValue());

                //Habilitar y setear el campo de fecha fin del pack en el Controller Inicio
                Callback<DatePicker, DateCell> camposHabilitados = herramientas.getDayCellFactoryInicio(fechaInicioPack.getValue());
                fechaFinPack.setDayCellFactory(camposHabilitados);
                fechaFinPack.setDisable(false);
                pack.setFechaFinal(fechaInicioPack.getValue().plusDays(1));
                fechaFinPack.setValue(fechaInicioPack.getValue().plusDays(1)); //----
            }
            
        }
    }

    @FXML
    private void setFechaFinPack(ActionEvent event) {
        if (changeFechas()) {
            
            pack.setFechaFinal(fechaFinPack.getValue());
        }
    }

    @FXML
    private void crearNombrePack(ActionEvent event) {
        if (textNombrePack.getText().length() != 0) {
            pack.setNombre(textNombrePack.getText());
            labelNombrePack.setText("PACK: " + textNombrePack.getText().toUpperCase());
        } else {
            herramientas.showAlertErr("El nombre no puede estar vacio");
        }
    }

    private boolean checkFechas() {
        return fechaInicioPack.getValue().isBefore(fechaFinPack.getValue()) || fechaInicioPack.getValue().isEqual(fechaFinPack.getValue());
    }

    public void addVideo() {
        botonPausa = new Button();

        File file = new File("src/proyectoFinal/vid/malaga.mp4");
        Media media = new Media(file.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        botonPausa.setVisible(false);

        MediaView mediaView = new MediaView();
        mediaView.setMediaPlayer(mediaPlayer);
        mediaView.setFitHeight(400);
        mediaView.setFitWidth(600);
        mediaView.setLayoutX(215);
        mediaView.setLayoutY(60);

        botonPausa.setLayoutX(479);
        botonPausa.setLayoutY(195);
        botonPausa.setOpacity(0.65);
        botonPausa.setMaxSize(150, 150);
        botonPausa.setStyle("-fx-graphic: url(proyectoFinal/img/play.png); -fx-background-color: transparent");

        botonPausa.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {

                if (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
                    mediaPlayer.play();
                    botonPausa.setVisible(false);
                }

            }

        });

        botonPausa.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {

                botonPausa.setCursor(Cursor.HAND);
            }

        });

        mediaView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {

                if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaPlayer.pause();
                    botonPausa.setVisible(true);
                }

            }

        });
        mediaView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                    mediaView.setCursor(Cursor.HAND);
                } else {
                    mediaView.setCursor(Cursor.DEFAULT);
                }

            }

        });

        menu.getChildren().add(mediaView);
        menu.getChildren().add(botonPausa);
    }
}
