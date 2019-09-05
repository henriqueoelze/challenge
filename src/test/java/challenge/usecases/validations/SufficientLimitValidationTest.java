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
public class SufficientLimitValidationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private SufficientLimitValidation validation;

    @Before
    public void setup() {
        this.validation = new SufficientLimitValidation();
    }

    @Test
    public void shouldNotReturnErrorWhenLimitIsOk() throws Violation {
        Account account = new Account();
        account.setAvailableLimit(110.0);
        Transaction newTransaction = getTransaction(100.0, "Merchant 2", LocalDateTime.now());

        validation.validate(account, newTransaction);
    }

    @Test
    public void shouldThrowErrorWhenLimitIsLowerThenTheTransaction() {
        Account account = new Account();
        account.setAvailableLimit(99.0);
        Transaction newTransaction = getTransaction(100.0, "Merchant 2", LocalDateTime.now());

        try {
            validation.validate(account, newTransaction);
            assert_().withMessage("Should throw a violation").fail();
        } catch (Violation violation) {
            assertThat(violation.getValues()).hasSize(1);
            assertThat(violation.getValues().get(0)).isEqualTo("insufficient-limit");
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