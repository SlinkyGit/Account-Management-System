package banking;

public class ValidatorRedirect {
    DepositValidator depositValidator;
    CreateValidator createValidator;
    PassTimeValidator passTimeValidator;
    WithdrawValidator withdrawValidator;
    TransferValidator transferValidator;

    ValidatorRedirect(Bank bank) {
        depositValidator = new DepositValidator(bank);
        createValidator = new CreateValidator(bank);
        passTimeValidator = new PassTimeValidator(bank);
        withdrawValidator = new WithdrawValidator(bank);
        transferValidator = new TransferValidator(bank);
        passTimeValidator = new PassTimeValidator(bank);
    }

    public String[] split_command(String command) {
        String[] commandString = command.split(" ");
        return commandString;
    }

    public boolean validate(String command) {
        String[] splitted = split_command(command);
        if (splitted[0].equalsIgnoreCase("create")) {
            return createValidator.validate(command);
        } else if (splitted[0].equalsIgnoreCase("deposit")) {
            return depositValidator.validate(command);
        } else if (splitted[0].equalsIgnoreCase("withdraw")) {
            return withdrawValidator.validate(command);
        } else if (splitted[0].equalsIgnoreCase("transfer")) {
            return transferValidator.validate(command);
        } else if (splitted[0].equalsIgnoreCase("pass")) {
            return passTimeValidator.validate(command);
        }
        return false;
    }
}
