package proyectoFinal.vista.menuAdmin.crearActividad;

import com.jfoenix.controls.JFXComboBox;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import proyectoFinal.datos.bda.actividadDAO;
import proyectoFinal.datos.bda.tipoDAO;
import proyectoFinal.modelo.Actividad;
import proyectoFinal.modelo.Herramientas;
import proyectoFinal.modelo.Tipo;

public class crearActividadController {

    @FXML
    private TextField nombre;
    @FXML
    private TextArea descripcion;
    @FXML
    private TextField precio;
    
    
    @FXML
    private Button botonCrear;
    @FXML
    private TextField url;
    @FXML
    private Button imagenBoton;
    @FXML
    private Label rutaIMG;
    @FXML
    private ImageView imgView;

    private Connection conexion;
    private Herramientas herramientas;
    private actividadDAO actividadDAO = new actividadDAO();
    private tipoDAO tipoDAO = new tipoDAO();

    private int calidadEstrellas = 5;
    ObservableList<Tipo> listaTipos;
    private Tipo tipo = new Tipo();
    private Path img;
    @FXML
    private SVGPath cal5;
    @FXML
    private SVGPath cal4;
    @FXML
    private SVGPath cal3;
    @FXML
    private SVGPath cal2;
    @FXML
    private SVGPath cal1;
    @FXML
    private JFXComboBox<Tipo> tipoBox;

    public void initialize() {

    }

    //Parametros
    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public void setHerramientas(Herramientas herramientas) {
        this.herramientas = herramientas;
    }

    //Default
    public void setDefault() {

        actividadDAO.setConex(conexion);
        tipoDAO.setConex(conexion);
        try {
            listaTipos = FXCollections.observableArrayList(tipoDAO.objetoTipo());
        } catch (SQLException ex) {
            herramientas.showAlertErr("Error " + ex.getMessage());

        }
        tipoBox.setItems(listaTipos);
        tipoBox.getSelectionModel().selectFirst();
        

    }

    //Checkeo de errores al insertar la actividad
    public boolean checkErrores() {
        String nombreAct = nombre.getText();
        String descripcionAct = descripcion.getText();
        String precioAct_string = precio.getText();
        String urlAct = url.getText();
        tipo = tipoBox.getValue();
        String rutaImagen = rutaIMG.getText();

        String error = "";

        if (nombreAct.length() == 0 || descripcionAct.length() == 0 || precioAct_string.length() == 0
                || urlAct.length() == 0 || rutaImagen.length() == 3) {
            error += "- Campos en Null \n";
        } else {
            if (nombreAct.length() < 5) {
                error += "- Nombre corto \n";
            }
            if (descripcionAct.length() < 15) {
                error += "- Descripcion corta \n";
            }
            if (!regExpURL(urlAct)) {
                error += "- Formato incorrecto de URL \n";
            }
            try {
                double precioAct = Double.parseDouble(precioAct_string);
            } catch (NumberFormatException e) {
                error += "- Formato de precio incorrecto (EJ: Hay letras) \n";
            }
        }
        if (!error.isEmpty()) {
            herramientas.showAlertErr(error);
            return false;
        }
        return true;
    }

    public boolean regExpURL(String URL) {
        String regExp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        return url.getText().matches(regExp);
    }

    //Seleccion de imagen con FileChooser
    @FXML
    private void elegirImagen(ActionEvent event) {
        try {
            img = loadImagen();
            rutaIMG.setText(img.toString());
            imgView.setImage(new Image("/proyectoFinal/img/" + img.toString()));
        } catch (Exception e) {
            herramientas.showAlertErr("No has seleccionado imagen");
        }
    }

    public Path loadImagen() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/proyectoFinal/img/"));
        File file = fileChooser.showOpenDialog(null);

        Path menu = Paths.get(file.getName());
        return menu;
    }

    @FXML
    private void crearActividad(ActionEvent event) {
        if (checkErrores()) {
            try {
                int idActividad = actividadDAO.ultimoIdActividad();
                Actividad newAct = new Actividad(++idActividad, nombre.getText(), descripcion.getText(), img.toString(), url.getText(), calidadEstrellas, Double.parseDouble(precio.getText()), tipo.getId());
                uploadActividad(newAct);
            } catch (SQLException ex) {
                herramientas.showAlertErr("Error al seleccionar ultima actividad");
            }
        }
    }

    public void uploadActividad(Actividad act) {

        try {
            if (actividadDAO.insertarActividades(act)) {
                herramientas.showAlertInfo("Nueva actividad creada");
            }
        } catch (SQLException e) {
            herramientas.showAlertErr("Error en añadir  la actividad");
        }
    }

    @FXML
    private void selecCalidad(MouseEvent event) {
        String idCalidad = "cal5";
        Node nodoQueHaGeneradoEvento = (Node) event.getSource();
        idCalidad = nodoQueHaGeneradoEvento.getId();
        estiloEstrellas(idCalidad);
        
    }

    private void estiloEstrellas(String idCalidad) {
        switch (idCalidad) {
            case "cal1":
                cal5.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal4.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal3.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal2.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal1.setStyle("-fx-fill: #0073ff");
                calidadEstrellas= 1;
                break;
            case "cal2":
                cal5.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal4.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal3.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal2.setStyle("-fx-fill: #0073ff");
                cal1.setStyle("-fx-fill: #0073ff");
                this.calidadEstrellas = 2;
                break;
            case "cal3":
                cal5.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal4.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal3.setStyle("-fx-fill: #0073ff");
                cal2.setStyle("-fx-fill: #0073ff");
                cal1.setStyle("-fx-fill: #0073ff");
                this.calidadEstrellas = 3;
                break;
            case "cal4":
                cal5.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal4.setStyle("-fx-fill: #0073ff");
                cal3.setStyle("-fx-fill: #0073ff");
                cal2.setStyle("-fx-fill: #0073ff");
                cal1.setStyle("-fx-fill: #0073ff");
                this.calidadEstrellas = 4;
                break;
            case "cal5":
                cal5.setStyle("-fx-fill: #0073ff");
                cal4.setStyle("-fx-fill: #0073ff");
                cal3.setStyle("-fx-fill: #0073ff");
                cal2.setStyle("-fx-fill: #0073ff");
                cal1.setStyle("-fx-fill: #0073ff");
                this.calidadEstrellas = 5;
                break;
        }
    }
}