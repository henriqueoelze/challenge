# Nu Bank Challenge
### By Henrique Oelze

This zip file contains all code and instructions related to the giving challenge.

The only pre-requisite for the machine is to have JRE on it in because the solution was build using Java 8. Link if necessary: https://www.java.com/pt_BR/download/

In order to run the application, please execute the following command inside the folder with the zip content:

`./run.sh` 
(OR `mvnw clean install` AND `mvnw spring-boot:run`)

The execution of the command above should lead to a interactive terminal.

This terminal have two custom commands, plus others already provided by spring shell.

The commands can be described executing the command:

`shell:>help`

#### Tech stack
- Java 8
- Spring Boot
- Spring Shell
- Maven
- Google Truth

#### Highlights of the implementation
1. The challenge was implemented using Java 8 stream feature.
2. The architecture is following the Clean Architecture pattern. Ref: https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html
3. All unit test implemented using Google Truth framework for asserts: https://github.com/google/truth
4. The challenge is using Spring Shell to provide everything as a command line tool. Feel free to experiment the native commands, like `help`, `history` and `clear`.
5. I used some of the Spring features to achieve a kind of `Validations Workflow`. If necessary to include new validations, you should only create a class implementing `BankValidation` and everything will work without any change on any other class.

### Improvements to be done
1. The solution is missing unit tests for `authorize-files`. Assuming the deadline and my free time for the challenge, and assuming that this method will be used only as a test purpose (not functional code), this test lost priority between other features/tests.
2. Some validations maybe need to be changed. I implemented using the PDF as guide, and for example `"There should not be more than 2 similar transactions (same amount and merchant) in a 2 minutes interval"` is saying "more than 2", so the validation is allowing 2 similiar transactions and only return error when receive the third one. Maybe this was a miss understand of the feature, but I think it is important to mention here.
3. I believe that only unit tests are not enough for almost every kind of application, and it is not different for this challenge. Assuming my deadline vs free time I didn't have enough time to code my end-to-end tests, but it will be my next task. If you want to see some examples of how to build end-to-end tests with Spring Shell, please check https://github.com/spring-projects/spring-shell/tree/master/spring-shell-test-samples/src/test/java/com/example/test.
4. Dockernize the build... unfortunately I didn't have time for that at the end.
