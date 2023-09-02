package lettery.models;

import java.util.ArrayList;
import java.util.Collections;

public class AccountList {
    private ArrayList<Account> accounts;
    private Account currentAccount;

    public AccountList() {
        accounts = new ArrayList<>();
    }
    public void addAccounts(Account acc) {
        accounts.add(acc);
    }
    public boolean checkAccount(String username, String password) {
        for (Account acc: accounts) {
            if (acc.getUsername().equals(username)) {
                currentAccount = acc;
                if (currentAccount.getPassword().equals(password)) {
                    return true;
                }
            }
        }
        currentAccount = null;
        return false;
    }

    public boolean checkUsernameAvailable(String username) {
        for (Account acc: accounts) {
            if (acc.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public ArrayList<Account> getAccountList() {
        return accounts;
    }

    public ArrayList<Account> getOfficerAccountList() {
        accounts.removeIf(acc -> !acc.getRole().equals("officer"));
        return accounts;
    }

    public void sortOfficerAccounts() {
        accounts.sort(Collections.reverseOrder());
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "AccountList{" +
                "accounts=" + accounts +
                ", currentAccount=" + currentAccount +
                '}';
    }
}
