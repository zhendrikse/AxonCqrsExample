package exploringaxon.model;

import exploringaxon.command.CreateAccountCommand;
import exploringaxon.command.CreditAccountCommand;
import exploringaxon.command.DebitAccountCommand;
import exploringaxon.event.AccountCreatedEvent;
import exploringaxon.event.AccountCreditedEvent;
import exploringaxon.event.AccountDebitedEvent;
import exploringaxon.model.AccountNumber;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

/**
 * Entity that models the Account.
 */
public class Account extends AbstractAnnotatedAggregateRoot {
    private static final long serialVersionUID = 8723320580782813954L;

    @AggregateIdentifier
    private String accountNo;

    private Double balance;

    private String currency;
  
    private String status;

    public Account() {
    }

    public Account(final CreateAccountCommand command) {
        apply(new AccountCreatedEvent(new AccountNumber(command.id), command.balance, command.currency));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountNo = event.accountNo.asString();
        this.balance = event.balance;
        this.currency = event.currency;
        this.status = String.valueOf(Status.CREATED);
    }

    // Business Logic:
    // Cannot debit with a negative amount
    // Cannot debit with amount that leaves the account balance in a negative state
    private boolean isDebitTransactionAllowed(final Double debitAmount) {
        return Double.compare(debitAmount, 0.0d) > 0 && this.balance - debitAmount > -1;
    } 

    @CommandHandler
    public void on(final DebitAccountCommand debitAccountCommand) {
        if (isDebitTransactionAllowed(debitAccountCommand.amount))
            /**
             * Instead of changing the state directly we apply an event
             * that conveys what happened.
             *
             * The event thus applied is stored.
             */
            apply(new AccountDebitedEvent(new AccountNumber(this.accountNo), debitAccountCommand.amount, this.balance));
        else
            throw new IllegalArgumentException("Cannot debit with the amount");
    }

    @EventSourcingHandler
    private void on(final AccountDebitedEvent event) {
        // This method is called as a reflection of applying events stored in the event store.
        // Consequent application of all the events in the event store will bring the Account
        // to the most recent state.
        this.balance -= event.amountDebited;
    }

    // Business Logic:
    // Cannot credit with a negative amount
    // Cannot credit with more than a million amount (You laundering money?)
    private boolean isCreditTransactionAllowed(final Double creditAmount) {
        return Double.compare(creditAmount, 0.0d) > 0 && Double.compare(creditAmount, 1000000) < 0;
    }

    @CommandHandler
    public void on(final CreditAccountCommand creditAccountCommand) {
        if (isCreditTransactionAllowed(creditAccountCommand.amount))
            // Instead of changing the state directly we apply an event
            // that conveys what happened.
            //
            // The event thus applied is stored.
            apply(new AccountCreditedEvent(new AccountNumber(this.accountNo), creditAccountCommand.amount, this.balance));
        else 
            throw new IllegalArgumentException("Cannot credit with the amount");
    }

    @EventSourcingHandler
    private void on(final AccountCreditedEvent event) {
        // This method is called as a reflection of applying events stored in the event store.
        // Consequent application of all the events in the event store will bring the Account
        // to the most recent state.
        this.balance += event.amountCredited;
    }

    public Double getBalance() {
        return balance;
    }

    public void setIdentifier(final String id) {
        this.accountNo = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Object getIdentifier() {
        return accountNo;
    }
}
