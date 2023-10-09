package server;

import manager.ServiceManager;
import model.Service;
import validate.Validate;

import java.util.Scanner;

public class ServiceMenu{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    static Validate validate= new Validate(new Scanner(System.in));

    public static void showMenu(ServiceManager serviceManager) {
        int choice;
        do {
            System.out.println(ANSI_BLUE+"=======>Quản Lý Dịch Vụ<======="+ANSI_RESET);
            System.out.println("1. Thêm Dịch Vụ Mới" +
                    "\n2. Sửa Dịch Vụ" +
                    "\n3. Xoá Dịch Vụ" +
                    "\n4. Danh Sách Dịch Vụ" +
                    "\n5. ???" +
                    "\n0. Quay Lại");
            choice = validate.inputChoice();
            switch (choice) {
                case 1:
                    addService(serviceManager);
                    break;
                case 2:
                    editService(serviceManager);
                    break;
                case 3:
                    deleteService(serviceManager);
                    break;
                case 4:
                    showAllServices(serviceManager);
                    break;
                case 5:
                    System.out.println("Tạm thời chưa có");
                    break;
                case 0:
                    break;
                default:
                    System.out.println(ANSI_RED + "----->Lựa chọn không tồn tại!<-----" + ANSI_RESET);
            }
            validate.waiting(choice);
        } while (choice != 0);
    }

    public static void addService(ServiceManager serviceManager) {
        System.out.println(ANSI_BLUE + "=====>Thêm Dịch Vụ Mới<====="+ANSI_RESET);
        String id;
        do {
            id = validate.inputServiceId();
            if (serviceManager.findIndexById(id) == -1) {
                break;
            }
            System.out.println(ANSI_RED + "Mã dịch vụ đã tồn tại" + ANSI_RESET);
        } while (true);
        String name = validate.inputServiceName();
        int price = validate.inputPrice();
        int quantity = validate.inputNumber("Nhập số lượng: ");
        serviceManager.add(new Service(id, name, price, quantity));
        System.out.println(ANSI_GREEN + "---->Thêm mới thành công!<----" + ANSI_RESET);
    }

    public static void editService(ServiceManager serviceManager) {
        System.out.println(ANSI_BLUE + "=====>Sửa Dịch VỤ<=====");
        String id = validate.inputServiceId();
        if (serviceManager.findIndexById(id) != -1) {
            String name = validate.inputServiceName();
            int price = validate.inputPrice();
            int quantity = validate.inputNumber("Nhập số lượng: ");
            serviceManager.edit(new Service(id, name, price, quantity));
            System.out.println(ANSI_GREEN + "---->Sửa thành công!<----" + ANSI_RESET);
        } else {
            System.out.println(ANSI_PURPLE + "Không tìm thấy dịch vụ có Id là: " + id + ANSI_RESET);
        }
    }

    public static void deleteService(ServiceManager serviceManager) {
        System.out.println(ANSI_BLUE + "=====>Xoá Dịch Vụ<=====");
        String id = validate.inputServiceId();
        if (serviceManager.findIndexById(id) != -1) {
            do {
                System.out.println(ANSI_RED + "Nhập 1 để xác nhận xoá | 0 để bỏ qua!" + ANSI_RESET);
                int choice = validate.inputChoice();
                if (choice == 1) {
                    serviceManager.delete(id);
                    System.out.println(ANSI_GREEN + "----->Đã Xoá Dịch Vụ!<-----" + ANSI_RESET);
                    break;
                } else if (choice == 0) {
                    break;
                }
                System.out.println(ANSI_RED + "Vui lòng nhập đúng lựa chọn!" + ANSI_RESET);
            } while (true);
        } else {
            System.out.println(ANSI_RED + "----->Không tìm thấy dịch vụ để xoá!<-----" + ANSI_RESET);
        }
    }

    public static void showAllServices(ServiceManager serviceManager) {
        System.out.println(ANSI_BLUE+"========>Danh Sách Dịch Vụ<========"+ANSI_RESET);
        if (serviceManager.findAll().isEmpty()) {
            System.out.println(ANSI_PURPLE + "---->Không có dịch vụ!<----" + ANSI_RESET);
        } else {
            //System.out.printf(ANSI_BLUE+"%-6s%-15s%-12s%s\n"+ANSI_RESET,"Mã","Tên","Giá","Số Lượng");
            for (Service service : serviceManager.findAll()) {
                System.out.println(service.getInfo());
            }
        }
    }
}
