package Controllers.Tables;

import Controllers.BookController;
import Models.Book;

import javax.swing.*;
import java.util.ArrayList;

public class SearchBookTable extends Table {

    private JTable table;
    private String searchTerm;

    public SearchBookTable(JTable table, String searchTerm) {
        this.table = table;
        this.searchTerm = searchTerm;
    }

    @Override
    public void generarTabla() {
        String[] columnNames = {"ID", "Titulo", "Autor", "Precio", "Categorias", "Estado"};
        BookController bc = new BookController();
        ArrayList<Book> bookList = bc.searchForBook("title", searchTerm);
        Object[][] tableData = new Object[bookList.size()][6];
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            tableData[i][0] = book.getId_book();
            tableData[i][1] = book.getTitle();
            tableData[i][2] = book.getAuthor();
            tableData[i][3] = book.getPrice();
            StringBuilder categories = new StringBuilder();
            for (String categoryName : book.getCategories()) {
                categories.append(categoryName).append(", ");
            }
            if (categories.length() > 0) {
                categories.setLength(categories.length() - 2);
            }
            tableData[i][4] = categories.toString();
            tableData[i][5] = book.getStatus() ? "Disponible" : "No Disponible";
        }
        tablaConfig(table, tableData, columnNames);
    }
}
