package entities;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    //ürünlerden oluşan envanterimiz
    private List<Product> products;

    //constructor ile boş bir dizi dönüyoruz
    public Inventory(){
        this.products = new ArrayList<>();
    }

    //envantere ait getter ve setterlar
    public List<Product> getProducts(){
        return  products;
    }

    public void setProducts(List<Product> products){
        this.products = products;
    }
}
