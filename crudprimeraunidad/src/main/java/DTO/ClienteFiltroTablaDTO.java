/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author cesar
 */
public class ClienteFiltroTablaDTO {

    private int limite;
    private int pagina;
    private String textoBusqueda; // Texto para buscar por nombre

    public ClienteFiltroTablaDTO(int limite, int pagina, String textoBusqueda) {
        this.limite = limite;
        this.pagina = pagina;
        this.textoBusqueda = textoBusqueda;
    }

    // Getters y Setters
    public int getLimite() {
        return limite;
    }

    public int getPagina() {
        return pagina;
    }

    public String getTextoBusqueda() {
        return textoBusqueda;
    }
}
