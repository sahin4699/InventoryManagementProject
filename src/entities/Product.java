package entities;

public class Product {
    //ürüne ait özellikler
    private String id;
    private String name;
    private double price;
    private int stockQuantity;

    //ürüne ait özelliklerin değerleri,parametreler
    public Product(String id, String name, double price, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    //ürün sınıfının get ve set metodlarını yazıyoruz
    //encapsulation yapıyoruz
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getStockQuantity() { return stockQuantity; }

    //stok sayısının 0'dan az girilmesini engelliyoruz
    public void setStockQuantity(int stockQuantity) {
        if(stockQuantity >= 0) this.stockQuantity = stockQuantity;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}