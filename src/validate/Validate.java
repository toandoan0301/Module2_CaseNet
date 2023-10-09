package validate;

import java.util.Scanner;

public class Validate {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    static final String USERNAME_REGEX = "^[a-zA-Z0-9]{6,}$";
    static final String PASSWORD_REGEX = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])[a-zA-Z0-9]{6,}$";
    static final String SERVICE_ID = "^[0-9]{3,}$";
    static final String SERVICE_NAME = "^.{3,}$";
    private Scanner INPUT;
    public Validate(Scanner scanner){
        this.INPUT=scanner;
    }
    public int inputChoice() {
        int number;
        do {
            System.out.print("Nhập lựa chọn: ");
            try {
                number = Integer.parseInt(INPUT.nextLine());
                return number;
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "Vui lòng nhập số!" + ANSI_RESET);
            }
        } while (true);
    }

    public String inputUsername() {
        String username;
        do {
            System.out.print("Nhập Tên Tài Khoản: ");
            username = INPUT.nextLine();
            if (username.matches(USERNAME_REGEX)) {
                return username;
            } else {
                System.out.println(ANSI_RED + "Bạn hãy nhập tối thiểu 6 ký tự và không chứa ký tự đặc biệt!" + ANSI_RESET);
            }
        } while (true);
    }

    public String inputPassword() {
        String password;
        do {
            System.out.print("Nhập Mật Khẩu(6 ký tự trở lên): ");
            password = INPUT.nextLine();
            if (password.matches(PASSWORD_REGEX)) {
                return password;
            } else {
                System.out.println(ANSI_RED + "Mật khẩu phải có số, chữ hoa, chữ thường!" + ANSI_RESET);
            }
        } while (true);
    }

    public int inputMoney() {
        int money;
        do {
            System.out.print("Nhập số tiền muốn nạp: ");
            try {
                money = Integer.parseInt(INPUT.nextLine());
                if (money < 1000)
                    throw new NumberFormatException(ANSI_RED + "Số tiền không được ít hơn 1000 VND" + ANSI_RESET);
                return money;
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "Vui lòng nhập số!" + ANSI_RESET);
            }
        } while (true);
    }

    public void waiting(int choice) {
        if (choice != 0) {
            System.out.print(ANSI_BLUE + "Nhấn Enter Để Tiếp Tục..." + ANSI_RESET);
            INPUT.nextLine();
        }
    }

    public String inputServiceId() {
        String id;
        do {
            System.out.print("Nhập Mã Dịch Vụ(vd: 001): ");
            id = INPUT.nextLine();
            if (id.matches(SERVICE_ID)) {
                return id;
            }
            System.out.println(ANSI_RED + "----->Mã dịch vụ cần có ít nhất 3 chữ số!<-----" + ANSI_RESET);
        } while (true);
    }

    public String inputServiceName() {
        String name;
        do {
            System.out.print("Nhập Tên Dịch Vụ: ");
            name = INPUT.nextLine();
            if (name.matches(SERVICE_NAME)) {
                return name;
            }
            System.out.println(ANSI_RED + "----->Tên dịch vụ cần Ít nhất 3 ký tự!<-----" + ANSI_RESET);
        } while (true);
    }

    public int inputNumber(String str) {
        int number;
        do {
            try {
                System.out.print(str);
                number = Integer.parseInt(INPUT.nextLine());
                return number;
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "----->vui lòng nhập số!<-----" + ANSI_RESET);
            }
        } while (true);
    }

    public int inputPrice() {
        int price;
        do {
            System.out.print("Nhập giá dịch vụ: ");
            try {
                price = Integer.parseInt(INPUT.nextLine());
                return price;
            } catch (NumberFormatException e) {
                System.out.println(ANSI_RED + "----->vui lòng nhập số!<-----" + ANSI_RESET);
            }
        } while (true);
    }

    public String inputString(String str) {
        String result;
        do {
            System.out.print(str);
            result = INPUT.nextLine();
            if (result.matches("^.{1,}")) break;
            System.out.println(ANSI_RED + "Không được để trống" + ANSI_RESET);
        } while (true);

        return result;
    }

}
