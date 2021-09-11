package exploringaxon.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import exploringaxon.model.AccountNumber;

public class DebitAccountCommand {

    @TargetAggregateIdentifier
    public final String account;

    public final Double amount;

    public DebitAccountCommand(AccountNumber account, Double amount) {
        this.account = account.asString();
        this.amount = amount;
    }
}
