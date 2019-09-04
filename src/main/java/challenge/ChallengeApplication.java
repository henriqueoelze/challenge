package challenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ChallengeApplication {
    @Autowired
    private ObjectMapper objectMapper;

    public static void main(String[] args) {
        String[] disabledCommands = {
                "--spring.shell.command.stacktrace.enabled=false",
                "--spring.shell.command.script.enabled=false"};
        String[] fullArgs = StringUtils.concatenateStringArrays(args, disabledCommands);
        SpringApplication.run(ChallengeApplication.class, fullArgs);
    }

    @PostConstruct
    public void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }
}