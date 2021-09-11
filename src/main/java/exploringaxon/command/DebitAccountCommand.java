package exploringaxon.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class DebitAccountCommand {

    @TargetAggregateIdentifier
    private final String account;

    private final Double amount;

    public DebitAccountCommand(final String account, final Double amount) {
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
