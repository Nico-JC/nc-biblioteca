package Controllers.Tables;


import Controllers.LoanController;

import javax.swing.*;

public class HistoryTable extends Table {

    private JTable table;

    public HistoryTable(JTable table) {
        this.table = table;
    }

    @Override
    public void generarTabla() {
        String[] columnNames = {"ID", "Cliente", "Libro", "Fecha", "Estado"};
        LoanController lc = new LoanController();
        Object[][] history = lc.getLoanHistory(1);
        if (history == null) {
            history = new Object[0][columnNames.length];
        }
        tablaConfig(table, history, columnNames);
    }
}