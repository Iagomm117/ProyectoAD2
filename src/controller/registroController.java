package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Usuario;
import proyecto2_ad.OperacionsDB;
import view.inicioSesionJDialog;
import view.registroJDialog;

/**
 *
 * @author alumno
 */
public class registroController {

    private registroJDialog view;
    private inicioSesionJDialog view2;

    public registroController(registroJDialog view) {

        this.view = view;
        this.view.addIniciarSesionRegistroActionListener(this.getIniciarSesionRegistroListener());
        this.view.addSiguienteRegistroButtonActionListener(this.getSiguienteRegistrarseListener());

    }

    private ActionListener getSiguienteRegistrarseListener() {

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    colocarLabels();
                } catch (SQLException | IOException ex) {
                    System.getLogger(registroController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }

            }

        };
        return al;
    }

    private ActionListener getIniciarSesionRegistroListener() {

        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    view.dispose();
                    inicioSesionJDialog isd = new inicioSesionJDialog();
                    MainController main = new MainController(isd);
                    isd.setVisible(true);
                } catch (IOException ex) {
                    System.getLogger(registroController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                } catch (SQLException ex) {
                    System.getLogger(registroController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }

            }

        };
        return al;
    }

    private void colocarLabels() throws SQLException, IOException {
        Connection conexion = OperacionsDB.abrirConexion("jdbc:mysql://localhost:3306/spotify");
        Usuario usuario = new Usuario();
        String nombreRegistrado = view.getNombreRegistroTextField().getText();
        String contraseñaRegistrada = view.getContraseñaRegistroTextField().getText();
        String artistasComboBox = view.getArtistasComboBox().getSelectedItem().toString();
        if (OperacionsDB.usuarioExiste(conexion, nombreRegistrado)) {
            JOptionPane.showMessageDialog(view, "El nombre de usuario ya está registrado. Por favor, elige otro.");
            view.getNombreRegistroTextField().setText("");
            view.getContraseñaRegistroTextField().setText("");
            
            return;
        } else {

            System.out.println(nombreRegistrado);
            System.out.println(contraseñaRegistrada);
            System.out.println(artistasComboBox);
            usuario = new Usuario(nombreRegistrado, contraseñaRegistrada, artistasComboBox);
            OperacionsDB.crearUsuario(conexion, usuario);
            JOptionPane.showMessageDialog(view, "El nombre de usuario se ha registrado correctamente.");
            view.dispose();
            inicioSesionJDialog isd = new inicioSesionJDialog();
            MainController main = new MainController(isd);
            isd.setVisible(true);

        }

    }

}
