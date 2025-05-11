package Views.BookTable;

import Controllers.Tables.SearchBookTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShowBooks extends JFrame{
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JTextField buscadorField;
    private JButton buscarBTN;
    private JTable dataTable;

    public ShowBooks() {
        generateBookTable("");
        buscarBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = buscadorField.getText().trim();
                generateBookTable(searchTerm);
            }
        });
        setTitle("Buscador");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }

    public void generateBookTable(String searchTerm) {
        SearchBookTable generarLibrosTable = new SearchBookTable(dataTable, searchTerm);
        generarLibrosTable.generarTabla();
    }
}
