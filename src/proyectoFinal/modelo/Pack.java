/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoFinal.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author itoga
 */
public class Pack {
    
    private int idPack;
    private String nombre;
    private String descripcion;
    private double precio;
    private LocalTime tiempoCreacion;
    private LocalDate fechaCreacion;
    private LocalDate fechaInicio;
    private LocalTime tiempoInicio;
    private LocalDate fechaFinal;
    private LocalTime tiempoFin;
    private int idUsuario;
    private ArrayList<DetallePack> listaActividades = new ArrayList<>();
    private static int totalPacks = 1;

    //Constructor(es)
    public Pack(String nombre, LocalDate fechaInicio, LocalDate fechaFinal, String descripcion) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.descripcion = descripcion;
        this.idPack = totalPacks;
        totalPacks++;
    }

    public Pack() {
    }
    
    //Gets
    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }
    
    public LocalDateTime getTimeCreacion(){
        LocalDateTime time = fechaCreacion.atTime(tiempoCreacion);
        return time;
    }
    
    public LocalDateTime getTimeInicio(){
        LocalDateTime time = fechaInicio.atTime(tiempoInicio);
        return time;
    }
    
    public LocalDateTime getTimeFin(){
        LocalDateTime time = fechaFinal.atTime(tiempoFin);
        return time;
    }

    public int getIdPack() {
        return idPack;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public ArrayList<DetallePack> getListaActividades() {
        return listaActividades;
    }
    
    //sets

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setTiempoCreacion(LocalTime tiempoCreacion) {
        this.tiempoCreacion = tiempoCreacion;
    }
    
    public void setTiempoInicio(LocalTime tiempoInicio) {
        this.tiempoInicio = tiempoInicio;
    }

    public void setTiempoFin(LocalTime tiempoFin) {
        this.tiempoFin = tiempoFin;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setIdPack(int idPack) {
        this.idPack = idPack;
    }
    
    
    //método toString

    @Override
    public String toString() {
        return "Pack "+idPack+": "+nombre+" - "+precio;
    }
    
    //calcular precio del pack
    public void calcularPrecioTotal(){
        
        for (DetallePack linea : listaActividades) {
            
            precio += linea.getPrecio();
            
        }
        
        
    }
    
    public void addCarrito(DetallePack linea){
        
        listaActividades.add(linea);
        
    }
    
    //crear ticket
    
//    public void generarTicket(){
//        
//        
//    }
    
}
