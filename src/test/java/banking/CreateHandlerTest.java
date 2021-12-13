package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateHandlerTest {
    private static final String ID = "12345678";
    private static final String ID2 = "87654321";
    private static final String ID3 = "24681012";
    private static final double APR = 4.5;
    HandlerRedirect commandHandler;
    Bank bank;
    Account checking;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandHandler = new HandlerRedirect(bank);
    }

    @Test
    void valid_create_checking() {
        String command = "create checking 12345678 0.01";
        commandHandler.handle(command);
        assertEquals(1, bank.getAccounts().size());
        assertEquals(ID, bank.getAccounts().get(ID).getID());

    }

    @Test
    void valid_create_savings() {
        String command1 = "create savings 87654321 0.01";
        commandHandler.handle(command1);
        assertEquals(1, bank.getAccounts().size());
        assertEquals(ID2, bank.getAccounts().get(ID2).getID());

    }

    @Test
    void valid_create_cd() {
        String command2 = "create cd 24681012 1.2 2000";
        commandHandler.handle(command2);
        assertEquals(1, bank.getAccounts().size());
        assertEquals(ID3, bank.getAccounts().get(ID3).getID());
    }


    @Test
    void invalid_create_with_same_ID() {
        String command = "create checking 12345678 0.01";
        String command1 = "create checking 12345678 0.01";
        commandHandler.handle(command);
        commandHandler.handle(command1);
        assertEquals(1, bank.getAccounts().size());
        assertEquals(ID, bank.getAccounts().get(ID).getID());
    }

    @Test
    void valid_create_APR() {
        String command = "create checking 12345678 0.01";
        commandHandler.handle(command);
        assertEquals(0.01, bank.getAccounts().get(ID).getAPR());
    }

    @Test
    void valid_create_ID() {
        String command = "create savings 87654321 0.23";
        commandHandler.handle(command);
        assertEquals(ID2, bank.getAccounts().get(ID2).getID());
    }


}
