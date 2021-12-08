/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoFinal.modelo;
/**
 *
 * @author itoga
 */
public class Actividad {

    private int idActividad;
    private String nombre;
    private String descripcion;
    private String imagen;
    private String url;
    private int calidad;
    private double precio;
    private String tipo;
    private String ubicacion;

    //Constructor
    public Actividad(int idActividad, String nombre, String descripcion, String imagen, String url, int calidad, double precio, String tipo) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.url = url;
        this.calidad = calidad;
        this.precio = precio;
        this.tipo = tipo;
    }
    
    public Actividad(int idActividad, String nombre, String descripcion, String imagen, String url, double precio, String tipo) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.url = url;
        this.precio = precio;
        this.tipo = tipo;
    }

    public Actividad(int idActividad, String nombre, String descripcion, String imagen, String url, int calidad, double precio, String tipo, String ubicacion) {
        this.idActividad = idActividad;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.url = url;
        this.calidad = calidad;
        this.precio = precio;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
    }
    
    

    //gets
    public int getIdActividad() {
        return idActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public String getUrl() {
        return url;
    }

    public int getCalidad() {
        return calidad;
    }

    public double getPrecio() {
        return precio;
    }

    public String getTipo() {
        return tipo;
    }

    public String getUbicacion() {
        return ubicacion;
    }
    
    

    //sets
    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCalidad(int calidad) {
        this.calidad = calidad;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    @Override
    public String toString() {
        return "Actividad " + idActividad + ": " + nombre + " - " + precio;
    }

}
