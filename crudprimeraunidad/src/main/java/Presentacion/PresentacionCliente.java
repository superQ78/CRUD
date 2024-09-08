/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Presentacion;

import DTO.ClienteDTO;
import DTO.ClienteFiltroTablaDTO;
import DTO.ClienteTablaDTO;
import Negocio.ClienteNegocio;
import Negocio.IClienteNegocio;
import Negocio.NegocioException;
import Persistencia.ClienteDAO;
import Persistencia.ConexionBD;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import utilerias.JButtonCellEditor;
import utilerias.JButtonRenderer;

/**
 *
 * @author cesar
 */
public class PresentacionCliente extends javax.swing.JFrame {
 private IClienteNegocio clienteNegocio;
    private int pagina = 1;
    private final int LIMITE = 20;
    /**
     * Creates new form PresentacionClienye
     */
    public PresentacionCliente(IClienteNegocio clienteNegocio) {
         initComponents();

        this.clienteNegocio = clienteNegocio;

        this.metodosIniciales();
    }

    PresentacionCliente() {
    }
    private void metodosIniciales(){
       this.cargarConfiguracionInicialPantalla();
        this.cargarConfiguracionInicialTablaClientes();
        this.cargarTablaClientes();
    }
    private void cargarConfiguracionInicialPantalla() {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
     private void cargarConfiguracionInicialTablaClientes() {
        ActionListener onEditarClickListener = new ActionListener() {
            final int columnaId = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                //Metodo para editar
                editar();
            }
        };
        int indiceColumnaEditar = 5;
        TableColumnModel modeloColumnas = this.tblClientes.getColumnModel();
        modeloColumnas.getColumn(indiceColumnaEditar).setCellRenderer(new JButtonRenderer("Editar"));
        modeloColumnas.getColumn(indiceColumnaEditar)
                .setCellEditor(new JButtonCellEditor("Editar", onEditarClickListener));

        ActionListener onEliminarClickListener = new ActionListener() {
            final int columnaId = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                //Metodo para eliminar
                eliminar();
            }
        };
        int indiceColumnaEliminar = 6;
        modeloColumnas = this.tblClientes.getColumnModel();
        modeloColumnas.getColumn(indiceColumnaEliminar)
                .setCellRenderer(new JButtonRenderer("Eliminar"));
        modeloColumnas.getColumn(indiceColumnaEliminar)
                .setCellEditor(new JButtonCellEditor("Eliminar", onEliminarClickListener));
    }
     
     private int getIdSeleccionadoTablaClientes() {
        int indiceFilaSeleccionada = this.tblClientes.getSelectedRow();
        if (indiceFilaSeleccionada != -1) {
            DefaultTableModel modelo = (DefaultTableModel) this.tblClientes.getModel();
            int indiceColumnaId = 0;
            int idSocioSeleccionado = (int) modelo.getValueAt(indiceFilaSeleccionada,
                    indiceColumnaId);
            return idSocioSeleccionado;
        } else {
            return 0;
        }
    }
      private void editar() {
        int id = this.getIdSeleccionadoTablaClientes();
        System.out.println("El id para editar es " + id);
    }

    private void eliminar() {
        int id = this.getIdSeleccionadoTablaClientes();
        System.out.println("El id para eliminar es " + id);
    }

    private void BorrarRegistrosTablaClientes() {
        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblClientes.getModel();
        if (modeloTabla.getRowCount() > 0) {
            for (int row = modeloTabla.getRowCount() - 1; row > -1; row--) {
                modeloTabla.removeRow(row);
            }
        }
    }
    private void AgregarRegistrosTablaCliente(List<ClienteTablaDTO> clientesLista) {
        if (clientesLista == null) {
            return;
        }

        DefaultTableModel modeloTabla = (DefaultTableModel) this.tblClientes.getModel();
        clientesLista.forEach(row -> {
            Object[] fila = new Object[5];
            fila[0] = row.getIdClientes();
            fila[1] = row.getNombres();
            fila[2] = row.getApellidoP();
            fila[3] = row.getApellidoM();
            fila[4] = row.getApellidoM();

            modeloTabla.addRow(fila);
        });
    }

    private void cargarClientes() {
    try {
        // Obtener los clientes desde la capa de negocio
        IClienteNegocio clienteNegocio = new ClienteNegocio(new ClienteDAO(new ConexionBD()));
        List<ClienteDTO> listaClientes = clienteNegocio.obtenerClientes();

        // Crear un modelo de tabla para mostrar los datos
        DefaultTableModel modeloTabla = (DefaultTableModel) tblClientes.getModel();
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de cargar los datos

        // Llenar la tabla con los clientes
        for (ClienteDTO cliente : listaClientes) {
            Object[] fila = new Object[]{
                cliente.getIdClientes(),
                cliente.getNombre(),
                cliente.getApellidoP(),
                cliente.getApellidoM(),
                cliente.isEstaEliminado() ? "Eliminado" : "Activo"
            };
            modeloTabla.addRow(fila);
        }

    } catch (NegocioException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar los clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
     private void cargarTablaClientes() {
        try {
            ClienteFiltroTablaDTO filtro = this.obtenerFiltrosTabla();
            List<ClienteTablaDTO> clientesLista = this.clienteNegocio.buscarClientesFiltro(filtro);
            this.BorrarRegistrosTablaClientes();
            this.AgregarRegistrosTablaCliente(clientesLista);
        } catch (NegocioException ex) {
            this.BorrarRegistrosTablaClientes();
            this.pagina--;
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Información", JOptionPane.ERROR_MESSAGE);
        }
    }
       private ClienteFiltroTablaDTO obtenerFiltrosTabla() {
        return new ClienteFiltroTablaDTO(this.LIMITE, this.pagina, txtFiltro.getText());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lblTitulo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtFiltro = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        btnPaginaAnterior = new javax.swing.JButton();
        btnPaginaSiguiente = new javax.swing.JButton();
        lblNumeroPagina = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTitulo.setText("Administracion de Clientes");
        jPanel1.add(lblTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel2.setText("Filtro de busqueda:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));
        jPanel1.add(txtFiltro, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 500, -1));

        btnBuscar.setBackground(new java.awt.Color(153, 204, 255));
        btnBuscar.setText("BUSCAR");
        jPanel1.add(btnBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 100, -1, -1));

        tblClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "nombres", "paterno", "materno", "estatus", "editar", "eliminar"
            }
        ));
        jScrollPane2.setViewportView(tblClientes);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 660, 130));

        btnPaginaAnterior.setBackground(new java.awt.Color(153, 204, 255));
        btnPaginaAnterior.setText("Anterior");
        jPanel1.add(btnPaginaAnterior, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, -1));

        btnPaginaSiguiente.setBackground(new java.awt.Color(153, 204, 255));
        btnPaginaSiguiente.setText("Siguiente");
        btnPaginaSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaginaSiguienteActionPerformed(evt);
            }
        });
        jPanel1.add(btnPaginaSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 350, -1, -1));

        lblNumeroPagina.setText("jLabel3");
        jPanel1.add(lblNumeroPagina, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 360, -1, -1));

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 721, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
     NuevoRegistro nu= new NuevoRegistro();
     nu.setVisible(true);
     this.dispose();
     
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnPaginaSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaginaSiguienteActionPerformed
        this.pagina++;
        lblNumeroPagina.setText("Página " + this.pagina);
        this.cargarTablaClientes();                              
    }//GEN-LAST:event_btnPaginaSiguienteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PresentacionCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PresentacionCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PresentacionCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PresentacionCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PresentacionCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnPaginaAnterior;
    private javax.swing.JButton btnPaginaSiguiente;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblNumeroPagina;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtFiltro;
    // End of variables declaration//GEN-END:variables
}
