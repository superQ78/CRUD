/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.mycompany.crudprimeraunidad;

import Negocio.ClienteNegocio;
import Negocio.IClienteNegocio;
import Persistencia.ClienteDAO;
import Persistencia.ConexionBD;
import Persistencia.IClienteDAO;
import Persistencia.IConexionBD;
import java.sql.SQLException;

/**
 *
 * @author cesar
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
    
        IConexionBD conexion=new ConexionBD();
        IClienteDAO clienteDAO= new ClienteDAO(conexion);
        IClienteNegocio clienteNegocio= new ClienteNegocio(clienteDAO);
        
//        frmCliente insFrmCliente= new frmCliente(clienteNegocio);
//        insFrmCliente.setVisible(true);
    }
    
}
