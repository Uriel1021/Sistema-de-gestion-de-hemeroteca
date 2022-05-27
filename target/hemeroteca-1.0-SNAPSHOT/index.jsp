<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <%@include file="head.jsp" %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hemeroteca</title>
    </head>

    <body>
        <%@include file="navbar.jsp" %> 

        <div class="container">
            <div class="row">
                <div class="col-md-8 offset-md-2">
                    <h1>INDEX</h1>
                    <a href="user?action=list" class="btn btn-dark btn-md" tabindex="-1" role="button" aria-disabled="true">Administracion de usuarios</a>
                    <a href="note?action=list" class="btn btn-dark btn-md" tabindex="-1" role="button" aria-disabled="true">Administracion de notas</a>
                </div>
            </div>
        </div>

    </body>
</html>