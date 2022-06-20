package test;

import datos.Conexion;
import datos.UsuarioDaoJDBC;
import domain.UsuarioDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ManejoUsuarios {
    public static void main(String[] args) {
        UsuarioDaoJDBC usuarioDaoJdbc = new UsuarioDaoJDBC();


        Connection conexion = null;

        try {
            conexion = Conexion.getConnection();
            if(conexion.getAutoCommit()){
                conexion.setAutoCommit(false);
            }

            UsuarioDaoJDBC usuarioDao = new UsuarioDaoJDBC(conexion);

            List<UsuarioDTO> usuarios = usuarioDao.select();

            usuarios.forEach(System.out::println);


            conexion.commit();
            System.out.println("Se ha hecho el commit de la transaccion");
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            System.out.println("Entramos al rollback");
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
}
