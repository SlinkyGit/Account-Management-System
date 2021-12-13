package banking;

public class WithdrawHandler extends Handler {
    private Bank bank;

    public WithdrawHandler(Bank bank) {
        super();
        this.bank = bank;
    }

    @Override
    public void handle(String command) {
        String[] splitted = split_command(command);
        double amt = Double.parseDouble(splitted[2]);
        bank.getAccounts().get(splitted[1]).withdraw(amt);
        bank.getAccounts().get(splitted[1]).addTransaction(command);
    }


}
