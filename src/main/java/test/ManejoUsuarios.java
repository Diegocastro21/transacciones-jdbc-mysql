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
        
/*        //Ejecutando el listado de usuarios
        List<UsuarioDTO> usuarios = usuarioDaoJdbc.select();
        for(UsuarioDTO usuario: usuarios){
            System.out.println("UsuarioDTO:" + usuario);
        }
        
        //Insertamos un nuevo usuario
//        UsuarioDTO usuario = new UsuarioDTO("carlos.juarez", "123");
//        usuarioDaoJdbc.insert(usuario);

        //Modificamos un usuario existente
//        UsuarioDTO usuario = new UsuarioDTO(3, "carlos.juarez","456");
//        usuarioDaoJdbc.update(usuario);

        usuarioDaoJdbc.delete(new UsuarioDTO(3));*/

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
