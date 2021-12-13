package banking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StoreCommandTest {
    Bank bank;
    ArrayList<String> commands;
    StoreCommand storeCommand;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        commands = new ArrayList<>();
        storeCommand = new StoreCommand();
    }

    @Test
    void invalid_command() {
        String command = "creatj checking 12345678 0.01";
        storeCommand.addCommand(command);
        assertEquals(command, storeCommand.getCommand().get(0));

    }

    @Test
    void store_multiple_commands() {
        String command = "cerare checking 12345678 0.32";
        String command2 = "create asvings 12345678 0.23";
        storeCommand.addCommand(command);
        storeCommand.addCommand(command2);
        assertEquals(command, storeCommand.getCommand().get(0));
        assertEquals(command2, storeCommand.getCommand().get(1));

    }

    @Test
    void valid_command() {
        String command = "create checking 12345678 0.59";
        assertEquals(command, command);
    }

    @Test
    void store_invalid_ID() {
        String command = "create checking 13l4ks3o 0.01";
        storeCommand.addCommand(command);
        assertEquals(command, storeCommand.getCommand().get(0));
    }


}
