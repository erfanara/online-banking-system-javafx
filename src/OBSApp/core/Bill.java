package OBSApp.core;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Bill {
    public enum Type {
        WATER(1),
        POWER(2),
        GAS(3),
        PHONE(4);

        private int value;

        private Type(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private final Type type;
    private final BigDecimal amount;
    private final BigDecimal remainingDebt;
    private final LocalDateTime creationDate = LocalDateTime.now();
    private final String subsidiaryCompanyId;
    // source : https://hamrahcard.ir/%D8%B4%D9%86%D8%A7%D8%B3%D9%87-%D9%82%D8%A8%D8%B6-%D9%88-%D9%BE%D8%B1%D8%AF%D8%A7%D8%AE%D8%AA-%DA%86%D9%87-%D9%85%D8%B9%D9%86%D8%A7%DB%8C%DB%8C-%D8%AF%D8%A7%D8%B1%D8%AF%D8%9F/
    private final String id;
    private final String paymentId;
    private boolean wasPaid;

    // userHash is user's nationalCode
    public Bill(Type type, BigDecimal amount, BigDecimal remainingDebt, String subsidiaryCompanyId, String userHash) {
        this.type = type;
        this.amount = amount.add(remainingDebt);
        this.remainingDebt = remainingDebt;
        this.subsidiaryCompanyId = subsidiaryCompanyId;

        this.id = userHash + subsidiaryCompanyId + type.getValue();
        // TODO: adding Billing period to the paymentId
        this.paymentId = this.amount.toString() + creationDate.getYear();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }

    public String getPaymentId() {
        return paymentId;
    }
}

