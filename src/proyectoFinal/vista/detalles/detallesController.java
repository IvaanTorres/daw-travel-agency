package proyectoFinal.vista.detalles;

import java.time.Period;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import proyectoFinal.modelo.Actividad;
import proyectoFinal.modelo.DetallePack;
import proyectoFinal.modelo.Herramientas;
import proyectoFinal.modelo.Pack;

public class detallesController {

    ObservableList<String> listaHoras = FXCollections.observableArrayList("12:00", "14:00", "17:00", "19:00", "21:00");

    private Actividad actividad;
    private DetallePack detallePack = null;
    private Pack pack;
    private Herramientas herramientas;

    private int idPack;
    private int idActividad;
    private String nombreDetallePack;
    private int numPlazas = 1;
    private double precioTotal = 0;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalTime horaLocalTime;

    @FXML
    private ImageView ivImagen;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private DatePicker dpFechaInicio;
    @FXML
    private DatePicker dpFechaFin;
    @FXML
    private Spinner<Integer> spiPlazas;
    @FXML
    private Button bAgregarCarrito;
    @FXML
    private Hyperlink labURLActividad;
    @FXML
    private Label labNombreActividad;
    @FXML
    private ComboBox<String> cbHoraInicio;
    @FXML
    private ComboBox<String> cbHoraFin;
    @FXML
    private Label labHoraFin;
    @FXML
    private Label labFechaFin;
    @FXML
    private Label labNumPersonas;
    @FXML
    private Label labelPrecio;
    @FXML
    private Label labelPrecioPersona;
    @FXML
    private Label labelDias;

    public void initialize() {
    }

    //Setear parametros
    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public void setHerramientas(Herramientas herramientas) {
        this.herramientas = herramientas;
    }

    //Setear datos
    public void setInfo(Actividad actividad) {
        labNombreActividad.setText(actividad.getNombre());
        labURLActividad.setText(actividad.getUrl());
        ivImagen.setImage(new Image("/proyectoFinal/img/" + actividad.getImagen()));
        taDescripcion.setText(actividad.getDescripcion());
        labelPrecio.setText(actividad.getPrecio() + "€");
        labelPrecioPersona.setText(actividad.getPrecio() + "€");

        this.actividad = actividad;
        switch (actividad.getTipo()) {
            case "1":
                cargarHotel();
                break;
            case "2":
                cargarOcio();
                break;
            case "3":
                cargarHosteleria();
                break;
        }

        setElementos();
    }
    
    public void cargarHotel() {         
        labelDias.setVisible(true);         
        labNumPersonas.setText("Habitaciones");            
    }

    public void cargarOcio() {
        labFechaFin.setVisible(false);
        dpFechaFin.setVisible(false);
        labHoraFin.setVisible(false);
        cbHoraFin.setVisible(false);
        labNumPersonas.setText("Personas");
    }

    public void cargarHosteleria() {
        labFechaFin.setVisible(false);
        dpFechaFin.setVisible(false);
        labHoraFin.setVisible(false);
        cbHoraFin.setVisible(false);
        labNumPersonas.setText("Comensales");
    }

    public void setElementos() {
        cbHoraInicio.setItems(listaHoras);
        cbHoraInicio.getSelectionModel().selectFirst();
        String horaString = cbHoraInicio.getValue();
        this.horaLocalTime = LocalTime.parse(horaString);

        cbHoraFin.setItems(listaHoras);
        cbHoraFin.getSelectionModel().selectFirst();

        dpFechaInicio.setDayCellFactory(herramientas.getDayCellFactory(pack.getFechaInicio(), pack.getFechaFinal()));
        spiPlazas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10));

    }

    public DetallePack getDetallePack() {
        return this.detallePack;
    }

    //Actualizar valores/Eventos
    @FXML
    private void updateFechaInicio(ActionEvent event) {
        this.fechaInicio = dpFechaInicio.getValue();
        //Habilitar y setear fechaFin
        dpFechaFin.setDayCellFactory(herramientas.getDayCellFactory(fechaInicio, pack.getFechaFinal()));
        dpFechaFin.setDisable(false);
        this.fechaFin = dpFechaInicio.getValue().plusDays(1);
        dpFechaFin.setValue(dpFechaInicio.getValue().plusDays(1));
        calcDias();
    }

    @FXML
    private void updateFechaFin(ActionEvent event) {
        this.fechaFin = dpFechaFin.getValue();
        try {
            labelPrecio.setText(calcularPrecio() + "€");
        } catch (Exception e) {
            herramientas.showAlertErr("No se ha podido calcular el precio");
        }
        calcDias();
    }

    //Revisar
    @FXML
    private void calcHoraInicio(ActionEvent event) {
        String horaString = cbHoraInicio.getValue();
        this.horaLocalTime = LocalTime.parse(horaString);
        cbHoraFin.setDisable(false);
    }

    @FXML
    private void updatePlazas(MouseEvent event) {
        this.numPlazas = spiPlazas.getValue();
        labelPrecio.setText(calcularPrecio() + "€");
    }

    //Calcular el precio
    public double calcularPrecio() {
        double precio = actividad.getPrecio();
        precio = precio * numPlazas;
        if (fechaInicio != null && fechaFin != null) {
            int numDias = Period.between(fechaInicio, fechaFin).getDays();
            precio = precio * numDias;
        }
        return precio;
    }

    //Checkear y setear el objeto DetallePack
    private boolean checkDetallePack() {
        return fechaInicio != null && fechaFin != null && cbHoraFin != null && cbHoraInicio.getValue() != null;
    }

    private boolean setDetallePack() {
        this.idPack = actividad.getIdActividad();
        this.idActividad = actividad.getIdActividad();
        this.nombreDetallePack = actividad.getNombre();

        if (checkDetallePack()) {
            this.precioTotal = calcularPrecio();
            detallePack = new DetallePack(idPack, idActividad, nombreDetallePack, numPlazas, precioTotal, fechaInicio, fechaFin, horaLocalTime);
            detallePack.setPrecioBase(actividad.getPrecio());

            return true;
        } else {
            herramientas.showAlertErr("Para añadir la actividad, primero debes configurarla.");
        }
        return false;
    }

    //Agregar detallePack al objeto Pack
    @FXML
    private void addDetallePack(ActionEvent event) {
        if (setDetallePack() && herramientas.showAlertConf("¿Está seguro que quiere añadir la actividad?")) {
            pack.getListaActividades().add(detallePack);

            //Cerrar ventana
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void visitarURL(ActionEvent event) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI(labURLActividad.getText()));
            } catch (IOException | URISyntaxException e1) {
                herramientas.showAlertErr("Fallo al cargar la página web");
            }
        }
    }
    
    private void calcDias() {         
        Period periodoDias = Period.between(this.fechaInicio, this.fechaFin);         
        labelDias.setText("por " + String.valueOf(periodoDias.getDays()) + " noches");     
    }


}
