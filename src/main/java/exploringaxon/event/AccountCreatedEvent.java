package exploringaxon.event;

public class AccountCreatedEvent {

    private final String accountNo;

    public AccountCreatedEvent(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountNo() {
        return accountNo;
    }
}
