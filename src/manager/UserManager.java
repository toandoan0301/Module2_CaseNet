package manager;

import model.UserAccount;
import util.ReadAndWrite;

import java.io.Serializable;
import java.util.List;

public class UserManager implements IAccountManager<UserAccount>, Serializable {
    private static final long serialVersionUID = 1L;
    private final List<UserAccount> list;

    public UserManager() {
        this.list = ReadAndWrite.readFileUser();
    }

    @Override
    public void add(UserAccount userAccount) {
        list.add(userAccount);
        ReadAndWrite.writeFileUser(list);
    }

    @Override
    public void updatePassword(String userName, String newPassword) {
        findByUsername(userName).setPassword(newPassword);
        ReadAndWrite.writeFileUser(list);
    }

    @Override
    public void delete(String username) {
        list.remove(findIndexByUsername(username));
        ReadAndWrite.writeFileUser(list);
    }

    @Override
    public List<UserAccount> findAll() {
        return list;
    }

    @Override
    public int findIndexByUsername(String username) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public UserAccount findByUsername(String username) {
        for (UserAccount account : list) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }

    @Override
    public boolean checkAccount(String username, String password) {
        for (UserAccount account : list) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    public String checkAccount(UserAccount accountLogin) {
        for (UserAccount account : list) {
            if (account.getUsername().equals(accountLogin.getUsername()) && account.getPassword().equals(accountLogin.getPassword())) {
                return account.getUsername();
            }
        }
        return null;
    }
    public void updateWallet(String username, int wallet) {
        findByUsername(username).updateWallet(wallet);
        ReadAndWrite.writeFileUser(list);
    }
}
