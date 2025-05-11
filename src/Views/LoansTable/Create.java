package Views.LoansTable;

import Controllers.BookController;
import Controllers.LoanController;
import Controllers.UserController;
import Models.Book;
import Views.Main.Home;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Create extends JFrame{
    private JComboBox userSelect;
    private JComboBox bookSelect;
    private JButton crearButton;
    private JLabel bookLabel;
    private JLabel userLabel;
    private JLabel smgAlert;
    private JPanel mainPanel;
    private Integer clientSelected;
    private Integer bookSelected;

    public Create(Home home){
        smgAlert.setVisible(false);
        UserController uc = new UserController();
        DefaultComboBoxModel<String> userModel = new DefaultComboBoxModel<>();
        Object[][] users = uc.getUserList();
        String data = "";
        userModel.addElement(data);
        for(int i = 0; i < users.length; i++) {
            data = users[i][0] + " - " + users[i][1];
            userModel.addElement(data);
        }
        userSelect.setModel(userModel);
        BookController bc = new BookController();
        DefaultComboBoxModel<String> bookModel = new DefaultComboBoxModel<>();
        ArrayList<Book> bookList = bc.searchForBook("status",1);
        data = "";
        bookModel.addElement(data);
        for(Book book : bookList) {
            data = book.getId_book() + " - " + book.getTitle();
            bookModel.addElement(data);
        }
        bookSelect.setModel(bookModel);
        bookSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) bookSelect.getSelectedItem();
                if(selectedValue != "") {
                    String[] parts = selectedValue.split(" - ");
                    bookSelected = Integer.parseInt(parts[0]);
                }
                else{
                    bookSelected = null;
                }
            }
        });

        userSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) userSelect.getSelectedItem();
                if(selectedValue != "") {
                    String[] parts = selectedValue.split(" - ");
                    clientSelected = Integer.parseInt(parts[0]);
                }
                else{
                    clientSelected = null;
                }
            }
        });

        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoanController pc = new LoanController();
                if(bookSelected != null && clientSelected != null){
                    if(pc.crateLoan(clientSelected,bookSelected)){
                        setVisible(false);
                        home.update();
                    }
                    else{
                        smgAlert.setText("Error al crear el prestamo");
                        smgAlert.setVisible(true);
                        pack();
                    }
                }
                else{
                    smgAlert.setText("Seleccione los valores...");
                    smgAlert.setVisible(true);
                    pack();
                }
            }
        });

        setTitle("Crear prestamo");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

    }
}
