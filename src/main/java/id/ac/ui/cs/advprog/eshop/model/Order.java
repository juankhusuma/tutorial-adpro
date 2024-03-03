package id.ac.ui.cs.advprog.eshop.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Order {
    private String id;
    private List<Product> products;
    private long orderTime;
    private String author;
    @Setter
    private String status;

    public Order(String id, List<Product> products, Long orderTime, String author) {
    }

    public Order(String id, List<Product> products, Long orderTime, String author, String status) {
    }
}
