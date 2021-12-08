package proyectoFinal.vista.menuAdmin;

import java.io.IOException;
import java.sql.Connection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import proyectoFinal.modelo.Herramientas;
import proyectoFinal.vista.menuAdmin.crearActividad.crearActividadController;
import proyectoFinal.vista.menuAdmin.listarModBor.listarModBorController;

public class menuAdminController {

    private Connection conex;
    private Herramientas herramientas = new Herramientas();
    @FXML
    private Button botonCrear;
    @FXML
    private Button botonListar;

    public void initialize() {

    }

    public Connection getConex() {
        return conex;
    }

    public void setConex(Connection conex) {
        this.conex = conex;
    }

    public Herramientas getHerramientas() {
        return herramientas;
    }

    public void setHerramientas(Herramientas herramientas) {
        this.herramientas = herramientas;
    }

    @FXML
    public void crearActividad(ActionEvent event) {
        FXMLLoader loader;
        Parent root = null;
        try {

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/proyectoFinal/vista/menuAdmin/crearActividad/crearActividad.fxml"));
            root = loader.load(); //Se ejecuta el Initialize

            crearActividadController crearAct = loader.getController();
            crearAct.setConexion(conex);
            crearAct.setHerramientas(herramientas);
            crearAct.setDefault();

            Pane parentBoton = (Pane) botonCrear.getParent();

            parentBoton.getChildren().setAll(root);

        } catch (IOException ex) {
            herramientas.showAlertErr("Error " + ex.getMessage());
        }
    }

    @FXML
    private void listarActividades(ActionEvent event) {
        FXMLLoader loader;
        Parent root = null;
        try {

            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/proyectoFinal/vista/menuAdmin/listarModBor/listarModBor.fxml"));
            root = loader.load(); //Se ejecuta el Initialize

            listarModBorController listarAct = loader.getController();
            listarAct.setConexion(conex);
            listarAct.setHerramientas(herramientas);
            listarAct.setDefault();

            Pane parentBoton = (Pane) botonCrear.getParent();

            parentBoton.getChildren().setAll(root);

        } catch (IOException ex) {
            herramientas.showAlertErr("Error " + ex.getMessage());
        }
    }
}
