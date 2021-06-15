/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoFinal.datos.bda;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import proyectoFinal.modelo.Actividad;

/**
 *
 * @author itoga
 */
public class actividadDAO {

    private Connection conex;

    public actividadDAO() {
    }

    public Connection getConex() {
        return conex;
    }

    public void setConex(Connection conex) {
        this.conex = conex;
    }

    public boolean insertarActividades(Actividad actividad) throws SQLException {
        String sql = "INSERT INTO actividades VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conex.prepareStatement(sql);

        ps.setInt(1, actividad.getIdActividad());
        ps.setString(2, actividad.getNombre());
        ps.setString(3, actividad.getDescripcion());
        ps.setString(4, actividad.getImagen());
        ps.setString(5, actividad.getUrl());
        ps.setString(6, String.valueOf(actividad.getCalidad()));
        ps.setString(7, actividad.getTipo());
        ps.setDouble(8, actividad.getPrecio());
        ps.setString(9, null);

        int numFilas = ps.executeUpdate();
        if (numFilas > 0) {
            return true; //Se ha ejecutado el insert
        } else {
            return false;
        }
    }

    public boolean borrarActividad(Actividad actividad) throws SQLException {
        String sql = "DELETE FROM actividades WHERE id = ?;";
        PreparedStatement ps = conex.prepareStatement(sql);

        ps.setInt(1, actividad.getIdActividad());

        int numFilas = ps.executeUpdate();
        if (numFilas > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean editarActividad(Actividad actividadModif) throws SQLException {
        String sql = "UPDATE actividades SET nombre = ?, descripcion = ?, imagen = ?,"
                + " url = ?, calidad = ?, tipo = ?, precio = ? WHERE id = ?;";
        PreparedStatement ps = conex.prepareStatement(sql);
  
        ps.setString(1, actividadModif.getNombre());
        ps.setString(2, actividadModif.getDescripcion());
        ps.setString(3, actividadModif.getImagen());
        ps.setString(4, actividadModif.getUrl());
        ps.setString(5, String.valueOf(actividadModif.getCalidad()));
        ps.setString(6, actividadModif.getTipo());
        ps.setDouble(7, actividadModif.getPrecio());
        ps.setInt(8, actividadModif.getIdActividad());

        int numFilas = ps.executeUpdate();
        if (numFilas > 0) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Actividad> obtenerActividades(int tipoAct) throws SQLException {
        ArrayList<Actividad> conjuntoActividades = new ArrayList<>();

        String sql = "SELECT * FROM actividades WHERE tipo = ?;";

        PreparedStatement ps = conex.prepareCall(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1, tipoAct);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            String descripcion = rs.getString("descripcion");
            String imagen = rs.getString("imagen");
            String url = rs.getString("url");
            int calidad = rs.getInt("calidad");
            String tipo = rs.getString("tipo");
            double precio = rs.getDouble("precio");
            String ubicacion = rs.getString("ubicacion");

            //Crear actividad y añadir a HashSet
            Actividad actividad
                    = new Actividad(id, nombre, descripcion, imagen, url, calidad, precio, tipo, ubicacion);
            conjuntoActividades.add(actividad);
        }
        return conjuntoActividades;
    }

    public int ultimoIdActividad() throws SQLException {

        String sql = "SELECT MAX(id) FROM actividades;";

        PreparedStatement ps = conex.prepareCall(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = ps.executeQuery();
        int id = 0;
        while (rs.next()) {
            id = rs.getInt("MAX(id)");
        }
        return id;
    }

    public ArrayList<Actividad> obtenerTodasActividades() throws SQLException {
        ArrayList<Actividad> conjuntoActividades = new ArrayList<>();

        String sql = "SELECT * FROM actividades;";

        PreparedStatement ps = conex.prepareCall(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            String descripcion = rs.getString("descripcion");
            String imagen = rs.getString("imagen");
            String url = rs.getString("url");
            int calidad = rs.getInt("calidad");
            String tipo = rs.getString("tipo");
            double precio = rs.getDouble("precio");

            //Crear actividad y añadir a HashSet
            Actividad actividad
                    = new Actividad(id, nombre, descripcion, imagen, url, calidad, precio, tipo);
            conjuntoActividades.add(actividad);
        }
        return conjuntoActividades;
    }
}
