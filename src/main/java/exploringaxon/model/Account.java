package exploringaxon.model;

import exploringaxon.api.command.CreditAccountCommand;
import exploringaxon.api.command.DebitAccountCommand;
import exploringaxon.api.event.AccountCreatedEvent;
import exploringaxon.api.event.AccountCreditedEvent;
import exploringaxon.api.event.AccountDebitedEvent;
import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

/**
 * Entity that models the Account
 *
 * Created by Dadepo Aderemi.
 */

public class Account extends AbstractAnnotatedAggregateRoot {
    private static final long serialVersionUID = 8723320580782813954L;

    @AggregateIdentifier
    private String accountNo;

    private Double balance;

    public Account() {
    }

    public Account(String accountNo) {
        apply(new AccountCreatedEvent(accountNo));
    }

    @EventSourcingHandler
    public void applyAccountCreation(AccountCreatedEvent event) {
        this.accountNo = event.getAccountNo();
        this.balance = 0.0d;
    }

    /**
     * Business Logic:
     * Cannot debit with a negative amount
     * Cannot debit with amount that leaves the account balance in a negative state
     *
     * @param debitAccountCommand
     */
    @CommandHandler
    public void debit(DebitAccountCommand debitAccountCommand) {
        Double debitAmount = debitAccountCommand.getAmount();
        if (Double.compare(debitAmount, 0.0d) > 0 &&
                this.balance - debitAmount > -1) {
            /**
             * Instead of changing the state directly we apply an event
             * that conveys what happened.
             *
             * The event thus applied is stored.
             */
            apply(new AccountDebitedEvent(this.accountNo, debitAmount, this.balance));
        } else {
            throw new IllegalArgumentException("Cannot debit with the amount");
        }
    }

    @EventSourcingHandler
    private void applyDebit(AccountDebitedEvent event) {
        /**
         * This method is called as a reflection of applying events stored in the event store.
         * Consequent application of all the events in the event store will bring the Account
         * to the most recent state.
         */
        this.balance -= event.getAmountDebited();
    }

    /**
     * Business Logic:
     * Cannot credit with a negative amount
     * Cannot credit with more than a million amount (You laundering money?)
     *
     * @param creditAccountCommand
     */

    @CommandHandler
    public void credit(CreditAccountCommand creditAccountCommand) {
        Double creditAmount = creditAccountCommand.getAmount();
        if (Double.compare(creditAmount, 0.0d) > 0 && Double.compare(creditAmount, 1000000) < 0) {
            /**
             * Instead of changing the state directly we apply an event
             * that conveys what happened.
             *
             * The event thus applied is stored.
             */
            apply(new AccountCreditedEvent(this.accountNo, creditAmount, this.balance));
        } else {
            throw new IllegalArgumentException("Cannot credit with the amount");
        }
    }

    @EventSourcingHandler
    private void applyCredit(AccountCreditedEvent event) {
        /**
         * This method is called as a reflection of applying events stored in the event store.
         * Consequent application of all the events in the event store will bring the Account
         * to the most recent state.
         */
        this.balance += event.getAmountCredited();
    }

    public Double getBalance() {
        return balance;
    }

    public void setIdentifier(String id) {
        this.accountNo = id;
    }

    @Override
    public Object getIdentifier() {
        return accountNo;
    }
}
