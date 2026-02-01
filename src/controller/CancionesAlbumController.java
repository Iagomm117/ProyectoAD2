package controller;

import java.awt.Image;
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
import model.Album;
import model.Artista;
import model.Cancion;
import model.RenderTabla;
import proyecto2_ad.OperacionsDB;
import view.AlbumJDialog;
import view.DatosCancionJDialog;

/**
 *
 * @author alumno
 */
public class CancionesAlbumController {

    private AlbumJDialog ad;
    private Player mp3Player;
    private String artista;
    private Thread playbackThread;
    private boolean isPlaying = false;

    public CancionesAlbumController(AlbumJDialog ad, String parametro, String artista) {
        this.ad = ad;
        this.artista = artista;
        this.ad.setStatusAddButton(false);
        this.ad.addCancionesTableMouseListener(this.tableMouseListener(parametro));
        try {
            colocarImagenAlbum(parametro);
            colocarLabels(parametro);
        } catch (SQLException | IOException ex) {
            System.getLogger(CancionesAlbumController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private void colocarImagenAlbum(String parametro) throws SQLException, MalformedURLException, IOException {
        File imagenP = new File("res/play.jpg");
        Image play = ImageIO.read(imagenP);
        Image playR = play.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
        ad.getCancionesTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        Connection conexion = OperacionsDB.abrirConexion("jdbc:mysql://localhost:3306/spotify");
        ad.getCancionesTable().setDefaultRenderer(Object.class, new RenderTabla());
        List<Artista> artistas = OperacionsDB.conseguirArtista();
        List<Cancion> canciones = OperacionsDB.conseguirCancionesAlbum(conexion, parametro);
        DefaultTableModel model = (DefaultTableModel) ad.getCancionesTable().getModel();

        for (int i = 0; i < canciones.size(); i++) {
            Cancion cancion = canciones.get(i);
            File imagenAlbum = new File(cancion.getAlbum().getImagen());
            Image image = ImageIO.read(imagenAlbum);
            int ancho = 75;
            int altura = 75;
            Image imagenRedimensionada = image.getScaledInstance(altura, ancho, Image.SCALE_SMOOTH);
            model.addRow(new Object[]{new JLabel(new ImageIcon(imagenRedimensionada)), cancion.getAlbum().getNombre(), cancion.getNombre(), new JButton(new ImageIcon(playR))});

        }

        ad.getCancionesTable().getColumnModel().getColumn(0).setPreferredWidth(75);
        ad.getCancionesTable().getColumnModel().getColumn(1).setPreferredWidth(150);
        ad.getCancionesTable().getColumnModel().getColumn(2).setPreferredWidth(236);
        ad.getCancionesTable().setModel(model);

    }

    private void colocarLabels(String parametro) throws SQLException, IOException {

        Connection conexion = OperacionsDB.abrirConexion("jdbc:mysql://localhost:3306/spotify");

        Album album = OperacionsDB.conseguirAlbum(conexion, parametro);

        int horas = (album.getDuracion() / 3600);
        int min = ((album.getDuracion() - horas * 3600) / 60);

        File imagenAlbum = new File(album.getImagen());
        Image image = ImageIO.read(imagenAlbum);

        int ancho = 75;
        int altura = 75;
        Image imagenRedimensionada = image.getScaledInstance(altura, ancho, Image.SCALE_SMOOTH);

        ad.getImagenLabel().setIcon(new ImageIcon(imagenRedimensionada));

        ad.getAlbumLabel().setText("Álbum - " + album.getGenero());

        ad.getNombreLabel().setText(album.getNombre() + " ");

        if (horas == 0) {

            ad.getPupurriLabel().setText(
                    album.getCantidadCanciones() + " canciones, "
                    + min + " min"
            );
        } else {
            ad.getPupurriLabel().setText(
                    album.getCantidadCanciones() + " canciones, " + horas + " h "
                    + min + " min"
            );

        }
    }

    private MouseListener tableMouseListener(String album) {
        MouseListener ml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ad.getCancionesTable().getSelectedColumn() == 3) {
                    int selectedRow = ad.getCancionesTable().getSelectedRow();
                    try {
                        String cancion = ad.getCancionesTable().getValueAt(ad.getCancionesTable().getSelectedRow(), 2).toString();
                        File imagenS = new File("res/stop.jpg");
                        Image stop = ImageIO.read(imagenS);
                        Image stopR = stop.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
                        File imagenP = new File("res/play.jpg");
                        Image play = ImageIO.read(imagenP);
                        Image playR = play.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
                        if (isPlaying) {
                            ad.setReproducirLabel("");
                            ad.getCancionesTable().setValueAt(new JButton(new ImageIcon(playR)), selectedRow, 3);
                            stopPlayback();
                            isPlaying = false;
                        } else {
                            String filePath = "resources/canciones/" + artista + "/" + album + "/" + cancion + ".mp3";
                            ad.setReproducirLabel("Se esta reproduciendo la cancion " + cancion);
                            ad.getCancionesTable().setValueAt(new JButton(new ImageIcon(stopR)), selectedRow, 3);
                            startPlayback(filePath, selectedRow);
                            isPlaying = true;
                        }
                    } catch (IOException ex) {
                        System.getLogger(CancionesAlbumController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                }
                if (ad.getCancionesTable().getSelectedColumn() == 2) {
                    DatosCancionJDialog dcd = new DatosCancionJDialog(ad, true);
                    DatosCancionesController dcc = new DatosCancionesController(dcd, ad.getCancionesTable().getValueAt(ad.getCancionesTable().getSelectedRow(), 2).toString(),false);
                    dcd.setVisible(true);
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
                                ad.getCancionesTable().setValueAt(new JButton(new ImageIcon(playR)), row, 3);
                                isPlaying = false;
                                ad.setReproducirLabel("");
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

        isPlaying = true;
        if (mp3Player != null) {
            mp3Player.close();
        }
        if (playbackThread != null && playbackThread.isAlive()) {
            playbackThread.interrupt();
        }

    }

}
