import manager.MasterManager;
import manager.ServiceManager;
import manager.UserManager;
import model.Service;
import model.UserAccount;
import server.ServerMenu;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class MainServer {

    static ServerSocket serverSocket;
    static Socket socket;
    static final MasterManager masterManager = new MasterManager();
    ;
    static final UserManager userManager = new UserManager();
    static final ServiceManager serviceManager = new ServiceManager();
    static DataOutputStream dataOutStream;
    static DataInputStream dataInStream;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(11998);
            Thread mainThread = new Thread(new ServerMenu(masterManager, userManager, serviceManager));
            mainThread.start();
            System.out.println("Server đang chờ kết nối...");
            while (true) {
                socket = serverSocket.accept();
                Thread thread = new Thread(new ClientHandler(socket));
                System.out.println("Client " + thread.getId() + "đã kết nối!");
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private ObjectOutputStream objectOutputStream;
        private ObjectInputStream objectInputStream;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectInputStream = new ObjectInputStream(socket.getInputStream());
                do {
                    String request = (String) objectInputStream.readObject();
                    if(request.equals("Deduct_Money")){
                        deductMoney();
                    }
                    if (request.equals("Check_Login")) {
                        login();
                    }
                    if (request.equals("Login_Info")) {
                        getInforUser();
                    }
                    if (request.equals("ShowAll_Services")) {
                        List<Service> list = serviceManager.findAll();
                        objectOutputStream.writeObject(list);
                        reset();
                    }
                    if (request.equals("Change_Password")) {
                        changePassword();
                    }
                    if (request.equals("Use_Service")) {
                        useService();
                    }
                } while (true);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println(" Client đã ngắt kết nối");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        private void deductMoney() throws IOException, ClassNotFoundException {
            synchronized (userManager){
                String user= (String) objectInputStream.readObject();
                UserAccount account = userManager.findByUsername(user);
                if(account!=null){
                    if(account.getWallet()>0){
                        userManager.updateWallet(user,-5000);
                        objectOutputStream.writeObject("success");
                    }else {
                        objectOutputStream.writeObject("failed");
                    }
                    reset();
                }
            }
        }

        private void useService() throws IOException, ClassNotFoundException {
            String id = (String) objectInputStream.readObject();
            Service service = serviceManager.findById(id);
            objectOutputStream.writeObject(service);
            reset();
            if(service != null) {
                String data = (String) objectInputStream.readObject();
                if (data != null) {
                    String username = data.split(",")[0];
                    int quantity = Integer.parseInt(data.split(",")[1]);
                    int index = serviceManager.findIndexById(service.getId());
                    int price= quantity*(service.getPrice());
                    if(userManager.findByUsername(username).getWallet()>=price) {
                        serviceManager.changeQuantity(index, -quantity);
                        userManager.updateWallet(username, -price);
                        objectOutputStream.writeObject("success");
                        reset();
                    }else {
                        objectOutputStream.writeObject("failed");
                        reset();
                    }
                }
            }
        }
        private void getInforUser() throws IOException, ClassNotFoundException {
            String username = (String) objectInputStream.readObject();
            UserAccount account = userManager.findByUsername(username);
            objectOutputStream.writeObject(account);
            reset();
        }

        private void reset() throws IOException {
            objectOutputStream.flush();
            objectOutputStream.reset();
        }

        private void login() throws IOException, ClassNotFoundException {
            UserAccount account = (UserAccount) objectInputStream.readObject();
            String result = userManager.checkAccount(account);
            objectOutputStream.writeObject(result);
            reset();
        }

        private void changePassword() throws IOException, ClassNotFoundException {
            UserAccount account = (UserAccount) objectInputStream.readObject();
            String result = userManager.checkAccount(account);
            System.out.println(result);
            objectOutputStream.writeObject(result);
            reset();
            if (result != null) {
                String newPassword = (String) objectInputStream.readObject();
                userManager.updatePassword(account.getUsername(), newPassword);
            }
        }
    }
}


