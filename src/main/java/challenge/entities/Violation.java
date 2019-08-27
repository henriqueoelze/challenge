package challenge.entities;

import java.util.Collections;
import java.util.List;

public class Violation extends Exception {
    private List<String> values;
    private Account account;

    public Violation(String value, Account account) {
        this.values = Collections.singletonList(value);
        this.account = account;
    }

    public Violation(List<String> value, Account account) {
        this.values = value;
        this.account = account;
    }

    public List<String> getValues() {
        return values;
    }

    public Account getAccount() {
        return account;
    }
}
