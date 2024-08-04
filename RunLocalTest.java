import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */
@RunWith(Enclosed.class)
public class RunLocalTest {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(AccountDatabaseTestCase.class, PostTest.class, RepliesTest.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    public static class BadDataExceptionTest {

        @Test
        public void testExceptionMessage() {
            String message = "Test exception message";
            BadDataException exception = new BadDataException(message);
            assertEquals(message, exception.getMessage());
        }
    }

    public static class AccountDatabaseTestCase {
        private static final String INFILE = "input.txt";
        private static final String OUTFILE_DB = "databaseOut.txt";
        private static final String OUTFILE_MAIN = "mainOut.txt";

        @Before
        public void setup() throws IOException {
            // Write input for the test to a file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(INFILE))) {
                String input = "register\nlihua_123456_my father's name_liqiang_[]_[]\n" +
                        "register\nzhangsan_123456_my mother's name_zhangsi_[]_[]\n" +
                        "register\nsunyi_123456_my brother's name_suner_[]_[]\n" +
                        "login\nsunyi 123456\nlogin\nsunyi 123\nverifyQuestion\n1 liqiang\n" +
                        "verifyQuestion\n1 li\naddFriend\n1 2\naddFriend\n1 3\ndeleteFriend\n1 2\n" +
                        "addBlock\n1 2\nisBlock\n1 2\ndeleteBlock\n1 2\nisBlock\n1 2\nisFriend\n1 2\n" +
                        "isFriend\n1 3\naddFriend\n1 2\nregister\nwangwu_123456_my brother's name_liu_[]_[]\nlogout\n4\nsaveData";
                writer.write(input);
                writer.flush();
            }
        }

        @Test(timeout = 1000)
        public void testDatabaseOutput() throws IOException {
            AccountDatabaseTest.main(new String[0]); // Assuming this is your method to execute the test logic

            String expectedOutput = "1_lihua_123456_my father's name_liqiang_[3, 2]_[]\n" +
                    "2_zhangsan_123456_my mother's name_zhangsi_[1]_[]\n" +
                    "3_sunyi_123456_my brother's name_suner_[1]_[]\n";

            StringBuilder actualOutput = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(OUTFILE_DB))) {
                reader.lines().forEach(line -> actualOutput.append(line).append("\n"));
            }

            assertEquals("Database output does not match expected output.", expectedOutput.trim(), actualOutput.toString().trim());
        }

      /*  @Test(timeout = 1000)
        public void testMainOutput() throws IOException {
            AccountDatabaseTest.main(new String[0]); // Assuming this is your method to execute the test logic

            String expectedOutput = "register success\n" +
                    "register success\n" +
                    "register success\n" +
                    "login success\n" +
                    "login fail\n" +
                    "answer correct\n" +
                    "answer incorrect\n" +
                    "add friend 1 2 success\n" +
                    "add friend 1 3 success\n" +
                    "delete friend 1 2 success\n" +
                    "add block 1 2 success\n" +
                    "delete block 1 2 success\n" +
                    "1-2 are not friends\n" +
                    "1-3 are friends\n" +
                    "add friend 1 2 success\n" +
                    "register success\n" +
                    "logout success\n";

            StringBuilder actualOutput = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(OUTFILE_MAIN))) {
                reader.lines().forEach(line -> actualOutput.append(line).append("\n"));
            }

            assertEquals("Main output does not match expected output.", expectedOutput.trim(), actualOutput.toString().trim());
        }*/
    }

    public static class AccountModTest {
        private AccountMod accountMod;
        private final String TEST_FILE = "testAccounts.txt";

        @Before
        public void setUp() throws IOException, BadDataException {
            // Setup to run before each test
            new File(TEST_FILE).createNewFile(); // Ensure file exists
            this.accountMod = new AccountMod(TEST_FILE);
            // Assuming Account has a proper constructor
            this.accountMod.updateAccount(0, new Account("1,testUser,testPass,true,What's your pet's name?,Fluffy,[],[]"));
        }

        @After
        public void tearDown() {
            // Clean up after tests
            new File(TEST_FILE).delete(); // Remove test file
        }


        @Test
        public void testLoginFailure() {
            Account result = accountMod.login("wrongUser", "wrongPass");
            assertNull("Login should fail for incorrect credentials", result);
        }


        @Test
        public void testSaveData() {
            boolean result = accountMod.saveData();
            assertTrue("Data should be saved successfully", result);
        }
    }

    public static class AccountTest {
        private Account account;


        @Before
        public void setUp() {
            account = new Account("testUser", "password123");
        }

        @Test
        public void testGetName() {
            assertEquals("testUser", account.getName());
        }

        @Test
        public void testGetPassword() {
            assertEquals("password123", account.getPassword());
        }

        @Test
        public void testIsSecurityProtection() {
            assertFalse(account.isSecurityProtection());
        }

        @Test
        public void testGetFriends() {
            assertTrue(account.getFriends().isEmpty());
        }

        @Test
        public void testAddFriend() {
            account.addFriend(1);
            assertTrue(account.getFriends().contains(1));
        }

        @Test
        public void testDeleteFriend() {
            account.addFriend(1);
            account.deleteFriend(1);
            assertFalse(account.getFriends().contains(1));
        }

        @Test
        public void testGetBlocks() {
            assertTrue(account.getBlocks().isEmpty());
        }

        @Test
        public void testAddBlock() {
            account.addBlock(1);
            assertTrue(account.getBlocks().contains(1));
        }

        @Test
        public void testDeleteBlock() {
            account.addBlock(1);
            account.deleteBlock(1);
            assertFalse(account.getBlocks().contains(1));
        }

        @Test
        public void testGetPosts() {
            assertTrue(account.getPosts().isEmpty());
        }


    }

    public static class PostTest {
        private Post post;

        @Before
        public void setUp() {
            post = new Post("Lily", 1, 291, "This is a test post");
        }

        @Test
        public void testPostCreation() {
            assertEquals("Lily", post.getUsername());
            assertEquals(1, post.getUserID());
            assertEquals(291, post.getPostID());
            assertEquals("This is a test post", post.getContent());
        }


        @Test
        public void testDeletePost() {
            post.deletePost();
            assertEquals(0, post.getPostID());
            assertNull(post.getContent());
        }
    }

    public static class RepliesTest {
        private Replies reply;

        @Before
        public void setUp() {
            reply = new Replies("Lily", 1, 291, "This is a test post");
        }

        @Test
        public void testReplyCreation() {
            assertEquals("Lily", reply.getUsername());
            assertEquals(1, reply.getUserID());
            assertEquals(291, reply.getPostID());
            assertEquals("This is a test post", reply.getContent());
        }



        @Test
        public void testDeleteReply() {
            reply.deletePost();
            assertEquals(0, reply.getPostID());
            assertNull(reply.getContent());
        }
    }

    public static class LogInTest {
        private LogIn logIn;
        private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        private final PrintStream originalOut = System.out;

        @Before
        public void setUp() {
            System.setOut(new PrintStream(outContent));
        }

        @Test
        public void testCheckingIn() {
            String input = "1\n";
            Scanner scanner = new Scanner(input);
            logIn = new LogIn(scanner);
            assertTrue(logIn.checkingIn(scanner));
        }

        @Test
        public void testCheckAccount() throws IOException {
            String input = "testUser\npassword123\n";
            Scanner scanner = new Scanner(input);
            logIn = new LogIn(scanner);
            assertEquals("testUser,password123", logIn.checkAccount(scanner));
        }
    }

    public static class MainMergeTest {

        @Test
        public void testGetPostByID() {
            Account account = new Account("TestUser", "TestPassword");
            Post post1 = new Post("TestUser", 1, 1, "Post content 1");
            Post post2 = new Post("TestUser", 1, 2, "Post content 2");
            account.addPost(post1);
            account.addPost(post2);

            assertEquals(post1, MainMerge.getPostByID(1, account));
            assertEquals(post2, MainMerge.getPostByID(2, account));
            assertNull(MainMerge.getPostByID(3, account)); // Post with ID 3 should not exist
        }
    }

}
