/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Negocio;

import DTO.ClienteDTO;
import DTO.ClienteFiltroTablaDTO;
import DTO.ClienteTablaDTO;
import Entity.ClienteEntidad;
import Persistencia.IClienteDAO;
import Persistencia.PersistenciaExceptions;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author cesar
 */
public class ClienteNegocio implements IClienteNegocio{
    private IClienteDAO clienteDAO;
    
    public ClienteNegocio(IClienteDAO clienteDAO){
        this.clienteDAO=clienteDAO;
    }

   
    @Override
    public void guardar(ClienteDTO clienteDTO) throws NegocioException {
        try{
            System.out.println("paso por negocio del cliente"+ LocalDateTime.now());
             ClienteEntidad clienteEntity = transformarDTOaEntity(clienteDTO);
              this.clienteDAO.guardar(clienteEntity);
        } catch (PersistenciaExceptions e) {
            throw new NegocioException("Error en la capa de negocio: " + e.getMessage(), e);
        }
    
    }
     @Override
    public List<ClienteDTO> obtenerClientes() throws NegocioException {
        try {
            // Obtener la lista de entidades desde el DAO
            List<ClienteEntidad> listaEntities = clienteDAO.obtenerClientes();

            // Convertir la lista de entidades a lista de DTOs
            List<ClienteDTO> listaDTOs = new ArrayList<>();
            for (ClienteEntidad entity : listaEntities) {
                listaDTOs.add(transformarEntityaDTO(entity));
            }

            // Devolver la lista de DTOs
            return listaDTOs;
        } catch (PersistenciaExceptions e) {
            throw new NegocioException("Error al obtener los clientes: " + e.getMessage(), e);
        }
    }
     @Override
    public void eliminar(int idCliente) throws NegocioException {
        try {
            clienteDAO.eliminar(idCliente); // Llama al método de eliminar en el DAO
        } catch (PersistenciaExceptions e) {
            throw new NegocioException("Error al eliminar el cliente", e);
        }
    }
    @Override
    public void Editar(ClienteDTO clienteDTO) throws NegocioException {
        try {
            // Transformar DTO a Entidad
            ClienteEntidad clienteEntity = transformarDTOaEntity(clienteDTO);
            // Llamar al método de actualización en el DAO
            clienteDAO.Editar(clienteEntity);
        } catch (PersistenciaExceptions e) {
            throw new NegocioException("Error al editar el cliente: " + e.getMessage(), e);
        }
    }
     @Override
    public List<ClienteTablaDTO> buscarClientesFiltro(ClienteFiltroTablaDTO filtro) throws NegocioException {
        try {
            // Llamada al DAO para obtener los clientes filtrados
            List<ClienteEntidad> listaEntidades = clienteDAO.buscarClientesPorFiltro(filtro);

            // Convertir las entidades en DTOs
            List<ClienteTablaDTO> listaDTOs = new ArrayList<>();
            for (ClienteEntidad entidad : listaEntidades) {
                ClienteTablaDTO clienteTablaDTO = transformarEntidadATablaDTO(entidad);
                listaDTOs.add(clienteTablaDTO);
            }

            return listaDTOs;
        } catch (PersistenciaExceptions e) {
            throw new NegocioException("Error al buscar clientes: " + e.getMessage(), e);
        }
    }
         private ClienteTablaDTO transformarEntidadATablaDTO(ClienteEntidad entidad) {
        ClienteTablaDTO clienteTablaDTO = new ClienteTablaDTO();
        clienteTablaDTO.setIdClientes(entidad.getIdClientes());
        clienteTablaDTO.setNombre(entidad.getNombre());
        clienteTablaDTO.setApellidoP(entidad.getApellidoP());
        clienteTablaDTO.setApellidoM(entidad.getApellidoM());
        // Otros campos que necesites en la tabla...
        return clienteTablaDTO;
    
        
    }
    private ClienteEntidad transformarDTOaEntity(ClienteDTO clienteDTO) {
        ClienteEntidad clienteEntidad = new ClienteEntidad();
        clienteEntidad.setNombre(clienteDTO.getNombre());
        clienteEntidad.setApellidoP(clienteDTO.getApellidoP());
        clienteEntidad.setApellidoM(clienteDTO.getApellidoM());
        clienteEntidad.setEstaEliminado(false); // Por defecto no está eliminado
        clienteEntidad.setFechaHoraRegistro(new java.util.Date()); // Fecha actual
        return clienteEntidad;
    }
    private ClienteDTO transformarEntityaDTO(ClienteEntidad clienteEntidad) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setIdClientes(clienteEntidad.getIdClientes());
        clienteDTO.setNombre(clienteEntidad.getNombre());
        clienteDTO.setApellidoP(clienteEntidad.getApellidoP());
        clienteDTO.setApellidoM(clienteEntidad.getApellidoM());
        clienteDTO.setFechaHoraRegistro(clienteEntidad.getFechaHoraRegistro());
        clienteDTO.setEstaEliminado(clienteEntidad.isEstaEliminado());
        return clienteDTO;
    }

    
}

    
    

