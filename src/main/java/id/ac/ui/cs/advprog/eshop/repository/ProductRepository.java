package id.ac.ui.cs.advprog.eshop.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.stereotype.Repository;
import id.ac.ui.cs.advprog.eshop.model.Product;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        String productId = productData.size() + 1 + "";
        product.setProductId(productId);
        productData.add(product);
        return product;
    }

    public Product edit(String productId, Product newProductData) {
        Product productToEdit = findById(productId);

        if (productToEdit != null) {
            productToEdit.setProductName(newProductData.getProductName());
            productToEdit.setProductQuantity(newProductData.getProductQuantity());
        }

        return productToEdit;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String productId) {
        for (Product product : productData) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }
}