package exploringaxon.eventhandler;

import exploringaxon.event.AccountCreditedEvent;
import org.axonframework.domain.Message;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventhandling.annotation.Timestamp;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

@Component
public class AccountCreditedEventHandler {
    //private static Logger logger = LoggerFactory.getLogger(AccountCreditedEventHandler.class);
    
    @Autowired
    DataSource dataSource;

    @EventHandler
    public void handleAccountCreditedEvent(AccountCreditedEvent event, Message eventMessage, @Timestamp DateTime moment) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        // Get the current states as reflected in the event
        String accountNo = event.getAccountNo();
        Double balance = event.getBalance();
        Double amountCredited = event.getAmountCredited();
        Double newBalance = balance + amountCredited;

        // Update the view
        String updateQuery = "UPDATE account_view SET balance = ? WHERE account_no = ?";
        jdbcTemplate.update(updateQuery, new Object[]{newBalance, accountNo});

        //logger.info("Events Handled With EventMessage " + eventMessage.toString() + " at " + moment.toString());
    }

}
