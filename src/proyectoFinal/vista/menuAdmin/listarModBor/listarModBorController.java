package proyectoFinal.vista.menuAdmin.listarModBor;

import com.jfoenix.controls.JFXComboBox;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import proyectoFinal.datos.bda.actividadDAO;
import proyectoFinal.datos.bda.detallePackDAO;
import proyectoFinal.datos.bda.packDAO;
import proyectoFinal.datos.bda.tipoDAO;
import proyectoFinal.modelo.Actividad;
import proyectoFinal.modelo.Herramientas;
import proyectoFinal.modelo.Tipo;

public class listarModBorController {

    @FXML
    private TextField nombre;
    @FXML
    private TextArea descripcion;
    @FXML
    private TextField precio;

    @FXML
    private TextField url;
    @FXML
    private Button imagenBoton;
    @FXML
    private Label rutaIMG;
    @FXML
    private ImageView imgView;

    private Actividad actividadSelecc;

    private Connection conexion;
    private Herramientas herramientas;

    private actividadDAO actividadDAO = new actividadDAO();
    private detallePackDAO detallePackDAO = new detallePackDAO();
    private packDAO packDAO = new packDAO();

    private tipoDAO tipoDAO = new tipoDAO();

    private int calidadEstrellas;
    ObservableList<Tipo> listaTipos;
    ObservableList<Actividad> olistActividades;
    private Tipo tipo = new Tipo();
    private Path img;

    @FXML
    private Button botonEliminar;
    @FXML
    private Button botonModificar1;
    @FXML
    private ListView<Actividad> lvActividades;
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

        try {
            //Cargar actividades en listView

            actualizarActividades();
            lvActividades.getSelectionModel().selectFirst();
            //Cargar tipos de actividad en comboBox
            tipoDAO.setConex(conexion);
            listaTipos = FXCollections.observableArrayList(tipoDAO.objetoTipo());
            tipoBox.setItems(listaTipos);

        } catch (SQLException ex) {
            herramientas.showAlertErr("Error: " + ex.getMessage());
        }
    }

    public void actualizarActividades() {
        try {
            actividadDAO.setConex(conexion);
            olistActividades = FXCollections.observableArrayList(actividadDAO.obtenerTodasActividades());
            lvActividades.setItems(olistActividades);
            resetEstilo();

        } catch (SQLException e) {
            herramientas.showAlertErr("Error: " + e.getMessage());
        }
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
    private void modActividad(ActionEvent event) {
        if (checkErrores()) {
            try {
                int idActividad = actividadSelecc.getIdActividad();
                Actividad actividadModif = new Actividad(idActividad, nombre.getText(),
                        descripcion.getText(), img.toString(), url.getText(),
                        calidadEstrellas, Double.parseDouble(precio.getText()),
                        tipo.getId());
                if (actividadDAO.editarActividad(actividadModif)) {
                    herramientas.showAlertInfo("Actividad editada");
                    actualizarActividades();
                } else {
                    herramientas.showAlertErr("Error al editar la actividad");
                }
            } catch (SQLException e) {
                herramientas.showAlertErr("Error: " + e.getMessage());
            }
        }
    }

    @FXML
    private void elimActividad(ActionEvent event) {
        try {
            if (actividadDAO.borrarActividad(actividadSelecc)) {
                herramientas.showAlertInfo("Actividad eliminada");
                actualizarActividades();
                limpiarCampos();

            } else {
                herramientas.showAlertErr("Error al eliminar la actividad");
            }
        } catch (SQLException e) {
            boolean eliminar = herramientas.showAlertConf("No se puede eliminar la actividad, actualmente"
                    + " está incluida en un pack \n"
                    + "¿Desea eliminar también los packs relacionados?");
            if (eliminar == true) {
                detallePackDAO.setConex(conexion);
                try {
                    detallePackDAO.borrarDetallePack(actividadSelecc);
                    elimActividad(event);
                } catch (SQLException e2) {
                    herramientas.showAlertErr("Error: " + e2.getMessage());
                }
            }
        }
    }

    @FXML
    private void seleccActividad(MouseEvent event) {
        actividadSelecc = lvActividades.getSelectionModel().getSelectedItem();
        //Setear datos de la actividad seleccionada
        nombre.setText(actividadSelecc.getNombre());
        url.setText(actividadSelecc.getUrl());
        precio.setText(String.valueOf(actividadSelecc.getPrecio()));
        calidadEstrellas = actividadSelecc.getCalidad();
        estiloEstrellas("cal" + actividadSelecc.getCalidad());

        //Setear tipo
        for (Tipo tipo : listaTipos) {
            if (tipo.getId().equals(actividadSelecc.getTipo())) {
                tipoBox.getSelectionModel().select(tipo);
            }
        }
        descripcion.setText(actividadSelecc.getDescripcion());

        img = Paths.get(actividadSelecc.getImagen());
        rutaIMG.setText(actividadSelecc.getImagen());
        imgView.setImage(new Image("/proyectoFinal/img/" + actividadSelecc.getImagen()));
    }

    @FXML
    private void selecCalidad(MouseEvent event) {

        String idCalidad = "cal" + actividadSelecc.getCalidad();
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
                calidadEstrellas = 1;
                break;
            case "cal2":
                cal5.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal4.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal3.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal2.setStyle("-fx-fill: #0073ff");
                cal1.setStyle("-fx-fill: #0073ff");
                calidadEstrellas = 2;
                break;
            case "cal3":
                cal5.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal4.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal3.setStyle("-fx-fill: #0073ff");
                cal2.setStyle("-fx-fill: #0073ff");
                cal1.setStyle("-fx-fill: #0073ff");
                calidadEstrellas = 3;
                break;
            case "cal4":
                cal5.setStyle("-fx-fill: white; -fx-stroke: #0073ff");
                cal4.setStyle("-fx-fill: #0073ff");
                cal3.setStyle("-fx-fill: #0073ff");
                cal2.setStyle("-fx-fill: #0073ff");
                cal1.setStyle("-fx-fill: #0073ff");
                calidadEstrellas = 4;
                break;
            case "cal5":
                cal5.setStyle("-fx-fill: #0073ff");
                cal4.setStyle("-fx-fill: #0073ff");
                cal3.setStyle("-fx-fill: #0073ff");
                cal2.setStyle("-fx-fill: #0073ff");
                cal1.setStyle("-fx-fill: #0073ff");
                calidadEstrellas = 5;
                break;
        }
    }

    private void limpiarCampos() {

        //Setear datos de la actividad seleccionada
        nombre.setText("");
        url.setText("");
        precio.setText(String.valueOf(0));

        estiloEstrellas("cal5");

        descripcion.setText("");

        img = Paths.get("");
        rutaIMG.setText("");
        imgView.setImage(null);
    }

    @FXML
    private void modificarElemento(KeyEvent event) {
        if (actividadSelecc.getNombre().equals(nombre.getText()) == false) {
            nombre.setStyle(estiloResaltado());
        } else if (actividadSelecc.getNombre().equals(nombre.getText()) == true) {
            nombre.setStyle(estiloNormal());
        }

        if (actividadSelecc.getPrecio() != Double.valueOf(precio.getText())) {
            precio.setStyle(estiloResaltado());
        } else if (actividadSelecc.getPrecio() == Double.valueOf(precio.getText())) {
            precio.setStyle(estiloNormal());
        }

        if (actividadSelecc.getUrl().equals(url.getText()) == false) {
            url.setStyle(estiloResaltado());
        } else if (actividadSelecc.getUrl().equals(url.getText()) == true) {
            url.setStyle(estiloNormal());
        }

        if (actividadSelecc.getDescripcion().equals(descripcion.getText()) == false) {
            descripcion.setStyle("-fx-background-color: white;"
                    + " -fx-border-color: red;");
        } else if (actividadSelecc.getDescripcion().equals(descripcion.getText()) == true) {
            descripcion.setStyle("-fx-background-color: white;"
                    + " -fx-border-color: #85CAE3;");
        }
    }

    private String estiloResaltado() {
        return "-fx-background-color: white;"
                + " -fx-border-color: red;"
                + " -fx-border-style:  hidden hidden solid hidden;"
                + " -fx-border-width:  3px;"
                + " -fx-font-size:  16";
    }

    private String estiloNormal() {
        return "-fx-background-color: white;"
                + " -fx-border-color: #85CAE3;"
                + " -fx-border-style:  hidden hidden solid hidden;"
                + " -fx-border-width:  3px;"
                + " -fx-font-size:  16";

    }
    
    private void resetEstilo() {
        nombre.setStyle(estiloNormal());
        precio.setStyle(estiloNormal());
        url.setStyle(estiloNormal());
        descripcion.setStyle("-fx-background-color: white;"
                    + " -fx-border-color: #85CAE3;");
    }
}
