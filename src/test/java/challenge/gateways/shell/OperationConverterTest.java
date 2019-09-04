package challenge.gateways.shell;

import challenge.entities.Operation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static com.google.common.truth.Truth.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class OperationConverterTest {

    private OperationConverter operationConverter;

    @Before
    public void setup() {
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        operationConverter = new OperationConverter(om);
    }

    @Test
    public void shouldReturnCorrectObjectWhenValidAccountJson() {
        String jsonValue = "{\"account\":{\"activeCard\":true,\"availableLimit\":100}}";
        Operation operation = operationConverter.convert(jsonValue);
        assertThat(operation.getAccount()).isNotNull();
        assertThat(operation.getTransaction()).isNull();
        assertThat(operation.getViolations()).isEmpty();
        assertThat(operation.getAccount().isActiveCard()).isTrue();
        assertThat(operation.getAccount().getAvailableLimit()).isEqualTo(100.0);
    }

    @Test
    public void shouldReturnCorrectObjectWhenValidTransactionJson() {
        String jsonValue = "{\"transaction\":{\"merchant\":\"BurguerKing\"," +
                "\"amount\":10,\"time\":\"2019-08-25T21:57:00.000Z\"}}";
        Operation operation = operationConverter.convert(jsonValue);
        assertThat(operation.getAccount()).isNull();
        assertThat(operation.getTransaction()).isNotNull();
        assertThat(operation.getViolations()).isEmpty();
        assertThat(operation.getTransaction().getMerchant()).isEqualTo("BurguerKing");
        assertThat(operation.getTransaction().getAmount()).isEqualTo(10.0);
        assertThat(operation.getTransaction().getTime()).isEqualTo(LocalDateTime.parse("2019-08-25T21:57:00"));
    }

    @Test
    public void shouldReturnNullWhenInvalidJson() {
        String jsonValue = "dsadsa";
        Operation operation = operationConverter.convert(jsonValue);
        assertThat(operation).isNull();
    }
}