package banking;

import java.util.ArrayList;

public class StoreCommand {
    ArrayList<String> invalidCommands;

    StoreCommand() {
        invalidCommands = new ArrayList<>();
    }

    public void addCommand(String command) {
        invalidCommands.add(command);
    }

    public ArrayList<String> getCommand() {
        return invalidCommands;
    }
}
