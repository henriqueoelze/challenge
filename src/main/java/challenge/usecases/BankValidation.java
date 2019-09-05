package challenge.usecases;

import challenge.entities.Account;
import challenge.entities.Transaction;
import challenge.entities.Violation;
import org.springframework.stereotype.Service;

@Service
public interface BankValidation {

    void validate(Account account, Transaction transaction) throws Violation;
}
