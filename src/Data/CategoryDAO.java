package Data;

import Models.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CategoryDAO  extends TableDAO {

    public ArrayList<Category> getCategoryList(){
        ArrayList<Category> categoryList = new ArrayList<>();
        String sql = "SELECT * FROM category";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Category category = new Category(rs.getInt("id_category"), rs.getString("nombre"));
                    categoryList.add(category);
                }
                return categoryList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> Category getCategory(String columna,T value){
        return getTableInfo("category", columna, value, rs -> { // buscar más sobre "Expresión Lambda"
            try {
                return new Category(rs.getInt("id_category"), rs.getString("nombre"));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public boolean createCategory(String name) {
        if (getCategory("nombre", name) == null) {
            String sql = "INSERT INTO category (nombre) VALUES (?)";
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setString(1, name);
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return false;
    }

    public boolean editCategory(int id, String name) {
        if (getCategory("id_category", id) != null) {
            String sql = "UPDATE category SET nombre = ? WHERE id_category = ?";
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setString(1, name);
                stmt.setInt(2, id);
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return false;
    }

    public boolean deleteCategory(int id) {
        if (getCategory("id_category", id) != null) {
            String sql = "DELETE FROM category WHERE id_category = ?";
            try (PreparedStatement stmt = db.prepareStatement(sql)) {
                stmt.setInt(1, id);
                return stmt.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return false;
    }

    public boolean setCategoriesForBook(ArrayList<Integer> idList, int bookId){
        String sql = "INSERT INTO category_book (id_book, id_category) VALUES (?, ?)";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            for (Integer categoryId : idList) {
                stmt.setInt(1, bookId);
                stmt.setInt(2, categoryId);
                stmt.addBatch();
            }
            int[] resultados = stmt.executeBatch();
            return resultados.length > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<String> getCategoriesForBook(int bookId) {
        String sql = "SELECT id_category FROM category_book WHERE id_book = ?";
        ArrayList<String> categoryNames = new ArrayList<>();
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int categoryId = rs.getInt("id_category");
                Category category = getCategory("id_category", categoryId);
                if (category != null) {
                    categoryNames.add(category.getNombre());
                }
            }
            return categoryNames;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryNames;
    }

    public boolean removeCategoryForBook(int bookId, int categoryId) {
        String sql = "DELETE FROM category_book WHERE id_book = ? AND id_category = ?";
        try (PreparedStatement stmt = db.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, categoryId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
