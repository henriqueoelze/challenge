package challenge.usecases.validations;

import challenge.entities.Account;
import challenge.entities.Violation;
import com.google.common.truth.Truth;
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
        account.setActiveCard(true);

        boolean validate = validation.validate(account, null);
        assertThat(validate).isTrue();
    }

    @Test
    public void shouldThrowErrorWhenCardIsBlocked() throws Violation {
        Account account = new Account();
        account.setActiveCard(false);

        try {
            validation.validate(account, null);
            assert_().withMessage("Should throw a violation").fail();
        } catch (Violation violation) {
            assertThat(violation.getValues()).hasSize(1);
            assertThat(violation.getValues().get(0)).isEqualTo("card-blocked");
            assertThat(violation.getAccount()).isEqualTo(account);
        }
    }

}