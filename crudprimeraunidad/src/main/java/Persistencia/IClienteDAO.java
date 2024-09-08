/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Persistencia;

import DTO.ClienteFiltroTablaDTO;
import Entity.ClienteEntidad;
import java.util.List;

/**
 *
 * @author cesar
 */
public interface IClienteDAO {
    void guardar(ClienteEntidad cliente) throws PersistenciaExceptions;
     List<ClienteEntidad> obtenerClientes() throws PersistenciaExceptions;
     void Editar(ClienteEntidad cliente) throws PersistenciaExceptions;
    void eliminar(int idCliente) throws PersistenciaExceptions;
    List<ClienteEntidad> buscarClientesPorFiltro(ClienteFiltroTablaDTO filtro) throws PersistenciaExceptions;


}
