package exploringaxon.event;

import exploringaxon.model.AccountNumber;

public class AccountCreatedEvent {

    public final AccountNumber accountNo;

    public final String currency;
  
    public Double balance = 0.0d;

    public AccountCreatedEvent(final AccountNumber accountNo, final Double balance, final String currency) {
        this.currency = currency;
        this.balance = balance;
        this.accountNo = accountNo;
    }
}
