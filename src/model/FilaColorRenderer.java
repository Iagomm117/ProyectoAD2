package model;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author alumno
 */
public class FilaColorRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        // Obtener el componente por defecto
        Component c = super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
        
        c.setBackground(Color.BLACK);
        c.setForeground(Color.WHITE);
        
        return c;
    }
}
