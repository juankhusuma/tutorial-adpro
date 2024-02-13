package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    public static int productCount = 0;

    public Product(String productName, int productQuantity) {
        productCount++;
        this.productId = "" + productCount;
        this.productName = productName;
        this.productQuantity = productQuantity;
    }

    private String productId;
    private String productName;
    private int productQuantity;
}
