package banking;

public class PassTimeValidator extends Validator {

    public PassTimeValidator(Bank bank) {
        super(bank);
    }

    @Override
    public boolean validate(String command) {
        String[] splitted = split_command(command);
        if (splitted[0].equalsIgnoreCase("pass")) {
            if ((splitted.length < 2) || (splitted.length > 2)) {
                return false;
            } else {
                int x = Integer.parseInt(splitted[1]);
                if (x > 0 && x <= 60) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean validateMonth(String command) {
        String[] splitted = split_command(command);
        int month = Integer.parseInt(splitted[1]);
        return (month > 0);
    }

}
