/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyecto.dao;

import com.proyecto.util.Conexion;
import java.sql.Connection;

/**
 *
 * @author Samuel
 */
public class LoginDAO {
    private final Connection conn;

    public LoginDAO() {
        this.conn = Conexion.getInstancia().getConexion();
    }
}
