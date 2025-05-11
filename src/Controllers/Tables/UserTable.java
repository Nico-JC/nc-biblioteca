package Controllers.Tables;

import Controllers.UserController;

import javax.swing.*;

public class UserTable extends Table {

    private JTable userTable;

    public UserTable(JTable userTable) {
        this.userTable = userTable;
    }

    @Override
    public void generarTabla() {
        String[] columnNames = {"ID", "Cliente", "Prestamos Activos", "Prestamos Totales"};
        UserController uc = new UserController();
        Object[][] data = uc.getUserList();
        tablaConfig(userTable, data, columnNames);
    }
}

