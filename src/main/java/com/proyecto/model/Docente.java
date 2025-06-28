/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.model;

import java.util.List;

/**
 *
 * @author Samuel
 */
public class Docente {

    private int id;
    private String nombredocente;
    private String apellidosdocente;
    private String DNI;
    private String correo;
    private String contraseña;

    public Docente() {
    }

    public Docente(int id, String nombredocente, String apellidosdocente, String DNI, String correo, String contraseña) {
        this.id = id;
        this.nombredocente = nombredocente;
        this.apellidosdocente = apellidosdocente;
        this.DNI = DNI;
        this.correo = correo;
        this.contraseña = contraseña;
    }

    public int getId() {
        return id;
    }

    public String getNombredocente() {
        return nombredocente;
    }

    public String getApellidosdocente() {
        return apellidosdocente;
    }

    public String getDNI() {
        return DNI;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

}
