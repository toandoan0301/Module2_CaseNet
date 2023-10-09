package model;

import java.io.Serializable;

public class Service implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String id;
    private String name;
    private int price;
    private int quantity;

    public Service(String id, String name, int price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return id + "," + name + "," + price + "," + quantity;
    }

    public String getInfo() {
        return String.format("Mã: %-6sTên: %-15sGiá: %,-10d Số lượng: %s", id,name,price,quantity);
        //return "ID: " + id + ", Tên: %" + name + ", Giá: " + price + ", Số Lượng: " + quantity;
    }
    public void updateQuantity(int quantity){
        this.quantity += quantity;
    }
}
