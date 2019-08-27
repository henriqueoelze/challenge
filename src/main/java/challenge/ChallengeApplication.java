package challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

@SpringBootApplication
public class ChallengeApplication {

    public static void main(String[] args) {
        String[] disabledCommands = {
                "--spring.shell.command.stacktrace.enabled=false",
                "--spring.shell.command.script.enabled=false"};
        String[] fullArgs = StringUtils.concatenateStringArrays(args, disabledCommands);
        SpringApplication.run(ChallengeApplication.class, fullArgs);
    }
}