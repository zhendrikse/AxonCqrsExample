package exploringaxon.event;

import exploringaxon.model.AccountNumber;

public class AccountCreatedEvent {

    public final AccountNumber accountNo;

    public AccountCreatedEvent(final AccountNumber accountNo) {
        this.accountNo = accountNo;
    }
}
