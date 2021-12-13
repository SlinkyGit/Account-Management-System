package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateValidatorTest {
    private static final String ID = "12345678";
    private static final String ID2 = "87654321";
    private static final double APR = 4.5;
    ValidatorRedirect commandValidator;
    Bank bank;
    Account checking, savings;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandValidator = new ValidatorRedirect(bank);
        checking = new Checking("checking", ID, APR);
        bank.addAccount(ID, checking);
    }

    public String[] split_command(String command) {
        String[] commandString = command.split(" ");
        return commandString;
    }

    @Test
    void is_valid_create() {
        String command = "create checking 13245678 0.2";
        assertTrue(commandValidator.validate(command));
    }

    @Test
    void invalid_create_ID_is_not_number() {
        String command = "create checking 123abcwh 0.2";
        assertFalse(commandValidator.validate(command));
    }

    @Test
    void invalid_ID_length() {
        String command = "create checking 1234567 0.2";
        assertFalse(commandValidator.validate(command));
    }

    @Test
    void misspelling_in_account_type() {
        String command = "create chicking 12345678 0.2 1200";
        String[] splitted = split_command(command);
        boolean spelt_right = commandValidator.createValidator.type_spelt_right(splitted);
        assertFalse(commandValidator.validate(command));
        assertTrue(spelt_right);
    }

    @Test
    void missing_APR() {
        String command = "create cd 1234567 2000";
        assertFalse(commandValidator.validate(command));
    }

    @Test
    void APR_within_range() {
        String command = "create checking 12345678 -1";
        String[] splitted = split_command(command);
        boolean valid_apr = commandValidator.withdrawValidator.valid_APR_range(splitted);
        assertFalse(commandValidator.validate(command));
        assertFalse(valid_apr);
    }

    @Test
    void invalid_create_amount_too_low() {
        String command = "create cd 1234567 1.2 999";
        assertFalse(commandValidator.validate(command));
    }

    @Test
    void invalid_create_amount_too_high() {
        String command = "create cd 1234567 1.2 100000";
        assertFalse(commandValidator.validate(command));
    }

    @Test
    void is_empty_command() {
        String command = "";
        String[] splitted = split_command(command);
        boolean cmd = commandValidator.createValidator.empty_command(splitted);
        assertFalse(commandValidator.validate(command));
        assertTrue(cmd);
    }

    @Test
    void duplicate_id() {
        bank.addAccount("12345678", checking);
        String command = "create checking 12345678 0.01";
        assertFalse(commandValidator.validate(command));
    }

    @Test
    void missing_account_type() {
        String command = "create 12345678 -1";
        assertFalse(commandValidator.validate(command));
    }

    @Test
    void missing_initial_amount_for_cd() {
        String command = "create cd 12345678 0.32";
        assertFalse(commandValidator.validate(command));
    }

    @Test
    void typo_in_command() {
        String command = "cereat 12345678 0.24 1200";
        assertFalse(commandValidator.validate(command));
    }

    @Test
    void negative_amount_cd() {
        String command = "create 12345678 0.32 -1230";
        assertFalse(commandValidator.validate(command));
    }

    @Test
    void test_case_insensitive() {
        String command = "cReAtE 12345678 0.23 1200";
        assertFalse(commandValidator.validate(command));
    }

    @Test
    void invalid_create_string_with_no_spaces() {
        String command = "createchecking11111111300";
        assertFalse(commandValidator.validate(command));
    }

    @Test
    void missing_command_checking() {
        bank.addAccount("12345678", checking);
        String command = "checking 12345678 0.01";
        String[] splitted = split_command(command);
        boolean cmd = commandValidator.createValidator.command_present(splitted);
        assertFalse(commandValidator.validate(command));
        assertFalse(cmd);
    }

    @Test
    void missing_command_savings() {
        bank.addAccount("32142913", new Savings("savings", "32142913", APR));
        String command = "savings 32142913 0.01";
        String[] splitted = split_command(command);
        boolean cmd = commandValidator.createValidator.command_present(splitted);
        assertFalse(commandValidator.validate(command));
        assertFalse(cmd);
    }

    @Test
    void missing_command_CD() {
        bank.addAccount("21093726", new CD("cd", "21093726", APR, 500.0));
        String command = "cd 21093726 0.01 500";
        String[] splitted = split_command(command);
        boolean cmd = commandValidator.createValidator.command_present(splitted);
        assertFalse(commandValidator.validate(command));
        assertFalse(cmd);
    }

    @Test
    void command_not_create() {
        String command = "deposit 12345678 1200";
        assertFalse(commandValidator.validate(command));
    }

    @Test
    void too_high_APR() {
        String command = "create checking 12345678 1000";
        assertFalse(commandValidator.validate(command));
    }

    @Test
    void too_low_APR() {
        String command = "create checking 12345678 -200";
        assertFalse(commandValidator.validate(command));
    }

}
