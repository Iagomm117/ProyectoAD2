package controller;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Artista;
import model.RenderTabla;
import proyecto2_ad.OperacionsDB;
import view.AlbumJDialog;
import view.MainJFrame;

/**
 *
 * @author iagom
 */
public final class MainJFrameController {

    private MainJFrame view;
    private boolean like = false;
    private String artista;


    public MainJFrameController(MainJFrame view,String artista) throws SQLException {
        this.view = view;
        this.artista = artista;
        this.view.getSearchButtonListener(this.addSearchButtonActionListener());
        this.view.addArtistaTableListener(this.getSelectedPosition());
        try {
            colocarImagen();
        } catch (IOException ex) {
            System.getLogger(MainJFrameController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private MouseListener getSelectedPosition() {
        MouseListener ml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (view.getArtistaTable().isColumnSelected(3)) {
                    String valor = view.getPosition();
                    String artista = view.getArtistaTable().getValueAt(view.getArtistaTable().getSelectedRow(), 1).toString();
                    try {
                        AlbumJDialog ad = new AlbumJDialog(view, true);
                        CancionesAlbumController cc = new CancionesAlbumController(ad, valor, artista);
                        ad.setVisible(true);
                    } catch (SQLException ex) {
                        System.getLogger(MainJFrame.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    } catch (IOException ex) {
                        System.getLogger(MainJFrame.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                } else if (view.getArtistaTable().isColumnSelected(1)) {
                    String valor = view.getPosition();
                    try {
                        AlbumJDialog ad = new AlbumJDialog(view, true);
                        CancionesArtistaController cc = new CancionesArtistaController(ad, valor);
                        ad.setVisible(true);
                    } catch (SQLException ex) {
                        System.getLogger(MainJFrame.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    } catch (IOException ex) {
                        System.getLogger(MainJFrame.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                } else if (view.getArtistaTable().isColumnSelected(4)) {
                    if (like == false) {
                        try {
                            File fLike = new File("res/Like.jpg");
                            Image iLike = ImageIO.read(fLike);
                            Image iLikeR = iLike.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
                            like = true;
                            view.getArtistaTable().setValueAt(new JButton(new ImageIcon(iLikeR)), view.getArtistaTable().getSelectedRow(), 4);

                        } catch (IOException ex) {
                            System.getLogger(MainJFrameController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                        }
                    } else {
                        try {
                            File fLike = new File("res/unLike.jpg");
                            Image iLike = ImageIO.read(fLike);
                            like = false;
                            Image iLikeR = iLike.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
                            view.getArtistaTable().setValueAt(new JButton(new ImageIcon(iLikeR)), view.getArtistaTable().getSelectedRow(), 4);
                        } catch (IOException ex) {
                            System.getLogger(MainJFrameController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                        }
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

    private ActionListener addSearchButtonActionListener() {
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String parametro = view.getSearchTextField();
                    colocarDatos(parametro);
                } catch (SQLException | IOException ex) {
                    System.getLogger(MainJFrameController.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
            }
        };
        return al;
    }

    public void colocarImagen() throws SQLException, MalformedURLException, IOException {
        view.getArtistaTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        view.getArtistaTable().setDefaultRenderer(Object.class, new RenderTabla());
        List<Artista> artistas = OperacionsDB.conseguirArtista();
        DefaultTableModel model = (DefaultTableModel) view.getArtistaTable().getModel();
        model.setNumRows(0);
        File like = null;
        for (int i = 0; i < artistas.size(); i++) {
            Artista artista = artistas.get(i);
            File imagenArtista = new File(artista.getImagen());
            File imagenAlbum = new File(artista.getAlbumes().getImagen());
            if(artista.getNombre().trim().equals(this.artista.trim())){
                like = new File("res/Like.jpg");
            }
            else{
                like = new File("res/unLike.jpg");
            }
            Image image = ImageIO.read(imagenArtista);
            Image image2 = ImageIO.read(imagenAlbum);
            Image image3 = ImageIO.read(like);
            int ancho = 75;
            int altura = 75;
            Image imagenRedimensionada = image.getScaledInstance(altura, ancho, Image.SCALE_SMOOTH);
            Image imagenRedimensionada2 = image2.getScaledInstance(altura, ancho, Image.SCALE_SMOOTH);
            Image imagenRedimensionada3 = image3.getScaledInstance(altura, ancho, Image.SCALE_SMOOTH);
            model.addRow(new Object[]{new JLabel(new ImageIcon(imagenRedimensionada)), artista.getNombre(), new JLabel(new ImageIcon(imagenRedimensionada2)), artista.getAlbumes().getNombre(), new JButton(new ImageIcon(imagenRedimensionada3))});

        }
        view.getArtistaTable().getColumnModel().getColumn(0).setPreferredWidth(75);
        view.getArtistaTable().getColumnModel().getColumn(1).setPreferredWidth(150);
        view.getArtistaTable().getColumnModel().getColumn(2).setPreferredWidth(75);
        view.getArtistaTable().getColumnModel().getColumn(3).setPreferredWidth(250);
        view.getArtistaTable().getColumnModel().getColumn(4).setPreferredWidth(75);
        view.getArtistaTable().setModel(model);
    }

    public void colocarDatos(String parametro) throws SQLException, MalformedURLException, IOException {
        view.getArtistaTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        view.getArtistaTable().setDefaultRenderer(Object.class, new RenderTabla());
        List<Artista> artistas = OperacionsDB.conseguirArtistas(parametro);
        DefaultTableModel model = (DefaultTableModel) view.getArtistaTable().getModel();
        model.setNumRows(0);
        File like = null;
        for (int i = 0; i < artistas.size(); i++) {
            Artista artista = artistas.get(i);

            if(artista.getNombre().trim().equals(this.artista.trim())){
                like = new File("res/Like.jpg");
            }
            else{
                like = new File("res/unLike.jpg");
            }
            File imagenArtista = new File(artista.getImagen());
            File imagenAlbum = new File(artista.getAlbumes().getImagen());
            Image image = ImageIO.read(imagenArtista);
            Image image2 = ImageIO.read(imagenAlbum);
            Image image3 = ImageIO.read(like);
            int ancho = 75;
            int altura = 75;
            Image imagenRedimensionada = image.getScaledInstance(altura, ancho, Image.SCALE_SMOOTH);
            Image imagenRedimensionada2 = image2.getScaledInstance(altura, ancho, Image.SCALE_SMOOTH);
            Image imagenRedimensionada3 = image3.getScaledInstance(altura, ancho, Image.SCALE_SMOOTH);
            model.addRow(new Object[]{new JLabel(new ImageIcon(imagenRedimensionada)), artista.getNombre(), new JLabel(new ImageIcon(imagenRedimensionada2)), artista.getAlbumes().getNombre(), new JButton(new ImageIcon(imagenRedimensionada3))});

        }
        view.getArtistaTable().getColumnModel().getColumn(0).setPreferredWidth(75);
        view.getArtistaTable().getColumnModel().getColumn(1).setPreferredWidth(150);
        view.getArtistaTable().getColumnModel().getColumn(2).setPreferredWidth(75);
        view.getArtistaTable().getColumnModel().getColumn(3).setPreferredWidth(250);
        view.getArtistaTable().getColumnModel().getColumn(4).setPreferredWidth(75);
        view.getArtistaTable().setModel(model);
    }

}
