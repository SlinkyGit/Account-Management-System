package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BankTest {
    private static final String ID = "12345678";
    private static final String ID2 = "87654321";
    private static final double APR = 4.5;
    Bank bank;
    Account checking, savings;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        checking = new Checking("checking", ID, APR);
        savings = new Savings("savings", ID2, APR);
    }

    @Test
    void bank_has_no_accounts_initially() {
        assertTrue(bank.getAccounts().isEmpty());
    }

    @Test
    void add_account_to_bank() {
        bank.addAccount(ID, checking);
        assertEquals(ID, bank.getAccounts().get(ID).getID());
    }

    @Test
    void add_two_accounts_to_bank() {
        bank.addAccount(ID, checking);
        bank.addAccount(ID2, savings);
        assertEquals(ID, bank.getAccounts().get(ID).getID());
        assertEquals(ID2, bank.getAccounts().get(ID2).getID());
    }

    @Test
    void deposit_into_account() {
        checking.deposit(1000.00);
        assertEquals(checking.getBalance(), 1000.00);
    }

    @Test
    void withdraw_half_from_checking_account() {
        checking.deposit(1000.00);
        checking.withdraw(500.00);
        assertEquals(checking.getBalance(), 500.00);
    }

    @Test
    void withdraw_everything_from_checking_account() {
        checking.deposit(1000.00);
        checking.withdraw(1000.00);
        assertEquals(checking.getBalance(), 0);
    }

    @Test
    void withdraw_everything_from_savings_account() {
        savings.deposit(2000);
        savings.withdraw(1000.00);
        assertEquals(savings.getBalance(), 1000);
    }


}
