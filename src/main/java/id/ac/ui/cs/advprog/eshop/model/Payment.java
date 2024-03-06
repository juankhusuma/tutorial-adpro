package id.ac.ui.cs.advprog.eshop.model;

import java.util.Map;

import enums.PaymentMethod;
import enums.PaymentStatus;
import lombok.Getter;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(String id, String method, Map<String, String> paymentData, Order order)
            throws IllegalArgumentException {
        this.id = id;
        this.setMethod(method);
        this.setPaymentData(paymentData);
        if (order == null) {
            throw new IllegalArgumentException();
        } else {
            this.order = order;
        }
    }

    public void setMethod(String method) throws IllegalArgumentException {
        if (PaymentMethod.contains(method)) {
            this.method = method;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setStatus(String status) throws IllegalArgumentException {
        if (PaymentStatus.contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setPaymentData(Map<String, String> paymentData) {
        if (paymentData == null) {
            throw new IllegalArgumentException();
        }
        this.paymentData = paymentData;
        if (this.method.equals(PaymentMethod.VOUCHER.getValue())) {
            if (paymentData.get("voucherCode") == null ||
                    paymentData.get("voucherCode").length() != 16 ||
                    !paymentData.get("voucherCode").startsWith("ESHOP")) {
                this.setStatus(PaymentStatus.REJECTED.getValue());
                return;
            }
            int digitCount = 0;
            for (int i = 0; i < paymentData.get("voucherCode").length(); i++) {
                if (Character.isDigit(paymentData.get("voucherCode").charAt(i))) {
                    digitCount++;
                }
            }
            if (digitCount != 8) {
                this.setStatus(PaymentStatus.REJECTED.getValue());
                return;
            }
        } else if (this.method.equals(PaymentMethod.COD.getValue())) {
            if (paymentData.get("address") == null || paymentData.get("deliveryFee") == null ||
                    paymentData.get("address").isEmpty() || paymentData.get("deliveryFee").isEmpty()) {
                this.setStatus(PaymentStatus.REJECTED.getValue());
                return;
            }
        }
        this.setStatus(PaymentStatus.SUCCESS.getValue());
    }
}
