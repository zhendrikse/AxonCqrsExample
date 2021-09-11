package exploringaxon.event;

import exploringaxon.model.AccountNumber;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Event Class that communicates that an account has been credited.
 */
public class AccountCreditedEvent {

    public final AccountNumber accountNo;
    public final Double amountCredited;
    public final Double balance;
    public final long timeStamp;

    public AccountCreditedEvent(AccountNumber accountNo, Double amountCredited, Double balance) {
        this.accountNo = accountNo;
        this.amountCredited = amountCredited;
        this.balance = balance;
        ZoneId zoneId = ZoneId.systemDefault();
        this.timeStamp = LocalDateTime.now().atZone(zoneId).toEpochSecond();
    }
}
