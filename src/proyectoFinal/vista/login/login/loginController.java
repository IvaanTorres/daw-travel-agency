package proyectoFinal.vista.login.login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import proyectoFinal.datos.bda.conexionDAO;
import proyectoFinal.datos.bda.usuarioDAO;
import proyectoFinal.modelo.Herramientas;
import proyectoFinal.vista.inicio.inicioController;

public class loginController {

    private Connection conexion;
    private conexionDAO conectar;
    private Herramientas herramientas;
    private usuarioDAO usuarioDAO;

    @FXML
    private TextField mail;
    @FXML
    private TextField pwd;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Pane menu;

    private String mailUser;
    private String password;

    public void initialize() {
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public conexionDAO getConectar() {
        return conectar;
    }

    public void setConectar(conexionDAO conectar) {
        this.conectar = conectar;
    }

    public Herramientas getHerramientas() {
        return herramientas;
    }

    public void setHerramientas(Herramientas herramientas) {
        this.herramientas = herramientas;
    }

    public usuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(usuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    private boolean checkCampos() {
        mailUser = this.mail.getText();
        password = this.pwd.getText();

        if (mailUser.length() == 0 || password.length() == 0) {
            herramientas.showAlertErr("¡Para iniciar sesión debes rellenar los campos!");
        } else {
            String pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
            if(!mailUser.matches(pattern)){
                herramientas.showAlertErr("¡Formato de mail incorrecto!");
            }else{
                return true;
            }
        }
        return false;
    }

    @FXML
    private void login(ActionEvent event) {
        try {
            if (checkCampos()) {
                if (usuarioDAO.checkUsuarioYaRegistrado(mailUser)) {
                    if(usuarioDAO.checkPWD(password)){
                        goToInicio();
                    }else{
                        herramientas.showAlertErr("Contraseña incorrecta, ¡inténtelo de nuevo!");
                    }
                }else{
                    herramientas.showAlertErr("¡Usuario no registrado!");
                }
            }
        } catch (SQLException e) {
            herramientas.showAlertErr(e.getMessage());
        }
    }

    @FXML
    private void goToRegister(ActionEvent event) {
        FXMLLoader loader;
        Parent root = null;
        try {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/proyectoFinal/vista/login/register/register.fxml"));
            root = loader.load(); //Se ejecuta el Initialize

            menu.getChildren().addAll(root);
        } catch (IOException ex) {
            herramientas.showAlertErr("Error " + ex.getMessage());
        }
    }

    private void goToInicio() {
        FXMLLoader loader;
        Parent root = null;
        try {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/proyectoFinal/vista/inicio/inicio.fxml"));
            root = loader.load(); //Se ejecuta el Initialize

            inicioController inicioController = loader.getController();
            inicioController.setConexion(conexion);
            inicioController.setHerramientas(herramientas);
            inicioController.setConectar(conectar);
            inicioController.setUsuarioDAO(usuarioDAO);
            inicioController.setMailUsuario(mailUser);
            inicioController.inicio();

            menu.getChildren().addAll(root);
        } catch (IOException ex) {
            herramientas.showAlertErr("Error " + ex.getMessage());
            
        }
    }
}
