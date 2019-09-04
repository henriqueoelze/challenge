package challenge.usecases;

import challenge.entities.Account;
import challenge.entities.Violation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProcessAccountTest {

    @InjectMocks
    private ProcessAccount processAccount;

    @Mock
    private AccountGateway accountGateway;

    @Test
    public void shouldReturnWhatAccountGatewayReturn() throws Violation {
        Account account = new Account();
        account.setActiveCard(true);
        when(accountGateway.createAccount(account)).thenReturn(account);
        Account execute = processAccount.execute(account);
        assertThat(execute).isSameInstanceAs(account);
    }
}