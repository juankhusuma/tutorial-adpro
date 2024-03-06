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

import enums.OrderStatus;
import enums.PaymentMethod;
import enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
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
        private Order order2;

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
                this.order2 = new Order("7f9e15bb-4b15-42f4-aebc-c3af385fb078", this.products,
                                1708570000L, "Putri Kecil Mustafa");
        }

        @Test
        void testCreateVoucherPayment() {
                Payment payment = paymentService.addPayment(
                                order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
                doReturn(payment).when(paymentRepository).addPayment(order1, PaymentMethod.VOUCHER.getValue(),
                                voucherPaymentData);
                verify(paymentRepository, times(1))
                                .addPayment(order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
                paymentService.addPayment(order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
                assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        }

        @Test
        void testCreateCODPayment() {
                Payment payment = paymentService.addPayment(order1, PaymentMethod.COD.getValue(), codPaymentData);
                doReturn(payment).when(paymentRepository).addPayment(order1, PaymentMethod.COD.getValue(),
                                codPaymentData);
                verify(paymentRepository, times(1))
                                .addPayment(order1, PaymentMethod.COD.getValue(), codPaymentData);
                paymentService.addPayment(order1, PaymentMethod.COD.getValue(), codPaymentData);
                assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        }

        @Test
        void testCreateVoucerInvalid() {
                Map<String, String> paymentDataWithoutESHOPPrefix = new HashMap<String, String>();
                Map<String, String> paymentDataWithoutEightNumbers = new HashMap<String, String>();
                Map<String, String> paymentDataWithLessThanSixteenCharacters = new HashMap<String, String>();
                Map<String, String> paymentDataWithGreaterThanSixteenCharacters = new HashMap<String, String>();
                paymentDataWithoutESHOPPrefix.put("voucherCode", "1234ABC5678");
                paymentDataWithoutEightNumbers.put("voucherCode", "ESHOP1234ABC");
                paymentDataWithLessThanSixteenCharacters.put("voucherCode", "ESHOP1234ABC567");
                paymentDataWithGreaterThanSixteenCharacters.put("voucherCode", "ESHOP1234ABC56781234");

                Payment paymentWithoutESHOPPrefix = paymentService.addPayment(
                                order1, PaymentMethod.VOUCHER.getValue(), paymentDataWithoutESHOPPrefix);
                Payment paymentWithoutEightNumbers = paymentService.addPayment(
                                order1, PaymentMethod.VOUCHER.getValue(), paymentDataWithoutEightNumbers);
                Payment paymentWithLessThanSixteenCharacters = paymentService.addPayment(
                                order1, PaymentMethod.VOUCHER.getValue(), paymentDataWithLessThanSixteenCharacters);
                Payment paymentWithGreaterThanSixteenCharacters = paymentService.addPayment(
                                order1, PaymentMethod.VOUCHER.getValue(), paymentDataWithGreaterThanSixteenCharacters);

                assertEquals(PaymentStatus.REJECTED.getValue(), paymentWithoutESHOPPrefix.getStatus());
                assertEquals(PaymentStatus.REJECTED.getValue(), paymentWithoutEightNumbers.getStatus());
                assertEquals(PaymentStatus.REJECTED.getValue(), paymentWithLessThanSixteenCharacters.getStatus());
                assertEquals(PaymentStatus.REJECTED.getValue(), paymentWithGreaterThanSixteenCharacters.getStatus());
                assertEquals(OrderStatus.FAILED.getValue(), order1.getStatus());
        }

        @Test
        void testGetAllPayments() {
                paymentService.addPayment(order1, PaymentMethod.VOUCHER.getValue(), voucherPaymentData);
                paymentService.addPayment(order2, PaymentMethod.COD.getValue(), codPaymentData);
                assertEquals(2, paymentService.getAllPayments().size());
                assertSame(order1, paymentService.getAllPayments().get(0).getOrder());
                assertSame(order2, paymentService.getAllPayments().get(1).getOrder());
        }

        @Test
        void testGetByIdFound() {
                Payment payment = paymentService.addPayment(order1, PaymentMethod.VOUCHER.getValue(),
                                voucherPaymentData);
                assertEquals(payment, paymentService.getPayment(payment.getId()));
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
