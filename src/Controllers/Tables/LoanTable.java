package Controllers.Tables;

import Controllers.LoanController;

import javax.swing.*;

public class LoanTable extends Table {

    private JTable prestamosTable;

    public LoanTable(JTable prestamosTable) {
        this.prestamosTable = prestamosTable;
    }

    @Override
    public void generarTabla() {
        String[] columnNames = {"ID", "Cliente", "Libro", "Fecha", "Estado"};
        LoanController lc = new LoanController();
        Object[][] history = lc.getLoanHistory(0);
        if (history == null) {
            history = new Object[0][columnNames.length];
        }
        tablaConfig(prestamosTable, history, columnNames);
    }
}
