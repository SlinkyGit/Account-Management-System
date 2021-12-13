package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DepositValidatorTest {
    private static final String ID = "12345678";
    private static final double APR = 4.5;
    ValidatorRedirect depositValidator;
    Bank bank;
    Account checking;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        depositValidator = new ValidatorRedirect(bank);
        checking = new Checking("checking", ID, APR);
        bank.addAccount(ID, checking);
        bank.addAccount("34215987", new Savings("savings", "34215987", APR));
    }

    public String[] split_command(String command) {
        String[] commandString = command.split(" ");
        return commandString;
    }

    @Test
    void valid_deposit_for_checking() {
        String command = "deposit 12345678 900";
        assertTrue(depositValidator.validate(command));
    }

    @Test
    void valid_deposit_of_0_for_checking() {
        String command = "deposit 12345678 0";
        assertTrue(depositValidator.validate(command));
    }

    @Test
    void valid_deposit_for_savings() {
        String command = "deposit 34215987 900";
        assertTrue(depositValidator.validate(command));
    }

    @Test
    void valid_deposit_of_0_for_savings() {
        String command = "deposit 34215987 0";
        assertTrue(depositValidator.validate(command));
    }

    @Test
    void invalid_create_ID_is_not_number() {
        String command = "deposit 123abcwh 0.2";
        assertFalse(depositValidator.validate(command));
    }

    @Test
    void invalid_ID_length() {
        String command = "deposit 1234567 0.2";
        assertFalse(depositValidator.validate(command));
    }

    @Test
    void misspelling_in_account_type() {
        String command = "deposit savungs 12345678 0.2 1200";
        assertFalse(depositValidator.validate(command));
    }

    @Test
    void is_empty_command() {
        String command = "";
        assertFalse(depositValidator.validate(command));
    }

    @Test
    void duplicate_id() {
        bank.addAccount("12345678", checking);
        String command = "deposit checking 12345678 0.01";
        assertFalse(depositValidator.validate(command));
    }

    @Test
    void missing_deposit_command() {
        String command = "12345678 1000";
        assertFalse(depositValidator.validate(command));
    }

    @Test
    void negative_deposit_amount() {
        String command = "deposit 12345678 -1000";
        assertFalse(depositValidator.validate(command));
    }

    @Test
    void missing_ID() {
        String command = "deposit 1000";
        assertFalse(depositValidator.validate(command));
    }

    @Test
    void max_deposit_for_checking() {
        String command = "deposit 12345678 1000";
        assertTrue(depositValidator.validate(command));
    }

    @Test
    void max_deposit_for_savings() {
        String command = "deposit 34215987 2500";
        assertTrue(depositValidator.validate(command));
    }

    @Test
    void over_max_deposit_for_checking() {
        String command = "deposit 12345678 2000";
        assertFalse(depositValidator.validate(command));
    }

    @Test
    void over_max_deposit_for_savings() {
        String command = "deposit 34215987 3500";
        assertFalse(depositValidator.validate(command));
    }

    @Test
    void typo_in_command() {
        String command = "doepist 12345678 1000";
        assertFalse(depositValidator.validate(command));
    }

    @Test
    void cannot_deposit_into_cd() {
        bank.addAccount("21314359", new CD("cd", "21314359", APR, 500.0));
        String command = "deposit 21314359 1000";
        assertFalse(depositValidator.validate(command));
        assertEquals("cd", bank.getAccounts().get("21314359").getType());
    }

    @Test
    void invalid_command_string() {
        String command = "depositcheckinggg123456781000";
        assertFalse(depositValidator.validate(command));
    }

    @Test
    void no_deposit_amount_present() {
        String command = "deposit 12342132";
        String[] splitted = split_command(command);
        boolean cmd = depositValidator.depositValidator.is_deposit_amount_present(splitted);
        assertFalse(depositValidator.validate(command));
        assertTrue(cmd);
    }

    @Test
    void missing_command_checking() {
        bank.addAccount("12345678", checking);
        String command = "checking 12345678 0.01";
        assertFalse(depositValidator.validate(command));
    }

    @Test
    void missing_command_savings() {
        bank.addAccount("09873645", new Savings("savings", "09873645", APR));
        String command = "savings 09873645 0.03";
        assertFalse(depositValidator.validate(command));
    }

    @Test
    void missing_command_CD() {
        bank.addAccount("99876354", new CD("cd", "99876354", APR, 500.0));
        String command = "cd 99876354 0.20 500";
        assertFalse(depositValidator.validate(command));
    }

    @Test
    void command_not_deposit() {
        String command = "create savings 12345678 0.6";
        assertFalse(depositValidator.validate(command));
    }

}
