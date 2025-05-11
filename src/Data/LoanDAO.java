package Data;

import Models.Book;
import Models.Loan;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LoanDAO extends TableDAO {

    public boolean createLoan(int idUser, int idBook) {
        String sql = "INSERT INTO loan (id_user, id_book, inicioDate, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setInt(1, idUser);
            stmt.setInt(2, idBook);
            java.util.Date date = new java.util.Date();
            Date dateSQL = new Date(date.getTime());
            stmt.setDate(3, dateSQL);
            stmt.setInt(4,1);
            if (stmt.executeUpdate() > 0) {
                BookDAO book = new BookDAO();
                if (book.changeBookStatus(idBook, false)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean editLoanStatus(int id, boolean status) {
        String sql = "UPDATE loan SET status = ? WHERE id_loan = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setBoolean(1, status);
            stmt.setInt(2, id);
            if (stmt.executeUpdate() > 0) {
                BookDAO bc = new BookDAO();
                sql = "SELECT id_book FROM loan WHERE id_loan = ?";
                try (PreparedStatement stmt2 = db.prepareStatement(sql)) {
                    stmt2.setInt(1, id);
                    ResultSet rs = stmt2.executeQuery();
                    if (rs.next()) {
                        return bc.changeBookStatus(rs.getInt("id_book"), true);
                    } else {
                        return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public <T> Loan getLoan(String columna, T value){
        return getTableInfo("loan", columna, value, lambdaRs -> {
            try {
                return new Loan(lambdaRs.getInt("id_loan"), lambdaRs.getInt("id_user"), lambdaRs.getInt("id_book"), lambdaRs.getDate("inicioDate"), lambdaRs.getBoolean("status"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Object[][] getLoanHistory(int option) {
        String sql = "SELECT * FROM loan";
        if(option == 0){
            sql += " WHERE status = 1";
        }
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                ArrayList<Object[]> dataList = new ArrayList<>();
                while (rs.next()) {
                    UserDAO uc = new UserDAO();
                    String userName = uc.getUser("id_user",rs.getInt("id_user")).getNombre();

                    BookDAO bc = new BookDAO();
                    ArrayList<Book> books = bc.searchForBook("id_book",rs.getInt("id_book"));
                    if (!books.isEmpty()) {
                        Collections.sort(books, Comparator.comparingInt(Book::getId_book));
                        Book book = books.get(0);

                        Object[] row = {
                                rs.getInt("id_loan"),
                                userName,
                                book.getTitle(),
                                rs.getString("inicioDate"),
                                rs.getBoolean("status") ? "Activo" : "Desactivado"
                        };
                        dataList.add(row);
                    }
                }
                return dataList.toArray(new Object[0][0]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Object[] generateReport(){
        Object[] resultado = new Object[3];
        String sql = "SELECT " +
                " (SELECT name FROM user WHERE id_user = id_user_max) AS nombre_usuario, " +
                "  (SELECT title FROM book WHERE id_book = id_book_max) AS nombre_libro, " +
                " loanDate" +
                " FROM ( SELECT id_user AS id_user_max,  id_book AS id_book_max, inicioDate AS loanDate" +
                " FROM loan" +
                " GROUP BY id_user, id_book, inicioDate" +
                " ORDER BY COUNT(*) DESC" +
                " LIMIT 1 ) AS subconsulta_maximos";
        try (PreparedStatement stmt = db.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nameUser = rs.getString("nombre_usuario");
                String nameLibro = rs.getString("nombre_libro");
                Date fecha = rs.getDate("loanDate");

                resultado[0] = nameUser;
                resultado[1] = nameLibro;
                resultado[2] = fecha;
                return resultado;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultado;
    }
}
