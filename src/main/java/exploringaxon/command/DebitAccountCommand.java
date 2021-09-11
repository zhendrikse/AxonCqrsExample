package exploringaxon.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class DebitAccountCommand {

    @TargetAggregateIdentifier
    public final String account;

    public final Double amount;

    public DebitAccountCommand(String account, Double amount) {
        this.account = account;
        this.amount = amount;
    }
}
