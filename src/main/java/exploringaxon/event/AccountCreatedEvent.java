package exploringaxon.event;

import exploringaxon.model.AccountNumber;

public class AccountCreatedEvent {

    private final AccountNumber accountNo;

    public AccountCreatedEvent(final AccountNumber accountNo) {
        this.accountNo = accountNo;
    }

    public AccountNumber getAccountNo() {
        return accountNo;
    }
}
