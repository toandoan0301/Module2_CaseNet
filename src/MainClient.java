import client.ClientMenu;

import java.io.*;
import java.net.Socket;

public class MainClient extends Thread {
    static Socket socket;

    public static void main(String[] args) {
        MainClient client = new MainClient();
        client.start();
    }

    public void run() {
        try {
            socket = new Socket("localhost", 11998);
            ClientMenu clientMenu = new ClientMenu(socket);
            clientMenu.start();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
