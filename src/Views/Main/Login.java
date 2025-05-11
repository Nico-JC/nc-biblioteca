package Views.Main;

import Controllers.UserController;
import Models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JPanel mainPanel;
    private JLabel binvenidaLabel;
    private JTextField userNameField;
    private JLabel userNameLabel;
    private JPasswordField userPasswordField;
    private JLabel userPasswordLabel;
    private JButton ingresarButton;
    private JLabel alertSMGLabel;

    public Login(){
        alertSMGLabel.setVisible(false);
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserController uc = new UserController();
                String pass = new String(userPasswordField.getPassword());
                User userTemp = uc.login(userNameField.getText().trim(),pass);
                if(userTemp != null){

                    if(userTemp.getPermisos() == 2){
                        alertSMGLabel.setVisible(false);
                        setVisible(false);
                        Home homeView = new Home(userTemp);
                        homeView.setVisible(true);
                    }
                    else{
                        alertSMGLabel.setText("Acceso Denegado");
                        alertSMGLabel.setVisible(true);
                        setSize(300, 270);
                    }
                }
                else{
                    alertSMGLabel.setText("Credenciales inválidas");
                    alertSMGLabel.setVisible(true);
                    setSize(300, 270);
                }
            }
        });

        setTitle("Iniciar Sesión");
        setSize(300, 250);
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // pack(); -> auto ajusta la medida de la ventana, segun el contenido
    }

}
