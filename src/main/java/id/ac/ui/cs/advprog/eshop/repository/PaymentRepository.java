package id.ac.ui.cs.advprog.eshop.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;

public class PaymentRepository {
    private List<Payment> paymentData = new ArrayList<>();

    public Payment addPayment(
            Order order, String method, Map<String, String> paymentData) throws IllegalArgumentException {
        Payment payment = new Payment(UUID.randomUUID().toString(), method, paymentData, order);
        this.paymentData.add(payment);
        return payment;
    }

    public Payment setStatus(Payment payment, String status) throws IllegalArgumentException {
        if (!PaymentStatus.contains(status)) {
            throw new IllegalArgumentException();
        }
        payment.setStatus(status);
        payment.getOrder().setStatus(
                status.equals("SUCCESS") ? "SUCCESS" : "FAILED");
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