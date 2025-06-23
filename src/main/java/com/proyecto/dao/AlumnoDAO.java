/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.dao;

import com.proyecto.model.Alumno;
import com.proyecto.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samuel
 */
public class AlumnoDAO {

    private final Connection conn;

    public AlumnoDAO() {
        conn = Conexion.getInstancia().getConexion();
    }

    // GUARDAR
    public boolean guardarAlumno(Alumno alumno) {
        String sql = "INSERT INTO alumno (nombre_alumno, apellidos_alumno, dni, correo_alumno, carrera_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, alumno.getNombrealumno());
            ps.setString(2, alumno.getApellidosalumno());
            ps.setString(3, alumno.getDNI());
            ps.setString(4, alumno.getCorreoalumno());
            ps.setInt(5, obtenerIdCarreraPorNombre(alumno.getCarrera())); // 👈 carrera_id

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar alumno: " + e.getMessage());
            return false;
        }
    }

    private int obtenerIdCarreraPorNombre(String nombreCarrera) throws SQLException {
        String sql = "SELECT id FROM carrera WHERE nombre_carrera = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombreCarrera);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("Carrera no encontrada: " + nombreCarrera);
            }
        }
    }

    public boolean modificarAlumno(Alumno alumno) {
        String sql = "UPDATE alumno SET nombre_alumno = ?, apellidos_alumno = ?, dni = ?, correo_alumno = ?, carrera_id = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, alumno.getNombrealumno());
            ps.setString(2, alumno.getApellidosalumno());
            ps.setString(3, alumno.getDNI());
            ps.setString(4, alumno.getCorreoalumno());
            ps.setInt(5, obtenerIdCarreraPorNombre(alumno.getCarrera())); 
            ps.setInt(6, alumno.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al modificar alumno: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarAlumno(int id) {
        String sql = "DELETE FROM alumno WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar alumno: " + e.getMessage());
            return false;
        }
    }

    public Alumno buscarAlumnoPorId(int id) {
        String sql = "SELECT * FROM alumno WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Alumno(
                        rs.getInt("id"),
                        rs.getString("nombre_alumno"),
                        rs.getString("apellido_alumno"),
                        rs.getString("dni"),
                        rs.getString("correo"),
                        rs.getString("carrera")
                );
            }

        } catch (SQLException e) {
            System.err.println(" Error al buscar alumno: " + e.getMessage());
        }
        return null;
    }

    public List<Alumno> listarAlumnos() {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT * FROM alumno";

        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Alumno alumno = new Alumno(
                        rs.getInt("id"),
                        rs.getString("nombre_alumno"),
                        rs.getString("apellido_alumno"),
                        rs.getString("dni"),
                        rs.getString("correo"),
                        rs.getString("carrera")
                );
                lista.add(alumno);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar alumnos: " + e.getMessage());
        }

        return lista;
    }
}
