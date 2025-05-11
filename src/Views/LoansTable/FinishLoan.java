package Views.LoansTable;

import Controllers.LoanController;
import Models.Loan;
import Views.Main.Home;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinishLoan extends JFrame{
    private JPanel mainPanel;
    private JComboBox prestamoSelect;
    private JCheckBox activoDestildarParaFinalizarloCheckBox;
    private JLabel statusLabel;
    private JLabel smgAlert;
    private JLabel prestamoLabel;
    private JButton confirmarButton;

    public FinishLoan(Home home){
        smgAlert.setVisible(false);
        LoanController pc = new LoanController();

        DefaultComboBoxModel<String> prestamoModel = new DefaultComboBoxModel<>();
        Object[][] data = pc.getLoanHistory(0);
        String content = "";
        prestamoModel.addElement(content);
        for (int i = 0; i < data.length; i++) {
            content = data[i][0] + " - " + data[i][1] + " - " + data[i][2];
            prestamoModel.addElement(content);
        }
        prestamoSelect.setModel(prestamoModel);
        prestamoSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) prestamoSelect.getSelectedItem();
                String[] parts = selectedValue.split(" - ");
                if (selectedValue != "") {
                    Loan prestamo = pc.getLoan("id_loan",parts[0]);
                    if(prestamo != null){
                        activoDestildarParaFinalizarloCheckBox.setSelected(prestamo.isStatus());
                        pack();
                    }
                }
            }
        });

        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedValue = (String) prestamoSelect.getSelectedItem();
                String[] parts = selectedValue.split(" - ");
                if (selectedValue != "") {
                    if(pc.editLoanStatus(Integer.parseInt(parts[0]),activoDestildarParaFinalizarloCheckBox.isSelected())){
                        home.update();
                        setVisible(false);
                    }
                    else{
                        smgAlert.setText("Error al editar el Prestamo");
                        smgAlert.setVisible(true);
                        pack();
                    }
                }
                else{
                    smgAlert.setText("Recuerde seleccionar un Prestamo");
                    smgAlert.setVisible(true);
                    pack();
                }
            }
        });

        setTitle("Editar prestamo");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }
}
