package challenge.usecases;

import challenge.entities.Account;
import challenge.entities.Violation;
import org.springframework.stereotype.Service;

@Service
public interface AccountGateway {
    Account createAccount(Account account) throws Violation;
    Account getAccount();
    Account saveAccount(Account account);
}
