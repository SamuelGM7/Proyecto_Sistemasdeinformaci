/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.dao;

import com.proyecto.model.Alumno;
import com.proyecto.model.Aula;
import com.proyecto.util.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author Samuel
 */
public class AulaDAO {

    private Connection conn;

    public AulaDAO() {
        conn = Conexion.getInstancia().getConexion();
    }

    public boolean guardarAula(Aula aula) {
        String sqlAula = "INSERT INTO aula(codigo_aula, aforo) VALUES (?, ?)";
        String sqlRelacion = "INSERT INTO aula_alumno(aula_id, alumno_id) VALUES (?, ?)";

        try {
            conn.setAutoCommit(false);

            // Insertar aula
            PreparedStatement psAula = conn.prepareStatement(sqlAula, Statement.RETURN_GENERATED_KEYS);
            psAula.setString(1, aula.getCodigoaula());
            psAula.setInt(2, aula.getAforo());
            psAula.executeUpdate();

            ResultSet rs = psAula.getGeneratedKeys();
            int aulaId = -1;
            if (rs.next()) {
                aulaId = rs.getInt(1);
            }

            psAula.close();
            rs.close();

            // Insertar relación aula-alumno
            PreparedStatement psRelacion = conn.prepareStatement(sqlRelacion);
            for (Alumno alumno : aula.getAlumnos()) {
                psRelacion.setInt(1, aulaId);
                psRelacion.setInt(2, alumno.getId());
                psRelacion.addBatch();
            }

            psRelacion.executeBatch();
            psRelacion.close();

            conn.commit();
            conn.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error en rollback: " + ex.getMessage());
            }
            System.err.println("Error al guardar aula: " + e.getMessage());
            return false;
        }
    }

    public List<Aula> listarAulas() {
        List<Aula> aulas = new ArrayList<>();
        String sql = "SELECT * FROM aula";

        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String codigo = rs.getString("codigo_aula");
                int aforo = rs.getInt("aforo");
                List<Alumno> alumnos = obtenerAlumnosPorAula(id);
                Aula aula = new Aula(id, codigo, aforo, alumnos);
                aulas.add(aula);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar aulas: " + e.getMessage());
        }

        return aulas;
    }

    private List<Alumno> obtenerAlumnosPorAula(int aulaId) {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = """
            SELECT a.id, a.nombre_alumno, a.apellidos_alumno, a.dni, a.correo_alumno
            FROM alumno a
            JOIN aula_alumno aa ON a.id = aa.alumno_id
            WHERE aa.aula_id = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, aulaId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Alumno alumno = new Alumno();
                alumno.setId(rs.getInt("id"));
                alumno.setNombrealumno(rs.getString("nombre_alumno"));
                alumno.setApellidosalumno(rs.getString("apellidos_alumno"));
                alumno.setDNI(rs.getString("dni"));
                alumno.setCorreoalumno(rs.getString("correo_alumno"));
                alumnos.add(alumno);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener alumnos del aula: " + e.getMessage());
        }

        return alumnos;
    }

    public List<Aula> obtenerTodasConAlumnos() {
        String sql = """
        SELECT a.id AS aula_id, a.codigo_aula, a.aforo,
               al.id AS alumno_id, al.nombre_alumno, al.apellidos_alumno
        FROM aula a
        LEFT JOIN aula_alumno aa ON a.id = aa.aula_id
        LEFT JOIN alumno al ON aa.alumno_id = al.id
        ORDER BY a.id
        """;

        Map<Integer, Aula> aulas = new LinkedHashMap<>();

        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idAula = rs.getInt("aula_id");
                Aula aula = aulas.get(idAula);

                if (aula == null) {
                    aula = new Aula();
                    aula.setId(idAula);
                    aula.setCodigoaula(rs.getString("codigo_aula"));
                    aula.setAforo(rs.getInt("aforo"));
                    aula.setAlumnos(new ArrayList<>());
                    aulas.put(idAula, aula);
                }

                int idAlumno = rs.getInt("alumno_id");
                if (idAlumno > 0) {
                    Alumno alumno = new Alumno();
                    alumno.setId(idAlumno);
                    alumno.setNombrealumno(rs.getString("nombre_alumno"));
                    alumno.setApellidosalumno(rs.getString("apellidos_alumno"));
                    aula.getAlumnos().add(alumno);
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al obtener aulas: " + e.getMessage());
        }

        return new ArrayList<>(aulas.values());
    }

}
