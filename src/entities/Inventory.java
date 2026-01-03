package entities;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<Product> products;
    private List<Supplier> suppliers;
    private List<Order> orders;


    //constructor ile boş bir dizi dönüyoruz
    public Inventory(){
        this.products = new ArrayList<>();
        this.suppliers=new ArrayList<>();
        this.orders=new ArrayList<>();
    }

    //envantere ait getter ve setterlar
    public List<Product> getProducts(){
        return  products;
    }

    public void setProducts(List<Product> products){
        this.products = products;
    }

    public List<Supplier> getSuppliers(){
        return  suppliers;
    }

    public List<Order> getOrders(){
        return  orders;
    }





}
