package exploringaxon.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
public class CreditAccountCommand {

    @TargetAggregateIdentifier
    public final String account;

    public final Double amount;

    public CreditAccountCommand(String account, Double amount) {
        this.account = account;
        this.amount = amount;
    }
}
