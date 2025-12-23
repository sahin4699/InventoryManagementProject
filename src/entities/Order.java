package entities;

public class Order {
    //siparişe ait özellikler
    private String orderId;
    //sipariş direkt olarak bir ürün olacağı için product classını property olarak yazalım
    private Product product;
    private int quantity;

    //siparişe ait özelliklerin değerleri,parametreler
    public Order(String orderId, Product product, int quantity) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
    }

    //sipariş sınıfının get ve set metodlarını yazıyoruz
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
}