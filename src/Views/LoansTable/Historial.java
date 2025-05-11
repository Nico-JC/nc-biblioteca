package Views.LoansTable;

import Controllers.Tables.HistoryTable;

import javax.swing.*;

public class Historial extends JFrame{
    private JPanel mainPanel;
    private JLabel tituloLabel;
    private JTable table1;

    public Historial(){
        HistoryTable generateHistoryTable = new HistoryTable(table1);
        generateHistoryTable.generarTabla();
        setTitle("Historial de Prestamos");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }
}
