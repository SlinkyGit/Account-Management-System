package banking;

public class DepositValidator extends Validator {
    CD cd;
    private Bank bank;

    public DepositValidator(Bank bank) {
        super(bank);
        this.bank = bank;
    }

    @Override
    public boolean validate(String command) {
        String[] splitted = split_command(command);
        if (splitted[0].equalsIgnoreCase("deposit")) {
            return (bank.accountExistsByID(splitted[1]) && ID_is_number(splitted)
                    && ID_is_long_enough(splitted) && type_spelt_right(splitted)
                    && empty_command(splitted) && is_duplicate_id(splitted)
                    && is_deposit_negative(splitted) && command_present(splitted)
                    && is_deposit_amount_present(splitted) && missing_ID(splitted)
                    && valid_deposit_range(splitted) && (bank.getAccounts().get(splitted[1]).getType() != "cd"));
        } else {
            return false;
        }
    }
}
