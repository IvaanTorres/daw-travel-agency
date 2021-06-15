package proyectoFinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("vista/inicio/inicio.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("vista/login/register/register.fxml"));
        primaryStage.setTitle("Sol Travel Experience");
        Image icono = new Image(this.getClass().getResource("img/iconoAPP.png").toString());
        primaryStage.getIcons().add(icono);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}