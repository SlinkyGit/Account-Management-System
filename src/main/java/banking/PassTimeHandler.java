package banking;

import java.util.ArrayList;
import java.util.List;

public class PassTimeHandler extends Handler {
    Bank bank;
    List<String> deleteAccount = new ArrayList<>();

    public PassTimeHandler(Bank bank) {
        super();
        this.bank = bank;
    }


    @Override
    public void handle(String command) {
        String[] splitted = split_command(command);
        int months = Integer.parseInt(splitted[1]);
        for (Account account : bank.getAccounts().values()) {
            for (int i = 0; i < months; i++) {
                double balance = account.getBalance();
                if (balance <= 0.0) {
                    deleteAccount.add(account.getID());
                } else {
                    if ((balance < 100) && (balance > 0)) {
                        account.withdraw(25);
                    }
                    account.age++;
                    account.calculateAPR();
                }
            }
        }
        for (String id : deleteAccount) {
            bank.removeAccount(id);
        }

    }
}
    
