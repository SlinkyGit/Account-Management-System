package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferValidatorTest {
    private static final String ID = "12345678";
    private static final String ID2 = "87654321";
    private static final double APR = 4.5;
    ValidatorRedirect transferValidator;
    Bank bank;
    Account checking, savings;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        transferValidator = new ValidatorRedirect(bank);
        checking = new Checking("checking", ID, APR);
        bank.addAccount(ID, checking);
        savings = new Savings("savings", ID2, APR);
        bank.addAccount(ID2, savings);
    }

    public String[] split_command(String command) {
        String[] commandString = command.split(" ");
        return commandString;
    }

    @Test
    public void transfer_command_is_valid() {
        String command = "transfer 12345678 87654321 300";
        assertTrue(transferValidator.validate(command));
    }

    @Test
    public void transfer_command_is_invalid_because_same_ID() {
        String command = "transfer 12345678 12345678 300";
        assertFalse(transferValidator.validate(command));
    }

    @Test
    public void invalid_transfer_command_length() {
        String command = "transfer 12345678";
        assertFalse(transferValidator.validate(command));
    }

    @Test
    public void invalid_transfer_amount_bc_of_char() {
        String command = "transfer 12345678 87654321 3a0";
        assertFalse(transferValidator.validate(command));
    }

    @Test
    public void invalid_transfer_bc_amt_is_negative() {
        String command = "transfer 12345678 87654321 -300";
        assertFalse(transferValidator.validate(command));
    }

    @Test
    public void valid_transfer_bc_amt_is_zero() {
        String command = "transfer 12345678 87654321 0";
        assertTrue(transferValidator.validate(command));
    }

    @Test
    public void invalid_withdraw_bc_amt_too_high_for_fromID_checking() {
        String command = "transfer 12345678 87654321 2000";
        assertFalse(transferValidator.validate(command));
    }

    @Test
    public void invalid_deposit_bc_amt_too_high_for_toID_savings() {
        String command = "transfer 12345678 87654321 9000";
        assertFalse(transferValidator.validate(command));
    }

    @Test
    public void invalid_transfer_because_ID_is_not_a_number() {
        String command = "transfer 12345678 876as321 1600";
        assertFalse(transferValidator.validate(command));
    }

    @Test
    public void invalid_transfer_ID_length() {
        String command = "transfer 12345 87654 300";
        assertFalse(transferValidator.validate(command));
    }

    @Test
    public void invalid_transfer_fromID_ID_length() {
        String command = "transfer 12345 87654321 300";
        String[] splitted = split_command(command);
        boolean cmd = transferValidator.transferValidator.ID_is_long_enough(splitted);
        assertFalse(transferValidator.validate(command));
        assertTrue(cmd);
    }

    @Test
    public void invalid_transfer_toID_ID_length() {
        String command = "transfer 12345678 87654 300";
        String[] splitted = split_command(command);
        boolean cmd = transferValidator.transferValidator.ID_is_long_enough(splitted);
        assertFalse(transferValidator.validate(command));
        assertTrue(cmd);
    }

    @Test
    public void invalid_transfer_fromID_ID_is_not_a_number() {
        String command = "transfer 1h2bsg35 87654321 300";
        assertFalse(transferValidator.validate(command));
    }

    @Test
    public void invalid_transfer_toID_ID_is_not_a_number() {
        String command = "transfer 12345678 876as3s 300";
        assertFalse(transferValidator.validate(command));
    }


}
