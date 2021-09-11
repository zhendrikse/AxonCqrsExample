package exploringaxon.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;
import exploringaxon.model.AccountNumber;

public class DebitAccountCommand {

    @TargetAggregateIdentifier
    public final String account;

    public final Double amount;

    public final String currency;

    public DebitAccountCommand(AccountNumber account, Double amount, String currency) {
        this.account = account.asString();
        this.amount = amount;
        this.currency = currency;
    }
}
