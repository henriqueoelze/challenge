package challenge.usecases.validations;

import challenge.entities.Account;
import challenge.entities.Violation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assert_;

@RunWith(MockitoJUnitRunner.class)
public class ActiveCardValidationTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ActiveCardValidation validation;

    @Before
    public void setup() {
        this.validation = new ActiveCardValidation();
    }

    @Test
    public void shouldNotReturnErrorWhenCardIsNotBlocked() throws Violation {
        Account account = new Account();
        account.setActiveCard(false);

        validation.validate(account, null);
    }

    @Test
    public void shouldThrowErrorWhenCardIsBlocked() {
        Account account = new Account();
        account.setActiveCard(false);

        try {
            validation.validate(account, null);
            assert_().withMessage("Should throw a violation").fail();
        } catch (Violation violation) {
            assertThat(violation.getValues()).hasSize(1);
            assertThat(violation.getValues().get(1)).isEqualTo("card-blocked");
            assertThat(violation.getAccount()).isEqualTo(account);
        }
    }

}
