package model;

import java.util.List;

/**
 *
 * @author alumno
 */
public class Cancion {

    private int id;
    private double reproduccionesTotales;
    private String nombre;
    private List<Artista> artistas;
    private int duracion;
    private String imagen;
    private Album album;
    private Genero genero;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getReproduccionesTotales() {
        return reproduccionesTotales;
    }

    public void setReproduccionesTotales(double reproduccionesTotales) {
        this.reproduccionesTotales = reproduccionesTotales;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Artista> getArtistas() {
        return artistas;
    }

    public void setArtistas(List<Artista> artistas) {
        this.artistas = artistas;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
            
            
            
            
            
}
