package challenge.usecases;

import challenge.entities.Account;
import challenge.entities.Transaction;
import challenge.entities.Violation;
import org.springframework.stereotype.Service;

@Service
public class ActiveCardValidation implements BankValidation {

    @Override
    public boolean validate(Account account, Transaction transaction) throws Violation {
        if (!account.isActiveCard()) {
            throw new Violation("card-blocked", account);
        }

        return true;
    }
}
