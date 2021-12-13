package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountTest {
    private static final String ID = "12345678";
    private static final String ID2 = "87654321";
    private static final double APR = 4.5;
    private static final double APR2 = 7.3;
    Checking checking;
    Savings savings;
    Account account;

    @BeforeEach
    void setUp() {
        checking = new Checking("checking", ID, APR);
        savings = new Savings("savings", ID2, APR2);
    }

    @Test
    void checking_account_exists() {
        assertEquals(ID, checking.getID());
    }

    @Test
    void checking_account_is_empty() {
        assertEquals(0, checking.getBalance());
    }

    @Test
    void deposit_into_checking_account() {
        checking.deposit(1000.00);
        assertEquals(checking.getBalance(), 1000.00);
    }

    @Test
    void deposit_into_savings_account() {
        savings.deposit(1000.00);
        assertEquals(savings.getBalance(), 1000.00);
    }

    @Test
    void deposit_into_checking_account_twice() {
        checking.deposit(1000.00);
        checking.deposit(1000.00);
        assertEquals(checking.getBalance(), 2000.00);
    }

    @Test
    void withdraw_from_checking_account() {
        checking.deposit(1000.00);
        checking.withdraw(500.00);
        assertEquals(checking.getBalance(), 500.00);
    }

    @Test
    void withdraw_from_savings_account() {
        savings.deposit(1000.00);
        savings.withdraw(500.00);
        assertEquals(savings.getBalance(), 500.00);
    }

    @Test
    void withdraw_more_than_available() {
        checking.deposit(1000.00);
        checking.withdraw(2000.00);
        assertEquals(checking.getBalance(), 0);
    }

    @Test
    void withdraw_from_account_twice() {
        checking.deposit(1000.00);
        checking.withdraw(500.00);
        checking.withdraw(500.00);
        assertEquals(checking.getBalance(), 0);
    }

    @Test
    void truncate_amount() {
        checking.deposit(1000.00);
        checking.withdraw(250.25);
        checking.withdraw(500.00);
        String amt = account.truncate(String.valueOf(checking.getBalance()));
        assertEquals(amt, "249.75");
    }


}
