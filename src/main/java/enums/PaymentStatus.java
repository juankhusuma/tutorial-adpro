package enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    SUCCESS("SUCCESS"),
    CANCELLED("REJECTED");

    private final String value;

    private PaymentStatus(String value) {
        this.value = value;
    }

    public static boolean contains(String param) {
        for (PaymentStatus status : PaymentStatus.values()) {
            if (status.value.equals(param)) {
                return true;
            }
        }
        return false;
    }
}
