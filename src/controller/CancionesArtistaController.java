package controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import model.Artista;
import model.Cancion;
import model.RenderTabla;
import proyecto2_ad.OperacionsDB;
import view.AlbumJDialog;
import view.DatosCancionJDialog;
import view.NuevaCancionJDialog;

/**
 *
 * @author iagom
 */
public class CancionesArtistaController {

    private AlbumJDialog view;
    private Player mp3Player;
    private Thread playbackThread;
    private boolean isPlaying = false;

    public CancionesArtistaController(AlbumJDialog view, String valor) {
        this.view = view;
        this.view.setStatusAddButton(true);
        this.view.addCancionesTableMouseListener(this.tableMouseListener(valor));
        this.view.addActionListenerAddButton(this.setActionListenerAddButton(valor));
        try {
            colocarImagenAlbum(valor);
            colocarLabels(valor);
        } catch (SQLException | IOException ex) {
            System.getLogger(CancionesArtistaController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private ActionListener setActionListenerAddButton(String artista) {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    NuevaCancionJDialog ncd = new NuevaCancionJDialog(view, true);
                    NuevaCancionController ncc = new NuevaCancionController(ncd, artista);
                    ncd.setVisible(true);
                    colocarImagenAlbum(artista);
                } catch (SQLException ex) {
                    System.getLogger(CancionesArtistaController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                } catch (IOException ex) {
                    System.getLogger(CancionesArtistaController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
        };
        return al;
    }

    private void colocarImagenAlbum(String parametro) throws SQLException, MalformedURLException, IOException {
        File imagenP = new File("res/play.jpg");
        Image play = ImageIO.read(imagenP);
        Image playR = play.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        view.getCancionesTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Connection conexion = OperacionsDB.abrirConexion("jdbc:mysql://localhost:3306/spotify");
        view.getCancionesTable().setDefaultRenderer(Object.class, new RenderTabla());
        List<Cancion> canciones = OperacionsDB.conseguirCancionesArtista(conexion, parametro);
        DefaultTableModel model = (DefaultTableModel) view.getCancionesTable().getModel();
        model.setRowCount(0);
        for (int i = 0; i < canciones.size(); i++) {
            Cancion cancion = canciones.get(i);
            File imagenAlbum = new File(cancion.getImagen());
            Image image = ImageIO.read(imagenAlbum);
            int ancho = 75;
            int altura = 75;
            Image imagenRedimensionada = image.getScaledInstance(altura, ancho, Image.SCALE_SMOOTH);
            model.addRow(new Object[]{new JLabel(new ImageIcon(imagenRedimensionada)), cancion.getAlbum().getNombre(), cancion.getNombre(), new JButton(new ImageIcon(playR))});
        }
        view.getCancionesTable().getColumnModel().getColumn(0).setPreferredWidth(75);
        view.getCancionesTable().getColumnModel().getColumn(1).setPreferredWidth(150);
        view.getCancionesTable().getColumnModel().getColumn(2).setPreferredWidth(236);
        view.getCancionesTable().setModel(model);

    }

    private void colocarLabels(String parametro) throws SQLException, IOException {
        Connection conexion = OperacionsDB.abrirConexion("jdbc:mysql://localhost:3306/spotify");
        Artista artista = OperacionsDB.conseguirInfoArtista(conexion, parametro);
        File imagenAlbum = new File(artista.getImagen());
        Image image = ImageIO.read(imagenAlbum);
        int ancho = 75;
        int altura = 75;
        Image imagenRedimensionada = image.getScaledInstance(altura, ancho, Image.SCALE_SMOOTH);
        view.getImagenLabel().setIcon(new ImageIcon(imagenRedimensionada));
        view.getAlbumLabel().setText("Artista - " + artista.getGenero());
        view.getNombreLabel().setText(artista.getNombre());
        view.getPupurriLabel().setText("Nacionalidad: " + artista.getNacionalidad() + ". Reproducciones:" + artista.getReproduccionesMensuales() + " mensuales");

    }

    private MouseListener tableMouseListener(String artista) {
        MouseListener ml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (view.getCancionesTable().getSelectedColumn() == 3) {
                    int selectedRow = view.getCancionesTable().getSelectedRow();
                    try {
                        String cancion = view.getCancionesTable().getValueAt(selectedRow, 2).toString();
                        File imagenS = new File("res/stop.jpg");
                        Image stop = ImageIO.read(imagenS);
                        Image stopR = stop.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
                        File imagenP = new File("res/play.jpg");
                        Image play = ImageIO.read(imagenP);
                        Image playR = play.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
                        if (isPlaying) {
                            view.setReproducirLabel("");
                            view.getCancionesTable().setValueAt(new JButton(new ImageIcon(playR)), selectedRow, 3);
                            stopPlayback();
                            isPlaying = false;
                        } else {
                            String filePath = "resources/canciones/" + artista + "/" + cancion + ".mp3";
                            view.setReproducirLabel("Se está reproduciendo la canción " + cancion);
                            view.getCancionesTable().setValueAt(new JButton(new ImageIcon(stopR)), selectedRow, 3);
                            startPlayback(filePath, selectedRow);
                            isPlaying = true;
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (view.getCancionesTable().getSelectedColumn() == 2) {
                    try {
                        DatosCancionJDialog dcd = new DatosCancionJDialog(view, true);
                        DatosCancionesController dcc = new DatosCancionesController(dcd, view.getCancionesTable().getValueAt(view.getCancionesTable().getSelectedRow(), 2).toString(),true);
                        dcd.setVisible(true);
                        colocarImagenAlbum(artista);
                    } catch (SQLException ex) {
                        System.getLogger(CancionesArtistaController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    } catch (IOException ex) {
                        System.getLogger(CancionesArtistaController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        };
        return ml;
    }

    private void startPlayback(String filePath, int row) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            BufferedInputStream bis = new BufferedInputStream(fis);
            mp3Player = new Player(bis);
            playbackThread = new Thread(() -> {
                try {
                    mp3Player.play();
                    if (mp3Player.isComplete()) {
                        javax.swing.SwingUtilities.invokeLater(() -> {
                            try {
                                File imagenP = new File("res/play.jpg");
                                Image play = ImageIO.read(imagenP);
                                Image playR = play.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
                                view.getCancionesTable().setValueAt(new JButton(new ImageIcon(playR)), row, 3);
                                isPlaying = false;
                                view.setReproducirLabel("");
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        });
                    }
                } catch (Exception e) {
                    if (!e.getMessage().contains("stream closed")) {
                        e.printStackTrace();
                    }
                }
            });
            playbackThread.start();

        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
        }
    }

    private void stopPlayback() {

        if (mp3Player != null) {
            mp3Player.close();

        }
        if (playbackThread != null && playbackThread.isAlive()) {
            playbackThread.interrupt();
        }
    }

}
