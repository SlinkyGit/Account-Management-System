package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CheckingTest {
    private static final String ID = "12345678";
    private static final double APR = 4.5;
    Bank bank;
    HandlerRedirect commandHandler;
    ValidatorRedirect withdrawValidator;


    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.addAccount(ID, new Checking("checking", ID, APR));
        commandHandler = new HandlerRedirect(bank);
        withdrawValidator = new ValidatorRedirect(bank);

    }

    @Test
    void initial_balance_of_zero() {
        assertEquals(0, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void account_has_apr() {
        assertEquals(APR, bank.getAccounts().get(ID).getAPR());
    }

    @Test
    void account_is_checking() {
        assertEquals("checking", bank.getAccounts().get(ID).getType());
    }

    @Test
    void value_greater_than_balance_for_withdraw() {
        String command = "withdraw 12345678 30000";
        assertFalse(withdrawValidator.validate(command));
    }

}