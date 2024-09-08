/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import DTO.ClienteFiltroTablaDTO;
import Entity.ClienteEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cesar
 */
public class ClienteDAO implements IClienteDAO {

    private IConexionBD conexionBD;

    public ClienteDAO(IConexionBD conexionBD) {

        this.conexionBD = conexionBD;
    }

    @Override
    public void guardar(ClienteEntidad cliente) throws PersistenciaExceptions {
        String consulta = "INSERT INTO Clientes (Nombre, ApellidoP, ApellidoM, estaEliminado, fechaHoraRegistro) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = conexionBD.crearConexion(); 
                PreparedStatement ps = connection.prepareStatement(consulta)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellidoP());
            ps.setString(3, cliente.getApellidoM());
            ps.setBoolean(4, cliente.isEstaEliminado());
            ps.setTimestamp(5, new java.sql.Timestamp(cliente.getFechaHoraRegistro().getTime()));

            ps.executeUpdate();
            System.out.println("Cliente guardado correctamente en la base de datos");

        } catch (SQLException e) {
            throw new PersistenciaExceptions("Error al guardar el cliente", e);
        }
    }

    @Override
    public List<ClienteEntidad> obtenerClientes() throws PersistenciaExceptions {
        List<ClienteEntidad> listaClientes = new ArrayList<>();
        String consulta = "SELECT * FROM Clientes WHERE estaEliminado = 0";

        try (Connection connection = conexionBD.crearConexion();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(consulta)) {

            while (rs.next()) {
                ClienteEntidad cliente = new ClienteEntidad();
                cliente.setIdClientes(rs.getInt("idClientes"));
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setApellidoP(rs.getString("ApellidoP"));
                cliente.setApellidoM(rs.getString("ApellidoM"));
                cliente.setEstaEliminado(rs.getBoolean("estaEliminado"));
                cliente.setFechaHoraRegistro(rs.getTimestamp("fechaHoraRegistro"));
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new PersistenciaExceptions("Error al obtener los clientes", e);
        }
        return listaClientes;
    }

    @Override
    public void Editar(ClienteEntidad cliente) throws PersistenciaExceptions {
        String updateCliente = """
                           UPDATE Clientes
                           SET Nombre = ?,
                               ApellidoP = ?,
                               ApellidoM = ?,
                               estaEliminado = ?
                           WHERE idClientes = ?
                           """;

        try (Connection connection = conexionBD.crearConexion();
                PreparedStatement preparedStatement = connection.prepareStatement(updateCliente)) {

            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getApellidoP());
            preparedStatement.setString(3, cliente.getApellidoM());
            preparedStatement.setBoolean(4, cliente.isEstaEliminado());
            preparedStatement.setInt(5, cliente.getIdClientes());

            int filasAfectadas = preparedStatement.executeUpdate();
            System.out.println("Filas afectadas: " + filasAfectadas);

        } catch (SQLException e) {
            throw new PersistenciaExceptions("Error al editar el cliente", e);
        }
    }

    @Override
    public void eliminar(int idCliente) throws PersistenciaExceptions {

        String sql = "DELETE FROM Clientes WHERE idClientes = ?";
        try (Connection conn = conexionBD.crearConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, idCliente);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaExceptions("Error al eliminar cliente", e);
        }
    }
   
@Override
public List<ClienteEntidad> buscarClientesPorFiltro(ClienteFiltroTablaDTO filtro) throws PersistenciaExceptions {
    List<ClienteEntidad> listaClientes = new ArrayList<>();
    String consulta = "SELECT * FROM Clientes WHERE estaEliminado = 0 AND Nombre LIKE ? LIMIT ? OFFSET ?";

    try (Connection connection = conexionBD.crearConexion();
         PreparedStatement ps = connection.prepareStatement(consulta)) {

        ps.setString(1, "%" + filtro.getTextoBusqueda() + "%"); // Filtro de búsqueda por nombre
        ps.setInt(2, filtro.getLimite());  // Límite de resultados
        ps.setInt(3, filtro.getPagina() * filtro.getLimite()); // Offset para la paginación

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ClienteEntidad cliente = new ClienteEntidad();
                cliente.setIdClientes(rs.getInt("idClientes"));
                cliente.setNombre(rs.getString("Nombre"));
                cliente.setApellidoP(rs.getString("ApellidoP"));
                cliente.setApellidoM(rs.getString("ApellidoM"));
                cliente.setEstaEliminado(rs.getBoolean("estaEliminado"));
                cliente.setFechaHoraRegistro(rs.getTimestamp("fechaHoraRegistro"));
                listaClientes.add(cliente);
            }
        }
    } catch (SQLException e) {
        throw new PersistenciaExceptions("Error al buscar clientes", e);
    }

    return listaClientes;
}

}
