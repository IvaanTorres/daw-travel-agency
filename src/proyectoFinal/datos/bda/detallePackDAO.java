/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoFinal.datos.bda;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import proyectoFinal.modelo.DetallePack;
import proyectoFinal.modelo.Pack;
import java.sql.Time;
import proyectoFinal.modelo.Actividad;

/**
 *
 * @author Juan Carlos
 */
public class detallePackDAO {

    private Connection conex;

    public detallePackDAO() {
    }

    public Connection getConex() {
        return conex;
    }

    public void setConex(Connection conex) {
        this.conex = conex;
    }

    public boolean insertarDetalle(DetallePack linea) throws SQLException {
        String sql = "INSERT INTO detalle_packs VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conex.prepareStatement(sql);

//        java.sql.Time duracionDetalle = java.sql.Time.valueOf(linea.getDuracion());
        ps.setInt(1, linea.getIdPack());
        ps.setInt(2, linea.getNumLinea());
        ps.setInt(3, linea.getIdActividad());
        ps.setInt(4, linea.getNumPlazas());
        ps.setDate(5, Date.valueOf(linea.getFechaInicio()));
        ps.setDate(6, Date.valueOf(linea.getFechaFinal()));
        ps.setTime(7, Time.valueOf(linea.getDuracion()));
//        ps.setInt(7, );

        int numFilas = ps.executeUpdate();
        if (numFilas > 0) {
            return true; //Se ha ejecutado el insert
        } else {
            return false;
        }
    }

    public boolean borrarDetallePack(Actividad actividad) throws SQLException {
        String sql = "DELETE FROM detalle_packs WHERE id_actividad = ?;";
        PreparedStatement ps = conex.prepareStatement(sql);

        ps.setInt(1, actividad.getIdActividad());

        int numFilas = ps.executeUpdate();
        if (numFilas > 0) {
            return true;
        } else {
            return false;
        }
    }

}
