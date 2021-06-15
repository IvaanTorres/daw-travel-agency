/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoFinal.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Juan Carlos
 */
public class DetallePack {

    private int idPack;
    private int idActividad;
    private int numLinea;
    private String nombreDetallePack;
    private int numPlazas;
    private double precio;
    private double precioBase;
    private LocalDate fechaInicio;
    private LocalDate fechaFinal;
    private String fechaInicioES;
    private String fechaFinalES;
    private LocalTime duracion;
    private ImageView imgBorrar = new ImageView(new Image("/proyectoFinal/img/borrar.png"));

    public DetallePack(int idPack, int idActividad, String nombreDetallePack, int numPlazas, double precio, LocalDate fechaInicio, LocalDate fechaFinal, LocalTime duracion) {
        this.idPack = idPack;
        this.idActividad = idActividad;
        this.nombreDetallePack = nombreDetallePack;
        this.numPlazas = numPlazas;
        this.precio = precio;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.fechaInicioES = formatFechaES(fechaInicio);
        this.fechaFinalES = formatFechaES(fechaFinal);
        this.duracion = duracion;
        imgBorrar.setFitHeight(33);
        imgBorrar.setFitWidth(33);
    }

    public DetallePack() {
    }

    public String getFechaInicioES() {
        return fechaInicioES;
    }

    public void setFechaInicioES(String fechaInicioES) {
        this.fechaInicioES = fechaInicioES;
    }

    public String getFechaFinalES() {
        return fechaFinalES;
    }

    public void setFechaFinalES(String fechaFinalES) {
        this.fechaFinalES = fechaFinalES;
    }

    //sets
    public void setIdPack(int idPack) {
        this.idPack = idPack;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public void setNombreDetallePack(String nombreDetallePack) {
        this.nombreDetallePack = nombreDetallePack;
    }

    public void setNumPlazas(int numPlazas) {
        this.numPlazas = numPlazas;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;

    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public void setDuracion(LocalTime duracion) {
        this.duracion = duracion;
    }

    public void setNumLinea(int numLinea) {
        this.numLinea = numLinea;
    }

    public void setImgBorrar(ImageView imgBorrar) {
        this.imgBorrar = imgBorrar;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    //gets
    public int getIdPack() {
        return idPack;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public String getNombreDetallePack() {
        return nombreDetallePack;
    }

    public int getNumPlazas() {
        return numPlazas;
    }

    public double getPrecio() {
        return precio;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public LocalTime getDuracion() {
        return duracion;
    }

    public int getNumLinea() {
        return numLinea;
    }

    public ImageView getImgBorrar() {
        return imgBorrar;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public String formatFechaES(LocalDate fecha) {
        DateTimeFormatter formatoEspana = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fechaEspana = fecha.format(formatoEspana);
        return fechaEspana;
    }
}
