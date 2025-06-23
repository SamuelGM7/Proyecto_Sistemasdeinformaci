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
public class Curso {
    private int id;
    private String nombrecurso;
    private List<Carrera> carreras_asociadas;
    private List<Evaluacion> evaluaciones;
    public Curso (){}
    public Curso(int id, String nombrecurso, List<Carrera> carreras_asociadas, List<Evaluacion> evaluaciones) {
        this.id = id;
        this.nombrecurso = nombrecurso;
        this.carreras_asociadas = carreras_asociadas;
        this.evaluaciones = evaluaciones;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombrecurso() {
        return nombrecurso;
    }

    public void setNombrecurso(String nombrecurso) {
        this.nombrecurso = nombrecurso;
    }

    public List<Carrera> getCarreras_asociadas() {
        return carreras_asociadas;
    }

    public void setCarreras_asociadas(List<Carrera> carreras_asociadas) {
        this.carreras_asociadas = carreras_asociadas;
    }

    public List<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(List<Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }
    
}
