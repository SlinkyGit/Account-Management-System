package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PassTimeValidatorTest {
    Bank bank;
    PassTimeValidator passValidator;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        passValidator = new PassTimeValidator(bank);
    }

    @Test
    void valid_pass_time() {
        String command = "pass 1";
        assertTrue(passValidator.validate(command));
    }

    @Test
    void invalid_pass_time_command_length_with_missing_month() {
        String command = "pass";
        assertFalse(passValidator.validate(command));
    }

    @Test
    void valid_pass_time_integer_for_month() {
        String command = "pass 3";
        assertTrue(passValidator.validateMonth(command));
    }

    @Test
    void invalid_pass_time_command_length_with_extra_number() {
        String command = "pass 2 2300";
        assertFalse(passValidator.validate(command));
    }

    @Test
    void invalid_pass_time_command_bc_zero_month() {
        String command = "pass 0";
        assertFalse(passValidator.validate(command));
    }

    @Test
    void maximum_pass_time() {
        String command = "pass 60";
        assertTrue(passValidator.validate(command));
    }

    @Test
    void over_maximum_pass_time() {
        String command = "pass 70";
        assertFalse(passValidator.validate(command));
    }

    @Test
    void over_minimum_pass_time() {
        String command = "pass -5";
        assertFalse(passValidator.validate(command));
    }
}
