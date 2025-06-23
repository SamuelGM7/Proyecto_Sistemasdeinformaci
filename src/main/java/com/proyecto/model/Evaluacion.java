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

public class Evaluacion {
    private int id;
    private String descripcion; // Por ejemplo "Evaluación del Primer Ciclo"
    private List<String> examenes;

    public Evaluacion() {}
    public Evaluacion(int id, String descripcion, List<String> examenes) {
        this.id = id;
        this.descripcion = descripcion;
        this.examenes = examenes;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public List<String> getExamenes() {
        return examenes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setExamenes(List<String> examenes) {
        this.examenes = examenes;
    }
}