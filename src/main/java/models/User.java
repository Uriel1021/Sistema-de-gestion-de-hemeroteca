/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//*****JAVA BEAN*****   
package models;

import javax.validation.constraints.*;

public class User {
    
    private int idUser;
    
    @NotEmpty(message = "Es obligatorio ingresar el nombre")
    @Size(max = 30, message = "Como máximo el nombre debe contener 30 caracteres")
    private String nombre;

    @NotEmpty(message = "Es obligatorio ingresar el apellido paterno")
    @Size(max = 30, message = "Como máximo el apellido paterno debe contener 30 caracteres")
    private String apePaterno;

    @NotEmpty(message = "Es obligatorio ingresar el apellido materno")
    @Size(max = 30, message = "Como máximo el apellido materno debe contener 30 caracteres")
    private String apeMaterno;
    
    //@Email(message="Dirección de email no válida")
    @Email(regexp = "^[^@]+@[^@]+\\.[a-zA-Z]{2,}$", message = "Dirección de email no válida")
    //@NotEmpty(message = "Es obligatorio ingresar el email")
    private String email;
    
    @NotEmpty(message = "Es obligatorio ingresar la contraseña")
    @Size(max = 30, message = "Como máximo el nombre debe contener 30 caracteres")
    private String password;
    
    @NotEmpty(message = "Es obligatorio elegir un tipo de rol")
    private String tipoUsuario;
    
    @NotEmpty(message = "Es obligatorio agregar una adscripción")
    private String adscripcion;
    
    private int estatus;
    
    

    public User() {
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApePaterno() {
        return apePaterno;
    }

    public void setApePaterno(String apePaterno) {
        this.apePaterno = apePaterno;
    }

    public String getApeMaterno() {
        return apeMaterno;
    }

    public void setApeMaterno(String apeMaterno) {
        this.apeMaterno = apeMaterno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getAdscripcion() {
        return adscripcion;
    }

    public void setAdscripcion(String adscripcion) {
        this.adscripcion = adscripcion;
    }

    public int getEstatus() {
        return estatus;
    }

    public void setEstatus(int estatus) {
        this.estatus = estatus;
    }
     

}