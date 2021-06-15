/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoFinal.modelo;

import java.time.LocalDate;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author itoga
 */
public class Herramientas {

    public Herramientas() {
    }

    public Callback<DatePicker, DateCell> getDayCellFactoryInicio(LocalDate fechaMIN) {

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        //CSS para los dias deshabilitados
                        if (empty || item.isBefore(fechaMIN)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #AED6F1;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }

    public Callback<DatePicker, DateCell> getDayCellFactory(LocalDate fechaMIN, LocalDate fechaMAX) {

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {

            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        //CSS para los dias deshabilitados
                        if (empty || item.isBefore(fechaMIN) || item.isAfter(fechaMAX) || item.isEqual(fechaMIN)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #AED6F1;");
                        }
                    }
                };
            }
        };
        return dayCellFactory;
    }
    
    public void showAlertErr(String error){
        Notifications notif = Notifications.create()
            .title("ERROR")
            .text(error)
            .graphic(null)
            .hideAfter(Duration.seconds(5))
            .position(Pos.BOTTOM_RIGHT)
            .onAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println(error);
                }
            });
        notif.darkStyle();
        notif.showError();
    }
    
    public void showAlertInfo(String info){
        Notifications notif = Notifications.create()
            .title("INFORMATION")
            .text(info)
            .graphic(null)
            .hideAfter(Duration.seconds(5))
            .position(Pos.BOTTOM_RIGHT)
            .onAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println(info);
                }
            });
        notif.darkStyle();
        notif.showInformation();
    }
    
//    public boolean showAlertConf(String conf){
//        Button b = new Button("Confirmation alert");
//        Button b1 = new Button("Confirmation alert");
//        
//        Notifications notif = Notifications.create()
//            .title("CONFIRMATION")
//            .text(conf)
//            .graphic(null)
//            .hideAfter(Duration.seconds(5))
//            .position(Pos.BOTTOM_RIGHT)
//            .onAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    System.out.println(conf);
//                }
//            });
//        notif.darkStyle();
//        notif.showConfirm();
//        return true;
//    }

    public boolean showAlertConf(String conf) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("CONFIRMAR");
        alerta.setHeaderText(null);
        alerta.setContentText(conf);
        Optional<ButtonType> action = alerta.showAndWait();
        return action.get() == ButtonType.OK;
    }
}
