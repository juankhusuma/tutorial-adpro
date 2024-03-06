package id.ac.ui.cs.advprog.eshop.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import enums.OrderStatus;
import enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;

public class PaymentRepository {
    private List<Payment> paymentData;

    public PaymentRepository() {
        this.paymentData = new ArrayList<>();
    }

    public Payment addPayment(
            Order order, String method, Map<String, String> data) {
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        Payment payment = new Payment(uuid, method, data, order);
        System.out.println(payment);
        paymentData.add(payment);
        return payment;
    }

    public Payment setStatus(Payment payment, String status) throws IllegalArgumentException {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException();
        }
        payment.setStatus(status);
        if (status.equals(PaymentStatus.SUCCESS.getValue())) {
            payment.getOrder().setStatus(
                    OrderStatus.SUCCESS.getValue());
        } else {
            payment.getOrder().setStatus(
                    OrderStatus.FAILED.getValue());
        }
        return payment;
    }

    public Payment getPayment(String paymentId) {
        for (Payment payment : paymentData) {
            if (payment.getId().equals(paymentId)) {
                return payment;
            }
        }
        return null;
    }

    public List<Payment> getAllPayments() {
        return paymentData;
    }
}