<%-- 
    Document   : editar
    Created on : 8 may. 2022, 21:12:02
    Author     : Universidad
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <title>Editar</title>
    </head>
    <body>
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-8">
                    <h2>Editar usuario</h2>
                    <%@include file="partials/fileds.jsp" %>
                </div>
            </div>
        </div>
    </body>
</html>