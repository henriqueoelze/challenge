package challenge.gateways.account;

import challenge.entities.Account;
import challenge.entities.Violation;
import challenge.usecases.AccountGateway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assert_;

@RunWith(MockitoJUnitRunner.class)
public class AccountGatewayImplTest {

    private AccountGateway gateway;

    @Before
    public void setup() {
        gateway = new AccountGatewayImpl();
    }

    @Test
    public void createAccountShouldSaveAccount() throws Violation {
        Account account = new Account();
        gateway.createAccount(account);
        Account accountAfterGet = gateway.getAccount();

        assertThat(accountAfterGet).isSameInstanceAs(account);
    }

    @Test
    public void saveAccountShouldReplaceInMemoryAccount() throws Violation {
        Account account = new Account();
        gateway.createAccount(account);
        Account account2 = new Account();
        account2.setActiveCard(false);
        gateway.saveAccount(account2);

        assertThat(gateway.getAccount()).isSameInstanceAs(account2);
    }

    @Test
    public void createAccountShouldReturnViolationIfAccountAlreadyExists() throws Violation {
        Account account = new Account();
        gateway.saveAccount(account);

        try {
            gateway.createAccount(account);
            assert_().withMessage("Should throw a violation").fail();
        } catch (Violation violation) {
            assertThat(violation.getValues()).hasSize(1);
            assertThat(violation.getValues().get(0)).isEqualTo("account-already-initialized");
            assertThat(violation.getAccount()).isEqualTo(account);
        }
    }
}