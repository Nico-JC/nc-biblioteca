package Controllers;

import Data.CategoryDAO;
import Models.Category;

import java.util.ArrayList;

public class CategoryController {
    private CategoryDAO category;

    public CategoryController() {
        category = new CategoryDAO();
    }

    public ArrayList<Category> getCategoryList() {
        ArrayList<Category> categoryList = new ArrayList<>();
        try {
            categoryList = category.getCategoryList();
            if (categoryList == null) {
                System.out.println("No se pudo obtener la lista de categorias.");
                return categoryList = new ArrayList<>();
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al obtener la lista de categorias: " + e.getMessage());
            e.printStackTrace();
        }
        return categoryList;
    }

    public <T> Category getCategory(String columna, T value) {
        return category.getCategory(columna, value);
    }

    public boolean createCategory(String name) {
        return category.createCategory(name);
    }

    public boolean editCategory(int id, String name) {
        return category.editCategory(id, name);
    }

    public boolean deleteCategory(int id) {
        return category.deleteCategory(id);
    }

    public boolean setCategoriesForBook(ArrayList<Integer> idList, int bookId) {
        return category.setCategoriesForBook(idList, bookId);
    }

    public ArrayList<String> getCategoriesForBook(int bookId) {
       return category.getCategoriesForBook(bookId);
    }

    public boolean removeCategoryForBook(int bookId, int categoryId) {
        return category.removeCategoryForBook(bookId, categoryId);
    }


}
