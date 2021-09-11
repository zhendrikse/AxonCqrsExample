package exploringaxon.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class CreditAccountCommand {

    @TargetAggregateIdentifier
    private final String account;

    private final Double amount;

    public CreditAccountCommand(final String account, final Double amount) {
        this.account = account;
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public Double getAmount() {
        return amount;
    }
}
