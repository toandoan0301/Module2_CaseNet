package manager;

import model.MasterAccount;

import java.util.List;

public interface IAccountManager<E> {
    void add(E e);
    void updatePassword(String username, String newPassword);
    void delete(String username);
    List<E> findAll();
    int findIndexByUsername(String username);
    E findByUsername(String username);
    boolean checkAccount(String username, String password);
}
