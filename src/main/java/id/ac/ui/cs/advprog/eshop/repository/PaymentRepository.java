package id.ac.ui.cs.advprog.eshop.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;

public class PaymentRepository {
    private List<Payment> paymentData = new ArrayList<>();

    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        return null;
    }

    public Payment setStatus(Payment payment, String status) {
        return null;
    }

    public Payment getPayment(String paymentId) {
        return null;
    }

    public List<Payment> getAllPayments() {
        return null;
    }
}