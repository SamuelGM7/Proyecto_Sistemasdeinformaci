/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.model;

/**
 *
 * @author Samuel
 */
public class Alumno {

    private int id;
    private String nombrealumno;
    private String apellidosalumno;
    private String DNI;
    private String correoalumno;
    private String carrera;

    public Alumno() {
    }

    public Alumno(int id, String nombrealumno, String apellidosalumno, String DNI, String correoalumno, String carrera) {
        this.id = id;
        this.nombrealumno = nombrealumno;
        this.apellidosalumno = apellidosalumno;
        this.DNI = DNI;
        this.correoalumno = correoalumno;
        this.carrera = carrera;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombrealumno() {
        return nombrealumno;
    }

    public void setNombrealumno(String nombrealumno) {
        this.nombrealumno = nombrealumno;
    }

    public String getApellidosalumno() {
        return apellidosalumno;
    }

    public void setApellidosalumno(String apellidosalumno) {
        this.apellidosalumno = apellidosalumno;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getCorreoalumno() {
        return correoalumno;
    }

    public void setCorreoalumno(String correoalumno) {
        this.correoalumno = correoalumno;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    @Override
    public String toString() {
        return  id + " "+ nombrealumno + " " + apellidosalumno;
    }

}
