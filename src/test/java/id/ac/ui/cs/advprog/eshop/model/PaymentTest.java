package id.ac.ui.cs.advprog.eshop.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PaymentTest {
    private Map<String, String> voucherPaymentData;
    private Map<String, String> codPaymentData;
    private List<Product> products;
    private Order order;

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
        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b", this.products,
                1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentWithoutPaymentData() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER", null, this.order);
        });
    }

    @Test
    void testCreateVoucherPaymentWithInvalidPaymentData() {
        Map<String, String> paymentDataWithoutESHOPPrefix = new HashMap<String, String>();
        Map<String, String> paymentDataWithoutEightNumbers = new HashMap<String, String>();
        Map<String, String> paymentDataWithLessThanSixteenCharacters = new HashMap<String, String>();
        Map<String, String> paymentDataWithGreaterThanSixteenCharacters = new HashMap<String, String>();
        paymentDataWithoutESHOPPrefix.put("voucherCode", "1234ABC5678");
        paymentDataWithoutEightNumbers.put("voucherCode", "ESHOP1234ABC");
        paymentDataWithLessThanSixteenCharacters.put("voucherCode", "ESHOP1234ABC567");
        paymentDataWithGreaterThanSixteenCharacters.put("voucherCode", "ESHOP1234ABC56781234");
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER",
                    paymentDataWithoutESHOPPrefix, this.order);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER",
                    paymentDataWithoutEightNumbers, this.order);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER",
                    paymentDataWithLessThanSixteenCharacters, this.order);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER",
                    paymentDataWithGreaterThanSixteenCharacters, this.order);
        });
    }

    @Test
    void testCreateCODPaymentWithInvalidData() {
        Map<String, String> paymentDataWithoutAddress = new HashMap<String, String>();
        Map<String, String> paymentDataWithoutDeliveryFee = new HashMap<String, String>();
        Map<String, String> paymentDataWithEmptyAddress = new HashMap<String, String>();
        Map<String, String> paymentDataWithEmptyDeliveryFee = new HashMap<String, String>();
        paymentDataWithoutAddress.put("deliveryFee", "12000");
        paymentDataWithoutDeliveryFee.put("address", "Jl. Walet Indah 3, No. 10");
        paymentDataWithEmptyAddress.put("address", "");
        paymentDataWithEmptyAddress.put("deliveryFee", "12000");
        paymentDataWithEmptyDeliveryFee.put("deliveryFee", "");
        paymentDataWithEmptyDeliveryFee.put("address", "Jl. Walet Indah 3, No. 10");
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-012a-4c07-b546-54eb1396d79b", "COD",
                    paymentDataWithoutAddress, this.order);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-012a-4c07-b546-54eb1396d79b", "COD",
                    paymentDataWithoutDeliveryFee, this.order);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-012a-4c07-b546-54eb1396d79b", "COD",
                    paymentDataWithEmptyAddress, this.order);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-012a-4c07-b546-54eb1396d79b", "COD",
                    paymentDataWithEmptyDeliveryFee, this.order);
        });
    }

    @Test
    void testCreatePaymentWithoutOrder() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment(null, "VOUCHER", this.voucherPaymentData, this.order);
        });
    }

    @Test
    void testCreatePaymentWithInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("13652556-012a-4c07-b546-54eb1396d79b", "MEOW", this.voucherPaymentData, this.order);
        });
    }

    @Test
    void testSetStatusToInvalidStatus() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER", this.voucherPaymentData,
                this.order);
        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("MEOW");
        });
    }

    @Test
    void testCreateVoucherPaymentSuccess() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "VOUCHER", this.voucherPaymentData,
                this.order);
        assertEquals("SUCCESS", payment.getStatus());
        assertSame(this.voucherPaymentData, payment.getPaymentData());
        assertSame(this.order, payment.getOrder());
    }

    @Test
    void testCreateCODPaymentSuccess() {
        Payment payment = new Payment("13652556-012a-4c07-b546-54eb1396d79b", "COD", this.codPaymentData, this.order);
        assertEquals("SUCCESS", payment.getStatus());
        assertSame(this.codPaymentData, payment.getPaymentData());
        assertSame(this.order, payment.getOrder());
    }
}