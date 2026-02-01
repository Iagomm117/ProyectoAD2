package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import model.Usuario;
import proyecto2_ad.OperacionsDB;
import view.MainJFrame;
import view.inicioSesionJDialog;
import view.registroJDialog;

public class MainController {

    private inicioSesionJDialog view;

    public MainController(inicioSesionJDialog view) throws IOException, SQLException {
        this.view = view;
        this.view.addRegistroActionListener(this.getaddRegistroActionListener());
        this.view.addInicioSesionActionListener(this.getaddInicioSesionActionListener());
        

    }

    private ActionListener getaddInicioSesionActionListener() {

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    colocarLabels();

                } catch (SQLException | IOException ex) {
                    System.getLogger(MainController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }

        };
        return al;

    }

    private ActionListener getaddRegistroActionListener() {

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
                registroJDialog registro = new registroJDialog(view, true);
                registroController controller = new registroController(registro);
                registro.setVisible(true);

            }

        };
        return al;

    }

    private void colocarLabels() throws SQLException, IOException {
        Connection conexion = OperacionsDB.abrirConexion("jdbc:mysql://localhost:3306/spotify");
        List<Usuario> listaUsuarios = OperacionsDB.conseguirUsuario(conexion);
        String nombre = view.getNombreInicioSesionTextField().getText().trim();
        String contraseña = view.getContraseñaInicioSesionTextField().getText().trim();
        
        for (Usuario usuario : listaUsuarios) {

            if (nombre.equals(usuario.getNombre()) && contraseña.equals(usuario.getContrasenha())) {
                String artista = usuario.getArtistaFavorito();
                JOptionPane.showMessageDialog(view,"Credenciales correctas");
                MainJFrame main = new MainJFrame(usuario);
                MainJFrameController mc = new MainJFrameController(main,artista);
                main.setVisible(true);
                view.dispose();
                break;
            }

        }
    }

}
