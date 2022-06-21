package datos;
import domain.UsuarioDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static datos.Conexion.close;

public class UsuarioDaoJDBC implements UsuarioDao {

    private Connection conexionTransaccional;
    private static final String SQL_SELECT = "SELECT id_usuario, username, password FROM usuario";
    private static final String SQL_INSERT = "INSERT INTO usuario(username, password) VALUES(?, ?)";
    private static final String SQL_UPDATE = "UPDATE usuario SET username=?, password=? WHERE id_usuario = ?";
    private static final String SQL_DELETE = "DELETE FROM usuario WHERE id_usuario=?";

    public UsuarioDaoJDBC() {

    }
    public UsuarioDaoJDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }

    public List<UsuarioDTO> select() throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        UsuarioDTO usuarioDTO = null;
        List<UsuarioDTO> usuarioDTOS = new ArrayList<UsuarioDTO>();
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            rs = stmt.executeQuery();
            while(rs.next()){
                int id_usuario = rs.getInt("id_usuario");
                String username = rs.getString("username");
                String password = rs.getString("password");
                
                usuarioDTO = new UsuarioDTO();
                usuarioDTO.setId_usuario(id_usuario);
                usuarioDTO.setUsername(username);
                usuarioDTO.setPassword(password);
                
                usuarioDTOS.add(usuarioDTO);
            }
            
        } /*catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }*/ finally{
            close(rs);
            close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }
        return usuarioDTOS;
    }
    
    public int insert(UsuarioDTO usuarioDTO)throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, usuarioDTO.getUsername());
            stmt.setString(2, usuarioDTO.getPassword());
            
            System.out.println("ejecutando query:" + SQL_INSERT);
            rows = stmt.executeUpdate();
            System.out.println("Registros afectados:" + rows);
        } /*catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }*/ finally{
            close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }
        
        return rows;
    }
    
    public int update(UsuarioDTO usuarioDTO)throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            System.out.println("ejecutando query: " + SQL_UPDATE);
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, usuarioDTO.getUsername());
            stmt.setString(2, usuarioDTO.getPassword());
            stmt.setInt(3, usuarioDTO.getId_usuario());
            
            rows = stmt.executeUpdate();
            System.out.println("Registros actualizado:" + rows);
            
        } /*catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }*/ finally{
            close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }
        
        return rows;
    }
    
    public int delete(UsuarioDTO usuarioDTO)throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            System.out.println("Ejecutando query:" + SQL_DELETE);
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, usuarioDTO.getId_usuario());
            rows = stmt.executeUpdate();
            System.out.println("Registros eliminados:" + rows);
        } /*catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }*/ finally{
            close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }
        return rows;
    }
}
