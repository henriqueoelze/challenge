package challenge.usecases;

import challenge.entities.Account;
import challenge.entities.Transaction;
import challenge.entities.Violation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessTransaction {

    private AccountGateway accountGateway;
    private List<BankValidation> validations;

    @Autowired
    public ProcessTransaction(AccountGateway accountGateway, List<BankValidation> validations) {
        this.accountGateway = accountGateway;
        this.validations = validations;
    }

    public Account execute(Transaction transaction) throws Violation {
        Account account = accountGateway.getAccount();
        for (BankValidation validation : validations) {
            validation.validate(account, transaction);
        }

        account.acceptTransaction(transaction);
        return accountGateway.saveAccount(account);
    }

}
