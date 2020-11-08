/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package claseCoche;

import lombok.Data;

/**
 *
 * @author oscartabora
 */
//1).Implemento la anotacion @Data del repositorio Lombok
//Crea constructores, getters, setter, ahorra espacio.
@Data
public class Coche {

    //2).Atributos de clase coche
    private int id;
    private String marca;
    private String modelo;
    private String cilindrada;

    //3).Devuelvo el valor que luego usare para añadir los valores en una List
    @Override
    public String toString() {
        return "\n" + "Id: " + id + "\n Marca: " + marca + "\n Modelo: " + modelo + "\n Cilindrada: " + cilindrada + "\n";
    }

}
