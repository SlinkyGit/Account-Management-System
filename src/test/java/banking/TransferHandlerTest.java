package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferHandlerTest {
    private static final String ID = "12345678";
    private static final String ID2 = "87654321";
    private static final String ID3 = "24681012";
    private static final double APR = 4.5;
    private static final double APR2 = 0.60;
    Account checking, savings, cd;
    HandlerRedirect commandHandler;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commandHandler = new HandlerRedirect(bank);
        checking = new Checking("checking", ID, APR);
        bank.addAccount(ID, checking);
        savings = new Savings("savings", ID2, APR);
        bank.addAccount(ID2, savings);
        cd = new CD("cd", ID3, APR2, 2000);
        bank.addAccount(ID3, cd);
    }

    @Test
    public void transfer_between_checking_and_savings() {
        String command = "deposit 12345678 700";
        commandHandler.handle(command);
        String command1 = "deposit 87654321 450";
        commandHandler.handle(command1);
        String command2 = "transfer 12345678 87654321 250";
        commandHandler.handle(command2);
        assertEquals(700, bank.getAccounts().get("87654321").getBalance());
        assertEquals(450, bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    public void transfer_twice_between_checking_and_savings() {
        String command = "deposit 12345678 300";
        commandHandler.handle(command);
        String command1 = "deposit 87654321 200";
        commandHandler.handle(command1);
        String command2 = "transfer 12345678 87654321 100";
        commandHandler.handle(command2);
        String command3 = "transfer 12345678 87654321 100";
        commandHandler.handle(command3);
        assertEquals(400, bank.getAccounts().get("87654321").getBalance());
        assertEquals(100, bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    public void transfer_between_checking_and_CD() {
        String command3 = "transfer 12345678 24681012 1600";
        commandHandler.handle((command3));
        String command2 = "pass 3";
        commandHandler.handle(command2);
        assertEquals(3621.659499111465, bank.getAccounts().get("24681012").getBalance());
    }

    @Test
    public void valid_transfer_of_zero_between_checking_and_savings() {
        String command = "deposit 12345678 700";
        commandHandler.handle(command);
        String command1 = "deposit 87654321 450";
        commandHandler.handle(command1);
        String command2 = "transfer 12345678 87654321 0";
        commandHandler.handle(command2);
        assertEquals(450, bank.getAccounts().get("87654321").getBalance());
        assertEquals(700, bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    public void transfer_between_savings_and_checking() {
        String command = "deposit 12345678 450";
        commandHandler.handle(command);
        String command1 = "deposit 87654321 700";
        commandHandler.handle(command1);
        String command2 = "transfer 12345678 87654321 250";
        commandHandler.handle(command2);
        assertEquals(950, bank.getAccounts().get("87654321").getBalance());
        assertEquals(200, bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    public void valid_transfer_of_zero_between_savings_and_checking() {
        String command = "deposit 12345678 700";
        commandHandler.handle(command);
        String command1 = "deposit 87654321 450";
        commandHandler.handle(command1);
        String command2 = "transfer 87654321 12345678 0";
        commandHandler.handle(command2);
        assertEquals(450, bank.getAccounts().get("87654321").getBalance());
        assertEquals(700, bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    public void valid_transfer_between_2_savings() {
        savings = new Savings("savings", "13245890", APR);
        bank.addAccount("13245890", savings);
        String command = "deposit 13245890 700";
        commandHandler.handle(command);
        String command1 = "deposit 87654321 300";
        commandHandler.handle(command1);
        String command2 = "transfer 13245890 87654321 100";
        commandHandler.handle(command2);
        assertEquals(600, bank.getAccounts().get("13245890").getBalance());
        assertEquals(400, bank.getAccounts().get("87654321").getBalance());
    }

    @Test
    public void valid_transfer_between_2_checking() {
        checking = new Checking("checking", "54321678", APR);
        bank.addAccount("54321678", checking);
        String command = "deposit 54321678 700";
        commandHandler.handle(command);
        String command1 = "deposit 12345678 300";
        commandHandler.handle(command1);
        String command2 = "transfer 54321678 12345678 300";
        commandHandler.handle(command2);
        assertEquals(400, bank.getAccounts().get("54321678").getBalance());
        assertEquals(600, bank.getAccounts().get("12345678").getBalance());
    }

    @Test
    public void transfer_between_two_CDs() {
        cd = new CD("cd", "09832134", APR2, 1500);
        bank.addAccount("09832134", cd);
        String command3 = "transfer 09832134 24681012 1500";
        commandHandler.handle((command3));
        String command2 = "pass 3";
        commandHandler.handle(command2);
        assertEquals(3521.057846358368, bank.getAccounts().get("24681012").getBalance());
    }

    @Test
    public void transfer_between_two_checking_accounts_and_back_again() {
        checking = new Checking("checking", "54321678", APR);
        bank.addAccount("54321678", checking);
        String command = "deposit 12345678 700";
        commandHandler.handle(command);
        String command2 = "deposit 54321678 500";
        commandHandler.handle(command2);
        String command3 = "transfer 12345678 54321678 200";
        commandHandler.handle((command3));
        String command4 = "transfer 54321678 12345678 200";
        commandHandler.handle((command4));
        assertEquals(700, bank.getAccounts().get("12345678").getBalance());
        assertEquals(500, bank.getAccounts().get("54321678").getBalance());
    }

    @Test
    public void transfer_between_two_savings_accounts_and_back_again() {
        savings = new Savings("savings", "76435287", APR);
        bank.addAccount("76435287", savings);
        String command = "deposit 76435287 2400";
        commandHandler.handle(command);
        String command1 = "deposit 87654321 450";
        commandHandler.handle(command1);
        String command3 = "transfer 76435287 87654321 500";
        commandHandler.handle((command3));
        String command4 = "transfer 87654321 76435287 500";
        commandHandler.handle((command4));
        assertEquals(2400, bank.getAccounts().get("76435287").getBalance());
        assertEquals(450, bank.getAccounts().get("87654321").getBalance());
    }

    @Test
    void return_transaction_history_for_savings_and_checking() {
        String command = "deposit 12345678 700";
        commandHandler.handle(command);
        String command1 = "deposit 87654321 450";
        commandHandler.handle(command1);
        String command2 = "transfer 87654321 12345678 0";
        commandHandler.handle(command2);
        ArrayList<Object> command_hist_toID = new ArrayList<>();
        ArrayList<Object> command_hist_fromID = new ArrayList<>();
        command_hist_toID.add(command);
        command_hist_toID.add(command2);
        command_hist_fromID.add(command1);
        command_hist_fromID.add(command2);
        assertEquals(command_hist_toID, bank.getAccounts().get("12345678").getTransactionHistory());
        assertEquals(command_hist_fromID, bank.getAccounts().get("87654321").getTransactionHistory());
    }

}
