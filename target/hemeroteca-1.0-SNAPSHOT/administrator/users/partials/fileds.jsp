<%--Ambos formularios deben pasar por el metodo doPost--%>
<form action="user" method="POST">

    <input type="hidden" name="action" value="${action}">

    <div class="mb-3">
        <input type="hidden" name="formIdUser" class="form-control" value="${user.getIdUser()}">
    </div>
    
    <div class="mb-3">
        <label class="form-label">Nombre</label>
        <input type="text" name="formNombre" class="form-control" value="${user.getNombre()}">
        <div class="form-text text-danger fw-bold">${errorNombre}</div>
    </div>

    <div class="mb-3">
        <label class="form-label">Apellido paterno</label>
        <input type="text" name="formApePaterno" class="form-control" value="${user.getApePaterno()}">
        <div class="form-text text-danger fw-bold">${errorApePaterno}</div>
    </div>

    <div class="mb-3">
        <label class="form-label">Apellido materno</label>
        <input type="text" name="formApeMaterno" class="form-control" value="${user.getApeMaterno()}">
        <div class="form-text text-danger fw-bold">${errorApeMaterno}</div>
    </div>

    <div class="mb-3">
        <label class="form-label">Email</label>
        <input type="text" name="formEmail" class="form-control" value="${user.getEmail()}">
        <div class="form-text text-danger fw-bold">${errorEmail}</div>
    </div>

    <div class="mb-3">
        <label class="form-label">Contraseña</label>
        <input type="text" name="formContrasena" class="form-control" value="${user.getPassword()}">
        <div class="form-text text-danger fw-bold">${errorContrasena}</div>
    </div>

    <div class="form-group form-check">
        <input type="checkbox" class="form-check-input" id="exampleCheck1">
        <label class="form-check-label" for="exampleCheck1">Mostrar contraseña</label>
    </div>

    
    <div class="mb-3">
        <label for="exampleFormControlSelect1">Seleccionar rol de user</label>
        <div class="radio">
            <label><input type="radio" name="formTipoUsuario" value="Super Administrador">Super administrador</label>
        </div>
        <div class="radio">
            <label><input type="radio" name="formTipoUsuario" value="Administrador">Administrador</label>
        </div>
        <div class="radio">
            <label><input type="radio" name="formTipoUsuario" value="Invitado" checked>Invitado</label>
        </div>
        <div class="form-text text-danger fw-bold">${errorTipoUsuario}</div>
    </div>
    



    <div class="mb-3">
        <label class="form-label">Localidad / Adscripción</label>
        <input type="text" name="formAdscripcion" class="form-control" value="${user.getAdscripcion()}">
        <div class="form-text text-danger fw-bold">${errorAdscripcion}</div>
    </div>

    <button type="submit" class="btn btn-primary">Registrar</button>
</form>