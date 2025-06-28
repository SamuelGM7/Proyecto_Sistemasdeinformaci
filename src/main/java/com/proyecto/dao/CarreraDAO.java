/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.dao;

/**
 *
 * @author Samuel
 */
import com.proyecto.util.Conexion;
import com.proyecto.model.Carrera;
import com.proyecto.util.Tablas;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarreraDAO {

    private final Connection conn;

    public CarreraDAO() {
        this.conn = Conexion.getInstancia().getConexion();

    }

    public Carrera obtenerPorNombre(String nombre) {
        String sql = "SELECT * FROM carrera WHERE nombre_carrera = ?";
        try (Connection conn = Conexion.getInstancia().getConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Carrera(rs.getInt("id"),
                                   rs.getString("nombre_carrera"));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar carrera: " + e.getMessage());
        }
        return null;
    }

    // Guardar carrera
    public boolean guardarCarrera(Carrera carrera) {
        String sql = "INSERT INTO carrera (nombre_carrera) VALUES (?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, carrera.getNombrecarrera());
            ps.executeUpdate();
            
            return true;
        } catch (SQLException e) {
            System.err.println("Error al guardar carrera: " + e.getMessage());
            return false;
        }
    }

    // Modificar carrera
    public boolean modificarCarrera(Carrera carrera) {
        String sql = "UPDATE carrera SET nombre_carrera = ? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, carrera.getNombrecarrera());
            ps.setInt(2, carrera.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al modificar carrera: " + e.getMessage());
            return false;
        }
    }

    // Eliminar carrera
    public boolean eliminarCarrera(int id) {
        String sql = "DELETE FROM carrera WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar carrera: " + e.getMessage());
            return false;
        }
    }

    // Buscar carrera por ID
    public Carrera buscarCarreraPorId(int id) {
        String sql = "SELECT * FROM carrera WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Carrera(rs.getInt("id"), rs.getString("nombre_carrera"));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar carrera: " + e.getMessage());
        }
        return null;
    }

    // Listar todas las carreras
    public List<Carrera> listarCarreras() {
        List<Carrera> lista = new ArrayList<>();
        String sql = "SELECT * FROM carrera";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Carrera c = new Carrera(rs.getInt("id"), rs.getString("nombre_carrera"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar carreras: " + e.getMessage());
        }
        return lista;
    }

}
