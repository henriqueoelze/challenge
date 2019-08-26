package challenge.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private boolean activeCard;
    private double availableLimit;

    @JsonIgnore
    private List<Transaction> transactionHistory;

    public Account() {
        this.transactionHistory = new ArrayList<>();
    }

    public boolean isActiveCard() {
        return activeCard;
    }

    public void setActiveCard(boolean activeCard) {
        this.activeCard = activeCard;
    }

    public double getAvailableLimit() {
        return availableLimit;
    }

    public void setAvailableLimit(double availableLimit) {
        this.availableLimit = availableLimit;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void acceptTransaction(Transaction transaction) {
        transactionHistory.add(transaction);
        this.availableLimit -= transaction.getAmount();
    }
}
