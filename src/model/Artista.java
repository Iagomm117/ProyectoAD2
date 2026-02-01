package model;

/**
 *
 * @author alumno
 */
public class Artista {

    private int id;
    private int reproduccionesMensuales;
    private Cancion canciones;
    private Album albumes;
    private String nombre;
    private Genero genero;
    private String imagen;
    private String nacionalidad;

    public Artista(int id, int reproduccionesMensuales, Cancion canciones, Album albumes, String nombre, Genero genero, String imagen, String nacionalidad) {
        this.id = id;
        this.reproduccionesMensuales = reproduccionesMensuales;
        this.canciones = canciones;
        this.albumes = albumes;
        this.nombre = nombre;
        this.genero = genero;
        this.imagen = imagen;
        this.nacionalidad = nacionalidad;
    }

    public Artista() {
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReproduccionesMensuales() {
        return reproduccionesMensuales;
    }

    public void setReproduccionesMensuales(int reproduccionesMensuales) {
        this.reproduccionesMensuales = reproduccionesMensuales;
    }

    public Cancion getCanciones() {
        return canciones;
    }

    public void setCanciones(Cancion canciones) {
        this.canciones = canciones;
    }

    public Album getAlbumes() {
        return albumes;
    }

    public void setAlbumes(Album albumes) {
        this.albumes = albumes;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }
        
}
