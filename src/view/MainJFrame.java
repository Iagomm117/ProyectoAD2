package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import model.Usuario;
/**
 *
 * @author alumno
 */
public class MainJFrame extends javax.swing.JDialog {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainJFrame.class.getName());
    private Usuario usuario;
    /**
     * Creates new form inicioSesionJDialog
     */
    public MainJFrame(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

    }

    public MainJFrame(Usuario usuario) throws SQLException, IOException {
    this.usuario = usuario;
    initComponents();
    this.getContentPane().setBackground(Color.black);
    this.ArtistaTable.getTableHeader().setBackground(Color.black);
    this.ArtistaTable.getTableHeader().setForeground(Color.white);
    this.ArtistaTable.getTableHeader().setFont(new Font("Arial Black", Font.BOLD, 14));

    usuarioLabel.setText(usuario.getNombre());
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ArtistaTable = new javax.swing.JTable();
        searchTextField = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        usuarioLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        ArtistaTable.setBackground(new java.awt.Color(153, 255, 153));
        ArtistaTable.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N
        ArtistaTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                " Imagen", "Artista", "Imagen", "Album", "Like"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ArtistaTable.setRowHeight(75);
        ArtistaTable.setShowGrid(true);
        ArtistaTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ArtistaTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(ArtistaTable);

        searchTextField.setText("");
        searchTextField.addActionListener(this::searchTextFieldActionPerformed);
        searchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                searchTextFieldKeyTyped(evt);
            }
        });

        searchButton.setText("Buscar");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logo_2.png"))); // NOI18N

        usuarioLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        usuarioLabel.setForeground(new java.awt.Color(255, 255, 255));
        usuarioLabel.setText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(searchButton))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 644, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(107, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(usuarioLabel)
                        .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(searchButton))
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(usuarioLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ArtistaTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ArtistaTableMouseClicked

    }//GEN-LAST:event_ArtistaTableMouseClicked

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextFieldActionPerformed

    private void searchTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextFieldKeyTyped

        String filtrado = this.searchTextField.getText();

    }//GEN-LAST:event_searchTextFieldKeyTyped
    
    public String getPosition() {

        String valor = "";
        if (this.ArtistaTable.getSelectedColumn() == 1 || this.ArtistaTable.getSelectedColumn() == 3) {

            valor = this.ArtistaTable.getValueAt(this.ArtistaTable.getSelectedRow(), this.ArtistaTable.getSelectedColumn()).toString();

        }
        return valor;
    }
    
    public void addArtistaTableListener(MouseListener ml) {

        this.ArtistaTable.addMouseListener(ml);

    }

    public void getSearchButtonListener(ActionListener al) {

        this.searchButton.addActionListener(al);

    }

    public JTable getArtistaTable() {
        return ArtistaTable;
    }

    public void setArtistaTable(JTable ArtistaTable) {
        this.ArtistaTable = ArtistaTable;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(JButton searchButton) {
        this.searchButton = searchButton;
    }

    public String getSearchTextField() {
        return searchTextField.getText();
    }

    public void setSearchTextField(JTextField searchTextField) {
        this.searchTextField = searchTextField;
    }

    public JLabel getUsuarioLabel() {
        return usuarioLabel;
    }

    public void setUsuarioLabel(JLabel usuarioLabel) {
        this.usuarioLabel = usuarioLabel;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ArtistaTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JLabel usuarioLabel;
    // End of variables declaration//GEN-END:variables
}
