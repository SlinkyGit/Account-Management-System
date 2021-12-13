package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WithdrawValidatorTest {
    private static final String ID = "12345678";
    private static final String ID2 = "87654321";
    private static final String ID3 = "13579117";
    private static final double APR = 4.5;
    private static final double AMT = 2000;
    ValidatorRedirect withdrawValidator;
    Bank bank;
    Account checking, savings, cd;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        withdrawValidator = new ValidatorRedirect(bank);
        checking = new Checking("checking", ID, APR);
        bank.addAccount(ID, checking);
        savings = new Savings("savings", ID2, APR);
        bank.addAccount(ID2, savings);
        cd = new CD("cd", ID3, APR, AMT);
        bank.addAccount(ID3, cd);
    }

    public String[] split_command(String command) {
        String[] commandString = command.split(" ");
        return commandString;
    }

    @Test
    public void valid_withdraw_command() {
        String command = "withdraw 12345678 250";
        assertTrue(withdrawValidator.validate(command));
    }

    @Test
    public void invalid_withdraw_command() {
        String command = "widathwr 12345678 250";
        assertFalse(withdrawValidator.validate(command));
    }

    @Test
    public void valid_withdraw_positive_amount() {
        String command = "withdraw 12345678 200";
        assertTrue(withdrawValidator.validate(command));
    }

    @Test
    public void invalid_withdraw_negative_amount() {
        String command = "withdraw 12345678 -200";
        assertFalse(withdrawValidator.validate(command));
    }

    @Test
    public void invalid_withdraw_char_in_amount() {
        String command = "withdraw 12345678 20a";
        assertFalse(withdrawValidator.validate(command));
    }

    @Test
    public void valid_withdraw_zero_amount() {
        String command = "withdraw 12345678 0";
        assertTrue(withdrawValidator.validate(command));
    }

    @Test
    public void valid_withdraw_for_savings() {
        String command1 = "deposit 87654321 2300";
        withdrawValidator.validate(command1);
        String command2 = "withdraw 87654321 400";
        assertTrue(withdrawValidator.validate(command2));
    }

    @Test
    public void max_withdraw_for_savings() {
        String command1 = "deposit 87654321 2300";
        withdrawValidator.validate(command1);
        String command2 = "withdraw 87654321 1000";
        assertTrue(withdrawValidator.validate(command2));
    }

    @Test
    public void over_max_withdraw_for_savings() {
        String command1 = "deposit 87654321 2300";
        withdrawValidator.validate(command1);
        String command2 = "withdraw 87654321 2000";
        assertFalse(withdrawValidator.validate(command2));
    }

    @Test
    public void min_withdraw_for_savings() {
        String command1 = "deposit 87654321 2300";
        withdrawValidator.validate(command1);
        String command2 = "withdraw 87654321 0";
        assertTrue(withdrawValidator.validate(command2));
    }

    @Test
    public void valid_withdraw_for_checking() {
        String command1 = "deposit 12345678 400";
        withdrawValidator.validate(command1);
        String command2 = "withdraw 12345678 100";
        assertTrue(withdrawValidator.validate(command2));
    }

    @Test
    public void max_withdraw_for_checking() {
        String command1 = "deposit 12345678 700";
        withdrawValidator.validate(command1);
        String command2 = "withdraw 12345678 400";
        assertTrue(withdrawValidator.validate(command2));
    }

    @Test
    public void over_max_withdraw_for_checking() {
        String command1 = "deposit 12345678 700";
        withdrawValidator.validate(command1);
        String command2 = "withdraw 12345678 2000";
        assertFalse(withdrawValidator.validate(command2));
    }

    @Test
    public void min_withdraw_for_checking() {
        String command1 = "deposit 12345678 600";
        withdrawValidator.validate(command1);
        String command2 = "withdraw 12345678 0";
        assertTrue(withdrawValidator.validate(command2));
    }

    @Test
    public void valid_withdraw_for_CD() {
        cd = new CD("cd", "12343215", 1.2, 1500);
        bank.addAccount("12343215", cd);
        String command2 = "withdraw 12343215 1507";
        bank.getAccounts().get("12343215").incAge(12);
        String[] splitted = split_command(command2);
        boolean valid_withdraw_month = withdrawValidator.withdrawValidator.valid_withdraw_month(splitted);
        assertEquals(1506.0090060015, bank.getAccounts().get("12343215").getBalance());
        assertTrue(withdrawValidator.validate(command2));
        assertTrue(valid_withdraw_month);
    }

    @Test
    public void over_max_withdraw_for_CD() {
        cd = new CD("cd", "12343215", 1.2, 1500);
        bank.addAccount("12343215", cd);
        String command2 = "withdraw 12343215 2000";
        assertTrue(withdrawValidator.validate(command2));
    }

    @Test
    public void min_withdraw_for_CD() {
        cd = new CD("cd", "12343215", 1.2, 1500);
        bank.addAccount("12343215", cd);
        String command2 = "withdraw 12343215 0";
        bank.getAccounts().get("12343215").incAge(12);
        assertFalse(withdrawValidator.validate(command2));
    }

    @Test
    void command_not_withdraw() {
        String command = "deposit 12345678 1200";
        assertFalse(withdrawValidator.validate(command));
    }

    @Test
    void get_withdraw_id() {
        String command1 = "deposit 12345678 600";
        withdrawValidator.validate(command1);
        String command2 = "withdraw 12345678 0";
        String[] splitted = split_command(command2);
        withdrawValidator.validate(command2);
        assertEquals(splitted[1], bank.getAccounts().get("12345678").getID());
    }

}
