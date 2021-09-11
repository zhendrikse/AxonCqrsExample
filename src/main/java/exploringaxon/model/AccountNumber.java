package exploringaxon.model;

public class AccountNumber { 
    private static final int ACCOUNT_NUMBER_LENGTH = 11;
    private final String accountNumber;

    public AccountNumber(final String accountNo) {
        if (accountNo.length() != ACCOUNT_NUMBER_LENGTH)
            throw new IllegalArgumentException("Account length should be " + ACCOUNT_NUMBER_LENGTH + " but was " + accountNo.length());

        this.accountNumber = accountNo;
    }

    public String asString() {
        return this.accountNumber;
    }
}
