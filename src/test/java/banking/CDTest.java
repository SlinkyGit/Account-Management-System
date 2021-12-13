package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CDTest {
    private static final String ID = "65437281";
    private static final double APR = 4.5;
    private static final double BAL = 500.0;
    CD cd;
    Bank bank;
    HandlerRedirect commandHandler;
    ValidatorRedirect depositValidator;
    ValidatorRedirect withdrawValidator;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        cd = new CD("cd", ID, APR, BAL);
        commandHandler = new HandlerRedirect(bank);
        depositValidator = new ValidatorRedirect(bank);
        withdrawValidator = new ValidatorRedirect(bank);
    }

    public String[] split_command(String command) {
        String[] commandString = command.split(" ");
        return commandString;
    }

    @Test
    void add_CD_account_to_bank() {
        bank.addAccount(ID, cd);
        assertEquals(ID, bank.getAccounts().get(ID).getID());
    }

    @Test
    void cannot_deposit_into_CD() {
        bank.addAccount(ID, cd);
        String command = "deposit 65437281 300";
        boolean amt = bank.getAccounts().get(ID).validDeposit(300);
        assertFalse(depositValidator.validate(command));
        assertFalse(amt);
    }

    @Test
    void withdraw_age_increase_zero() {
        cd = new CD("cd", "38275463", 1.2, 1500);
        bank.addAccount("38275463", cd);
        String command2 = "withdraw 38275463 0";
        bank.getAccounts().get("38275463").incAge(0);
        assertFalse(withdrawValidator.validate(command2));
        assertEquals(0, bank.getAccounts().get("38275463").getAge());
    }

    @Test
    void withdraw_age_increase_max() {
        cd = new CD("cd", "32746502", 1.2, 1500);
        bank.addAccount("32746502", cd);
        String command2 = "withdraw 32746502 0";
        bank.getAccounts().get("32746502").incAge(60);
        assertFalse(withdrawValidator.validate(command2));
        assertEquals(60, bank.getAccounts().get("32746502").getAge());
    }

    @Test
    void withdraw_age_increase_over_over_max() {
        cd = new CD("cd", "82736482", 1.2, 1500);
        bank.addAccount("82736482", cd);
        String command2 = "withdraw 82736482 0";
        bank.getAccounts().get("82736482").incAge(70);
        assertFalse(withdrawValidator.validate(command2));
        assertEquals(70, bank.getAccounts().get("82736482").getAge());
    }

    @Test
    void valid_withdraw_for_CD() {
        cd = new CD("cd", "93874652", 1.2, 1500);
        bank.addAccount("93874652", cd);
        String command2 = "withdraw 93874652 1000";
        String[] splitted = split_command(command2);
        boolean valid_withdraw_amt = cd.validWithdraw(Double.parseDouble(splitted[2]));
        bank.getAccounts().get("93874652").incAge(11);
        withdrawValidator.validate(command2);
        assertEquals(11, bank.getAccounts().get("93874652").getAge());
        assertFalse(valid_withdraw_amt);
    }

}
