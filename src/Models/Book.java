package Models;

import java.util.ArrayList;

public class Book {
    private int id_book;
    private String title;
    private String author;
    private double price;
    private boolean status;
    private ArrayList<String> categories;

    public Book(int id_book, String title, String author, double price, boolean status) {
        this.id_book = id_book;
        this.title = title;
        this.author = author;
        this.price = price;
        this.status = status;
    }

    public void setCategories(ArrayList<String> categoryList){
        this.categories = categoryList;
    }

    public ArrayList<String> getCategories(){
        return this.categories;
    }

    public int getId_book() {
        return id_book;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    public boolean getStatus() {
        return status;
    }
}