package challenge.usecases.validations;

import challenge.entities.Account;
import challenge.entities.Transaction;
import challenge.entities.Violation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assert_;

@RunWith(MockitoJUnitRunner.class)
public class HighFrequencyValidationTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private HighFrequencyValidation validation;

    @Before
    public void setup() {
        this.validation = new HighFrequencyValidation();
    }

    @Test
    public void shouldNotReturnErrorWhenEmptyHistory() throws Violation {
        Account account = new Account();
        Transaction newTransaction = getTransaction(100.0, "Merchant 1", LocalDateTime.now());
        validation.validate(account, newTransaction);
    }

    @Test
    public void shouldNotReturnErrorWhenOneEntryAtHistoryWithLessThenTwoMinutes() throws Violation {
        Account account = new Account();
        account.acceptTransaction(
                getTransaction(100.0, "Merchant 1", LocalDateTime.now().minusMinutes(1L)));

        Transaction newTransaction = getTransaction(100.0, "Merchant 1", LocalDateTime.now());
        validation.validate(account, newTransaction);
    }

    @Test
    public void shouldNotReturnErrorWhenTwoEntryAtHistoryWithLessThenTwoMinutes() throws Violation {
        Account account = new Account();
        account.acceptTransaction(
                getTransaction(100.0, "Merchant 1", LocalDateTime.now().minusMinutes(1L)));
        account.acceptTransaction(
                getTransaction(10.0, "Merchant 1", LocalDateTime.now().minusMinutes(1L)));

        Transaction newTransaction = getTransaction(100.0, "Merchant 1", LocalDateTime.now());
        validation.validate(account, newTransaction);
    }

    @Test
    public void shouldReturnErrorWhenThreeTransactionsWithLessThenTwoMinutes() {
        Account account = new Account();
        account.acceptTransaction(
                getTransaction(10.0, "Merchant 1", LocalDateTime.now().minusMinutes(1L)));
        account.acceptTransaction(
                getTransaction(100.0, "Merchant 1", LocalDateTime.now().minusMinutes(1L)));
        account.acceptTransaction(
                getTransaction(1.0, "Merchant 1", LocalDateTime.now().minusMinutes(1L)));

        Transaction newTransaction = getTransaction(100.0, "Merchant 1", LocalDateTime.now());
        try {
            validation.validate(account, newTransaction);
            assert_().withMessage("Should throw a violation").fail();
        } catch (Violation violation) {
            assertThat(violation.getValues()).hasSize(1);
            assertThat(violation.getValues().get(0)).isEqualTo("high-frequency-small-interval");
            assertThat(violation.getAccount()).isEqualTo(account);
        }
    }

    private Transaction getTransaction(Double value, String merchant, LocalDateTime time) {
        Transaction transaction = new Transaction();
        transaction.setAmount(value);
        transaction.setMerchant(merchant);
        transaction.setTime(time);

        return transaction;
    }
}