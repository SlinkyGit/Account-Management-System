package banking;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<String, Account> accounts;

    Bank() {
        accounts = new HashMap<>();
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void addAccount(String id, Account account) {
        accounts.put(id, account);
    }

    public void removeAccount(String id) {
        accounts.remove(id);
    }

    public boolean accountExistsByID(String id) {
        return (accounts.get(id) != null);
    }

}


