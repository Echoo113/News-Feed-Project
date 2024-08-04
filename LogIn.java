import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */
public class LogIn implements ILogIn {
    private final Scanner scanner;
    private static String splitter = "-=-";

    public LogIn(Scanner scanner) {
        this.scanner = scanner;
    }

    public boolean checkingIn(Scanner s) {
        while (true) {
            System.out.println("Would you like to Sign into a account or" +
                    " create a new account");
            System.out.println("1. Sign in to a existing account");
            System.out.println("2. Create a new account");
            String choice = s.nextLine();
            if (choice.equals("1")) {
                return true;
            } else if (choice.equals("2")) {
                return false;
            } else {
                System.out.println("Please enter a valid decision");
            }
        }
    }

    public void createAccount(Account account) throws IOException {
        String fileName = "userFile.txt";
        FileWriter writer = new FileWriter(fileName, true);
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String temp;
        String result = null;
        int count = 0;
        while ((temp = reader.readLine()) != null) {
            result = temp;
            count++;
        }
        int index = 0;
        if (count != 0) {
            index = Integer.parseInt(result.substring(0, 1));
            index++;
        }
        // the method where makes a account will not have the id value
        // but we will do it in this method
        account.setId(index);
        writer.write(String.format("%d-=-%s-=-%s-=-%b-=-%s-=-%s-=-[]-=-[]\n",
                index, account.getName(), account.getPassword(),
                account.isSecurityProtection(), account.getSecurityQuestion(),
                account.getSecurityAnswer()));
        writer.flush();
        writer.close();
    }

    public String checkAccount(Scanner scan) throws IOException {
        String username = "";
        String password = "";
        System.out.println("What is your Username");
        username = scan.nextLine();
        System.out.println("What is your Password");
        password = scan.nextLine();
        return username + "," + password;
    }

    public Account makeAAccount(Scanner scan) throws IOException {
        boolean wantSecurity;
        String securityQuestion = null;
        String securityAnswer = null;
        System.out.println("Create your Username");
        String username = scan.nextLine();
        String checkPassword = null;
        String password = null;
        boolean passwordCheck = true;
        while (passwordCheck) {
            System.out.println("Create your Password");
            System.out.println("Include Numbers or Special Character");
            checkPassword = scan.nextLine();
            for (char x : checkPassword.toCharArray()) {
                if (!Character.isLetter(x) && !Character.isWhitespace(x)) {
                    password = checkPassword;
                    passwordCheck = false;
                }
            }
            if (passwordCheck) {
                System.out.println("Did not include numbers or special character");
            }
        }
        while (true) {
            System.out.println("Do you want a Security Question(yes or no):");
            String securityProtection = scan.nextLine().toLowerCase();
            if (securityProtection.equals("no")) {
                wantSecurity = false;
                break;
            } else if (securityProtection.equals("yes")) {
                wantSecurity = true;
                break;
            } else {
                System.out.println("Invalid Output");
            }
        }
        if (wantSecurity) {
            System.out.println("What Security Question do you want to ask");
            securityQuestion = scan.nextLine();
            System.out.println("What is your answer to your question");
            securityAnswer = scan.nextLine();
            Account account = new Account(username, password, true,
                    securityQuestion, securityAnswer);
            createAccount(account);
            return account;
        } else {
            Account account = new Account(username, password);
            createAccount(account);
            return account;
        }
    }
}
