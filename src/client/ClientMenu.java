package client;

import model.Service;
import model.UserAccount;
import validate.Validate;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

public class ClientMenu extends Thread {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_CYAN = "\u001B[36m";
    private String userLogin = null;
    private final Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private ObjectInputStream chatInputStream;
    private ObjectOutputStream chatOutputStream;
    private Validate validate;

    public ClientMenu(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            Thread chatThread = chatThread();
            chatThread.start();
            connect();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void connect() {
        userLogin = login();
        deductFromAccount();
        menuPlayer();
    }

    public String login() {
        do {
            validate = new Validate(new Scanner(System.in));
            System.out.println(ANSI_BLUE + "======>Đăng Nhập Vào Máy<======" + ANSI_RESET);
            String username = validate.inputString(String.format("%-18s%s", "Nhập tên Tài Khoản", ": "));
            String password = validate.inputString(String.format("%-18s%s", "Nhập mật khẩu", ": "));
            try {
                objectOutputStream.writeObject("Check_Login");
                resetOutput();
                objectOutputStream.writeObject(new UserAccount(username, password));
                resetOutput();
                String result = (String) objectInputStream.readObject();
                if (result != null) {
                    System.out.println(ANSI_GREEN + "Đăng Nhập Thành Công!" + ANSI_RESET);
                    return username;
                }
                System.out.println(ANSI_RED + "Sai Tài Khoản Hoặc Mật Khẩu!" + ANSI_RESET);
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }

        } while (true);
    }

    private void resetOutput() throws IOException {
        objectOutputStream.flush();
        objectOutputStream.reset();
    }

    public void menuPlayer() {
        if (userLogin == null) {
            connect();
        } else {
            int choice;
            do {
                showMenu();
                choice = validate.inputChoice();
                switch (choice) {
                    case 1:
                        showInfor();
                        break;
                    case 2:
                        changePassword();
                        break;
                    case 3:
                        menuService();
                        break;
                    case 4:
                        chat();

                        break;
                    case 0:
                        System.out.println("Đã Đăng Xuất");
                        userLogin = null;
                        connect();
                        break;
                    default:
                        System.out.println(ANSI_RED + "Sai lựa chọn" + ANSI_RESET);
                }
                validate.waiting(choice);
            } while (choice != 0);
        }
    }

    public void showInfor() {
        try {
            objectOutputStream.writeObject("Login_Info");
            resetOutput();
            objectOutputStream.writeObject(userLogin);
            resetOutput();
            UserAccount account = (UserAccount) objectInputStream.readObject();
            System.out.println(account.showInfor());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void changePassword() {
        try {
            objectOutputStream.writeObject("Change_Password");
            resetOutput();
            System.out.println(ANSI_GREEN + "====>Thay Đổi Mật Khẩu<=====" + ANSI_RESET);
            String oldPassword = validate.inputString("Nhập mật khẩu hiện tại: ");
            objectOutputStream.writeObject(new UserAccount(userLogin, oldPassword));
            resetOutput();
            String check = (String) objectInputStream.readObject();
            if (check != null) {
                System.out.println("Nhập mật khẩu mới");
                String newPassword = validate.inputPassword();
                objectOutputStream.writeObject(newPassword);
                resetOutput();
                System.out.println(ANSI_GREEN + "Đã đổi mật khẩu");

            } else {
                System.out.println(ANSI_RED + "Sai Mật Khẩu" + ANSI_RESET);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void menuService() {
        int choice;
        do {
            System.out.println(ANSI_BLUE + "=======>Sử Dụng Dịch Vụ<=======" + ANSI_RESET);
            System.out.println("\t1. Danh sách dịch vụ" +
                    "\n\t2. Sử dụng dịch vụ" +
                    "\n\t0. Quay Lại.");
            choice = validate.inputChoice();
            switch (choice) {
                case 1:
                    showAllServices();
                case 2:
                    useService();
                    break;
                case 0:
                    break;
                default:
                    System.out.println(ANSI_RED + "Nhập sai lựa chọn" + ANSI_RESET);
            }
            validate.waiting(choice);
        } while (choice != 0);
    }

    public void showAllServices() {
        try {
            objectOutputStream.writeObject("ShowAll_Services");
            resetOutput();
            System.out.println(ANSI_BLUE + "=======>Danh sách dịch vụ<========" + ANSI_RESET);
            List<Service> services = (List<Service>) objectInputStream.readObject();
            for (Service service : services) {
                System.out.println(service.getInfo());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void useService() {

        try {
            objectOutputStream.writeObject("Use_Service");
            resetOutput();
            String id = validate.inputString("Nhập mã dịch vụ muốn sử dụng: ");
            objectOutputStream.writeObject(id);
            resetOutput();
            Service service = (Service) objectInputStream.readObject();
            if (service != null) {
                int quantity = validate.inputNumber("Nhập số lượng: ");
                if (service.getQuantity() >= quantity) {
                    objectOutputStream.writeObject(userLogin + "," + quantity);
                    resetOutput();
                    String check = (String) objectInputStream.readObject();
                    if (check.equals("success")) {
                        System.out.println("-" + (service.getPrice() * quantity) + " VND");
                        System.out.println(ANSI_GREEN + "gọi dịch vụ thành công vui lòng chờ nhân viên phục vụ" + ANSI_RESET);
                    } else {
                        System.out.println(ANSI_RED + "Bạn không đủ tiền để gọi dịch vụ" + ANSI_RESET);
                    }

                } else {
                    objectOutputStream.writeObject(null);
                    resetOutput();
                    System.out.println(ANSI_RED + "Số lượng không đủ vui lòng chọn lại!" + ANSI_RESET);
                }
            } else
                System.out.println(ANSI_RED + "Sai mã số dịch vụ vui lòng chọn lại!" + ANSI_RESET);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void showMenu() {
        System.out.println(ANSI_CYAN + "=======>Menu Chức Năng<=======" + ANSI_RESET);
        System.out.println("Xin chào " + ANSI_YELLOW + userLogin + ANSI_RESET + " Ngày mới tốt lành");
        System.out.println("1. Xem thông tin tài khoản" +
                "\n2. Đổi mật khẩu" +
                "\n3. Sử dụng dịch vụ" +
                "\n4. Chat với chủ quản" +
                "\n0. Đăng xuất");
        System.out.println(ANSI_CYAN + "===============================" + ANSI_RESET);
    }

    public void deductFromAccount() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (userLogin != null) {
                        objectOutputStream.writeObject("Deduct_Money");
                        resetOutput();
                        objectOutputStream.writeObject(userLogin);
                        resetOutput();
                        String result = (String) objectInputStream.readObject();
                        if (result.equals("success")) {
                            System.out.println(ANSI_RED + "\n-5000 VND" + ANSI_RESET);
                        } else {
                            System.out.println(ANSI_YELLOW + "Đã hết tiền vui lòng nạp thêm !" + ANSI_RESET);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            userLogin = null;
                            connect();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }
        };
        Calendar data = Calendar.getInstance();
        timer.schedule(task, data.getTime(), 20000);
    }
    private Thread chatThread() {

        return new Thread(() -> {
            try {
                Socket chatSocket = new Socket("localhost",21998);
                chatInputStream = new ObjectInputStream(chatSocket.getInputStream());
                chatOutputStream = new ObjectOutputStream(chatSocket.getOutputStream());
                while (true) {
                    String request = (String) chatInputStream.readObject();
                    if (request.equals("Server_Chat")) {
                        String msg = (String) chatInputStream.readObject();
                        System.out.println(ANSI_YELLOW+"Chủ quán: " + msg+ANSI_RESET);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        });
    }
    public void chat() {
        try {
            chatOutputStream.writeObject("Cline_Chat");
            chatOutputStream.reset();
            String messageInput = validate.inputString("Nhập tin nhắn: ");
            String[] message = {userLogin, messageInput};
            chatOutputStream.writeObject(message);
            chatOutputStream.reset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
