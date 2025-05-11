package Controllers;

import Data.BookDAO;
import Exceptions.PriceCapBreachException;
import Models.Book;

import java.util.ArrayList;

public class BookController {

    private BookDAO book;

    public BookController(){
        book = new BookDAO();
    }

    public ArrayList<Book> getBookList() {
        ArrayList<Book> bookList = new ArrayList<>();
        try {
            bookList = book.getBookList();
            if (bookList == null) {
                System.out.println("No se pudo obtener la lista de libros.");
                return bookList = new ArrayList<>();
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error al obtener la lista de libros: " + e.getMessage());
            e.printStackTrace();
        }
        return bookList;
    }

    public <T> ArrayList<Book> searchForBook(String columna, T value){
        ArrayList<Book> bookList = new ArrayList<>();
        return bookList = book.searchForBook(columna, value);
    }

    public boolean createBook(String title, String author, double price, ArrayList<Integer> categorias) throws PriceCapBreachException {
        if (price > 2000.00) {
            throw new PriceCapBreachException("El precio supera el límite");
        }
        try {
            return book.createBook(title, author, price, categorias);
        } catch (Exception e) {
            System.out.println("Ocurrió un error al agregar el libro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean editBook(int id, String title, String author, double price, boolean status) throws PriceCapBreachException {
        try {
            return book.editBook(id, title, author, price, status);
        } catch (Exception e) {
            System.out.println("Ocurrió un error al editar el libro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean changeBookStatus(int id, boolean status){
        if (id <= 0) {
            System.out.println("El ID del libro no es válido.");
            return false;
        }
        try {
            return book.changeBookStatus(id, status);
        } catch (Exception e) {
            System.out.println("Ocurrió un error al editar el libro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBook(int id){
        if (id <= 0) {
            System.out.println("El ID del libro no es válido.");
            return false;
        }
        try {
            return book.deleteBook(id);
        }catch (Exception e){
            System.out.println("Error al eliminar: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
