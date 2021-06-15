package proyectoFinal.vista.actividades;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.SVGPath;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import proyectoFinal.datos.bda.actividadDAO;
import proyectoFinal.modelo.Actividad;
import proyectoFinal.modelo.DetallePack;
import proyectoFinal.modelo.Herramientas;
import proyectoFinal.modelo.Pack;
import proyectoFinal.vista.carrito.carritoController;
import proyectoFinal.vista.detalles.detallesController;
import proyectoFinal.vista.menuAdmin.crearActividad.crearActividadController;

public class actividadesController {

    private ArrayList<Actividad> listActividades;

    private int calidadSelecc = 5;
    private double precioMaxSelecc = 100;

    private actividadDAO actividadDAO = new actividadDAO();
    private Pack pack;
    private Herramientas herramientas;
    private Connection conexion;
    private int tipo;

    @FXML
    private Pane barra;
    @FXML
    private Pane menu;
    @FXML
    private Button carritoButton;
    @FXML
    private Pane menuFiltros;
    @FXML
    private Button filtros;
    @FXML
    private AnchorPane menuActividades;

    private Pane pane;
    private ImageView imagen;
    private Label labelPrecio;
    private Label labelTitulo;
    private Label labelDescripcion;
    private Label labelPersona;
    private Label labelPuntuacion;
    private SVGPath estrella;
    private Button botonAnyadir;
    private Hyperlink hiperLink;
    private Pane panePrecio;
    private Button botonUbi;
    private WebView webView;
    private Pane paneMapa;

    Double posicionXestrella = 450.;
    Double posicionYestrella = 60.;
    @FXML
    private Slider sliPrecio;
    @FXML
    private SVGPath cal1;
    @FXML
    private SVGPath cal2;
    @FXML
    private SVGPath cal3;
    @FXML
    private SVGPath cal4;
    @FXML
    private SVGPath cal5;
    @FXML
    private Label labPrecio;
    @FXML
    private Label labelCarrito;
    @FXML
    private Pane paneBackground;

    public void initialize() {

    }

    //Setear parametros
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public void setHerramientas(Herramientas herramientas) {
        this.herramientas = herramientas;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public void rellenarConPanes() {

        Double posicionX = 100.;
        Double posicionY = 60.;

        menuActividades.setMinWidth(1043);
        try {
            actividadDAO.setConex(conexion);
            listActividades = actividadDAO.obtenerActividades(tipo);

            for (Actividad actividad : listActividades) {
                menuActividades.getChildren().add(crearPane(actividad, posicionX, posicionY));
                posicionY += 225;
            }

        } catch (SQLException ex) {
            herramientas.showAlertErr("No se ha podido establecer la conexión");
        }

        labelCarrito.setText(String.valueOf(pack.getListaActividades().size()));
    }

    @FXML
    private void mostrarCarrito(ActionEvent event) {
        FXMLLoader loader;
        Parent root = null;
        try {

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/proyectoFinal/vista/carrito/carrito.fxml"));
            root = loader.load(); //Se ejecuta el Initialize

            carritoController carrito = loader.getController();
            carrito.setConexion(conexion);
            carrito.setPack(pack);
            carrito.setHerramientas(herramientas);

            carrito.rellenarCarrito();

            menu.getChildren().addAll(root);
        } catch (IOException ex) {
            herramientas.showAlertErr("Error al acceder al carrito");
        }
    }

    @FXML
    private void toggleFiltros(ActionEvent event) {
        if (menuFiltros.isVisible()) {
            menuFiltros.setVisible(false);
            paneBackground.setVisible(false);
        } else {
            menuFiltros.setVisible(true);
            paneBackground.setVisible(true);
        }
    }

    private Pane crearPane(Actividad actividad, double posicionX, double posicionY) {

        pane = new Pane();
        imagen = new ImageView(new Image("proyectoFinal/img/" + actividad.getImagen()));
        labelPrecio = new Label();
        labelTitulo = new Label();
        labelDescripcion = new Label();
        botonAnyadir = new Button();
        labelPersona = new Label();
        hiperLink = new Hyperlink();
        panePrecio = new Pane();
        botonUbi = new Button();

        // coordenadas X e Y
        pane.setLayoutX(posicionX);
        pane.setLayoutY(posicionY);

        //tamaño de los botones: largo, alto
        pane.setMinSize(850, 200);

        imagen.setLayoutX(10);
        imagen.setLayoutY(10);
        imagen.setFitHeight(180);
        imagen.setFitWidth(225);

        labelTitulo.setLayoutX(300);
        labelTitulo.setLayoutY(15);
        labelTitulo.setText(actividad.getNombre());
        labelTitulo.setStyle("-fx-font-size: 22; -fx-text-fill:white");

        labelPrecio.setLayoutX(10);
        labelPrecio.setLayoutY(15);
        labelPrecio.setMinSize(170, 50);
        labelPrecio.setText(String.format("%.0f€", actividad.getPrecio()));
        labelPrecio.setStyle("-fx-font-size: 50; -fx-text-fill:white; -fx-alignment:center");

        labelDescripcion.setText(actividad.getDescripcion());
        labelDescripcion.setLayoutX(250);
        labelDescripcion.setLayoutY(75);
        labelDescripcion.setMaxSize(350, 200);
        labelDescripcion.setWrapText(true);
        labelDescripcion.setStyle("-fx-text-fill:white");

        botonAnyadir.setText("Añadir al carrito");
        botonAnyadir.setMinSize(50, 25);
        botonAnyadir.setLayoutX(47);
        botonAnyadir.setLayoutY(135);
        botonAnyadir.setStyle("-fx-background-color: #8C8C8C; -fx-text-fill:white; -fx-background-radius: 10");
        botonAnyadir.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                //Este codigo lo crea en el momento del click. 
                //No puede hacer referencias a variables de este codigo porque coge el ultimo valor
                cargarDetalles(actividad);
            }

        });

        labelPersona.setText("por persona");
        labelPersona.setLayoutX(70);
        labelPersona.setLayoutY(75);
        labelPersona.setStyle("-fx-font-size: 12; -fx-text-fill:white ");

        panePrecio.setMinSize(190, 180);
        panePrecio.setLayoutX(650);
        panePrecio.setLayoutY(10);
        panePrecio.setStyle("-fx-background-color:#85CAE3; -fx-background-radius: 15 ");
        panePrecio.getChildren().add(labelPrecio);
        panePrecio.getChildren().add(labelPersona);
        panePrecio.getChildren().add(botonAnyadir);

        hiperLink.setText("Visitar Web");
        hiperLink.setLayoutX(260);
        hiperLink.setLayoutY(50);
        hiperLink.setStyle("-fx-text-fill: #85CAE3");

        hiperLink.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().browse(new URI(actividad.getUrl()));
                    } catch (IOException | URISyntaxException e1) {
                        herramientas.showAlertErr("Fallo al cargar la página web");
                    }
                }
            }

        });

        for (int i = 1; i <= actividad.getCalidad(); i++) {

            estrella = new SVGPath();

            estrella.setContent("M 0.000 4.000\n"
                    + "L 5.878 8.090\n"
                    + "L 3.804 1.236\n"
                    + "L 9.511 -3.090\n"
                    + "L 2.351 -3.236\n"
                    + "L 0.000 -10.000\n"
                    + "L -2.351 -3.236\n"
                    + "L -9.511 -3.090\n"
                    + "L -3.804 1.236\n"
                    + "L -5.878 8.090\n"
                    + "L 0.000 4.000");
            estrella.setStyle("-fx-fill: #6AB1CA");
            estrella.setLayoutX(posicionXestrella);
            estrella.setLayoutY(posicionYestrella);

            pane.getChildren().add(estrella);
            posicionXestrella += 25;

            if (i == actividad.getCalidad()) {
                posicionXestrella = 450.;
                posicionYestrella = 60.;
            }

        }

        botonUbi.setStyle("-fx-graphic: url(proyectoFinal/img/ubi.png); -fx-background-color: transparent");
        botonUbi.setLayoutX(255);
        botonUbi.setLayoutY(10);
        botonUbi.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {

                mostrarMapa(actividad);
            }

        });
        botonUbi.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {

                botonUbi.setCursor(Cursor.HAND);
            }

        });

        pane.setStyle("-fx-background-color: #8C8C8C; -fx-background-radius: 15");
        pane.getChildren().add(imagen);
        pane.getChildren().add(labelTitulo);
        pane.getChildren().add(labelDescripcion);
        pane.getChildren().add(hiperLink);
        pane.getChildren().add(panePrecio);
        pane.getChildren().add(botonUbi);

        return pane;
    }

    @FXML
    private void variarPrecio(MouseEvent event) {
        this.precioMaxSelecc = sliPrecio.getValue();
        labPrecio.setText(String.format("%.0f€", this.precioMaxSelecc));
        filtrarActividades();
    }

    @FXML
    private void selecCalidad(MouseEvent event) {
        String idCalidad = "cal5";
        Node nodoQueHaGeneradoEvento = (Node) event.getSource();
        idCalidad = nodoQueHaGeneradoEvento.getId();
        estiloEstrellas(idCalidad);
        filtrarActividades();
    }

    private void filtrarActividades() {
        Double posicionX = 100.;
        Double posicionY = 50.;

        menuActividades.getChildren().clear();
        for (Actividad actividad : listActividades) {

            if (actividad.getCalidad() <= calidadSelecc && actividad.getPrecio() <= this.precioMaxSelecc) {
                menuActividades.getChildren().add(crearPane(actividad, posicionX, posicionY));
                posicionY += 225;
            }
        }
    }

    private void estiloEstrellas(String idCalidad) {
        switch (idCalidad) {
            case "cal1":
                cal5.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal4.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal3.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal2.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal1.setStyle("-fx-fill: #0073ff");
                this.calidadSelecc = 1;
                break;
            case "cal2":
                cal5.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal4.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal3.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal2.setStyle("-fx-fill: #0073ff");
                cal1.setStyle("-fx-fill: #0073ff");
                this.calidadSelecc = 2;
                break;
            case "cal3":
                cal5.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal4.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal3.setStyle("-fx-fill: #0073ff");
                cal2.setStyle("-fx-fill: #0073ff");
                cal1.setStyle("-fx-fill: #0073ff");
                this.calidadSelecc = 3;
                break;
            case "cal4":
                cal5.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal4.setStyle("-fx-fill: #0073ff");
                cal3.setStyle("-fx-fill: #0073ff");
                cal2.setStyle("-fx-fill: #0073ff");
                cal1.setStyle("-fx-fill: #0073ff");
                this.calidadSelecc = 4;
                break;
            case "cal5":
                cal5.setStyle("-fx-fill: #0073ff");
                cal4.setStyle("-fx-fill: #0073ff");
                cal3.setStyle("-fx-fill: #0073ff");
                cal2.setStyle("-fx-fill: #0073ff");
                cal1.setStyle("-fx-fill: #0073ff");
                this.calidadSelecc = 5;
                break;
        }
    }

    public void cargarDetalles(Actividad actividad) {

        FXMLLoader loader;
        Parent root = null;
        try {

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/proyectoFinal/vista/detalles/detalles.fxml"));
            root = loader.load(); //Se ejecuta el Initialize

            detallesController detalleController = loader.getController();
            detalleController.setPack(pack);
            detalleController.setHerramientas(herramientas);
            detalleController.setInfo(actividad);

            Stage ventana = new Stage();

            ventana.setTitle("Detalles de actividad " + actividad.getNombre());
            ventana.setScene(new Scene(root));

            ventana.showAndWait();

            labelCarrito.setText(String.valueOf(pack.getListaActividades().size()));

        } catch (IOException ex) {
            herramientas.showAlertErr("Error al cargar los detalles de las actividades");
        }

    }

    public void mostrarMapa(Actividad actividad) {
        paneMapa = new Pane();
        webView = new WebView();
        paneMapa.setMinSize(800, 600);
        
        
        Stage escenaMapa = new Stage();
        Image icono = new Image(this.getClass().getResource("/proyectoFinal/img/ubi.png").toString());
        WebEngine webEngine = webView.getEngine();

        webEngine.load(actividad.getUbicacion());
        escenaMapa.getIcons().setAll(icono);
        escenaMapa.setScene(new Scene(paneMapa));
        escenaMapa.setTitle("Ubicación " + actividad.getNombre());
        paneMapa.getChildren().setAll(webView);
        
        escenaMapa.showAndWait();

    }

    @FXML
    private void borrarFiltros(ActionEvent event) {

        sliPrecio.setValue(100);
        labPrecio.setText(String.format("%.0f€", sliPrecio.getValue()));
        cal5.setStyle("-fx-fill: #0073ff");
        cal4.setStyle("-fx-fill: #0073ff");
        cal3.setStyle("-fx-fill: #0073ff");
        cal2.setStyle("-fx-fill: #0073ff");
        cal1.setStyle("-fx-fill: #0073ff");
        rellenarConPanes();
    }

}
