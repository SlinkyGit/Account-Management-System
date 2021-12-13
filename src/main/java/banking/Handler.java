package banking;

public abstract class Handler {
    public abstract void handle(String command);

    public String[] split_command(String command) {
        String[] commandString = command.split(" ");
        return commandString;
    }

}
