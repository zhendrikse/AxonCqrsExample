package exploringaxon.web;

import exploringaxon.command.CreditAccountCommand;
import exploringaxon.command.DebitAccountCommand;
import exploringaxon.model.AccountNumber;
import exploringaxon.replay.AccountCreditedReplayEventHandler;
import org.axonframework.commandhandling.GenericCommandMessage;
import org.axonframework.commandhandling.callbacks.LoggingCallback;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.replay.ReplayingCluster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    @Qualifier("replayCluster")
    ReplayingCluster replayCluster;

    @Autowired
    AccountCreditedReplayEventHandler replayEventHandler;

    @Autowired
    private CommandGateway commandGateway;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("name", "dadepo");
        return "index";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @RequestMapping("/debit")
    @Transactional
    @ResponseBody
    public void doDebit(@RequestParam("acc") String accountNumber, @RequestParam("amount") double amount) {
        final AccountNumber accountNo = new AccountNumber(accountNumber);
        // TODO make currency variable
        final DebitAccountCommand debitAccountCommandCommand = new DebitAccountCommand(accountNo, amount, "EUR");
        commandGateway.send(debitAccountCommandCommand);
    }

    @RequestMapping("/credit")
    @Transactional
    @ResponseBody
    public void doCredit(@RequestParam("acc") String accountNumber, @RequestParam("amount") double amount) {
        final AccountNumber accountNo = new AccountNumber(accountNumber);
        // TODO make currency variable
        final CreditAccountCommand creditAccountCommandCommand = new CreditAccountCommand(accountNo, amount, "EUR");
        final GenericCommandMessage<CreditAccountCommand> message = new GenericCommandMessage<>(creditAccountCommandCommand);
        commandGateway.send(creditAccountCommandCommand, new LoggingCallback(message));
    }

    @RequestMapping("/events")
    public String doReplay(Model model) {
        replayCluster.startReplay();
        model.addAttribute("events",replayEventHandler.getAudit());
        return "events";
    }
}
