package tranghtmph26263.fpoly.ungdungbanxedap.entity;

import java.sql.Blob;

public class Product {
    private int id, category_id;
    private String name, describe, import_date;
    private Blob avatar;
    private int price, stock, sold;

    public Product() {
    }

    public Product(int id, int category_id, String name, String describe, String import_date, Blob avatar, int price, int stock, int sold) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
        this.describe = describe;
        this.import_date = import_date;
        this.avatar = avatar;
        this.price = price;
        this.stock = stock;
        this.sold = sold;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImport_date() {
        return import_date;
    }

    public void setImport_date(String import_date) {
        this.import_date = import_date;
    }

    public Blob getAvatar() {
        return avatar;
    }

    public void setAvatar(Blob avatar) {
        this.avatar = avatar;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }
}
