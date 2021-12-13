package banking;

public class CreateHandler extends Handler {
    Account checking, savings, CD;
    private Bank bank;
    private int id;
    private double apr;

    public CreateHandler(Bank bank) {
        super();
        this.bank = bank;
    }

    @Override
    public void handle(String command) {
        String[] splitted = split_command(command);
        double x = Double.parseDouble(splitted[3]);
        if (splitted[1].equalsIgnoreCase("checking")) {
            bank.addAccount(splitted[2], new Checking("checking", splitted[2], x));
        } else if (splitted[1].equalsIgnoreCase("savings")) {
            bank.addAccount(splitted[2], new Savings("savings", splitted[2], x));
        } else if (splitted[1].equalsIgnoreCase("cd")) {
            double y = Double.parseDouble(splitted[4]);
            bank.addAccount(splitted[2], new CD("cd", splitted[2], x, y));
        }
    }
}