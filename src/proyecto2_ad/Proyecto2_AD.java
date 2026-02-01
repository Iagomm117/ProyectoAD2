package proyecto2_ad;

import controller.MainController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import view.inicioSesionJDialog;

public class Proyecto2_AD {

    private static Connection conexion;

    public static void main(String[] args) throws IOException {
        try {
            abrirBaseDatos();
            inicioSesionJDialog isd = new inicioSesionJDialog();
            MainController mc = new MainController(isd);
            isd.setVisible(true);

        } catch (ClassNotFoundException | SQLException ex) {
            System.getLogger(Proyecto2_AD.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }

    }

    public static void abrirBaseDatos() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        OperacionsDB.abrirConexion("jdbc:mysql://localhost:3306/spotify");
    }

}
