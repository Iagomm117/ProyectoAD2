package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import model.Cancion;
import proyecto2_ad.OperacionsDB;
import view.DatosCancionJDialog;

/**
 *
 * @author iagom
 */
public class DatosCancionesController {
    private DatosCancionJDialog view;

    public DatosCancionesController(DatosCancionJDialog view,String cancion, boolean status) {
        try {
            this.view = view;
            initControls(cancion);
            this.view.setDeleteButtonStatus(status);
            this.view.addDeleteButtonActionListener(this.setDeleteButtonActionListener());
        } catch (SQLException ex) {
            System.getLogger(DatosCancionesController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IOException ex) {
            System.getLogger(DatosCancionesController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    private void initControls(String cancion) throws SQLException, IOException{
        Cancion song = OperacionsDB.conseguirCancion(cancion);
        view.setIconImagenLabel(song.getImagen());
        String artistas = "";
        for (int i = 0; i < song.getArtistas().size(); i++) {
            artistas += song.getArtistas().get(i).getNombre();
            if(i != song.getArtistas().size() -1 ){
                artistas+= ",";
            }     
        }
        
        view.setTextArtistaLabel(artistas);
        view.setTextCancionLabel(song.getNombre());
        view.setTextVariosLabel(String.valueOf(song.getDuracion()) + "s");
        view.setTextReproduccionesLabel(song.getReproduccionesTotales());
        
    }
    
    private ActionListener setDeleteButtonActionListener(){
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int opcion = JOptionPane.showConfirmDialog(view, "¿Estas seguro de querer borrar la cancion?");
                if(opcion == JOptionPane.YES_OPTION){
                    String cancion = view.getTextCancionLabel();
                    try {
                        OperacionsDB.borrarCancion(cancion);
                        
                    } catch (SQLException ex) {
                        System.getLogger(DatosCancionesController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                }
                else if(opcion == JOptionPane.NO_OPTION){
                    
                }
                else{
                    
                }
            }
        };
        return al;
    }
}
