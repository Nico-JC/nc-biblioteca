package Views.LoansTable;

import Controllers.LoanController;

import javax.swing.*;

public class Registro extends JFrame{
    private JPanel mainPanel;
    private JLabel userLabel;
    private JLabel libroLabel;
    private JLabel dateLabel;

    public Registro(){
        LoanController lc = new LoanController();
        Object[] data = lc.generateReport();

        String user = data[0] != null ? (String) data[0] : "No hay registro";
        String book = data[1] != null ? (String) data[1] : "No hay registro";
//        String date = data[2] != null ? formatDate((Date) data[2]) : "No hay registro";

        userLabel.setText("Usuario con más Prestamos solicitados: " + user);
        libroLabel.setText("Libro más solicitado: " + book);
//        dateLabel.setText("Día con maás prestamos hechos: " + date);

        setTitle("Historial total");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }

//    private String formatDate(Date date) {
//        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//        return df.format(date);
//    }

}
