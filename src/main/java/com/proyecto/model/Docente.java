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
    private int DNI;
    private String correo;
    private List<Curso> cursos_asignados;

    public Docente(){}
    public Docente(int id, String nombredocente, String apellidosdocente, int DNI, String correo, List<Curso> cursos_asignados) {
        this.id = id;
        this.nombredocente = nombredocente;
        this.apellidosdocente = apellidosdocente;
        this.DNI = DNI;
        this.correo = correo;
        this.cursos_asignados = cursos_asignados;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombredocente() {
        return nombredocente;
    }

    public void setNombredocente(String nombredocente) {
        this.nombredocente = nombredocente;
    }

    public String getApellidosdocente() {
        return apellidosdocente;
    }

    public void setApellidosdocente(String apellidosdocente) {
        this.apellidosdocente = apellidosdocente;
    }

    public int getDNI() {
        return DNI;
    }

    public void setDNI(int DNI) {
        this.DNI = DNI;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Curso> getCursos_asignados() {
        return cursos_asignados;
    }

    public void setCursos_asignados(List<Curso> cursos_asignados) {
        this.cursos_asignados = cursos_asignados;
    }
    
}
