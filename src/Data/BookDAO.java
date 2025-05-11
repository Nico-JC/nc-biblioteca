package Data;

import Exceptions.PriceCapBreachException;
import Models.Book;
import Models.Loan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BookDAO extends TableDAO{

    public ArrayList<Book> getBookList(){
        ArrayList<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM book";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                CategoryDAO category = new CategoryDAO();
                while (rs.next()) {
                    Book book = new Book(rs.getInt("id_book"), rs.getString("title"), rs.getString("author"), rs.getDouble("price"), rs.getBoolean("status"));
                    book.setCategories(category.getCategoriesForBook(book.getId_book()));
                    bookList.add(book);
                }
                return bookList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> ArrayList<Book> searchForBook(String columna, T value) {
        String sql = "SELECT * FROM book WHERE " + columna + " = ? OR " + columna + " LIKE ?";
        ArrayList<Book> bookList = new ArrayList<>();
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            if (value instanceof String) {
                stmt.setString(1, (String) value);
                stmt.setString(2, "%" + value + "%");
                System.out.println(sql);
            } else if (value instanceof Integer) {
                stmt.setInt(1, (Integer) value);
                stmt.setInt(2, (Integer) value);
                System.out.println(sql);
            } else {
                return null;
            }
            try (ResultSet rs = stmt.executeQuery()) {
                CategoryDAO cc = new CategoryDAO();
                while (rs.next()) {
                    Book book = new Book(rs.getInt("id_book"), rs.getString("title"), rs.getString("author"), rs.getDouble("price"), rs.getBoolean("status"));
                    book.setCategories(cc.getCategoriesForBook(book.getId_book()));
                    bookList.add(book);
                }
                return bookList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // Create Book
    public boolean createBook(String title, String author, double price, ArrayList<Integer> categorias) throws PriceCapBreachException {
        String sql = "INSERT INTO book (title, author, price, status) VALUES (?, ?, ?, 1)";
        try (PreparedStatement stmt = db.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setDouble(3, price);
            if (stmt.executeUpdate() > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int bookId = generatedKeys.getInt(1);
                    CategoryDAO cc = new CategoryDAO();
                    return cc.setCategoriesForBook(categorias, bookId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean changeBookStatus(int id, boolean status) {
        ArrayList<Book> books = searchForBook("id_book", id);
        if (books != null && !books.isEmpty()) {
            String sql = "UPDATE book SET status = ? WHERE id_book = ?";
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setBoolean(1, status);
                stmt.setInt(2, id);
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            return false;
        }
        return false;
    }

    public boolean editBook(int id, String title, String autor, double price, boolean status) {
        LoanDAO pc = new LoanDAO();
        Loan prestamo = pc.getLoan("id_book", id);
        if( prestamo != null && prestamo.isStatus()){
            return false;
        }
        else{
            ArrayList<Book> books = searchForBook("id_book", id);
            if (books != null && !books.isEmpty()) {
                String sql = "UPDATE book SET title = ?,author = ?,price = ? WHERE id_book = ?";
                try (PreparedStatement stmt = db.prepareStatement(sql)) {
                    stmt.setString(1, title);
                    stmt.setString(2, autor);
                    stmt.setDouble(3, price);
                    stmt.setInt(4, id);
                    if(stmt.executeUpdate() > 0){
                        return changeBookStatus(id,status);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else {
                return false;
            }
        }
        return false;
    }

    public boolean deleteBook(int id) {
        ArrayList<Book> books = searchForBook("id_book", id);
        if (books != null && !books.isEmpty()) {
            for (Book book:books) {
                if(book.getId_book() == id && book.getStatus()){
                    String sql = "DELETE FROM book WHERE id_book = ?";
                    try (PreparedStatement stmt = db.prepareStatement(sql)) {
                        stmt.setInt(1, id);
                        return stmt.executeUpdate() > 0;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else {
            return false;
        }
        return false;
    }
}
