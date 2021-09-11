package exploringaxon.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

public class CreateAccountCommand {

    public final double balance;

    public final String currency;

    @TargetAggregateIdentifier
    public final String id;

    public CreateAccountCommand(String id, double accountBalance, String currency) {
        this.id = id;
        this.balance = accountBalance;
        this.currency = currency;
    }
}
