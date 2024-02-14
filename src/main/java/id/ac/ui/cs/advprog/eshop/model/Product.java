package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    static int productCount = 0;
    private String productId;
    private String productName;
    private int productQuantity;

    public Product() {
        productCount++;
        this.productId = "" + productCount;
    }

    public Product(String productName, int productQuantity) {
        productCount++;
        this.productId = "" + productCount;
        this.productName = productName;
        this.productQuantity = productQuantity;
    }

}
