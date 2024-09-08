/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Negocio;

import DTO.ClienteDTO;
import DTO.ClienteFiltroTablaDTO;
import DTO.ClienteTablaDTO;
import java.util.List;

/**
 *
 * @author cesar
 */
public interface IClienteNegocio {
    public void guardar(ClienteDTO cliente) throws NegocioException;
    
    List<ClienteDTO> obtenerClientes() throws NegocioException;
      public void eliminar(int idCliente) throws NegocioException;
      public void Editar(ClienteDTO clienteDTO) throws NegocioException;
      
        List<ClienteTablaDTO> buscarClientesFiltro(ClienteFiltroTablaDTO filtro) throws NegocioException;

}
