package server;

import manager.MasterManager;
import manager.ServiceManager;
import manager.UserManager;
import model.MasterAccount;
import model.Service;
import model.UserAccount;
import validate.Validate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Proxy;
import java.net.Socket;
import java.util.List;

public class ServerMenu implements Runnable {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    private Socket socket;
    private final MasterManager masterManager;
    private final UserManager userManager;
    private final ServiceManager serviceManager;
    private String currentUser = null;

    @Override
    public void run() {
        showMenu();
    }

    public ServerMenu(MasterManager masterManager,UserManager userManager,ServiceManager serviceManager){
        this.masterManager=masterManager;
        this.userManager=userManager;
        this.serviceManager=serviceManager;
    }
    public void showMenu() {
        int choice;
        do {
            System.out.println(ANSI_BLUE + "======>Menu Máy Chủ<======" + ANSI_RESET);
            System.out.println("1. Đăng Nhập" +
                    "\n2. Đăng Ký" +
                    "\n0. Thoát");
            choice = Validate.inputChoice();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 0:
                    System.out.println(ANSI_RED + "===>Thoát Ứng Dụng<====" + ANSI_RESET);
                    System.exit(0);
                    break;
                default:
                    System.out.println(ANSI_RED + "----->Nhập sai lựa chọn!<-----" + ANSI_RESET);
            }

        } while (true);
    }

    public void login() {
        String username = Validate.inputUsername();
        String password = Validate.inputPassword();
        if (masterManager.checkAccount(username, password)) {
            System.out.println(ANSI_GREEN + "---->Đăng Nhập Thành Công<----" + ANSI_RESET);
            currentUser = username;
            showMenuManagement();
        } else {
            System.out.println(ANSI_RED + "Sai tài khoản hoặc mật khẩu" + ANSI_RESET);
        }
    }

    public void register() {
        System.out.println(ANSI_BLUE + "----->Đăng Ký Tài Khoản<-----" + ANSI_RESET);
        String username;
        do {
            username = Validate.inputUsername();
            if (masterManager.findIndexByUsername(username) == -1) {
                break;
            }
            System.out.println(ANSI_RED + "Tài khoản đã tồn tại" + ANSI_RESET);
        } while (true);
        String password = Validate.inputPassword();
        masterManager.add(new MasterAccount(username, password));
        System.out.println(ANSI_GREEN + "-----Đăng ký Thành Công-----" + ANSI_RESET);
    }

    public void showMenuManagement() {
        System.out.println("Xin chào " + ANSI_PURPLE + currentUser + ANSI_RESET);
        int choice;
        do {
            System.out.println(ANSI_BLUE + "=========>" + ANSI_PURPLE + "Menu Chính" + ANSI_RESET + ANSI_BLUE + "<==========" + ANSI_RESET);
            System.out.println("1. Đăng Ký Tài Khoản Cho Khách" +
                    "\n2. Nạp Tiền Cho Khách" +
                    "\n3. Quản Lý Dịch Vụ" +
                    "\n4. Xem Tài Khoản Khách" +
                    "\n0. Đăng Xuất");
            System.out.println(ANSI_BLUE + "===============================" + ANSI_RESET);
            choice = Validate.inputChoice();
            switch (choice) {
                case 1:
                    registerUserAccount();
                    break;
                case 2:
                    rechargeForUser();
                    break;
                case 3:
                    ServiceMenu.showMenu(serviceManager);
                    break;
                case 4:
                    showAllUsers();
                    break;
                case 0:
                    currentUser = null;
                    showMenu();
                    break;
                default:
                    System.out.println(ANSI_RED + "Nhập sai lựa chọn!" + ANSI_RESET);
            }
            Validate.waiting(choice);
        } while (choice != 0);
    }

    public void registerUserAccount() {
        System.out.println("-----Đăng Ký Tài Khoản Cho Khách-----");
        String userName = Validate.inputUsername();
        String password = Validate.inputPassword();
        int money = Validate.inputMoney();
        userManager.add(new UserAccount(userName, password, money));
        System.out.println(ANSI_GREEN + "-----Đăng Ký Thành Công-----" + ANSI_RESET);
    }

    public void rechargeForUser() {
        System.out.println(ANSI_BLUE + "======>Nạp Tiền Cho Khách=======" + ANSI_RESET);
        String userName = Validate.inputUsername();
        if (userManager.findByUsername(userName) != null) {
            int money = Validate.inputMoney();
            userManager.updateWallet(userName, money);
            System.out.println(ANSI_GREEN + "----->Nạp Thành Công<-----" + ANSI_RESET);
        }
    }
    public void showAllUsers() {
        System.out.println(ANSI_BLUE+"=====>Danh sách tài khoản người chơi<====="+ANSI_RESET);
        if(userManager.findAll().isEmpty()){
            System.out.println("Khôn có tài khoản nào!");
        }else {
            for (UserAccount account : userManager.findAll()) {
                System.out.println(account.showInfor());
            }
        }
    }
}
