package challenge.gateways.shell;

import challenge.entities.Account;
import challenge.entities.Operation;
import challenge.entities.Transaction;
import challenge.entities.Violation;
import challenge.usecases.ProcessAccount;
import challenge.usecases.ProcessTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@ShellComponent
public class ShellController {

    private ObjectMapper objectMapper;
    private ProcessAccount processAccount;
    private ProcessTransaction processTransaction;

    @Autowired
    public ShellController(
            ObjectMapper objectMapper,
            ProcessAccount processAccount,
            ProcessTransaction processTransaction) {
        this.objectMapper = objectMapper;
        this.processAccount = processAccount;
        this.processTransaction = processTransaction;
    }

    @ShellMethod(value="Authorize a specific Operation", key="authorize")
    public void authorize(Operation operation) throws IOException {
        authorizeOperation(operation);
    }

    @ShellMethod(value="Authorize all operation inside the file", key="authorize-file")
    public void authorizeFile(String path) throws IOException {
        File file = ResourceUtils.getFile(path);
        BufferedReader b = new BufferedReader(new FileReader(file));
        String readLine = "";
        while ((readLine = b.readLine()) != null) {
            authorizeOperation(objectMapper.readValue(readLine, Operation.class));
        }
    }

    private void authorizeOperation(Operation requestOperation) throws IOException {
        Operation operation = new Operation();
        try {
            if (requestOperation.getAccount() != null) {
                operation.setAccount(processAccountMessage(requestOperation.getAccount()));
            } else {
                operation.setAccount(processTransactionOperation(requestOperation.getTransaction()));
            }
        } catch (Violation violation) {
            operation.setAccount(violation.getAccount());
            operation.addViolations(violation.getValues());
        }

        System.out.println(objectMapper.writeValueAsString(operation));
    }

    private Account processAccountMessage(Account account) throws Violation {
        return processAccount.execute(account);
    }

    private Account processTransactionOperation(Transaction transaction) throws Violation {
        return processTransaction.execute(transaction);
    }
}
