package challenge.usecases;

import challenge.entities.Account;
import challenge.entities.Violation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessAccount {

    private AccountGateway accountGateway;

    @Autowired
    public ProcessAccount(AccountGateway accountGateway) {
        this.accountGateway = accountGateway;
    }

    public Account execute(Account account) throws Violation {
        return accountGateway.createAccount(account);
    }

}
