package challenge.entities;

import java.util.ArrayList;
import java.util.List;

public class Operation {

    private Account account;
    private Transaction transaction;
    private List<String> violations;

    public Operation() {
        this.violations = new ArrayList<>();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public List<String> getViolations() {
        return violations;
    }

    public void addViolations(List<String> violation) {
        this.violations.addAll(violation);
    }
}
