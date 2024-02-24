package id.ac.ui.cs.advprog.eshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.eshop.model.Product;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {
    @InjectMocks
    ProductRepository productRepository;

    Product product;

    @BeforeEach
    void setUp() {
        this.productRepository = new ProductRepository();
        this.product = new Product();
        this.product.setProductId("1");
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }

    @Test
    void testCreateAndFind() {
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        productRepository.create(product);

        Product product2 = new Product();
        product2.setProductId("2");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditIfProductExist() {
        productRepository.create(product);

        Product newProductData = new Product();
        newProductData.setProductName("Sampo Cap Usep");
        newProductData.setProductQuantity(50);
        productRepository.update(product.getProductId(), newProductData);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(newProductData.getProductName(), savedProduct.getProductName());
        assertEquals(newProductData.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testEditIfProductNotFound() {
        productRepository.create(product);

        Product newProductData = new Product();
        newProductData.setProductName("Sampo Cap Usep");
        newProductData.setProductQuantity(50);
        productRepository.update("abc", newProductData);

        Iterator<Product> productIterator = productRepository.findAll();
        Product savedProduct = productIterator.next();
        assertEquals("Sampo Cap Bambang", savedProduct.getProductName());
        assertEquals(100, savedProduct.getProductQuantity());
    }

    @Test
    void testEditIfProductNotExist() {
        Product newProductData = new Product();
        newProductData.setProductId("1");
        newProductData.setProductName("Sampo Cap Usep");
        productRepository.update(newProductData.getProductId(), newProductData);

        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindByIdIfProductExist() {
        productRepository.create(product);

        Product savedProduct = productRepository.findById(product.getProductId());
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindByIdIfProductNotExist() {
        Product savedProduct = productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals(null, savedProduct);
    }

    @Test
    void testDeleteIfProductExist() {
        productRepository.create(product);

        productRepository.delete(product.getProductId());

        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteOnMultipleProducts() {
        productRepository.create(product);

        Product product2 = new Product();
        product2.setProductId("2");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        productRepository.delete(product.getProductId());

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteIfProductNotExist() {
        productRepository.delete("1");

        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteIfProductNotFound() {
        productRepository.create(product);

        productRepository.delete("abc");

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }
}
