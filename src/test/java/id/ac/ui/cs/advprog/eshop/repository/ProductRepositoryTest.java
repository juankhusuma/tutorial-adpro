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

    @BeforeEach
    void setUp() {
        this.productRepository = new ProductRepository();
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product("Sampo Cap Bambang", 100);
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
        Product product1 = new Product("Sampo Cap Bambang", 100);
        productRepository.create(product1);

        Product product2 = new Product("Sampo Cap Usep", 50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditIfProductExist() {
        Product product = new Product("Sampo Cap Bambang", 100);
        productRepository.create(product);

        Product newProductData = new Product("Sampo Cap Usep", 50);
        productRepository.edit(product.getProductId(), newProductData);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(newProductData.getProductName(), savedProduct.getProductName());
        assertEquals(newProductData.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testEditIfProductNotFound() {
        Product product = new Product("Sampo Cap Bambang", 100);
        productRepository.create(product);

        Product newProductData = new Product("Sampo Cap Usep", 50);
        productRepository.edit("abc", newProductData);

        Iterator<Product> productIterator = productRepository.findAll();
        Product savedProduct = productIterator.next();
        assertEquals("Sampo Cap Bambang", savedProduct.getProductName());
        assertEquals(100, savedProduct.getProductQuantity());
    }

    @Test
    void testEditIfProductNotExist() {
        Product newProductData = new Product("Sampo Cap Usep", 50);
        productRepository.edit(newProductData.getProductId(), newProductData);

        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindByIdIfProductExist() {
        Product product = new Product("Sampo Cap Bambang", 100);
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
        Product product = new Product("Sampo Cap Bambang", 100);
        productRepository.create(product);

        productRepository.delete(product.getProductId());

        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteOnMultipleProducts() {
        Product product1 = new Product("Sampo Cap Bambang", 100);
        productRepository.create(product1);

        Product product2 = new Product("Sampo Cap Usep", 50);
        productRepository.create(product2);

        productRepository.delete(product1.getProductId());

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
        Product product = new Product("Sampo Cap Bambang", 100);
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
