/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoFinal.datos.bda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import proyectoFinal.modelo.Tipo;
import proyectoFinal.modelo.Tipo;

/**
 *
 * @author Yaiza
 */
public class tipoDAO {

    private Connection conex;

    public tipoDAO() {

    }

    public Connection getConex() {
        return conex;
    }

    public void setConex(Connection conex) {
        this.conex = conex;
    }
    
    

    public ArrayList<Tipo> objetoTipo() throws SQLException {
        ArrayList<Tipo> conjuntoTipos = new ArrayList<>();

        String sql = "SELECT * FROM tipos";
        PreparedStatement ps = conex.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String id = rs.getString("codigo");
            String nombre = rs.getString("nombre");

            Tipo tipo = new Tipo(id, nombre);

            conjuntoTipos.add(tipo);

        }
        return conjuntoTipos;
    }

}
