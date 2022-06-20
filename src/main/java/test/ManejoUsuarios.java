package test;

import datos.Conexion;
import datos.PersonaJDBC;
import datos.UsuarioJDBC;
import domain.Persona;
import domain.Usuario;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ManejoUsuarios {
    public static void main(String[] args) {
        UsuarioJDBC usuarioJdbc = new UsuarioJDBC();
        
/*        //Ejecutando el listado de usuarios
        List<Usuario> usuarios = usuarioJdbc.select();
        for(Usuario usuario: usuarios){
            System.out.println("Usuario:" + usuario);
        }
        
        //Insertamos un nuevo usuario
//        Usuario usuario = new Usuario("carlos.juarez", "123");
//        usuarioJdbc.insert(usuario);

        //Modificamos un usuario existente
//        Usuario usuario = new Usuario(3, "carlos.juarez","456");
//        usuarioJdbc.update(usuario);

        usuarioJdbc.delete(new Usuario(3));*/

        Connection conexion = null;

        try {
            conexion = Conexion.getConnection();
            if(conexion.getAutoCommit()){
                conexion.setAutoCommit(false);
            }

            UsuarioJDBC usuarioJDBC = new UsuarioJDBC(conexion);

            Usuario cambioUsuario = new Usuario();
            cambioUsuario.setId_usuario(2);
            cambioUsuario.setUsername("rodolfosorry!");
            cambioUsuario.setPassword("2022");
            usuarioJDBC.update(cambioUsuario);

            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setUsername("petrico");
            //nuevoUsuario.setPassword("202688888888888888888888888888888888888888888888");
            nuevoUsuario.setPassword("2022");
            usuarioJDBC.insert(nuevoUsuario);

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
