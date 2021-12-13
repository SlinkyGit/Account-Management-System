package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SavingsTest {
    private static final String ID = "87645321";
    private static final double APR = 4.5;
    Bank bank;
    HandlerRedirect commandHandler;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.addAccount(ID, new Savings("savings", ID, APR));
        commandHandler = new HandlerRedirect(bank);
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
    void account_is_savings() {
        assertEquals("savings", bank.getAccounts().get(ID).getType());
    }
}
