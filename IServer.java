import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */
interface IServer {

     static void mainPage(Account account, BufferedReader reader, PrintWriter writer) throws IOException {

    }

    static void createAccount(Account account) throws IOException {

    }

    static String viewPosts(Account account) {
        return null;
    }

    static void writeSinglePost(Post post, Account account) throws IOException {

    }

    static void flushPostFile(Account account) {

    }

    static ArrayList<Post> initiatePosts(Account account) {
        return null;
    }
}
