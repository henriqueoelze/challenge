package challenge.usecases.validations;

import challenge.entities.Account;
import challenge.entities.Transaction;
import challenge.entities.Violation;
import challenge.usecases.BankValidation;
import org.springframework.stereotype.Service;

@Service
public class SufficientLimitValidation implements BankValidation {

    @Override
    public void validate(Account account, Transaction transaction) throws Violation {
        if (account.getAvailableLimit() < transaction.getAmount()) {
            throw new Violation("insufficient-limit", account);
        }
    }
}
