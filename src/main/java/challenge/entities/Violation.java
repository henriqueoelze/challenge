package challenge.entities;

public class Violation extends Exception {
    private String value;
    private Account account;

    public Violation(String value, Account account) {
        this.value = value;
        this.account = account;
    }

    public String getValue() {
        return value;
    }

    public Account getAccount() {
        return account;
    }
}
