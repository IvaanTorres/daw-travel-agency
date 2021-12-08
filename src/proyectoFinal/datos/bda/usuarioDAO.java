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
import org.apache.commons.codec.digest.DigestUtils;
import proyectoFinal.modelo.Pack;
import proyectoFinal.modelo.Usuario;

/**
 *
 * @author itoga
 */
public class usuarioDAO {
    private Connection conex;

    public usuarioDAO() {
    }

    public Connection getConex() {
        return conex;
    }

    public void setConex(Connection conex) {
        this.conex = conex;
    }
    
    public void registrarUsuario(Usuario user) throws SQLException{
        String sql = "INSERT INTO usuarios (id, rol, nombre, mail, telefono, tarjeta, `contraseña`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conex.prepareStatement(sql);

        ps.setInt(1, user.getId());
        ps.setString(2, user.getRol());
        ps.setString(3, user.getNombre());
        ps.setString(4, user.getMail());
        ps.setInt(5, user.getTelefono());
        ps.setLong(6, user.getTarjeta());
        ps.setString(7, user.getPwd());

        ps.executeUpdate();
    }
    
    public boolean checkUsuarioYaRegistrado(String mail) throws SQLException{
        String sql = "SELECT mail FROM usuarios WHERE mail = ?";

        PreparedStatement ps = conex.prepareCall(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, mail);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if(rs.getString("mail").equalsIgnoreCase(mail)){
                return true;
            }
        }
        return false;
    }
    
    public boolean checkPWD(String pwd) throws SQLException{
        String sql = "SELECT contraseña FROM usuarios WHERE contraseña = ?";

        PreparedStatement ps = conex.prepareCall(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        String pwdMD5=DigestUtils.md5Hex(pwd);
        ps.setString(1, pwdMD5);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            if(rs.getString("contraseña").equalsIgnoreCase(pwdMD5)){
                return true;
            }
        }
        return false;
    }
    
    public String nombreUsuario(Pack pack) throws SQLException{
        String sql = "SELECT usuarios.nombre FROM packs,usuarios WHERE packs.id_usuario = usuarios.id && packs.id = ?;";

        PreparedStatement ps = conex.prepareCall(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1, pack.getIdPack());
        ResultSet rs = ps.executeQuery();
        String nombre = "";
        while (rs.next()) {
            nombre = rs.getString("usuarios.nombre");
        }
        return nombre;
    }
    
    public int getIdusuario(String mail) throws SQLException{
        String sql = "SELECT id FROM usuarios WHERE mail = ?";

        PreparedStatement ps = conex.prepareCall(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, mail);
        ResultSet rs = ps.executeQuery();
        int id = 0;
        while (rs.next()) {
            id = rs.getInt("id");
        }
        return id;
    }
    
    public String getRol(int idUsuario) throws SQLException{
        String sql = "SELECT rol FROM usuarios WHERE id = ?;";

        PreparedStatement ps = conex.prepareCall(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1, idUsuario);
        ResultSet rs = ps.executeQuery();
        String rol = "";
        while (rs.next()) {
            rol = rs.getString("rol");
        }
        return rol;
    }
}
