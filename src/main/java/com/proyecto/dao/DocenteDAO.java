/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.dao;

import com.proyecto.Layout.Login;
import com.proyecto.Layout.MenuAdministrador;
import com.proyecto.Layout.MenuDocentes;
import com.proyecto.model.Docente;
import com.proyecto.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author Samuel
 */
public class DocenteDAO {

    private final Connection conn;

    public DocenteDAO() {
        this.conn = Conexion.getInstancia().getConexion();

    }

    public boolean verificarCredenciales(String correo, String contraseña) {
        String sql = "SELECT 1 FROM docente WHERE correo = ? AND contraseña = ?";

        try  {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, contraseña);
            ResultSet rs = ps.executeQuery();

            MenuDocentes menu = new MenuDocentes();
            menu.setVisible(true);

            Login login = new Login();
            login.dispose();
            return rs.next();

        } catch (SQLException e) {
            System.err.println("Error al verificar credenciales: " + e.getMessage());
            return false;
        }
    }

    public boolean guardarDocente(Docente docente, List<Integer> cursosId) {
        String sqlDocente = "INSERT INTO docente(nombre_docente, apellidos_docente, dni, correo, contraseña) VALUES (?, ?, ?, ?, ?)";
        String sqlDocenteCurso = "INSERT INTO docente_curso(docente_id, curso_id) VALUES (?, ?)";

        try {
            conn.setAutoCommit(false); 
            // Insertar docente
            PreparedStatement psDocente = conn.prepareStatement(sqlDocente, Statement.RETURN_GENERATED_KEYS);
            psDocente.setString(1, docente.getNombredocente());
            psDocente.setString(2, docente.getApellidosdocente());
            psDocente.setString(3, docente.getDNI());
            psDocente.setString(4, docente.getCorreo());
            psDocente.setString(5, docente.getContraseña()); 

            psDocente.executeUpdate();
            ResultSet rs = psDocente.getGeneratedKeys();

            int docenteId = -1;
            if (rs.next()) {
                docenteId = rs.getInt(1);
            }

            rs.close();
            psDocente.close();

            // Insertar cursos asociados
            PreparedStatement psCursos = conn.prepareStatement(sqlDocenteCurso);
            for (Integer cursoId : cursosId) {
                psCursos.setInt(1, docenteId);
                psCursos.setInt(2, cursoId);
                psCursos.addBatch();
            }

            psCursos.executeBatch();
            psCursos.close();

            conn.commit();
            conn.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            System.err.println("Error al guardar docente: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
            return false;
        }
    }

}
