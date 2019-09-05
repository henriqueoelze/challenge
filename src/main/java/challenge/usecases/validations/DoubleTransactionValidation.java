package challenge.usecases.validations;

import challenge.entities.Account;
import challenge.entities.Transaction;
import challenge.entities.Violation;
import challenge.usecases.BankValidation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DoubleTransactionValidation implements BankValidation {

    @Override
    public void validate(Account account, Transaction transaction) throws Violation {
        long count = account.getTransactionHistory()
                .stream()
                .filter(t -> LocalDateTime.now().minusMinutes(2).isBefore(t.getTime()))
                .filter(t -> t.getMerchant().equals(transaction.getMerchant()) && t.getAmount() == transaction.getAmount())
                .count();

        if (count >= 2) {
            throw new Violation("doubled-transaction", account);
        }
    }
}
