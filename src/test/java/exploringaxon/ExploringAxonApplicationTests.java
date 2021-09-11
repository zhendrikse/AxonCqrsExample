package exploringaxon;

import exploringaxon.command.CreditAccountCommand;
import exploringaxon.command.DebitAccountCommand;
import exploringaxon.event.AccountCreatedEvent;
import exploringaxon.event.AccountCreditedEvent;
import exploringaxon.event.AccountDebitedEvent;
import exploringaxon.model.Account;
import exploringaxon.model.AccountNumber;
import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ExploringAxonApplication.class)
@WebAppConfiguration
public class ExploringAxonApplicationTests {

	private FixtureConfiguration<Account> fixture;
	private String accountNo = "test-acc";
	private AccountNumber accountNumber = new AccountNumber(accountNo);

	@Before
	public void setUp() {
		fixture = Fixtures.newGivenWhenThenFixture(Account.class);
    }

	@Test
	public void testFirstDeposit() {
		fixture.given(new AccountCreatedEvent(accountNumber))
			   .when(new CreditAccountCommand(accountNumber, 100.00))
			   .expectEvents(new AccountCreditedEvent(accountNo, 100.00, 0.0));

	}

	@Test
	public void testFirstSecondDeposit() {
		fixture.given(new AccountCreatedEvent(accountNumber),
					  new AccountCreditedEvent(accountNo, 100.00, 0.0))
			   .when(new CreditAccountCommand(accountNumber, 40.00))
			   .expectEvents(new AccountCreditedEvent(accountNo, 40.00, 100.00));
	}

	@Test
	public void testCreditingDebitingAndCrediting() {
		fixture.given(new AccountCreatedEvent(accountNumber),
					  new AccountCreditedEvent(accountNo, 100.00, 0.0),
					  new AccountDebitedEvent(accountNo, 40.00, 100.0))
			   .when(new CreditAccountCommand(accountNumber, 40.00))
			   .expectEvents(new AccountCreditedEvent(accountNo, 40.00, 60.00));
	}

	@Test
	public void cannotCreditWithAMoreThanMillion() {
		fixture.given(new AccountCreatedEvent(accountNumber))
			   .when(new CreditAccountCommand(accountNumber, 10000000.00))
			   .expectException(IllegalArgumentException.class);
	}

	@Test
	public void cannotDebitAccountWithZeroBalance() {
		fixture.given(new AccountCreatedEvent(accountNumber))
			   .when(new DebitAccountCommand(accountNumber, 1.0))
			   .expectException(IllegalArgumentException.class);
	}

}
