/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.model;

/**
 *
 * @author Samuel
 */
public class AulaAlumnos {
    private int id;
    private String nombre;
    private String apellidos;

    public AulaAlumnos(int id, String nombre, String apellidos) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    @Override
    public String toString() {
        return id + " - " + nombre + " " + apellidos;
    }

    // Getters si necesitas trabajar con los datos
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
}
