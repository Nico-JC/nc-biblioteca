package Views.ClientsTable;

import Controllers.PrivilegeController;
import Controllers.UserController;
import Data.UserSingleton;
import Models.Privilege;
import Models.User;
import Views.Main.Home;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class EditDelete extends JFrame{
    private JPanel mainPanel;
    private JLabel userNameLabel;
    private JButton editarButton;
    private JButton eliminarButton;
    private JLabel permisosLabel;
    private JComboBox permisosBox;
    private JTextField userNameField;
    private JLabel smgAlert;
    private JComboBox clientSelector;
    private int user_id;
    UserSingleton user = UserSingleton.obtenerInstancia();

    public EditDelete(Home home){

        smgAlert.setVisible(false);
        UserController uc = new UserController();

        DefaultComboBoxModel<String> userModel = new DefaultComboBoxModel<>();
        Object[][] users = uc.getUserList();
        for (int i = 0; i < users.length; i++) {
            String data = users[i][0] + " - " + users[i][1];
            userModel.addElement(data);
        }
        clientSelector.setModel(userModel);

        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

        PrivilegeController pc = new PrivilegeController();
        ArrayList<Privilege> permisos = pc.getPrivilegeList();
        for (Privilege permiso : permisos) {
            String item = permiso.getId_permisos() + " - " + permiso.getNombre();
            model.addElement(item);
        }
        permisosBox.setModel(model);

        clientSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) clientSelector.getSelectedItem();
                String[] parts = selectedValue.split(" - ");
                if (parts.length > 0) {
                    User user = uc.getUser("id_user",parts[0]);
                    user_id = user.getId_user();
                    userNameField.setText(user.getNombre());
                    for (int i = 0; i < permisos.size(); i++) {
                        Privilege privilge = permisos.get(i);
                        if (Objects.equals(privilge.getNombre(), pc.getPrivilege("id_privilege", user.getPermisos()).getNombre())) {
                            permisosBox.setSelectedIndex(i);
                            break;
                        }
                    }

                }
            }
        });

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserController uc = new UserController();
                String selectedItem = (String) permisosBox.getSelectedItem();
                String[] selectedItemSeparado = selectedItem.split(" - ");
                if (user.getId() == user_id){
                    smgAlert.setText("No es posible editar tu propio usuario");
                    smgAlert.setVisible(true);
                    pack();
                }else {
                    int idPerm = Integer.parseInt(selectedItemSeparado[0].trim());
                    if(uc.editUser(user_id,userNameField.getText(),idPerm)){
                        setVisible(false);
                        home.update();
                    }
                    else{
                        smgAlert.setText("El nombre ya esta uso");
                        smgAlert.setVisible(true);
                        pack();
                    }
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserController uc = new UserController();
                if (user.getId() == user_id){
                    smgAlert.setText("No es eliminar tu propia cuenta");
                    smgAlert.setVisible(true);
                    pack();
                }else {
                    if(uc.deleteUser(user_id)){
                        setVisible(false);
                        home.update();
                    }
                    else{
                        smgAlert.setText("Error al Eliminar");
                        smgAlert.setVisible(true);
                        pack();
                    }
                }

            }
        });

        setTitle("Editar usuario");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }
}
