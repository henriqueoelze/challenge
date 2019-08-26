package challenge.controllers;

import challenge.entities.Account;
import challenge.entities.Operation;
import challenge.entities.Transaction;
import challenge.entities.Violation;
import challenge.usecases.ProcessAccount;
import challenge.usecases.ProcessTransaction;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OperationController {

    private ProcessAccount processAccount;
    private ProcessTransaction processTransaction;

    public OperationController(ProcessAccount processAccount, ProcessTransaction processTransaction) {
        this.processAccount = processAccount;
        this.processTransaction = processTransaction;
    }

    @PostMapping("/operations")
    public Operation processOperation(@RequestBody Operation requestOperation) {

        Operation operation = new Operation();
        try {
            if (requestOperation.getAccount() != null) {
                operation.setAccount(processAccountMessage(requestOperation.getAccount()));
            } else {
                operation.setAccount(processTransactionOperation(requestOperation.getTransaction()));
            }
        } catch (Violation violation) {
            operation.setAccount(violation.getAccount());
            operation.addViolation(violation.getValue());
        }

        return operation;
    }

    private Account processAccountMessage(Account account) throws Violation {
        return processAccount.execute(account);
    }

    private Account processTransactionOperation(Transaction transaction) throws Violation {
        return processTransaction.execute(transaction);
    }


}
