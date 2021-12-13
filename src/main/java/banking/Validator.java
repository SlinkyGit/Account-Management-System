package banking;

public abstract class Validator {
    Bank bank;

    public Validator(Bank bank) {
        this.bank = bank;
    }

    public abstract boolean validate(String command);

    public String[] split_command(String command) {
        String[] commandString = command.split(" ");
        return commandString;
    }

    public boolean ID_is_number(String[] splitted) {
        try {
            if (splitted[0].equalsIgnoreCase("withdraw") || splitted[0].equalsIgnoreCase("deposit")) {
                Integer.parseInt(splitted[1]);
            }
            if (splitted[0].equalsIgnoreCase("create")) {
                Integer.parseInt(splitted[2]);
            }
            if (splitted[0].equalsIgnoreCase("transfer")) {
                Integer.parseInt(splitted[1]);
                Integer.parseInt(splitted[2]);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean ID_is_long_enough(String[] splitted) {
        if (splitted[0].equalsIgnoreCase("create")) {
            if (splitted[2].length() < 8) {
                return false;
            } else if ((splitted[0].equalsIgnoreCase("deposit")) || (splitted[0].equalsIgnoreCase("withdraw"))) {
                if (splitted[1].length() < 8) {
                    return false;
                }
            } else if (splitted[0].equalsIgnoreCase("transfer")) {
                if ((splitted[1].length() < 8) && (splitted[2].length() < 8)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean type_spelt_right(String[] splitted) {
        return (splitted[1].toLowerCase() != "savings")
                || (splitted[1].toLowerCase() != "checking")
                || (splitted[1].toLowerCase() != "cd");
    }

    public boolean missing_APR(String[] splitted) {
        if ((splitted[1].equalsIgnoreCase("cd"))) {
            return !(splitted.length < 5);
        }
        return true;
    }

    public boolean valid_APR_range(String[] splitted) {
        double x = Double.parseDouble(splitted[3]);
        return ((x >= 0) && (x <= 10));
    }

    public boolean valid_create_amount(String[] splitted) {
        if ((splitted[1].equalsIgnoreCase("cd"))) {
            int x = Integer.parseInt(splitted[4]);
            return ((x >= 1000) && (x <= 10000));
        } else {
            return splitted.length == 4;
        }
    }

    public boolean empty_command(String[] splitted) {
        return !(splitted.length == 0);
    }

    public boolean is_duplicate_id(String[] splitted) {
        return !(bank.getAccounts().containsKey(splitted[2]));
    }

    public boolean command_present(String[] splitted) {
        return ((splitted[0].equalsIgnoreCase("create") || (splitted[0].equalsIgnoreCase("deposit"))));
    }

    public boolean is_deposit_negative(String[] splitted) {
        int x = Integer.parseInt(splitted[2]);
        return !(x < 0);
    }

    public boolean is_deposit_amount_present(String[] splitted) {
        if (splitted[0] == "deposit") {
            return !(splitted.length < 3);
        }
        return true;
    }

    public boolean missing_ID(String[] splitted) {
        if (splitted[1].length() < 8) {
            return false;
        } else {
            return true;
        }
    }

    public boolean valid_deposit_range(String[] splitted) {
        if (splitted.length == 3) {
            double x = Double.parseDouble(splitted[2]);
            return bank.getAccounts().get(splitted[1]).validDeposit(x);
        }
        return false;
    }

    public boolean valid_withdraw_amt(String[] splitted) {
        double amt = 0;
        try {
            amt = Double.parseDouble(splitted[2]);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return bank.getAccounts().get(splitted[1]).validWithdraw(amt);
    }

    public boolean valid_withdraw_month(String[] splitted) {
        return bank.getAccounts().get(splitted[1]).allowWithdraw;

    }


}
