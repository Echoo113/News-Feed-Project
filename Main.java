import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */
public class Main {
    public static void main(String[] args) throws IOException {
        AccountMod dataMod = new AccountMod("userFile.txt");
        Scanner scanner = new Scanner(System.in);
        LogIn logIn = new LogIn(scanner);
        if (logIn.checkingIn(scanner)) {
            String data = logIn.checkAccount(scanner);
            String[] temp = data.split(",");
            Account account = dataMod.login(temp[0], temp[
                    1]);
            if (account == null) {
                System.out.println("This is a invalid password or this account does not exist");
            } else {
                runCode(scanner, account);
            }
        } else {
            System.out.println("This is the create your account page");
            Account account = logIn.makeAAccount(scanner);
            runCode(scanner, account);
        }
    }

    public static void runCode(Scanner scanner, Account account) throws IOException {
        AccountMod dataMod = new AccountMod("userFile.txt");
        while (true) {
            System.out.println("Welcome to InsFabRam!");
            System.out.println("What will you like to do");
            System.out.println("1.Search up a User");
            System.out.println("2.Block a User");
            System.out.println("3.Post a Post");
            System.out.println("4.Check Post");
            System.out.println("5.Change your Bio");
            System.out.println("6.Change your username or password");
            System.out.println("7.Logout");
            String option = scanner.nextLine();

            if (option.equals("1")) {
                System.out.println("Do you want to add or delete a friend");
                System.out.println("1.Add");
                System.out.println("2.Delete");
                String choice = scanner.nextLine();
                if (choice.equals("1")) {
                    System.out.println("Please enter your friend's username to add");
                    String user = scanner.nextLine();
                    Account account1 = dataMod.getAccountWithUsername(user);
                    if (account1 != null) {
                        if (dataMod.isFriend(account1.getId(), account.getId())) {
                            System.out.println("Your friends already");
                        } else if (dataMod.isBlock(account1.getId(), account.getId())) {
                            System.out.println("User currently in block list");
                        } else {
                            dataMod.addFriend(account1.getId(), account.getId());
                            System.out.println("Friend Added");
                        }
                    } else {
                        System.out.println("User Not found");
                    }
                }
                if (choice.equals("2")) {
                    System.out.println("Please enter your friend's username to Delete");
                    String user = scanner.nextLine();
                    Account account1 = dataMod.getAccountWithUsername(user);
                    if (account1 != null) {
                        if (dataMod.isFriend(account1.getId(), account.getId())) {
                            dataMod.deleteFriend(account1.getId(), account.getId());
                            System.out.println("Friend Deleted");
                        } else {
                            System.out.println("Not Friends, can't delete");
                        }
                    } else {
                        System.out.println("User Not found");
                    }
                }
            }
            if (option.equals("2")) {
                System.out.println("Do you want to block someone or unblock someone");
                System.out.println("1.Block");
                System.out.println("2.Unblock");
                String choice = scanner.nextLine();
                if (choice.equals("1")) {
                    System.out.println("Please enter your friend to block");
                    String block = scanner.nextLine();
                    Account account1 = dataMod.getAccountWithUsername(block);
                    if (account1 != null) {
                        if (dataMod.isBlock(account1.getId(), account.getId())) {
                            System.out.println("The user is already blocked by you");
                        } else {
                            dataMod.addBlock(account.getId(), account1.getId());
                            if (dataMod.isFriend(account1.getId(), account.getId())) {
                                dataMod.deleteFriend(account.getId(), account1.getId());
                            }
                            System.out.println("User is blocked by you");
                        }
                    } else {
                        System.out.println("Invalid User");
                    }
                } else if (choice.equals("2")) {
                    System.out.println("Please enter your friend to unblock");
                    String block = scanner.nextLine();
                    Account account1 = dataMod.getAccountWithUsername(block);
                    if (account1 != null) {
                        if (dataMod.isBlock(account1.getId(), account.getId())) {
                            dataMod.deleteBlock(account1.getId(), account.getId());
                            System.out.println("You unblocked the user");
                        } else {
                            System.out.println("You did not block the user");
                        }
                    } else {
                        System.out.println("Invalid User");
                    }
                } else {
                    System.out.println("Invalid Input");
                }

            }
            if (option.equals("6")) {
                System.out.println("Do you want to change your Username, Password, or both");
                System.out.println("1.Username");
                System.out.println("2.Password");
                System.out.println("3.Both");
                String choice = scanner.nextLine();
                if (choice.equals("1") || choice.equals("3")) {
                    System.out.println("Enter your new Username");
                    String temp = scanner.nextLine();
                    account.setName(temp);
                }
                if (choice.equals("2") || choice.equals("3")) {
                    System.out.println("Enter your new Password");
                    String temp = scanner.nextLine();
                    account.setPassword(temp);
                }
                int i = account.getId();
                dataMod.updateAccount(i, account);
            }
            if (option.equals("7")) {
                System.out.println("You sure you want to logout?");
                System.out.println("1.Yes");
                System.out.println("2.No");
                String choice = scanner.nextLine();
                if (choice.equals("1")) {
                    System.out.println("Have a nice day");
                    dataMod.saveData();
                    break;
                }
            }
        }
    }
}
