package manager;

import model.MasterAccount;
import util.ReadAndWrite;

import java.io.Serializable;
import java.util.List;

public class MasterManager implements IAccountManager<MasterAccount>, Serializable {
    private static final long serialVersionUID = 1L;
    private final List<MasterAccount> list;

    public MasterManager() {
        this.list = ReadAndWrite.readFileMaster();
    }

    public void add(MasterAccount masterAccount) {
        list.add(masterAccount);
        ReadAndWrite.writeFileMaster(list);
    }
    @Override
    public void updatePassword(String username, String newPassword) {
       findByUsername(username).setPassword(newPassword);
        ReadAndWrite.writeFileMaster(list);
    }
    @Override
    public void delete(String username) {
        list.remove(findIndexByUsername(username));
        ReadAndWrite.writeFileMaster(list);
    }

    @Override
    public List<MasterAccount> findAll() {
        return this.list;
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
    public MasterAccount findByUsername(String username) {
        for (MasterAccount account : list) {
            if(account.getUsername().equals(username))
                return account;
        }
        return null;
    }

    @Override
    public boolean checkAccount(String username, String password) {
        for (MasterAccount account : list) {
            if (account.getUsername().equals(username) && account.getPassword().equals(password))
                return true;
        }
        return false;
    }
}
