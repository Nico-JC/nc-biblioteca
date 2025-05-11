/*Reveer modelos https://es.stackoverflow.com/questions/23295/asignar-un-defaulttablemodel-a-un-jtable-mvc, https://stackoverflow.com/questions/11686535/how-to-change-jtable-header-height
* https://www.geeksforgeeks.org/java-swing-jtable/ */
package Controllers.Tables;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public abstract class Table {
    public abstract void generarTabla();

    protected void tablaConfig(JTable table, Object[][] data, String[] columnNames) {
        DefaultTableModel dtm = new DefaultTableModel(data, columnNames);
        table.setModel(dtm);
        table.getTableHeader().setReorderingAllowed(false);
        table.revalidate();
        table.repaint();
    }
}
