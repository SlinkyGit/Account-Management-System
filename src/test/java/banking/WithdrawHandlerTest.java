package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WithdrawHandlerTest {
    private static final String ID = "12345678";
    private static final String ID2 = "87654321";
    private static final String ID3 = "13579117";
    private static final double APR = 4.5;
    private static final double AMT = 2000;
    Account account, checking, savings, cd;
    HandlerRedirect commandHandler;
    Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        savings = new Savings("savings", ID2, APR);
        bank.addAccount(ID, savings);
        commandHandler = new HandlerRedirect(bank);
        checking = new Checking("checking", ID, APR);
        bank.addAccount(ID, checking);
        cd = new CD("cd", ID3, APR, AMT);
        bank.addAccount(ID3, cd);
    }

    @Test
    void valid_withdraw() {
        String command = "deposit 12345678 500";
        commandHandler.handle(command);
        String command1 = "withdraw 12345678 300";
        commandHandler.handle(command1);
        assertEquals(200, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void valid_withdraw_from_zero() {
        String command = "deposit 12345678 200";
        commandHandler.handle(command);
        String command1 = "withdraw 12345678 200";
        commandHandler.handle(command1);
        assertEquals(0, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void valid_withdraw_more_than_balance() {
        String command = "deposit 12345678 200";
        commandHandler.handle(command);
        String command1 = "withdraw 12345678 800";
        commandHandler.handle(command1);
        assertEquals(0, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void valid_withdraw_from_checking() {
        String command = "deposit 12345678 200";
        commandHandler.handle(command);
        String command1 = "withdraw 12345678 100";
        commandHandler.handle(command1);
        assertEquals(100, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void withdraw_more_than_balance_from_checking() {
        String command = "deposit 12345678 200";
        commandHandler.handle(command);
        String command1 = "withdraw 12345678 300";
        commandHandler.handle(command1);
        assertEquals(0, bank.getAccounts().get(ID).getBalance());
    }

    @Test
    void withdraw_zero_from_checking() {
        String command = "deposit 12345678 200";
        commandHandler.handle(command);
        String command1 = "withdraw 12345678 0";
        commandHandler.handle(command1);
        assertEquals(200, bank.getAccounts().get(ID).getBalance());
    }

    //Savings:
    @Test
    void valid_withdraw_from_savings() {
        savings = new Savings("savings", "32456781", APR);
        bank.addAccount("32456781", savings);
        String command = "deposit 32456781 2000";
        commandHandler.handle(command);
        String command1 = "withdraw 32456781 900";
        commandHandler.handle(command1);
        assertEquals(1100, bank.getAccounts().get("32456781").getBalance());
    }

    @Test
    void withdraw_more_than_balance_from_savings() {
        savings = new Savings("savings", "32456781", APR);
        bank.addAccount("32456781", savings);
        String command = "deposit 32456781 2000";
        commandHandler.handle(command);
        String command1 = "withdraw 32456781 2200";
        commandHandler.handle(command1);
        assertEquals(0, bank.getAccounts().get("32456781").getBalance());
    }

    @Test
    void withdraw_zero_from_savings() {
        savings = new Savings("savings", "32456781", APR);
        bank.addAccount("32456781", savings);
        String command = "deposit 32456781 200";
        commandHandler.handle(command);
        String command1 = "withdraw 32456781 0";
        commandHandler.handle(command1);
        assertEquals(200, bank.getAccounts().get("32456781").getBalance());
    }

    @Test
    void return_transaction_history_for_savings() {
        savings = new Savings("savings", "74832943", APR);
        bank.addAccount("74832943", savings);
        String command = "deposit 74832943 200";
        commandHandler.handle(command);
        String command1 = "withdraw 74832943 0";
        commandHandler.handle(command1);
        ArrayList<Object> command_hist = new ArrayList<>();
        command_hist.add(command);
        command_hist.add(command1);
        assertEquals(command_hist, bank.getAccounts().get("74832943").getTransactionHistory());
    }

    @Test
    void return_transaction_history_for_checking() {
        checking = new Checking("checking", "21849372", APR);
        bank.addAccount("21849372", checking);
        String command = "deposit 21849372 200";
        commandHandler.handle(command);
        String command1 = "withdraw 21849372 0";
        commandHandler.handle(command1);
        ArrayList<Object> command_hist = new ArrayList<>();
        command_hist.add(command);
        command_hist.add(command1);
        assertEquals(command_hist, bank.getAccounts().get("21849372").getTransactionHistory());
    }

    @Test
    public void valid_withdraw_from_cd_account() {
        bank.getAccounts().get(ID3).incAge(12);
        String command = "withdraw 13579117 600";
        commandHandler.handle(command);
        assertEquals(0, bank.getAccounts().get(ID3).getBalance());
    }

    @Test
    void return_transaction_history_for_CD() {
        bank.getAccounts().get(ID3).incAge(12);
        String command = "withdraw 13579117 600";
        commandHandler.handle(command);
        ArrayList<Object> command_hist = new ArrayList<>();
        command_hist.add(command);
        assertEquals(command_hist, bank.getAccounts().get(ID3).getTransactionHistory());
    }


}
