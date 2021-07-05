package OBSApp.server;

import OBSApp.core.Account;
import OBSApp.core.Bill;
import OBSApp.core.User;

import java.math.BigDecimal;

public class BillManagment {
    public static Bill createBill(User user, Bill.Type type, BigDecimal amount, String subsidiaryCompanyId) {
        Bill bill = new Bill(type, amount, user.getBillAmount(), subsidiaryCompanyId, user.getNationalCode());
        user.currentBills.put(bill.getId() + bill.getPaymentId(), bill);
        return bill;
    }

    public static boolean payBill(User user, Account fromAcc, String billId, String paymentId) {
        String key = billId + paymentId;
        if (!user.paidBills.containsKey(key)) {
            Bill bill = user.currentBills.get(key);
            if (bill != null) {
                return fromAcc.withdraw(bill.getAmount(), ServerCli.motherAcc);
            } else {
                // TODO: throw Exception
            }
        } else {
            // TODO : throw exception
        }
        return false;
    }

    public void createBillsAutomatically() {

    }
}
