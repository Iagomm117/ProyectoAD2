package model;

/**
 *
 * @author alumno
 */
public class Genero {

    private int id;
    private String nombre;

    
    public Genero(String nombre){
    
    this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Genero : "+ nombre ;
    }
    
    
}


