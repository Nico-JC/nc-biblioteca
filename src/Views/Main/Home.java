package Views.Main;

import Controllers.Tables.BookTable;
import Controllers.Tables.LoanTable;
import Controllers.Tables.UserTable;
import Interface.Update;
import Models.User;
import Views.BookTable.CreateTable;
import Views.BookTable.EditDeleteTable;
import Views.ClientsTable.Create;
import Views.ClientsTable.EditDelete;
import Views.LoansTable.FinishLoan;
import Views.LoansTable.Historial;
import Views.LoansTable.Registro;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame implements Update {
    private JPanel mainPanel;
    private JTable prestamosTable;
    private JButton crearPrestamoButton;
    private JButton historialButton;
    private JButton reporteButton;
    private JTable userTable;
    private JLabel msgLabel_2;
    private JButton crearUsuarioButton;
    private JButton editarEliminarButton;
    private JButton editarPrestamoButton;
    private JTable booksTable;
    private JLabel booksLabel;
    private JScrollPane dataUserPanel;
    private JButton editDeleteBookBTN;
    private JButton createBookBTN;
    private JScrollPane dataPresPanel;
    private JButton getSpecificBook;
    private JScrollPane dataBookPanel;
    private JLabel msgLabel_1;

    public Home(User user){

        LoanTable generarPrestamoTable = new LoanTable(prestamosTable);
        generarPrestamoTable.generarTabla();
        UserTable generarUserTable = new UserTable(userTable);
        generarUserTable.generarTabla();
        BookTable generarBookTable = new BookTable(booksTable);
        generarBookTable.generarTabla();

        crearUsuarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Create createUser = new Create(Home.this);
                createUser.setVisible(true);
            }
        });
        editarEliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditDelete edUser = new EditDelete(Home.this);
                edUser.setVisible(true);
            }
        });

        createBookBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateTable bookCreate = new CreateTable(Home.this);
                bookCreate.setVisible(true);
            }
        });
        editDeleteBookBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditDeleteTable bookEditDelete = new EditDeleteTable(Home.this);
                bookEditDelete.setVisible(true);
            }
        });
//        getSpecificBook.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ShowBooks buscador = new ShowBooks();
//                buscador.setVisible(true);
//            }
//        });

        historialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Historial historial = new Historial();
                historial.setVisible(true);
            }
        });
        reporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registro registro = new Registro();
                registro.setVisible(true);
            }
        });
        crearPrestamoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Views.LoansTable.Create createPrestamo = new Views.LoansTable.Create(Home.this);
                createPrestamo.setVisible(true);
            }
        });
        editarPrestamoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FinishLoan editPrestamo = new FinishLoan(Home.this);
                editPrestamo.setVisible(true);
            }
        });
        // ---------------------------- ---------------------------- ----------------------------

        setTitle("Inicio");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

    @Override
    public void update() {
        LoanTable generarPrestamoTable = new LoanTable(prestamosTable);
        generarPrestamoTable.generarTabla();
        UserTable generarUserTable = new UserTable(userTable);
        generarUserTable.generarTabla();
        BookTable generarBookTable = new BookTable(booksTable);
        generarBookTable.generarTabla();

        setTitle("Inicio");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }
}