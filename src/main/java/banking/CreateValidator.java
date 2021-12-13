package banking;

public class CreateValidator extends Validator {
    private Bank bank;

    public CreateValidator(Bank bank) {
        super(bank);
        this.bank = bank;
    }

    @Override
    public boolean validate(String command) {
        String[] splitted = split_command(command);
        if (splitted[0].equalsIgnoreCase("create")) {
            return (ID_is_number(splitted)
                    && ID_is_long_enough(splitted) && type_spelt_right(splitted)
                    && missing_APR(splitted) && valid_APR_range(splitted)
                    && valid_create_amount(splitted) && empty_command(splitted)
                    && is_duplicate_id(splitted));
        }
        return false;
    }
}