package daos;

import connection.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import models.User;

public class UserDao {

    private DbConnection conn;

    public UserDao(DbConnection conn) {
        this.conn = conn;
    }

    /**
     * Metodo que retorna todos los registros de la tabla usuario ordenados en
     * forma descendente
     *
     * @return una lista que contiene objetos de tipo alumno si la consulta se
     * ejecuta exitosamente, caso contrario retorna null
     * @Exception SQLException
     */
    public List<User> getAll() {
        try {
            String sql = "select * from usuario where estatus=1 order by idusuario desc;";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            //Crear una instancia
            List<User> list = new LinkedList<User>();
            User user;

            while (rs.next()) {
                user = new User();
                user.setIdUser(rs.getInt("idUsuario"));
                user.setNombre(rs.getString("nombre"));
                user.setApePaterno(rs.getString("apePaterno"));
                user.setApeMaterno(rs.getString("apeMaterno"));
                user.setEmail(rs.getString("correo"));
                user.setPassword(rs.getString("contrasena"));
                user.setTipoUsuario(rs.getString("tipoUsuario"));
                user.setEstatus(rs.getInt("estatus"));
                user.setAdscripcion(rs.getString("adscripcion"));
                //Agregamos a la lista el objeto user
                list.add(user);
            }
            return list;
            //Retorna la lista con todos los objetos

        } catch (SQLException e) {
            System.out.println("Error en UserDao.getAll:" + e.getMessage());
            return null;
        }
    }

    public boolean insert(User usuario) {
        conn.getConnection();
        try {

            String sql = "INSERT INTO usuario (nombre, apePaterno, apeMaterno, correo, contrasena, tipoUsuario, estatus, adscripcion) VALUES (?,?,?,?,?,?,1,?)";

            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getApePaterno());
            preparedStatement.setString(3, usuario.getApeMaterno());
            preparedStatement.setString(4, usuario.getEmail());
            preparedStatement.setString(5, usuario.getPassword());
            preparedStatement.setString(6, usuario.getTipoUsuario());
            preparedStatement.setString(7, usuario.getAdscripcion());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            //System.out.println("Error UserDao.insert: " + e.getMessage());
            return false;
        }

    }

    /*//Metodo que verifica si existen registros repetidos
    //Regresa false si no existen registros repetidos
    //Regresa true cuando existan registros repetidos
     */
    public int repeat(User usuario) {
        int total = 0;
        //Consulta para verificar directamente en base de datos si existe algun usuario con correo repetido
        //SELECT COUNT(correo) FROM usuario WHERE correo='frameme@gmail.com';
        String correoRecuperado = usuario.getEmail();
        String sql = "SELECT nombre FROM usuario WHERE correo='" + correoRecuperado + "'";
        try {
            PreparedStatement ps;
            ResultSet rs;
            ps = conn.getConnection().prepareStatement(sql);
            rs = ps.executeQuery(sql);
            while (rs.next()) {
                total++;
            }
        } catch (SQLException e) {
        }
        return total;
    }
    
    public boolean update(User user, int id) {
        conn.getConnection();
        try {

            String sql = "UPDATE usuario SET nombre=?, apePaterno=?, apeMaterno=?, correo=?, contrasena=?, tipoUsuario=?, estatus=1, adscripcion=? WHERE idUsuario="+id+"";

            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, user.getNombre());
            preparedStatement.setString(2, user.getApePaterno());
            preparedStatement.setString(3, user.getApeMaterno());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getTipoUsuario());
            preparedStatement.setString(7, user.getAdscripcion());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {   
            return false;
        }

    }
    
    public User getById(int id){
        
        try{
            conn.getConnection();
            String sql = "SELECT * FROM usuario WHERE idUsuario=? LIMIT 1";
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            
            rs.next();
            User user = new User(); 
            user.setIdUser(rs.getInt("idUsuario"));
            user.setNombre(rs.getString("nombre"));
            user.setApePaterno(rs.getString("apePaterno"));
            user.setApeMaterno(rs.getString("apeMaterno"));
            user.setEmail(rs.getString("correo"));
            user.setPassword(rs.getString("contrasena"));
            user.setTipoUsuario(rs.getString("tipoUsuario"));
            user.setAdscripcion(rs.getString("adscripcion"));
            return user;
            
        }catch(SQLException e){
            System.out.println("Error en AlumnoDao.getById:" + e.getMessage());
            return null;
        }
    }
    

    /**
     * Metodo para eliminar un Usuario en la base de datos
     *
     * @param id
     * @return un entero (número de filas afectadas) si la consulta se ejecuta
     * exitosamente, caso contrario retorna 0.
     * @Exception SQLException
     */
    public int delete(int id) {
        try {
            String sql = "update usuario set estatus='0' where idUsuario=?";//se esta armando la cadena de la consulta
            PreparedStatement preparedStatement = conn.getConnection().prepareStatement(sql); //para ejecutar consultas parametrzadas para evtar que inyecten codigo sql a las consultas
            preparedStatement.setInt(1, id);// el valor del identificacdor que recibimos como parametro
            int rows = preparedStatement.executeUpdate();//Ejecuta la consulta lo que traiga de resultado se asigna a rows
            return rows;

        } catch (SQLException e) {
            System.out.println("Error UserDao.eliminar: " + e.getMessage());
            return 0;
        }
    }

  
}
