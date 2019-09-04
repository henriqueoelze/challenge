package challenge.gateways.shell;

import challenge.entities.Account;
import challenge.entities.Operation;
import challenge.entities.Transaction;
import challenge.entities.Violation;
import challenge.usecases.ProcessAccount;
import challenge.usecases.ProcessTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShellControllerTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Captor
    public ArgumentCaptor<Operation> operationArgumentCaptor;

    @InjectMocks
    private ShellController shellController;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private ProcessAccount processAccount;

    @Mock
    private ProcessTransaction processTransaction;

    @Test
    public void authorizeWithAccountShouldReturnWhatProcessAccountReturns() throws IOException, Violation {
        Operation operation = new Operation();
        Account account = new Account();
        operation.setAccount(account);

        Account expectedAccount = new Account();
        expectedAccount.setAvailableLimit(100.0);
        when(processAccount.execute(account)).thenReturn(expectedAccount);

        shellController.authorize(operation);
        verify(objectMapper).writeValueAsString(operationArgumentCaptor.capture());
        Operation capturedValue = operationArgumentCaptor.getValue();
        assertThat(capturedValue.getAccount()).isSameInstanceAs(expectedAccount);

        verify(processTransaction, times(0)).execute(any());
    }

    @Test
    public void authorizeWithAccountShouldReturnErrorWhenViolation() throws IOException, Violation {
        Operation operation = new Operation();
        Account account = new Account();
        operation.setAccount(account);

        String violationText = "my-violation";
        Violation violation = new Violation(violationText, account);
        when(processAccount.execute(account)).thenThrow(violation);

        shellController.authorize(operation);
        verify(objectMapper).writeValueAsString(operationArgumentCaptor.capture());
        Operation capturedValue = operationArgumentCaptor.getValue();
        assertThat(capturedValue.getAccount()).isSameInstanceAs(account);
        assertThat(capturedValue.getViolations()).contains(violationText);

        verify(processTransaction, times(0)).execute(any());
    }

    @Test
    public void authorizeWithTransactionShouldReturnWhatProcessTransactionReturns() throws IOException, Violation {
        Operation operation = new Operation();
        Transaction transaction = new Transaction();
        operation.setTransaction(transaction);

        Account expectedAccount = new Account();
        expectedAccount.setAvailableLimit(100.0);
        when(processTransaction.execute(transaction)).thenReturn(expectedAccount);

        shellController.authorize(operation);
        verify(objectMapper).writeValueAsString(operationArgumentCaptor.capture());
        Operation capturedValue = operationArgumentCaptor.getValue();
        assertThat(capturedValue.getAccount()).isSameInstanceAs(expectedAccount);

        verify(processAccount, times(0)).execute(any());
    }

    @Test
    public void authorizeWithTransactionShouldReturnErrorWhenViolation() throws IOException, Violation {
        Operation operation = new Operation();
        Transaction transaction = new Transaction();
        operation.setTransaction(transaction);

        Account expectedAccount = new Account();
        String violationText = "my-violation";
        Violation violation = new Violation(violationText, expectedAccount);
        when(processTransaction.execute(transaction)).thenThrow(violation);

        shellController.authorize(operation);
        verify(objectMapper).writeValueAsString(operationArgumentCaptor.capture());
        Operation capturedValue = operationArgumentCaptor.getValue();
        assertThat(capturedValue.getAccount()).isSameInstanceAs(expectedAccount);
        assertThat(capturedValue.getViolations()).contains(violationText);

        verify(processAccount, times(0)).execute(any());
    }

}