package controllers;

import connection.DbConnection;
import daos.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import static java.util.Collections.list;
import java.util.List;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import models.User;
import validacionesUser.ValidacionesUser;

public class UserController extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "new":
                agregar(request, response);
                break;
            case "list":
                index(request, response);
                break;
            case "edit":
                edit(request, response);
                break;
            case "delete":
                destroy(request, response);
                break;
            case "details":
                break;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "store":
                store(request, response);
                break;
            case "actualizar":
                charge(request, response);
                break;
        }
    }

    /**
     * Método que permite recuperar la lista de users.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    public void index(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DbConnection conn = new DbConnection();
        UserDao userDao = new UserDao(conn);
        List<User> list = userDao.getAll();
        conn.disconnect();

        //request.setAttribute("msg", mensaje);
        request.setAttribute("users", list);
        //Interface que define un objeto que recibe peticiones y las reenvía a otro recurso: servlet, jsp o HTML
        RequestDispatcher rd;
        rd = request.getRequestDispatcher("administrator/users/list.jsp");
        rd.forward(request, response);
    }

    /**
     * Método para eliminar los datos de un user.
     *
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     *
     */
    public void destroy(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Aquí vamos a eliminar un registro en la base de datos
        int id = 0;
        id = Integer.parseInt(request.getParameter("idUsuario"));

        DbConnection conn = new DbConnection();
        UserDao userDao = new UserDao(conn);

        userDao.delete(id);

        String style = "alert-danger";
        String msg = "El user fue eliminado exitosamente";
        request.setAttribute("style", style);
        request.setAttribute("msg", msg);
        index(request, response);
    }

    /**
     * Método que permite desplegar la página agregar.jsp
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void agregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher rd;

        request.setAttribute("action", "store");
        rd = request.getRequestDispatcher("administrator/users/add.jsp");
        rd.forward(request, response);
    }

    /**
     * Método para store un user en la base de datos
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void store(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Recuperamos los datos que vienen del formulario, los nombres asignados a las variables
        //Son los que reucperamos directamente del formulario
        String nombre = request.getParameter("formNombre");
        String apePaterno = request.getParameter("formApePaterno");
        String apeMaterno = request.getParameter("formApeMaterno");
        String email = request.getParameter("formEmail");
        String contra = request.getParameter("formContrasena");
        String typeUser = request.getParameter("formTipoUsuario");
        String adscripcion = request.getParameter("formAdscripcion");

        //Bloque de código para eliminar espacios al inicio y al final del String
        //Ademas de eliminar acentos para que la base de datos no tenga errores
        ValidacionesUser valida = new ValidacionesUser();
        nombre = valida.quitAcent(nombre).toUpperCase().trim();
        apePaterno = valida.quitAcent(apePaterno).toUpperCase().trim();
        apeMaterno = valida.quitAcent(apeMaterno).toUpperCase().trim();
        email = valida.quitAcent(email).toLowerCase().trim();
        //typeUser = valida.quitAcent(typeUser).toUpperCase().trim();
        adscripcion = valida.quitAcent(adscripcion).toUpperCase().trim();

        //Pasamos los datos recuperados a un objeto de modelo user
        User user = new User();
        user.setNombre(nombre);
        user.setApePaterno(apePaterno);
        user.setApeMaterno(apeMaterno);
        user.setEmail(email);
        user.setPassword(contra);
        user.setTipoUsuario(typeUser);
        user.setAdscripcion(adscripcion);

        //Instrucciones necesarias para validaciones
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        //validamos al mismo tiempo todos los atributos del objeto user
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        //Interface que define un objeto que recibe peticiones y las reenvía a otro recurso: servlet, jsp o HTML
        RequestDispatcher rd;
        if (violations.size() > 0) {
            //validamos el atributo nombre del objeto user
            Set<ConstraintViolation<User>> errorNombre = validator.validateProperty(user, "nombre");
            if (errorNombre.size() > 0) {
                request.setAttribute("errorNombre", errorNombre.iterator().next().getMessage());
            }

            //validamos el atributo apePaterno del objeto user
            Set<ConstraintViolation<User>> errorApePaterno = validator.validateProperty(user, "apePaterno");
            if (errorApePaterno.size() > 0) {
                request.setAttribute("errorApePaterno", errorApePaterno.iterator().next().getMessage());
            }

            //validamos el atributo apeMaterno del objeto user
            Set<ConstraintViolation<User>> errorApeMaterno = validator.validateProperty(user, "apeMaterno");
            if (errorApeMaterno.size() > 0) {
                request.setAttribute("errorApeMaterno", errorApeMaterno.iterator().next().getMessage());
            }

            //validamos el atributo email del objeto user
            Set<ConstraintViolation<User>> errorEmail = validator.validateProperty(user, "email");
            if (errorEmail.size() > 0) {
                request.setAttribute("errorEmail", errorEmail.iterator().next().getMessage());
            }

            //validamos el atributo contraseña del objeto user
            Set<ConstraintViolation<User>> errorContrasena = validator.validateProperty(user, "password");
            if (errorContrasena.size() > 0) {
                request.setAttribute("errorContrasena", errorContrasena.iterator().next().getMessage());
            }

            //validamos el atributo Adscripcion del objeto user
            Set<ConstraintViolation<User>> errorAdscripcion = validator.validateProperty(user, "adscripcion");
            if (errorAdscripcion.size() > 0) {
                request.setAttribute("errorAdscripcion", errorAdscripcion.iterator().next().getMessage());
            }

            //validamos el atributo tipoUsuario del objeto user
            Set<ConstraintViolation<User>> errorTypeUser = validator.validateProperty(user, "tipoUsuario");
            if (errorTypeUser.size() > 0) {
                request.setAttribute("errorTipoUsuario", errorTypeUser.iterator().next().getMessage());
            }

            //compartimos el objeto user para reenviarlo a la solicitud
            //Esto con el fin de que el formulario ya vaya precargado con datos y no volver a reingresar todo
            request.setAttribute("user", user);
            request.setAttribute("action", "store");
            rd = request.getRequestDispatcher("administrator/users/add.jsp");
            rd.forward(request, response);

        } else {
            DbConnection conn = new DbConnection();
            UserDao userDao = new UserDao(conn);
            int valor = userDao.repeat(user);
            //Condicion cuando se encuentran users con el mismo correo dentro de nuestra base de datos
            if (valor > 0) {
                //JOptionPane.showMessageDialog(null, "Usuario ya existente Si entramos al ELSEEEEEEEEEEEEEE");
                request.setAttribute("action", "new");
                rd = request.getRequestDispatcher("administrator/users/add.jsp");
                rd.forward(request, response);
            } else {
                //JOptionPane.showMessageDialog(null, "Usuario No000000 existente Entramos al IFFFFFFFFFFF");
                //realizar la inserción en la base de datos
                userDao.insert(user);
                //redirigir la solicitud a listar.jsp
                /*
                request.setAttribute("action", "list");
                rd = request.getRequestDispatcher("administrator/users/list.jsp");
                rd.forward(request, response);
                 */
                List<User> list = userDao.getAll();
                request.setAttribute("users", list);
                //Interface que define un objeto que recibe peticiones y las reenvía a otro recurso: servlet, jsp o HTML
                //RequestDispatcher rd;
                rd = request.getRequestDispatcher("administrator/users/list.jsp");
                rd.forward(request, response);
            }
        }
    }

    /**
     * Método que permite desplegar la página edit.jsp
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("idUsuario"));
        DbConnection conn = new DbConnection();

        UserDao userDao = new UserDao(conn);
        User user = userDao.getById(id);
        request.setAttribute("action", "actualizar");
        request.setAttribute("user", user);
        RequestDispatcher rd;
        rd = request.getRequestDispatcher("administrator/users/edit.jsp");
        rd.forward(request, response);
    }

    protected void charge(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Recuperamos los datos que vienen del formulario, los nombres asignados a las variables
        //Son los que reucperamos directamente del formulario
        int id = Integer.parseInt(request.getParameter("formIdUser"));
        String nombre = request.getParameter("formNombre");
        String apePaterno = request.getParameter("formApePaterno");
        String apeMaterno = request.getParameter("formApeMaterno");
        String email = request.getParameter("formEmail");
        String contra = request.getParameter("formContrasena");
        String typeUser = request.getParameter("formTipoUsuario");
        String adscripcion = request.getParameter("formAdscripcion");

        //Bloque de código para eliminar espacios al inicio y al final del String
        //Ademas de eliminar acentos para que la base de datos no tenga errores
        ValidacionesUser valida = new ValidacionesUser();
        nombre = valida.quitAcent(nombre).toUpperCase().trim();
        apePaterno = valida.quitAcent(apePaterno).toUpperCase().trim();
        apeMaterno = valida.quitAcent(apeMaterno).toUpperCase().trim();
        email = valida.quitAcent(email).toLowerCase().trim();
        //typeUser = valida.quitAcent(typeUser).toUpperCase().trim();
        adscripcion = valida.quitAcent(adscripcion).toUpperCase().trim();

        //Pasamos los datos recuperados a un objeto de modelo user
        User user = new User();
        user.setNombre(nombre);
        user.setApePaterno(apePaterno);
        user.setApeMaterno(apeMaterno);
        user.setEmail(email);
        user.setPassword(contra);
        user.setTipoUsuario(typeUser);
        user.setAdscripcion(adscripcion);

        //Instrucciones necesarias para validaciones
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        //validamos al mismo tiempo todos los atributos del objeto user
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        //Interface que define un objeto que recibe peticiones y las reenvía a otro recurso: servlet, jsp o HTML
        RequestDispatcher rd;
        if (violations.size() > 0) {
            //validamos el atributo nombre del objeto user
            Set<ConstraintViolation<User>> errorNombre = validator.validateProperty(user, "nombre");
            if (errorNombre.size() > 0) {
                request.setAttribute("errorNombre", errorNombre.iterator().next().getMessage());
            }

            //validamos el atributo apePaterno del objeto user
            Set<ConstraintViolation<User>> errorApePaterno = validator.validateProperty(user, "apePaterno");
            if (errorApePaterno.size() > 0) {
                request.setAttribute("errorApePaterno", errorApePaterno.iterator().next().getMessage());
            }

            //validamos el atributo apeMaterno del objeto user
            Set<ConstraintViolation<User>> errorApeMaterno = validator.validateProperty(user, "apeMaterno");
            if (errorApeMaterno.size() > 0) {
                request.setAttribute("errorApeMaterno", errorApeMaterno.iterator().next().getMessage());
            }

            //validamos el atributo email del objeto user
            Set<ConstraintViolation<User>> errorEmail = validator.validateProperty(user, "email");
            if (errorEmail.size() > 0) {
                request.setAttribute("errorEmail", errorEmail.iterator().next().getMessage());
            }

            //validamos el atributo contraseña del objeto user
            Set<ConstraintViolation<User>> errorContrasena = validator.validateProperty(user, "password");
            if (errorContrasena.size() > 0) {
                request.setAttribute("errorContrasena", errorContrasena.iterator().next().getMessage());
            }

            //validamos el atributo Adscripcion del objeto user
            Set<ConstraintViolation<User>> errorAdscripcion = validator.validateProperty(user, "adscripcion");
            if (errorAdscripcion.size() > 0) {
                request.setAttribute("errorAdscripcion", errorAdscripcion.iterator().next().getMessage());
            }

            //validamos el atributo tipoUsuario del objeto user
            Set<ConstraintViolation<User>> errorTypeUser = validator.validateProperty(user, "tipoUsuario");
            if (errorTypeUser.size() > 0) {
                request.setAttribute("errorTipoUsuario", errorTypeUser.iterator().next().getMessage());
            }

            //compartimos el objeto user para reenviarlo a la solicitud
            //Esto con el fin de que el formulario ya vaya precargado con datos y no volver a reingresar todo
            request.setAttribute("user", user);
            request.setAttribute("action", "actualizar");
            rd = request.getRequestDispatcher("administrator/users/edit.jsp");
            rd.forward(request, response);

        } else {
            DbConnection conn = new DbConnection();
            UserDao userDao = new UserDao(conn);
            //JOptionPane.showMessageDialog(null, "Usuario No000000 existente Entramos al IFFFFFFFFFFF");
            //realizar la inserción en la base de datos
            userDao.update(user, id);
            //redirigir la solicitud a listar.jsp
            /*
            request.setAttribute("action", "list");
            rd = request.getRequestDispatcher("administrator/users/list.jsp");
            rd.forward(request, response);
             */
            List<User> list = userDao.getAll();
            request.setAttribute("users", list);
            //Interface que define un objeto que recibe peticiones y las reenvía a otro recurso: servlet, jsp o HTML
            //RequestDispatcher rd;
            rd = request.getRequestDispatcher("administrator/users/list.jsp");
            rd.forward(request, response);
        }
    }
}
