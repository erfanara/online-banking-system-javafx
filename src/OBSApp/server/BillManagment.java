package OBSApp.server;

import OBSApp.core.Account;
import OBSApp.core.Bill;
import OBSApp.core.User;
import OBSApp.core.exceptions.BillAlreadyPaidException;
import OBSApp.core.exceptions.BillNotFoundException;

import java.math.BigDecimal;

public class BillManagment {
    private static Bill createBill(User user, Bill.Type type, BigDecimal amount, String subsidiaryCompanyId) {
        Bill bill = new Bill(type, amount, subsidiaryCompanyId, user.getNationalCode());
        user.currentBills.put(bill.getId() + bill.getPaymentId(), bill);
        return bill;
    }

    public static boolean payBill(User user, Account fromAcc, String billId, String paymentId)
            throws BillNotFoundException, BillAlreadyPaidException {
        String key = billId + paymentId;
        if (!user.paidBills.containsKey(key)) {
            Bill bill = user.currentBills.get(key);
            if (bill != null) {
                boolean success = fromAcc.withdraw(bill.getAmount(), ServerCli.motherAcc);
                if (success) {
                    user.paidBills.put(key, bill);
                    user.currentBills.remove(key);
                }
                return success;
            } else {
                throw new BillNotFoundException();
            }
        } else {
            throw new BillAlreadyPaidException();
        }
    }
}
