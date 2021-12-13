package banking;

public class HandlerRedirect {
    DepositHandler depositHandler;
    CreateHandler createHandler;
    WithdrawHandler withdrawHandler;
    PassTimeHandler passHandler;
    TransferHandler transferHandler;

    HandlerRedirect(Bank bank) {
        depositHandler = new DepositHandler(bank);
        createHandler = new CreateHandler(bank);
        withdrawHandler = new WithdrawHandler(bank);
        passHandler = new PassTimeHandler(bank);
        transferHandler = new TransferHandler(bank);
    }

    public String[] split_command(String command) {
        String[] commandString = command.split(" ");
        return commandString;
    }

    public void handle(String command) {
        String[] splitted = split_command(command);
        if (splitted[0].equalsIgnoreCase("create")) {
            createHandler.handle(command);
        } else if (splitted[0].equalsIgnoreCase("deposit")) {
            depositHandler.handle(command);
        } else if (splitted[0].equalsIgnoreCase("withdraw")) {
            withdrawHandler.handle(command);
        } else if (splitted[0].equalsIgnoreCase("pass")) {
            passHandler.handle(command);
        } else if (splitted[0].equalsIgnoreCase("transfer")) {
            transferHandler.handle(command);
        }
    }
}
