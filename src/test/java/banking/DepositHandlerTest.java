package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepositHandlerTest {
    private static final String ID = "12345678";
    private static final double APR = 4.5;
    Account account, checking, savings;
    HandlerRedirect commandHandler;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.addAccount(ID, new Savings("savings", ID, APR));
        commandHandler = new HandlerRedirect(bank);
        bank.addAccount("12321423", new Checking("checking", "12321423", APR));

    }

    @Test
    void valid_deposit_in_savings() {
        String command = "deposit 12345678 100";
        commandHandler.handle(command);
        assertEquals(100.00, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void valid_deposit_in_checking() {
        String command = "deposit 12321423 100";
        commandHandler.handle(command);
        assertEquals(100.00, bank.getAccounts().get("12321423").getBalance());
    }

    @Test
    void valid_deposit_again() {
        bank.getAccounts().get(ID).deposit(100);
        String command = "deposit 12345678 100";
        commandHandler.handle(command);
        assertEquals(100.00 + 100.00, bank.getAccounts().get(ID).getBalance());

    }

    @Test
    void invalid_deposit() {
        String command = "deposit 12345678 -100";
        commandHandler.handle(command);
        assertEquals(-100, bank.getAccounts().get(ID).getBalance());

    }

    @Test
    void valid_deposit_account() {
        String command = "deposit 12345678 100";
        commandHandler.handle(command);
        assertEquals(ID, bank.getAccounts().get(ID).getID());

    }

    @Test
    void deposit_nothing_into_account() {
        bank.getAccounts().get(ID).deposit(100);
        String command = "deposit 12345678 0";
        commandHandler.handle(command);
        assertEquals(100.00, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void deposit_max_into_checking() {
        String command = "deposit 12321423 1000";
        commandHandler.handle(command);
        assertEquals(1000.00, bank.getAccounts().get("12321423").getBalance());
    }

    @Test
    void deposit_min_into_checking() {
        String command = "deposit 12321423 0";
        commandHandler.handle(command);
        assertEquals(0, bank.getAccounts().get("12321423").getBalance());
    }

    @Test
    void deposit_max_into_savings() {
        String command = "deposit 12345678 2500";
        commandHandler.handle(command);
        assertEquals(2500, bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    void deposit_min_into_savings() {
        String command = "deposit 12345678 0";
        commandHandler.handle(command);
        assertEquals(0, bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    void invalid_deposit_into_CD() {
        bank.addAccount("34259863", new CD("cd", "34259863", APR, 2000));
        String command = "deposit 34259863 200";
        commandHandler.handle(command);
        assertEquals(2000, bank.getAccounts().get("34259863").getBalance());
    }

}
