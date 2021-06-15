/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoFinal.datos.bda;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Juan Carlos
 */
public class conexionDAO {
    
   private Connection conexion;

    public conexionDAO() {
        
        
    }

    public boolean conectar(String usuario, String pwd, String bda) throws SQLException {

        String url = "jdbc:mysql://localhost:3306/" + bda + "?serverTimezone=UTC";

        conexion = DriverManager.getConnection(url, usuario, pwd);

        if (conexion != null) {
            return true;
        } else {
            return false;
        }

    }

    public Connection getConexion() {
        return conexion;
    }
    
    
    
}
