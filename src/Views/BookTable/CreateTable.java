package Views.BookTable;

import Controllers.BookController;
import Controllers.CategoryController;
import Exceptions.PriceCapBreachException;
import Models.Category;
import Views.Main.Home;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreateTable extends JFrame {
    private JLabel tituloLabel;
    private JLabel autorLabel;
    private JLabel precioLabel;
    private JLabel categoriaLabel;
    private JTextField tituloField;
    private JTextField autorField;
    private JTextField precioField;
    private JScrollPane categoryScrollPanel;
    private JLabel smgAlert;
    private JButton crearButton;
    private JPanel mainPanel;
    private ArrayList<Category> categories;
    private ArrayList<Integer> selectedCategoryIds;

    public CreateTable(Home home) {
        smgAlert.setVisible(false);
        categories = new ArrayList<>();
        selectedCategoryIds = new ArrayList<>();
        CategoryController cc = new CategoryController();
        categories = cc.getCategoryList();
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryScrollPanel.setViewportView(categoryPanel);
        for (Category category : categories) {
            JCheckBox checkBox = new JCheckBox(category.getNombre());
            checkBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (checkBox.isSelected()) {
                        selectedCategoryIds.add(category.getId_category());
                    } else {
                        selectedCategoryIds.remove(Integer.valueOf(category.getId_category()));
                    }
                }
            });
            categoryPanel.add(checkBox);
        }

        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BookController bc = new BookController();
                try {
                    if (bc.createBook(tituloField.getText().trim(), autorField.getText().trim(),
                            Double.parseDouble(precioField.getText().trim().replace(',', '.')), selectedCategoryIds)) {
                        setVisible(false);
                        home.update();
                    } else {
                        smgAlert.setText("Error al crear el libro");
                        smgAlert.setVisible(true);
                        pack();
                    }
                } catch (NumberFormatException exception) {
                    smgAlert.setText("Ingrese un precio válido");
                    smgAlert.setVisible(true);
                    pack();
                } catch (PriceCapBreachException exception) {
                    smgAlert.setText("El precio no puede ser mayor a 999,99");
                    smgAlert.setVisible(true);
                    pack();
                } catch (Exception exception) {
                    exception.printStackTrace();
                    smgAlert.setText("Error: " + exception.getMessage());
                    smgAlert.setVisible(true);
                    pack();
                }
            }
        });
        setTitle("Crear Libro");
        setContentPane(mainPanel);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        pack();
    }
}
