package challenge.usecases.validations;

import challenge.entities.Account;
import challenge.entities.Transaction;
import challenge.entities.Violation;
import challenge.usecases.BankValidation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HighFrequencyValidation implements BankValidation {

    @Override
    public boolean validate(Account account, Transaction transaction) throws Violation {
        long count = account.getTransactionHistory()
                .stream()
                .filter(t -> LocalDateTime.now().minusMinutes(2).isBefore(t.getTime()))
                .count();

        if (count >= 3) {
            throw new Violation("high-frequency-small-interval", account);
        }

        return true;
    }
}
