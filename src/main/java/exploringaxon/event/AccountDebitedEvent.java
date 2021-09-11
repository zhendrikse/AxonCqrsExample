package exploringaxon.event;

import java.time.LocalDateTime;
import java.time.ZoneId;
import exploringaxon.model.AccountNumber;

/**
 * Event Class that communicates that an account has been debited.
 */
public class AccountDebitedEvent {
    public final AccountNumber accountNo;
    public final Double amountDebited;
    public final Double balance;
    public final long timeStamp;

    public AccountDebitedEvent(AccountNumber accountNo, Double amountDebited, Double balance) {
        this.accountNo = accountNo;
        this.amountDebited = amountDebited;
        this.balance = balance;
        ZoneId zoneId = ZoneId.systemDefault();
        this.timeStamp = LocalDateTime.now().atZone(zoneId).toEpochSecond();
    }
}
