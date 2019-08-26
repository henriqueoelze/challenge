package challenge.usecases;

import challenge.entities.Account;
import challenge.entities.Transaction;
import challenge.entities.Violation;
import org.springframework.stereotype.Service;

@Service
public class DoubleTransactionValidation implements BankValidation {

    @Override
    public boolean validate(Account account, Transaction transaction) throws Violation {
        long count = account.getTransactionHistory()
                .stream()
                .filter(t -> t.getMerchant().equals(transaction.getMerchant()) && t.getAmount() == transaction.getAmount())
                .count();

        if (count >= 2) {
            throw new Violation("doubled-transaction", account);
        }

        return true;
    }
}
