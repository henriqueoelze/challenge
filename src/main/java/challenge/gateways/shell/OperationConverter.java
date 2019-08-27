package challenge.gateways.shell;

import challenge.entities.Operation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OperationConverter implements Converter<String, Operation> {

    private ObjectMapper objectMapper;

    @Autowired
    public OperationConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Operation convert(String s) {
        try {
            return objectMapper.readValue(s, Operation.class);
        } catch (IOException e) {
            return null;
        }
    }
}
