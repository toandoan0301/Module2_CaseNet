import client.ClientMenu;

import java.io.*;
import java.net.Socket;

public class MainClient{
    static ObjectOutputStream objectOutputStream;
    static ObjectInputStream objectInputStream;
    static Socket socket;

    public static void main(String[] args) {
      //  MainClient client = new MainClient();
//        client.start();
        try {
            socket = new Socket("localhost", 11998);
            ClientMenu clientMenu = new ClientMenu(socket);
            clientMenu.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        try {
            socket = new Socket("localhost", 11998);
            Thread thread= new Thread(new ClientMenu(socket));
            thread.start();
//            ClientMenu clientMenu = new ClientMenu(socket);
//            clientMenu.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
