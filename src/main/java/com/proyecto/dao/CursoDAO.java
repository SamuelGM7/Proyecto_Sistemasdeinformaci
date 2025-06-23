/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.dao;

/**
 *
 * @author Samuel
 */
import com.proyecto.model.Curso;
import com.proyecto.model.Carrera;
import com.proyecto.model.Evaluacion;
import com.proyecto.util.Conexion;

import java.sql.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class CursoDAO {

    private final Connection conn;

    public CursoDAO() {
        this.conn = Conexion.getInstancia().getConexion();
    }

    public boolean guardarCurso(Curso curso) {
        String sqlCurso = "INSERT INTO curso(nombre_curso) VALUES (?)";
        String sqlCursoCarrera = "INSERT INTO curso_carrera(curso_id, carrera_id) VALUES (?, ?)";
        String sqlEvaluacion = "INSERT INTO evaluacion(descripcion, curso_id) VALUES (?, ?)";
        String sqlExamen = "INSERT INTO evaluacion_examenes(nombre_examen, evaluacion_id) VALUES (?, ?)";

        try {
            conn.setAutoCommit(false); // transacción

            // Insertar curso
            PreparedStatement psCurso = conn.prepareStatement(sqlCurso, Statement.RETURN_GENERATED_KEYS);
            psCurso.setString(1, curso.getNombrecurso());
            psCurso.executeUpdate();

            ResultSet rs = psCurso.getGeneratedKeys();
            int cursoId = -1;
            if (rs.next()) {
                cursoId = rs.getInt(1);
            }

            // Insertar carreras asociadas
            PreparedStatement psCursoCarrera = conn.prepareStatement(sqlCursoCarrera);
            for (Carrera carrera : curso.getCarreras_asociadas()) {
                psCursoCarrera.setInt(1, cursoId);
                psCursoCarrera.setInt(2, carrera.getId());
                psCursoCarrera.addBatch();
            }
            psCursoCarrera.executeBatch();

            // Insertar evaluaciones y sus exámenes
            PreparedStatement psEval = conn.prepareStatement(sqlEvaluacion, Statement.RETURN_GENERATED_KEYS);
            PreparedStatement psExamen = conn.prepareStatement(sqlExamen);

            for (Evaluacion evaluacion : curso.getEvaluaciones()) {
                psEval.setString(1, evaluacion.getDescripcion());
                psEval.setInt(2, cursoId);
                psEval.executeUpdate();

                ResultSet rsEval = psEval.getGeneratedKeys();
                int evaluacionId = -1;
                if (rsEval.next()) {
                    evaluacionId = rsEval.getInt(1);
                }

                for (String examen : evaluacion.getExamenes()) {
                    psExamen.setString(1, examen);
                    psExamen.setInt(2, evaluacionId);
                    psExamen.addBatch();
                }
            }
            psExamen.executeBatch();

            conn.commit();
            conn.setAutoCommit(true);
            return true;

        } catch (SQLException e) {
            System.err.println("Error al guardar curso: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
            return false;
        }
    }

    public void eliminarCursoDesdeTabla(JTable tablaCursos) {
        int fila = tablaCursos.getSelectedRow();

        if (fila >= 0) {
            // Obtenemos el ID del curso de la columna 0
            String idStr = tablaCursos.getValueAt(fila, 0).toString();
            int id = Integer.parseInt(idStr);

            // Confirmación
            int confirmacion = JOptionPane.showConfirmDialog(null,
                    "¿Estás seguro de que deseas eliminar este curso?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                CursoDAO dao = new CursoDAO();
                boolean eliminado = dao.eliminarCurso(id);

                if (eliminado) {
                    JOptionPane.showMessageDialog(null, "Curso eliminado correctamente.");
                    //cargarCursosEnTabla(); // refresca la tabla con los cursos actualizados
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el curso.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecciona una fila de la tabla primero.");
        }
    }

    public boolean eliminarCurso(int id) {
        String sql1 = "DELETE FROM evaluacion_examenes WHERE evaluacion_id IN (SELECT id FROM evaluacion WHERE curso_id = ?)";
        String sql2 = "DELETE FROM evaluacion WHERE curso_id = ?";
        String sql3 = "DELETE FROM curso_carrera WHERE curso_id = ?";
        String sql4 = "DELETE FROM curso WHERE id = ?";

        try {
            conn.setAutoCommit(false);

            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setInt(1, id);
            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, id);
            ps2.executeUpdate();

            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setInt(1, id);
            ps3.executeUpdate();

            PreparedStatement ps4 = conn.prepareStatement(sql4);
            ps4.setInt(1, id);
            ps4.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar curso: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback fallido: " + ex.getMessage());
            }
            return false;
        }
    }

    public Curso buscarCursoPorId(int id) {
        Curso curso = null;

        try {
            // Buscar curso
            String sqlCurso = "SELECT * FROM curso WHERE id = ?";
            PreparedStatement psCurso = conn.prepareStatement(sqlCurso);
            psCurso.setInt(1, id);
            ResultSet rsCurso = psCurso.executeQuery();

            if (rsCurso.next()) {
                String nombre = rsCurso.getString("nombre_curso");
                curso = new Curso();
                curso.setId(id);
                curso.setNombrecurso(nombre);
            } else {
                return null;
            }

            // Carreras asociadas
            String sqlCarrera = "SELECT c.id, c.nombre_carrera FROM curso_carrera cc JOIN carrera c ON cc.carrera_id = c.id WHERE cc.curso_id = ?";
            PreparedStatement psCarr = conn.prepareStatement(sqlCarrera);
            psCarr.setInt(1, id);
            ResultSet rsCarr = psCarr.executeQuery();

            List<Carrera> carreras = new java.util.ArrayList<>();
            while (rsCarr.next()) {
                Carrera carrera = new Carrera(rsCarr.getInt("id"), rsCarr.getString("nombre_carrera"));
                carreras.add(carrera);
            }
            curso.setCarreras_asociadas(carreras);

            // Evaluaciones
            String sqlEval = "SELECT * FROM evaluacion WHERE curso_id = ?";
            PreparedStatement psEval = conn.prepareStatement(sqlEval);
            psEval.setInt(1, id);
            ResultSet rsEval = psEval.executeQuery();

            List<Evaluacion> evaluaciones = new java.util.ArrayList<>();
            while (rsEval.next()) {
                int evalId = rsEval.getInt("id");
                String descripcion = rsEval.getString("descripcion");

                List<String> examenes = new java.util.ArrayList<>();
                String sqlExamen = "SELECT nombre_examen FROM evaluacion_examenes WHERE evaluacion_id = ?";
                PreparedStatement psEx = conn.prepareStatement(sqlExamen);
                psEx.setInt(1, evalId);
                ResultSet rsEx = psEx.executeQuery();
                while (rsEx.next()) {
                    examenes.add(rsEx.getString("nombre_examen"));
                }

                Evaluacion evaluacion = new Evaluacion(evalId, descripcion, examenes);
                evaluaciones.add(evaluacion);
            }
            curso.setEvaluaciones(evaluaciones);

            return curso;

        } catch (SQLException e) {
            System.err.println("Error al buscar curso: " + e.getMessage());
            return null;
        }
    }

    public boolean modificarCurso(Curso curso) {
        try {
            conn.setAutoCommit(false);

            // 1. Actualizar nombre
            String sql = "UPDATE curso SET nombre_curso = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, curso.getNombrecurso());
            ps.setInt(2, curso.getId());
            ps.executeUpdate();

            // 2. Eliminar asociaciones existentes
            eliminarCurso(curso.getId());

            // 3. Volver a guardar todo con datos actualizados
            guardarCurso(curso);

            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            System.err.println("Error al modificar curso: " + e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.err.println("Rollback fallido: " + ex.getMessage());
            }
            return false;
        }
    }
}
