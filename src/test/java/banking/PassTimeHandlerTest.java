package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PassTimeHandlerTest {
    private static final String ID = "12345678";
    private static final double APR = 4.5;
    HandlerRedirect commandHandler;
    Bank bank;

    private static double truncate(double amt) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.setRoundingMode(RoundingMode.FLOOR);
        String amtX = decimalFormat.format(Double.parseDouble(String.valueOf(amt)));
        double output = Double.parseDouble(amtX);
        return output;
    }

    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.addAccount(ID, new Savings("savings", ID, APR));
        commandHandler = new HandlerRedirect(bank);
    }

    @Test
    void valid_pass_time_command_of_zero() {
        String command = "create savings 87654321 0.32";
        commandHandler.handle(command);
        String command2 = "pass 0";
        commandHandler.handle(command2);
        assertEquals(0, bank.getAccounts().get("87654321").getAge());
    }

    @Test
    void valid_inc_by_a_month() {
        String command = "deposit 12345678 500";
        commandHandler.handle(command);
        String command1 = "pass 1";
        commandHandler.handle(command1);
        assertEquals(1, bank.getAccounts().get(ID).getAge());
    }

    @Test
    void pass_a_month_for_cd() {
        String command = "create cd 21354678 0.6 1500";
        commandHandler.handle(command);
        String command1 = "pass 1";
        commandHandler.handle(command1);
        assertEquals(1, bank.getAccounts().get("21354678").getAge());
        double amt = truncate(bank.getAccounts().get("21354678").getBalance());
        assertEquals(1503.0, amt);
    }

    @Test
    void pass_3_months_for_cd() {
        String command = "create cd 21354678 1.1 2000";
        commandHandler.handle(command);
        String command1 = "pass 3";
        commandHandler.handle(command1);
        assertEquals(3, bank.getAccounts().get("21354678").getAge());
        double amt = truncate(bank.getAccounts().get("21354678").getBalance());
        assertEquals(2022.11, amt);
    }

    @Test
    void valid_remove_savings() {
        String command = "create savings 87654321 0.32";
        commandHandler.handle(command);
        String command3 = "pass 1";
        commandHandler.handle(command3);
        assertEquals(0, bank.getAccounts().size());
    }

    @Test
    void valid_remove_checking() {
        String command = "create checking 12345658 0.32";
        commandHandler.handle(command);
        String command3 = "pass 1";
        commandHandler.handle(command3);
        assertEquals(0, bank.getAccounts().size());
    }

    @Test
    void valid_withdraw_25() {
        String command = "deposit 12345678 50";
        commandHandler.handle(command);
        String command1 = "pass 1";
        commandHandler.handle(command1);
        assertEquals(25.09375, bank.getAccounts().get(ID).getBalance());
    }


}
