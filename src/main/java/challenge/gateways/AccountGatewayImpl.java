package challenge.gateways;

import challenge.entities.Account;
import challenge.entities.Violation;
import challenge.usecases.AccountGateway;
import org.springframework.stereotype.Service;

@Service
public class AccountGatewayImpl implements AccountGateway {

    private Account currentAccount;

    public Account createAccount(Account account) throws Violation {
        if (currentAccount != null) {
            throw new Violation("account-already-initialized", this.currentAccount);
        }

        this.currentAccount = account;
        return this.currentAccount;
    }

    @Override
    public Account getAccount() {
        return this.currentAccount;
    }

    @Override
    public Account saveAccount(Account account) {
        this.currentAccount = account;
        return this.currentAccount;
    }
}
