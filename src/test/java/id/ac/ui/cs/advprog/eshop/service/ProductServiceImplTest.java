package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @InjectMocks
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    Product product;

    @BeforeEach
    void setUp() {
        this.product = new Product("Sampo Cap Bambang", 100);
    }

    @Test
    void testCreateReturnsProperly() {
        assertEquals(product, productService.create(product));
    }

    @Test
    void testCreateAddsProductToRepository() {
        productService.create(product);
        assertEquals(product, productService.findAll().get(0));
    }

    @Test
    void testFindAllReturnsProperly() {
        productService.create(product);
        Product product2 = new Product("Sampo Cap Budi", 100);
        productService.create(product2);
        assertEquals(product, productService.findAll().get(0));
        assertEquals(product2, productService.findAll().get(1));
    }

    @Test
    void testEditReturnsProperly() {
        productService.create(product);
        Product newProductData = new Product("Sampo Cap Bambang", 200);
        assertEquals(newProductData, productService.edit("eb558e9f-1c39-460e-8860-71af6af63bd6", newProductData));
    }

    @Test
    void testEditChangesProductInRepository() {
        productService.create(product);
        Product newProductData = new Product("Sampo Cap Bambang", 200);
        productService.edit("1", newProductData);
        Product productFromRepository = productService.findAll().get(0);
        assertEquals(newProductData.getProductName(), productFromRepository.getProductName());
        assertEquals(newProductData.getProductQuantity(), productFromRepository.getProductQuantity());
    }

    @Test
    void testFindByIdReturnsProperly() {
        productService.create(product);
        assertEquals(product, productService.findById(product.getProductId()));
    }

    @Test
    void testDeleteRemovesProductFromRepository() {
        productService.create(product);
        productService.delete(product.getProductId());
        assertEquals(0, productService.findAll().size());
    }
}
