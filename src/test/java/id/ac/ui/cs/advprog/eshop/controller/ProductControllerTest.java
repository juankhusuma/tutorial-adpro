package id.ac.ui.cs.advprog.eshop.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.apache.hc.core5.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

@AutoConfigureJsonTesters
@ContextConfiguration(classes = ProductController.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ProductService service;

        @Autowired
        private JacksonTester<Product> jsonProduct;

        @Test
        public void canGetCreateProductPage() throws Exception {
                MockHttpServletResponse response = mockMvc.perform(
                                get("/product/create"))
                                .andReturn().getResponse();
                assert (response.getStatus() == HttpStatus.SC_OK);
        }

        @Test
        public void canCreateProduct() throws Exception {
                Product product = new Product("Product 1", 10);
                String json = jsonProduct.write(product).getJson();
                MockHttpServletResponse response = mockMvc.perform(
                                post("/product/create")
                                                .contentType("application/json")
                                                .content(json != null ? json : ""))
                                .andReturn().getResponse();
                assert (response.getStatus() == HttpStatus.SC_MOVED_TEMPORARILY);
        }

        @Test
        public void canGetProductListPage() throws Exception {
                MockHttpServletResponse response = mockMvc.perform(
                                get("/product/list"))
                                .andReturn().getResponse();
                assert (response.getStatus() == HttpStatus.SC_OK);
        }

        @Test
        public void canGetEditProductPage() throws Exception {
                Product product = new Product("Product 1", 10);
                String json = jsonProduct.write(product).getJson();
                mockMvc.perform(
                                post("/product/create")
                                                .contentType("application/json")
                                                .content(json != null ? json : ""))
                                .andReturn().getResponse();

                Mockito.when(service.findById("1")).thenReturn(product);

                MockHttpServletResponse response = mockMvc.perform(
                                get("/product/edit/1"))
                                .andReturn().getResponse();
                assert (response.getStatus() == HttpStatus.SC_OK);

        }

        @Test
        public void canEditProduct() throws Exception {
                Product existingProduct = new Product("Product 1", 10);
                String jsonExistingProduct = jsonProduct.write(existingProduct).getJson();
                mockMvc.perform(
                                post("/product/create")
                                                .contentType("application/json")
                                                .content(jsonExistingProduct != null ? jsonExistingProduct : ""))
                                .andReturn().getResponse();

                Product updatedProduct = new Product("Product 1", 20);

                String json = jsonProduct.write(updatedProduct).getJson();
                MockHttpServletResponse response = mockMvc.perform(
                                post("/product/edit/1")
                                                .contentType("application/json")
                                                .content(json != null ? json : ""))
                                .andReturn().getResponse();
                assert (response.getStatus() == HttpStatus.SC_MOVED_TEMPORARILY);
        }

        @Test
        public void canRedirectWhenEditNotFound() throws Exception {
                MockHttpServletResponse response = mockMvc.perform(
                                get("/product/edit/69"))
                                .andReturn().getResponse();
                assert (response.getStatus() == HttpStatus.SC_MOVED_TEMPORARILY);
        }

        @Test
        public void canDeleteProduct() throws Exception {
                Product product = new Product("Product 1", 10);
                String json = jsonProduct.write(product).getJson();

                mockMvc.perform(
                                post("/product/create")
                                                .contentType("application/json")
                                                .content(json != null ? json : ""))
                                .andReturn().getResponse();

                MockHttpServletResponse response = mockMvc.perform(
                                get("/product/delete/1"))
                                .andReturn().getResponse();
                assert (response.getStatus() == HttpStatus.SC_MOVED_TEMPORARILY);
        }
}