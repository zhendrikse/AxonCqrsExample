package exploringaxon.event;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Event Class that communicates that an account has been debited
 */
public class AccountDebitedEvent {
    public final String accountNo;
    public final Double amountDebited;
    public final Double balance;
    public final long timeStamp;

    public AccountDebitedEvent(String accountNo, Double amountDebited, Double balance) {
        this.accountNo = accountNo;
        this.amountDebited = amountDebited;
        this.balance = balance;
        ZoneId zoneId = ZoneId.systemDefault();
        this.timeStamp = LocalDateTime.now().atZone(zoneId).toEpochSecond();
    }
}
