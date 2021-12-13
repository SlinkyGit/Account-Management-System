package banking;

public class TransferHandler extends Handler {
    private Bank bank;
    private String fromID;
    private String toID;
    private double amt;

    public TransferHandler(Bank bank) {
        super();
        this.bank = bank;
    }

    @Override
    public void handle(String command) {
        String[] splitted = split_command(command);
        amt = Double.parseDouble(splitted[3]);
        fromID = splitted[1];
        toID = splitted[2];
        bank.getAccounts().get(fromID).withdraw(amt);
        bank.getAccounts().get(toID).deposit(amt);
        bank.getAccounts().get(fromID).addTransaction(command);
        bank.getAccounts().get(toID).addTransaction(command);
    }

    //Do transaction history
}
