package exploringaxon.replay;

import exploringaxon.event.AccountCreditedEvent;
import exploringaxon.event.AccountDebitedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventhandling.replay.ReplayAware;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class AccountCreditedReplayEventHandler implements ReplayAware {

    private List<String> audit = new ArrayList<>();

    @EventHandler
    public void handle(AccountCreditedEvent event) {
        String auditMsg = String.format("%s credited to account with account no {%s} on %s",
                event.amountCredited, event.accountNo.asString(), formatTimestampToString(event.timeStamp));
        audit.add(auditMsg);
    }

    @EventHandler
    public void handle(AccountDebitedEvent event) {
        String auditMsg = String.format("%s debited from account with account no {%s} on %s",
                event.amountDebited, event.accountNo.asString(), formatTimestampToString(event.timeStamp));
        audit.add(auditMsg);
    }

    public List<String> getAudit() {
        return audit;
    }

    @Override
    public void beforeReplay() {
        audit.clear();
    }

    @Override
    public void afterReplay() {
    }

    @Override
    public void onReplayFailed(Throwable cause) {}

    private String formatTimestampToString(long timestamp) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp * 1000);
    }
}
