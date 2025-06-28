/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.model;

/**
 *
 * @author Samuel
 */
public class Carrera {
    private int id;
    private String nombrecarrera;
    
    public Carrera(){}
    public Carrera(int id, String nombrecarrera) {
        this.id = id;
        this.nombrecarrera = nombrecarrera;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombrecarrera() {
        return nombrecarrera;
    }

    public void setNombrecarrera(String nombrecarrera) {
        this.nombrecarrera = nombrecarrera;
    }
    @Override
    public String toString() {
        return nombrecarrera; 
    }
    
}
