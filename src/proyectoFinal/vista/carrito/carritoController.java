package proyectoFinal.vista.carrito;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import proyectoFinal.datos.bda.detallePackDAO;
import proyectoFinal.datos.bda.packDAO;
import proyectoFinal.datos.bda.usuarioDAO;
import proyectoFinal.datos.fichero.CreateTicketPDF;
import proyectoFinal.modelo.Actividad;
import proyectoFinal.modelo.DetallePack;
import proyectoFinal.modelo.Herramientas;
import proyectoFinal.modelo.Pack;

public class carritoController {

    @FXML
    private TableView<DetallePack> carrito;
    @FXML
    private TableColumn<DetallePack, Integer> id;
    @FXML
    private TableColumn<DetallePack, String> nombre;
    @FXML
    private TableColumn<DetallePack, Integer> numPers;
    @FXML
    private TableColumn<DetallePack, LocalDate> fechaInicio;
    @FXML
    private TableColumn<DetallePack, LocalDate> fechaFin;
    @FXML
    private TableColumn<DetallePack, Double> precioInd;
    @FXML
    private TableColumn<DetallePack, Double> precioTotal;
    @FXML
    private TableColumn<DetallePack, ImageView> borrar;
    @FXML
    private Button confirmar;
    @FXML
    private Label precioFinal;

    private CreateTicketPDF ticketCreator = new CreateTicketPDF();
    private Pack pack;
    ObservableList<DetallePack> obsevableActividad = FXCollections.observableArrayList();

    detallePackDAO detallePackDAO = new detallePackDAO();
    packDAO packDAO = new packDAO();
    usuarioDAO ticketDAO = new usuarioDAO();
    Connection conexion;
    Herramientas herramientas;

    @FXML
    private TextArea descripcion;

    public void initialize() {
    }

    //Setear parametros
    public void setPack(Pack pack) {
        this.pack = pack;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public void setHerramientas(Herramientas herramientas) {
        this.herramientas = herramientas;
    }

    //Rellenar carrito de la compra/pack
    public void rellenarCarrito() {
        //Rellenamos la observable list
        for (int i = 0; i < pack.getListaActividades().size(); i++) {
            obsevableActividad.add(pack.getListaActividades().get(i));
        }

        //Añadimos la observableList a la tabla
        carrito.setItems(obsevableActividad);
        carrito.getSelectionModel().setCellSelectionEnabled(true);
        //Asignamos las columnas
        nombre.setCellValueFactory(new PropertyValueFactory<>("nombreDetallePack"));
        numPers.setCellValueFactory(new PropertyValueFactory<>("numPlazas"));
        precioInd.setCellValueFactory(new PropertyValueFactory<>("precioBase"));
        fechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicioES"));
        fechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFinalES"));
        precioTotal.setCellValueFactory(new PropertyValueFactory<>("precio"));
        borrar.setCellValueFactory(new PropertyValueFactory<>("imgBorrar"));

        //Seteamos el parametro conexión al atributo de las clases que vamos a usar
        packDAO.setConex(conexion);
        detallePackDAO.setConex(conexion);

        //Calculamos el precio del carrito
        calcularPrecioFinal();
    }

    //Caclular precio final del pack
    public void calcularPrecioFinal() {
        double precioFinal = 0;
        for (DetallePack act : pack.getListaActividades()) {
            precioFinal += act.getPrecio();
        }
        this.precioFinal.setText("Precio final: " + precioFinal + "€");
        pack.setPrecio(precioFinal);

    }

    private void insertarDetalle() throws SQLException {

        int idUltimoPack = packDAO.ultimopack();

        for (int i = 0; i < pack.getListaActividades().size(); i++) {
            //Recojo linea a linea del pack y le asigno como numLinea su posicion
            DetallePack lineaInsertar = pack.getListaActividades().get(i);
            lineaInsertar.setNumLinea(i + 1);
            lineaInsertar.setIdPack(idUltimoPack);

            //Inserto la linea en la tabla detalle_pack
            detallePackDAO.insertarDetalle(lineaInsertar);
        }
    }

    @FXML
    private void confirmarPack(ActionEvent event) {
        try {
            if (pack.getListaActividades().isEmpty()) {
                throw new SQLException("El carrito está vacio!");
            }
            if (herramientas.showAlertConf("¿Está seguro de realizar el pedido?")) {
                //Insertar pack
                pack.setDescripcion(descripcion.getText());
                pack.setIdPack(packDAO.ultimopack() + 1);
                packDAO.insertarPack(pack);
                insertarDetalle();

                //Creacion del ticket PDF
                ticketDAO.setConex(conexion);
                ticketCreator.setTicketDAO(ticketDAO);
                ticketCreator.createTicketPDF(pack);
                herramientas.showAlertInfo("¡Se ha realizado correctamente el pedido de tu pack!");
                herramientas.showAlertInfo("Se ha creado un ticket en: " + ticketCreator.getFile().getAbsolutePath());
            } else {
                throw new SQLException("No se ha podido realizar el pedido.");
            }
        } catch (SQLException | DocumentException | FileNotFoundException e) {
            herramientas.showAlertErr(e.getMessage());
        }
    }

    @FXML
    private void borrarActividad(MouseEvent event) {
        if (pack.getListaActividades().size() > 0) {
            try {
                TablePosition selectedCell = carrito.getSelectionModel().getSelectedCells().get(0);
                int col = selectedCell.getColumn();
                int row = selectedCell.getRow();
                if (selectedCell.getColumn() == 6) {
                    DetallePack act = carrito.getSelectionModel().getSelectedItem();
                    if (herramientas.showAlertConf("Deseas eliminar esta actividad?")) {
                        carrito.getItems().clear();
                        pack.getListaActividades().remove(act);
                        rellenarCarrito();
                        calcularPrecioFinal();
                    }
                }
            } catch (Exception e) {
                herramientas.showAlertErr("Error: " + e.getMessage());
            }
        }
    }
}
