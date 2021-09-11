package exploringaxon.event;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Event Class that communicates that an account has been debited
 */
public class AccountDebitedEvent {
    private final String accountNo;
    private final Double amountDebited;
    private final Double balance;
    private final long timeStamp;

    public AccountDebitedEvent(final String accountNo, final Double amountDebited, final Double balance) {
        this.accountNo = accountNo;
        this.amountDebited = amountDebited;
        this.balance = balance;
        ZoneId zoneId = ZoneId.systemDefault();
        this.timeStamp = LocalDateTime.now().atZone(zoneId).toEpochSecond();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public Double getAmountDebited() {
        return amountDebited;
    }

    public Double getBalance() {
        return balance;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
