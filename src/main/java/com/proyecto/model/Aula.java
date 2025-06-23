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
public class Aula {

    private int id;
    private String codigoaula;
    private int aforo;
    private List<Alumno> alumnos;

    public Aula(){}
    public Aula(int id, String codigoaula, int aforo, List<Alumno> alumnos) {
        this.id = id;
        this.codigoaula = codigoaula;
        this.aforo = aforo;
        this.alumnos = alumnos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoaula() {
        return codigoaula;
    }

    public void setCodigoaula(String codigoaula) {
        this.codigoaula = codigoaula;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

}
