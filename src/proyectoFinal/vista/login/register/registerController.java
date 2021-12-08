package proyectoFinal.vista.login.register;

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
import org.apache.commons.codec.digest.DigestUtils;
import proyectoFinal.datos.bda.conexionDAO;
import proyectoFinal.datos.bda.usuarioDAO;
import proyectoFinal.modelo.Herramientas;
import proyectoFinal.modelo.Usuario;
import proyectoFinal.vista.login.login.loginController;

public class registerController {

    private Connection conexion;
    private conexionDAO conectar = new conexionDAO();
    private Herramientas herramientas = new Herramientas();
    private Usuario user;
    private usuarioDAO usuarioDAO = new usuarioDAO();

    @FXML
    private TextField nombre;
    @FXML
    private TextField mail;
    @FXML
    private TextField telefono;
    @FXML
    private TextField tarjeta;
    @FXML
    private TextField pwd;
    @FXML
    private TextField pwd2;
    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;
    @FXML
    private Pane menu;

    String nombreUser;
    String mailUser;
    String tlfUser;
    String tarjetaUser;
    String password1;
    String password2;
    int tlf_numero;
    int tarjeta_numero;

    public void initialize() {

        //Inicio la conexión
        try {

            if (conectar.conectar("root", "2002", "packsturisticos")) {
                conexion = conectar.getConexion();
                usuarioDAO.setConex(conexion);
            }

        } catch (SQLException ex) {
            herramientas.showAlertErr("No se ha establecido conexión con la base de datos");
        }

    }

    private boolean checkCampos() {
        nombreUser = this.nombre.getText();
        mailUser = this.mail.getText();
        tlfUser = this.telefono.getText();
        tarjetaUser = this.tarjeta.getText();
        password1 = this.pwd.getText();
        password2 = this.pwd2.getText();
        System.out.println(tarjetaUser);
        int errores = 0;

        try {

            if (nombreUser.length() == 0 || mailUser.length() == 0 || tlfUser.length() == 0 || tarjetaUser.length() == 0
                    || password1.length() == 0 || password2.length() == 0) {
                herramientas.showAlertErr("¡Para registrarte debes rellenar los campos!");
                errores++;
            } else {
                String pattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
                if (!mailUser.matches(pattern)) {
                    herramientas.showAlertErr("¡El mail está mal escrito!");
                    errores++;
                }
                if (tlfUser.length() != 9) {
                    herramientas.showAlertErr("¡El teléfono está mal escrito!");
                    errores++;
                }
                if (tarjetaUser.length() != 10) {
                    herramientas.showAlertErr("¡La tarjeta está mal escrita!");
                    errores++;
                }
                if (!password1.equalsIgnoreCase(password2)) {
                    herramientas.showAlertErr("¡Las contraseñas NO coinciden!");
                    errores++;
                }
            }
        } catch (NumberFormatException e) {
            herramientas.showAlertErr("¡Teléfono y tarjeta deben ser valores numéricos!");
            errores++;
        }
        if (errores == 0) {
            tlf_numero = Integer.parseInt(tlfUser);
            tarjeta_numero = Integer.parseInt(tarjetaUser);
            return true;
        }
        return false;
    }

    @FXML
    private void register(ActionEvent event) {
        try {
            if (checkCampos()) {
                user = new Usuario(nombreUser, mailUser, tlf_numero, tarjeta_numero, password1);
                //INSERT DEL OBJETO
                if (!usuarioDAO.checkUsuarioYaRegistrado(user.getMail())) {
                    //Encriptar contrasena
                    String pwdMD5=DigestUtils.md5Hex(password1);
                    user.setPwd(pwdMD5);
                    usuarioDAO.registrarUsuario(user);
                    herramientas.showAlertInfo("¡Usuario registrado!");
                } else {
                    herramientas.showAlertErr("¡El usuario con dicho correo ya está registrado!");
                }
            }
        } catch (SQLException e) {
            herramientas.showAlertErr(e.getMessage());
            System.out.println("ERROR: " + e.getErrorCode());
        }
    }

    @FXML
    private void goToLogin(ActionEvent event) {
        FXMLLoader loader;
        Parent root = null;
        try {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/proyectoFinal/vista/login/login/login.fxml"));
            root = loader.load(); //Se ejecuta el Initialize

            loginController loginController = loader.getController();
            loginController.setConexion(conexion);
            loginController.setHerramientas(herramientas);
            loginController.setUsuarioDAO(usuarioDAO);
            loginController.setConectar(conectar);

            menu.getChildren().addAll(root);
        } catch (IOException ex) {
            herramientas.showAlertErr(ex.getMessage());
        }
    }
}
