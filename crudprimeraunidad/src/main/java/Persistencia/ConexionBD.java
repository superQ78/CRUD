/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author cesar
 */
public class ConexionBD implements IConexionBD{
      private final String SERVER = "localhost";
    private final String BASE_DATOS = "proyecto1";
    private final String CADENA_CONEXION = "jdbc:mysql://" + SERVER + "/" + BASE_DATOS;
    private final String USUARIO = "root";
    private final String CONTRASEÑA = "Chicharo7878";
    private Connection conexion;

    @Override
    public Connection crearConexion() throws SQLException {
    conexion = DriverManager.getConnection(CADENA_CONEXION, USUARIO, CONTRASEÑA);
    return conexion;
    }
   
}
