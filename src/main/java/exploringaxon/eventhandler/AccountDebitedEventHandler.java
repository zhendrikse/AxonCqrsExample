package exploringaxon.eventhandler;

import exploringaxon.event.AccountDebitedEvent;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class AccountDebitedEventHandler {

    @Autowired
    private DataSource dataSource;

    @EventHandler
    public void handleAccountDebitedEvent(AccountDebitedEvent event) {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Get the current states as reflected in the event
        final String accountNo = event.accountNo.asString();
        final Double balance = event.balance;
        final Double amountDebited = event.amountDebited;
        final Double newBalance = balance - amountDebited;

        // Update the view
        final String updateQuery = "UPDATE account_view SET balance = ? WHERE account_no = ?";
        jdbcTemplate.update(updateQuery, new Object[]{newBalance, accountNo});
    }
}
