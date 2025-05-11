/*https://medium.com/@barbieri.santiago/java-stringbuilder-a352e04c7478 - https://www.aprenderaprogramar.com/index.php?option=com_content&view=article&id=961:stringbuffer-stringbuilder-java-ejemplo-diferencias-entre-clases-criterios-para-elegir-metodos-cu00914c&catid=58&Itemid=180*/
package Controllers.Tables;

import Controllers.BookController;
import Models.Book;

import javax.swing.*;
import java.util.ArrayList;

public class BookTable extends Table {

    private JTable booksTable;

    public BookTable(JTable booksTable) {
        this.booksTable = booksTable;
    }

    @Override
    public void generarTabla() {
        String[] columnNames = {"ID", "Titulo", "Autor", "Precio", "Categoria", "Estado"};
        BookController bc = new BookController();
        ArrayList<Book> bookList = bc.getBookList();
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
        tablaConfig(booksTable, tableData, columnNames);
    }
}

