package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import proyecto2_ad.OperacionsDB;
import view.NuevaCancionJDialog;

/**
 *
 * @author iagom
 */
public class NuevaCancionController {

    private NuevaCancionJDialog view;

    public NuevaCancionController(NuevaCancionJDialog view, String artista) {
        this.view = view;
        this.view.addSaveButtonActionListener(this.engadirCancion(artista));
        engadirCancion(artista);
    }

    private ActionListener engadirCancion(String artista) {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nombre = view.getTextCancionTextField();
                    String album = view.getTextAlbumTextField();
                    int duracion = view.getIntDuracionSpinner();
                    OperacionsDB.engadirCancion(nombre, artista, album, duracion);
                } catch (SQLException ex) {
                    System.getLogger(NuevaCancionController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
        };
        return al;
    }
    
    
}
