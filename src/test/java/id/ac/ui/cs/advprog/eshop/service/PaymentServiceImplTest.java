package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {
        @InjectMocks
        PaymentServiceImpl paymentService;
        @Mock
        PaymentRepository paymentRepository;
        private Map<String, String> voucherPaymentData;
        private Map<String, String> codPaymentData;
        private List<Product> products;
        private Order order1;

        @BeforeEach
        void setUp() {
                this.voucherPaymentData = new HashMap<String, String>();
                this.voucherPaymentData.put("voucherCode", "ESHOP1234ABC5678");
                this.codPaymentData = new HashMap<String, String>();
                this.codPaymentData.put("address", "Jl. Walet Indah 3, No. 10");
                this.codPaymentData.put("deliveryFee", "12000");
                this.products = new ArrayList<>();
                Product product1 = new Product();
                product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
                product1.setProductName("Sampo Cap Bambang");
                product1.setProductQuantity(2);
                Product product2 = new Product();
                product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
                product2.setProductName("Sabun Cap Usep");
                product2.setProductQuantity(1);
                this.products.add(product1);
                this.products.add(product2);
                this.order1 = new Order("13652556-012a-4c07-b546-54eb1396d79b", this.products,
                                1708560000L, "Safira Sudrajat");
        }

        @Test
        void testGetByIdNotFound() {
                paymentService.addPayment(order1, PaymentMethod.VOUCHER.getValue(),
                                voucherPaymentData);
                assertNull(paymentService.getPayment("asiap-mamank"));
        }

        @Test
        void testCreateInvalidMethod() {
                assertThrows(
                                IllegalArgumentException.class,
                                () -> paymentService.addPayment(order1, "OVO", voucherPaymentData));
        }
}
