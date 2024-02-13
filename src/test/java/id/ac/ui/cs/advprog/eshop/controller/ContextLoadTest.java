package id.ac.ui.cs.advprog.eshop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ContextLoadTest {

    @Autowired
    private ProductController productController;

    @Test
    void testContextLoads() throws Exception {
        assertNotNull(productController);
    }
}