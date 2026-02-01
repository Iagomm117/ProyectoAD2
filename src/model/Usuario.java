package model;

/**
 *
 * @author alumno
 */
public class Usuario {
    private int ID;
    private String nombre;
    public Usuario(String nombre, String contraseña,String artistaFavorito) {
        
        this.nombre= nombre;
        this.contrasenha = contraseña;
        this.artistaFavorito = artistaFavorito;
    }

    public Usuario() {
    }
    
    
    
    private String contrasenha;
    private String artistaFavorito;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenha() {
        return contrasenha;
    }

    public void setContrasenha(String contrasenha) {
        this.contrasenha = contrasenha;
    }

    public String getArtistaFavorito() {
        return artistaFavorito;
    }

    public void setArtistaFavorito(String artistaFavorito) {
        this.artistaFavorito = artistaFavorito;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
}
