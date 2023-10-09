package util;

import manager.MasterManager;
import manager.ServiceManager;
import manager.UserManager;
import model.UserAccount;

import java.util.List;

public class Cloud {
    private static final MasterManager masterManager = new MasterManager();
    private static final UserManager userManager = new UserManager();
    private static final ServiceManager serviceManager= new ServiceManager();

    public static MasterManager getMasterManager() {
        return masterManager;
    }
    public static UserManager getUserManager() {
        return userManager;
    }
    public static ServiceManager getServiceManager() {
        return serviceManager;
    }
    public static List<UserAccount> getUsers() {
        return userManager.findAll();
    }
}
