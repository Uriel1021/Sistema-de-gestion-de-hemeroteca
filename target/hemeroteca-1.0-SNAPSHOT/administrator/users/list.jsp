<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <%@include file="head.jsp" %>
        <title>Lista de Usuarios</title>


    </head>
    <body>

        <div class="container">
            <div class="row">                
                <div class="col-md-12 offset-md-0">
                    <h1>Lista de usuarios</h1>
                    <a href="user?action=new" class="btn btn-dark btn-md" tabindex="-1" role="button" aria-disabled="true">Nuevo usuario</a>

                    <c:if test = "${msg != null}">
                        <svg xmlns="http://www.w3.org/2000/svg" style="display: none;">
                        <symbol id="check-circle-fill" fill="currentColor" viewBox="0 0 16 16">
                            <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zm-3.97-3.03a.75.75 0 0 0-1.08.022L7.477 9.417 5.384 7.323a.75.75 0 0 0-1.06 1.06L6.97 11.03a.75.75 0 0 0 1.079-.02l3.992-4.99a.75.75 0 0 0-.01-1.05z"/>
                        </symbol>
                        <symbol id="info-fill" fill="currentColor" viewBox="0 0 16 16">
                            <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
                        </symbol>
                        <symbol id="exclamation-triangle-fill" fill="currentColor" viewBox="0 0 16 16">
                            <path d="M8.982 1.566a1.13 1.13 0 0 0-1.96 0L.165 13.233c-.457.778.091 1.767.98 1.767h13.713c.889 0 1.438-.99.98-1.767L8.982 1.566zM8 5c.535 0 .954.462.9.995l-.35 3.507a.552.552 0 0 1-1.1 0L7.1 5.995A.905.905 0 0 1 8 5zm.002 6a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                        </symbol>
                        </svg>
                        <div id="mensaje" class="alert ${style} alert-success d-flex align-items-center" role="alert">
                            <svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Success:"><use xlink:href="#check-circle-fill"/></svg>
                            <div>
                                ${msg}
                            </div>
                        </div>
                    </c:if>

                    <table id="tabla" class="table table-striped table-bordered" style="width:100%">
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Apellido Paterno</th>
                                <th>Apellido materno</th>
                                <th>Correo</th>
                                <th>Tipo de Usuario</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>

                        <tbody>

                            <c:forEach items="${users}" var="user" varStatus="status">
                                <tr>

                                    <td>${user.getNombre()}</td>
                                    <td>${user.getApePaterno()}</td>
                                    <td>${user.getApeMaterno()}</td>
                                    <td>${user.getEmail()}</td>
                                    <td>${user.getTipoUsuario()}</td>
                                    <td>                              
                                        <a href="user?action=details" class="btn btn-info btn-md" tabindex="-1" role="button" aria-disabled="true">Detalles</a>
                                        <a href="user?action=edit&idUsuario=${user.getIdUser()}" class="btn btn-success btn-md" tabindex="-1" role="button" aria-disabled="true">Editar</a>
                                        <a href="javascript:mostrarModal(${user.getIdUser()})" class="btn btn-danger btn-md" tabindex="-1" role="button" aria-disabled="true">Eliminar</a>

                                    </td>
                                </tr>
                            </c:forEach>

                        </tbody>

                        <tfoot>
                            <tr>
                                <th>Nombre</th>
                                <th>Apellido Paterno</th>
                                <th>Apellido materno</th>
                                <th>Correo</th>
                                <th>Tipo de Usuario</th>
                                <th>Acciones</th>
                            </tr>
                        </tfoot>
                    </table>
                </div>

            </div>
        </div>


        <div class="modal" id="modalDelete" tabindex="-1">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Eliminar Usuario</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Desea eliminar a este usuario?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                        <button type="button" class="btn btn-danger" id="btnDelete">Eliminar</button>
                    </div>
                </div>
            </div>
        </div>


        <script src="https://code.jquery.com/jquery-3.5.1.js"></script>
        <script src="https://cdn.datatables.net/1.12.0/js/jquery.dataTables.min.js"></script>
        <script src="https://cdn.datatables.net/1.12.0/js/dataTables.bootstrap5.min.js"></script>
        
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js" integrity="sha384-7+zCNj/IqJ95wo16oMtfsKbZ9ccEh31eOz1HGyDuCQ6wgnyJNSYdrPa03rtR1zdB" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

       


        <script>

        </script>
        
        <script>
            var id = "";
            $(document).ready(function () {
                $("#btnDelete").click(function () {
                    window.location.href = "user?action=delete&idUsuario=" + id;
                });
                $("#mensaje").fadeOut(5000, function () {
                    this.hide();
                });
            });
            function mostrarModal(idU) {
                id = idU;
                $("#modalDelete").modal("show");
            }
            
            
            
                      $(document).ready(function () {
                $('#tabla').DataTable();
            });



        </script>

    </body>
</html>