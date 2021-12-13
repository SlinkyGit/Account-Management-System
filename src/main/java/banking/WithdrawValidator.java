package banking;

public class WithdrawValidator extends Validator {
    private String id;

    public WithdrawValidator(Bank bank) {
        super(bank);
        this.bank = bank;
    }

    public String getID() {
        return this.id;
    }

    @Override
    public boolean validate(String command) {
        String[] splitted = split_command(command);
        if (splitted[0].equalsIgnoreCase("withdraw")) {
            return (ID_is_number(splitted)
                    && ID_is_long_enough(splitted)
                    && type_spelt_right(splitted)
                    && empty_command(splitted)
                    && valid_withdraw_month(splitted)
                    && valid_withdraw_amt(splitted));
        }
        return false;
    }


}
