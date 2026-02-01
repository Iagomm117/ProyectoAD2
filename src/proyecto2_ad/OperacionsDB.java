package proyecto2_ad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Album;
import model.Artista;
import model.Cancion;
import model.Genero;
import model.Usuario;
import view.AlbumJDialog;

/**
 *
 * @author alumno
 */
public class OperacionsDB {

    private static Connection conexion;
    private AlbumJDialog view;

    public static Connection abrirConexion(String url) throws SQLException {

        conexion = DriverManager.getConnection(url, "root", "abc123.");
        return conexion;

    }

    public static void cerrarConexion(Connection conexion) throws SQLException {

        conexion.close();

    }

    public static List<Artista> conseguirArtistas(String parametro) throws SQLException {
        Connection conexion = OperacionsDB.abrirConexion("jdbc:mysql://localhost:3306/spotify");
        List<Artista> artistas = new ArrayList();
        Artista artista = new Artista();
        String select = "Select artistas.nombre,artistas.imagen,album.nombre,album.imagen From artistas JOIN album on artistas.album = album.nombre WHERE artistas.nombre LIKE '%" + parametro + "%'";
        try (Statement s = conexion.createStatement(); ResultSet rs = s.executeQuery(select)) {
            while (rs.next()) {
                artista.setAlbumes(new Album(rs.getString("album.nombre"), rs.getString("album.imagen")));
                artista.setNombre(rs.getString("artistas.nombre"));
                artista.setImagen(rs.getString("artistas.Imagen"));
                artistas.add(artista);
                artista = new Artista();
            }
        }
        return artistas;
    }

    public static List<Artista> conseguirArtista() throws SQLException {
        Connection conexion = OperacionsDB.abrirConexion("jdbc:mysql://localhost:3306/spotify");
        List<Artista> artistas = new ArrayList();
        Artista artista = new Artista();
        String select = "Select artistas.nombre,artistas.imagen,album.nombre,album.imagen From artistas JOIN album on artistas.album = album.nombre";
        try (Statement s = conexion.createStatement(); ResultSet rs = s.executeQuery(select)) {
            while (rs.next()) {
                artista.setAlbumes(new Album(rs.getString("album.nombre"), rs.getString("album.imagen")));
                artista.setNombre(rs.getString("artistas.nombre"));
                artista.setImagen(rs.getString("artistas.Imagen"));
                artistas.add(artista);
                artista = new Artista();
            }
        }
        return artistas;
    }

    public static List<Cancion> conseguirCancionesAlbum(Connection conexion, String album) throws SQLException {

        List<Cancion> canciones = new ArrayList<>();
        Cancion cancion = new Cancion();

        String select = "SELECT artistas.album,album.imagen,album.canciones from artistas JOIN album on artistas.album = album.nombre WHERE album.nombre = '" + album + "'";

        try (Statement s = conexion.createStatement(); ResultSet rs = s.executeQuery(select)) {
            while (rs.next()) {
                String[] aux = rs.getString("album.canciones").split(",");
                for (int i = 0; i < aux.length; i++) {
                    cancion.setNombre(aux[i].trim());
                    cancion.setAlbum(new Album(rs.getString("artistas.album"), rs.getString("album.imagen")));
                    canciones.add(cancion);
                    cancion = new Cancion();
                }
            }
        }
        return canciones;
    }

    public static List<Cancion> conseguirCancionesArtista(Connection conexion, String artista) throws SQLException {

        List<Cancion> canciones = new ArrayList<>();
        Cancion cancion = new Cancion();

        String select = "SELECT cancion.* from cancion JOIN artistas on SUBSTRING_INDEX(cancion.artistas, ',', 1) = artistas.nombre WHERE cancion.album != artistas.album AND artistas.nombre = '" + artista + "'";

        try (Statement s = conexion.createStatement(); ResultSet rs = s.executeQuery(select)) {
            while (rs.next()) {
                cancion.setAlbum(new Album(rs.getString("cancion.album"), ""));
                cancion.setNombre(rs.getString("cancion.nombre"));
                cancion.setImagen(rs.getString("cancion.imagen"));
                canciones.add(cancion);
                cancion = new Cancion();
            }
        }
        return canciones;
    }

    public static Cancion conseguirCancion(String cancion) throws SQLException {
        Cancion song = new Cancion();
        Artista artista = new Artista();
        List<Artista> artistas = new ArrayList();
        Connection conexion = OperacionsDB.abrirConexion("jdbc:mysql://localhost:3306/spotify");
        String select = "Select cancion.* FROM cancion WHERE cancion.nombre = '" + cancion + "'";
        try (Statement s = conexion.createStatement(); ResultSet rs = s.executeQuery(select)) {
            while (rs.next()) {
                song.setNombre(rs.getString("nombre"));
                song.setAlbum(new Album(rs.getString("album")));
                String[] aux = rs.getString("artistas").split(",");
                for (int i = 0; i < aux.length; i++) {
                    artista.setNombre(aux[i]);
                    artistas.add(artista);
                    artista = new Artista();
                }
                song.setArtistas(artistas);
                song.setGenero(new Genero(rs.getString("genero")));
                song.setReproduccionesTotales(rs.getDouble("reproducciones"));
                song.setImagen(rs.getString("imagen"));
                song.setDuracion(rs.getInt("duracion"));

            }
        }
        return song;
    }

    public static void engadirCancion(String nombre, String artista, String album, int duracion) throws SQLException {
        Connection conexion = OperacionsDB.abrirConexion("jdbc:mysql://localhost:3306/spotify");
        String insert = "INSERT INTO cancion (nombre,artistas,album,genero,reproducciones,duracion,imagen) VALUES(?,?,?,?,?,?,?);";
        try (PreparedStatement ps = conexion.prepareStatement(insert)) {
            ps.execute("SET FOREIGN_KEY_CHECKS = 0;");
            ps.setString(1, nombre);
            ps.setString(2, artista);
            ps.setString(3, album);
            ps.setString(4, "");
            ps.setDouble(5, 0);
            ps.setInt(6, duracion);
            ps.setString(7, "resources/imagenes/default.jpg");
            ps.executeUpdate();
            ps.execute("SET FOREIGN_KEY_CHECKS = 1;");
        }
    }
    
    public static void borrarCancion(String nombre) throws SQLException{
        Connection conexion = OperacionsDB.abrirConexion("jdbc:mysql://localhost:3306/spotify");
        String delete = "DELETE cancion.* FROM cancion WHERE cancion.nombre = ?";
        try (PreparedStatement ps = conexion.prepareStatement(delete)) {
            ps.setString(1, nombre);
            ps.executeUpdate();
        }
    }

    public static Album conseguirAlbum(Connection conexion, String nombreAlbum) throws SQLException {

        Album album = new Album();
        Artista artista = new Artista();
        String select = "SELECT album.cantidad_canciones,artistas.album,album.imagen,album.duracion,album.genero,artistas.nacionalidad,artistas.nombre from artistas JOIN album on artistas.album = album.nombre WHERE album.nombre = '" + nombreAlbum + "'";
        try (Statement s = conexion.createStatement(); ResultSet rs = s.executeQuery(select)) {
            while (rs.next()) {
                artista.setNombre(rs.getString("artistas.nombre"));
                album.setNombre(rs.getString("artistas.album"));
                album.setImagen(rs.getString("album.imagen"));
                album.setDuracion(rs.getInt("album.duracion"));
                album.setCantidadCanciones(rs.getInt("album.cantidad_canciones"));
                album.setGenero(new Genero(rs.getString("album.genero")));

            }
        }
        return album;

    }

    public static Artista conseguirInfoArtista(Connection conexion, String artista) throws SQLException {

        Artista cantante = new Artista();

        String select = "SELECT artistas.nombre,artistas.nacionalidad,artistas.reproducciones,artistas.genero,artistas.imagen,cancion.* from cancion JOIN artistas on SUBSTRING_INDEX(cancion.artistas, ',', 1) = artistas.nombre WHERE cancion.album != artistas.album AND artistas.nombre = '" + artista + "'";

        try (Statement s = conexion.createStatement(); ResultSet rs = s.executeQuery(select)) {
            while (rs.next()) {
                cantante.setImagen(rs.getString("artistas.imagen"));
                cantante.setGenero(new Genero(rs.getString("artistas.genero")));
                cantante.setNacionalidad(rs.getString("artistas.nacionalidad"));
                cantante.setReproduccionesMensuales(rs.getInt("artistas.reproducciones"));
                cantante.setNombre(rs.getString("artistas.nombre"));
            }
        }
        return cantante;
    }

    public static List<Usuario> conseguirUsuario(Connection conexion) throws SQLException {

        List<Usuario> listaUsuarios = new ArrayList<>();

        Usuario usuario = new Usuario();

        String select = "SELECT usuario.nombre,usuario.contraseña,usuario.masReproducido FROM usuario ";

        try (Statement s = conexion.createStatement(); ResultSet rs = s.executeQuery(select)) {
            while (rs.next()) {

                usuario.setNombre(rs.getString("usuario.nombre"));
                usuario.setContrasenha(rs.getString("usuario.contraseña"));
                usuario.setArtistaFavorito(rs.getString("usuario.masReproducido"));
                listaUsuarios.add(usuario);
                usuario = new Usuario();

            }

        }
        return listaUsuarios;
    }

    public static Usuario crearUsuario(Connection conexion, Usuario usuario) throws SQLException {

        String select = "INSERT INTO usuario(Nombre,Contraseña,masReproducido) VALUES(?,?,?) ";

        try (PreparedStatement s = conexion.prepareStatement(select)) {
            s.setString(1, usuario.getNombre());
            s.setString(2, usuario.getContrasenha());
            s.setString(3, usuario.getArtistaFavorito());
            usuario = new Usuario();

            int rowsAffected = s.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Usuario creado correctamente");
            } else {

                System.out.println("Error al crear el usuario");
            }
        }
        return usuario;
    }

    public static boolean usuarioExiste(Connection conexion, String nombreUsuario) throws SQLException {
        String query = "SELECT COUNT(*) FROM usuario WHERE Nombre = ?";

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setString(1, nombreUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

}
