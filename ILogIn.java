import java.io.IOException;
import java.util.Scanner;

/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */
public interface ILogIn {
    boolean checkingIn(Scanner scanner);

    void createAccount(Account account) throws IOException;

    String checkAccount(Scanner scanner) throws IOException;

    Account makeAAccount(Scanner scanner) throws IOException;
}