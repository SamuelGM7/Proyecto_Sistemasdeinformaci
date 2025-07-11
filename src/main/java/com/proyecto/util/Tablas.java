/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.util;

import com.proyecto.dao.AulaDAO;
import com.proyecto.model.Alumno;
import com.proyecto.model.Aula;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Samuel
 */
public class Tablas {

    private final Connection conn;

    public Tablas() {
        conn = Conexion.getInstancia().getConexion();
    }

    public void TablaCurso(JTable tabla) {
        String sqlCursos = "SELECT * FROM curso";
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre del Curso");
        modelo.addColumn("Carreras Asociadas");
        modelo.addColumn("Evaluaciones");

        tabla.setModel(modelo);

        try {
            Statement st = conn.createStatement();
            ResultSet rsCurso = st.executeQuery(sqlCursos);

            while (rsCurso.next()) {
                int id = rsCurso.getInt("id");
                String nombre = rsCurso.getString("nombre_curso");

                String sqlCarreras = "SELECT c.nombre_carrera FROM curso_carrera cc JOIN carrera c ON cc.carrera_id = c.id WHERE cc.curso_id = ?";
                PreparedStatement psCarrera = conn.prepareStatement(sqlCarreras);
                psCarrera.setInt(1, id);
                ResultSet rsCarrera = psCarrera.executeQuery();

                List<String> carreras = new ArrayList<>();
                while (rsCarrera.next()) {
                    carreras.add(rsCarrera.getString("nombre_carrera"));
                }

                // Obtener evaluaciones y sus nombres de examen
                String sqlEval = "SELECT id, descripcion FROM evaluacion WHERE curso_id = ?";
                PreparedStatement psEval = conn.prepareStatement(sqlEval);
                psEval.setInt(1, id);
                ResultSet rsEval = psEval.executeQuery();

                List<String> examenes = new ArrayList<>();
                while (rsEval.next()) {
                    int evalId = rsEval.getInt("id");

                    String sqlExamenes = "SELECT nombre_examen FROM evaluacion_examenes WHERE evaluacion_id = ?";
                    PreparedStatement psEx = conn.prepareStatement(sqlExamenes);
                    psEx.setInt(1, evalId);
                    ResultSet rsEx = psEx.executeQuery();

                    while (rsEx.next()) {
                        examenes.add(rsEx.getString("nombre_examen"));
                    }
                }

                String[] fila = new String[4];
                fila[0] = String.valueOf(id);
                fila[1] = nombre;
                fila[2] = String.join(", ", carreras);
                fila[3] = String.join(", ", examenes);

                modelo.addRow(fila);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar cursos: " + e.getMessage());
        }
    }

    public void TablaAlumno(JTable tabla) {
        String sql = """
                SELECT a.id, a.nombre_alumno, a.apellidos_alumno, a.dni, a.correo_alumno, c.nombre_carrera 
                FROM alumno a 
                JOIN carrera c ON a.carrera_id = c.id
                """;

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellidos");
        modelo.addColumn("DNI");
        modelo.addColumn("Correo");
        modelo.addColumn("Carrera");

        tabla.setModel(modelo);
        String[] datos = new String[6];

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                datos[0] = String.valueOf(rs.getInt("id"));
                datos[1] = rs.getString("nombre_alumno");
                datos[2] = rs.getString("apellidos_alumno");
                datos[3] = rs.getString("dni");
                datos[4] = rs.getString("correo_alumno");
                datos[5] = rs.getString("nombre_carrera");
                modelo.addRow(datos);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar alumnos: " + e.getMessage());
        }
    }

    public void TablaDocente(JTable tabla) {
        String sqlDocente = "SELECT * FROM docente";
        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellidos");
        modelo.addColumn("DNI");
        modelo.addColumn("Correo");
        modelo.addColumn("Cursos Asignados");

        tabla.setModel(modelo);
        String[] datos = new String[6];

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sqlDocente);

            while (rs.next()) {
                int docenteId = rs.getInt("id");
                datos[0] = String.valueOf(docenteId);
                datos[1] = rs.getString("nombre_docente");
                datos[2] = rs.getString("apellidos_docente");
                datos[3] = rs.getString("dni");
                datos[4] = rs.getString("correo");

                String sqlCursos = "SELECT nombre_curso FROM docente_curso cd JOIN curso c ON cd.curso_id = c.id WHERE cd.docente_id = ?";
                PreparedStatement psCursos = conn.prepareStatement(sqlCursos);
                psCursos.setInt(1, docenteId);
                ResultSet rsCursos = psCursos.executeQuery();

                List<String> cursos = new ArrayList<>();
                while (rsCursos.next()) {
                    cursos.add(rsCursos.getString("nombre_curso"));
                }

                datos[5] = String.join(", ", cursos);

                modelo.addRow(datos);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar docentes: " + e.getMessage());
        }
    }

    public void TablaAula(JTable tabla) {
        AulaDAO auladao = new AulaDAO();
        List<Aula> aulas = auladao.obtenerTodasConAlumnos(); 

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Código Aula");
        modelo.addColumn("Aforo");
        modelo.addColumn("Alumnos Registrados");

        for (Aula a : aulas) {
            StringBuilder nombresAlumnos = new StringBuilder();
            List<Alumno> lista = a.getAlumnos();

            if (lista != null && !lista.isEmpty()) {
                for (Alumno al : lista) {
                    nombresAlumnos.append(al.getNombrealumno()).append(" ").append(al.getApellidosalumno()).append(", ");
                }
                nombresAlumnos.setLength(nombresAlumnos.length() - 2); 
            } else {
                nombresAlumnos.append("Sin alumnos");
            }

            modelo.addRow(new Object[]{
                a.getId(),
                a.getCodigoaula(),
                a.getAforo(),
                nombresAlumnos.toString()
            });
        }

        tabla.setModel(modelo);
    }

    public void TablaCarrera(JTable tabla) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre de la Carrera");

        try {
            Connection conn = Conexion.getInstancia().getConexion();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM carrera");

            while (rs.next()) {
                Object[] fila = {
                    rs.getInt("id"),
                    rs.getString("nombre_carrera")
                };
                modelo.addRow(fila);
            }

            tabla.setModel(modelo);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "❌ Error al actualizar tabla: " + e.getMessage());
        }
    }

    public void TablaSeleccionAlumno(JTable tabla) {
        String sql = """
            SELECT a.id, a.nombre_alumno, a.apellidos_alumno, a.dni, a.correo_alumno, c.nombre_carrera 
            FROM alumno a 
            JOIN carrera c ON a.carrera_id = c.id
            """;

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class; 
                }
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };

        modelo.addColumn("Seleccionar");
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellidos");
        modelo.addColumn("DNI");
        modelo.addColumn("Correo");
        modelo.addColumn("Carrera");

        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = false;
                fila[1] = rs.getInt("id");
                fila[2] = rs.getString("nombre_alumno");
                fila[3] = rs.getString("apellidos_alumno");
                fila[4] = rs.getString("dni");
                fila[5] = rs.getString("correo_alumno");
                fila[6] = rs.getString("nombre_carrera");

                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar alumnos: " + e.getMessage());
        }

        tabla.setModel(modelo);
    }

}
