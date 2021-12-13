package banking;

public class TransferValidator extends Validator {
    DepositValidator depositValidator;
    WithdrawValidator withdrawValidator;

    public TransferValidator(Bank bank) {
        super(bank);
        this.bank = bank;
    }

    public static boolean amount_is_number(String[] splitted) {
        try {
            Double.parseDouble(splitted[3]);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validate(String command) {
        init_validators();
        String[] splitted = split_command(command);
        if (splitted[0].equalsIgnoreCase("transfer")) {
            return (ID_is_number(splitted)
                    && ID_is_long_enough(splitted)
                    && empty_command(splitted)
                    && same_ID_given(splitted) && IDs_exist(splitted)
                    && valid_transfer_input_string(splitted)
                    && amount_is_number(splitted));
        }
        return false;
    }

    private boolean valid_transfer_input_string(String[] splitted) {
        String withdraw = "withdraw" + " " + splitted[1] + " " + splitted[3];
        String deposit = "deposit" + " " + splitted[2] + " " + splitted[3];
        return withdrawValidator.validate(withdraw) && depositValidator.validate(deposit);
    }

    private boolean IDs_exist(String[] splitted) {
        String fromID = splitted[1];
        String toID = splitted[2];
        return (bank.accountExistsByID(fromID) && bank.accountExistsByID(toID));
    }

    private boolean same_ID_given(String[] splitted) {
        if (splitted[1].equals(splitted[2])) {
            return false;
        }
        return true;
    }

    private void init_validators() {
        depositValidator = new DepositValidator(bank);
        withdrawValidator = new WithdrawValidator(bank);
    }


}
