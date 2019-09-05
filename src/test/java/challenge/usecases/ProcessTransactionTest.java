package challenge.usecases;

import challenge.entities.Account;
import challenge.entities.Transaction;
import challenge.entities.Violation;
import challenge.usecases.validations.ActiveCardValidation;
import challenge.usecases.validations.DoubleTransactionValidation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProcessTransactionTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Captor
    public ArgumentCaptor<Account> accountArgumentCaptor;

    @Mock
    private AccountGateway accountGateway;

    @Mock
    public ActiveCardValidation activeCardValidation;

    @Mock
    public DoubleTransactionValidation doubleTransactionValidation;

    private ProcessTransaction processTransaction;

    @Before
    public void setup() {
        processTransaction = new ProcessTransaction(
                accountGateway,
                Arrays.asList(activeCardValidation, doubleTransactionValidation));
    }

    @Test
    public void shouldProcessWithoutAnyViolation() throws Violation {
        Account account = new Account();
        account.setActiveCard(true);

        Transaction transaction = new Transaction();
        transaction.setMerchant("Test");

        when(accountGateway.getAccount()).thenReturn(account);

        processTransaction.execute(transaction);
        account.acceptTransaction(transaction);


        verify(accountGateway).saveAccount(accountArgumentCaptor.capture());
        Account captured = accountArgumentCaptor.getValue();

        assertThat(captured).isEqualTo(account);
    }

    @Test
    public void shouldProcessWithoutJustOneViolation() throws Violation {
        Account account = new Account();
        account.setActiveCard(true);

        Transaction transaction = new Transaction();
        transaction.setMerchant("Test");

        when(accountGateway.getAccount()).thenReturn(account);
        Violation doubleViolation = new Violation("double", account);
        doThrow(doubleViolation).when(doubleTransactionValidation).validate(account, transaction);

        try {
            processTransaction.execute(transaction);
        } catch (Violation violation) {
            assertThat(violation.getValues()).hasSize(1);
            assertThat(violation.getValues()).containsExactlyElementsIn(doubleViolation.getValues());
        }
    }

    @Test
    public void shouldProcessWithoutMoreThenOneViolation() throws Violation {
        Account account = new Account();
        account.setActiveCard(true);

        Transaction transaction = new Transaction();
        transaction.setMerchant("Test");

        when(accountGateway.getAccount()).thenReturn(account);
        Violation activeViolation = new Violation("active", account);
        doThrow(activeViolation).when(activeCardValidation).validate(account, transaction);
        Violation doubleViolation = new Violation("double", account);
        doThrow(doubleViolation).when(doubleTransactionValidation).validate(account, transaction);

        try {
            processTransaction.execute(transaction);
        } catch (Violation violation) {
            assertThat(violation.getValues()).hasSize(2);
            assertThat(violation.getValues()).containsAtLeastElementsIn(activeViolation.getValues());
            assertThat(violation.getValues()).containsAtLeastElementsIn(doubleViolation.getValues());
        }
    }

}