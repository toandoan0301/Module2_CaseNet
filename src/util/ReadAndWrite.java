package util;

import model.MasterAccount;
import model.Service;
import model.UserAccount;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadAndWrite {
    private static final String PATCH_MASTER = "data/master.csv";
    private static final String PATCH_USER = "data/user.csv";
    private static final String PATCH_SERVICE = "data/service.csv";

    public static void writeFileMaster(List<MasterAccount> list) {
        try {
            FileWriter fileWriter = new FileWriter(PATCH_MASTER);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            StringBuilder data = new StringBuilder();
            for (MasterAccount account : list) {
                data.append(account).append("\n");
            }
            writer.write(data.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<MasterAccount> readFileMaster() {
        List<MasterAccount> accounts = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(PATCH_MASTER);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                accounts.add(new MasterAccount(data[0], data[1]));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }
    public static void writeFileUser(List<UserAccount> list) {
        try {
            FileWriter fileWriter = new FileWriter(PATCH_USER);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            StringBuilder data = new StringBuilder();
            for (UserAccount account : list) {
                data.append(account).append("\n");
            }
            writer.write(data.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<UserAccount> readFileUser() {
        List<UserAccount> accounts = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(PATCH_USER);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                accounts.add(new UserAccount(data[0], data[1],Integer.parseInt(data[2])));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return accounts;
    }

    public static void writeFileService(List<Service> list) {
        try {
            FileWriter fileWriter = new FileWriter(PATCH_SERVICE);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            StringBuilder data = new StringBuilder();
            for (Service service : list) {
                data.append(service).append("\n");
            }
            writer.write(data.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Service> readFileService() {
        List<Service> list = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(PATCH_SERVICE);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                list.add(new Service(data[0], data[1],Integer.parseInt(data[2]),Integer.parseInt(data[3])));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
}
