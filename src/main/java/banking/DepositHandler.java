package banking;

public class DepositHandler extends Handler {
    private Bank bank;

    public DepositHandler(Bank bank) {
        super();
        this.bank = bank;
    }

    @Override
    public void handle(String command) {
        String[] splitted = split_command(command);
        if (bank.getAccounts().get(splitted[1]).getType().equalsIgnoreCase("cd")) {
            bank.getAccounts().get(splitted[1]).getBalance();
        } else {
            int amt = Integer.parseInt(splitted[2]);
            bank.getAccounts().get(splitted[1]).deposit(amt);
            bank.getAccounts().get(splitted[1]).addTransaction(command);
        }

    }
}
