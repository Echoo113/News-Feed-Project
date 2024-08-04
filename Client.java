import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */
public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 2020;
    private final static String INVALID_PA = "This is an invalid password or this account does not exist";
    private final static String CREATE_ACCOUNT_PAGE = "This is the create your account page";
    private final static String WELCOME_MESSAGE = "Welcome to InsFabRam!";
    private final static String MENU_MAIN = "1.Search up a User\n" +
            "2.Add or Delete a User\n" +
            "3.Check or modify a Post\n" +
            "4.Change your Bio\n" +
            "5.Change your username or password\n" +
            "6.Make comment on a post\n"
            + "7.Logout";
    private final static String MENU_POST = "1.Add a post\n" +
            "2.Delete a post\n" +
            "3.Edit a post\n" +
            "4.View all posts from your friends\n" +
            "5.Upvote or downvote a post\n" +
            "6.View what you have posted\n" +
            "7.DO NOTHING\n";
    private final static String MENU_COMMENT = "1.Add a comment\n" +
            "2.Delete a comment\n" +
            "3.Edit a comment\n" +
            "4.DO NOTHING\n";
    private final static String FRIEND_ACTION_PROMPT = "Do you want to add or delete a friend";
    private final static String ADD_OR_DELETE = "1.Add\n2.Delete";
    private final static String FRIEND_USERNAME = "Please enter your friend's username to add";
    private final static String ALREADY_FRIENDS = "You're already friends";
    private final static String USER_BLOCK_LIST = "User is currently in block list";
    private final static String ENTER_FRIEND_USERNAME_TO_DELETE = "Please enter your friend's username to delete";

    private final static String NOT_FRIENDS_CANT_DELETE = "Not friends, can't delete";
    private final static String LOGOUT_CONFIRMATION = "You sure you want to logout?";
    private final static String GOODBYE = "Have a nice day";


    public static void main(String[] args) throws Exception {
        Socket socket = null;
        try {
            socket = new Socket("localhost", SERVER_PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner scanner = new Scanner(System.in)) {

            LogIn logIn = new LogIn(scanner);
            if (logIn.checkingIn(scanner)) {
                String data = logIn.checkAccount(scanner);
               output.println(data);
                output.flush();
            } else {
                System.out.println(CREATE_ACCOUNT_PAGE);
                Account account = logIn.makeAAccount(scanner);
               String info = account.getName() + "," + account.getPassword();
                output.println(info);
                output.flush();
            }

            System.out.println(WELCOME_MESSAGE);
            boolean logout = false;
            while (!logout) {
                System.out.println(MENU_MAIN);
                String option = scanner.nextLine();
                output.println(option);
                output.flush();
                switch (option) {
                    case "1":
                        System.out.println("Enter the username of the friend you want to search up:");
                        String friendUsername = scanner.nextLine();
                        output.println(friendUsername);
                        output.flush();
                        String line;
                        while ((line = input.readLine()) != null) {
                            System.out.println(line);
                            if (line.equals("end")) {
                                break;
                            }
                        }
                        break;
                    case "2":
                        System.out.println(FRIEND_ACTION_PROMPT);
                        System.out.println(ADD_OR_DELETE);
                        String friendAction = scanner.nextLine();
                        output.println(friendAction);
                        output.flush();
                        if ("1".equals(friendAction)) {
                            System.out.println(FRIEND_USERNAME);
                            String friendUsern = scanner.nextLine();
                            output.println(friendUsern);
                            output.flush();
                            String response = input.readLine();
                            System.out.println(response);
                        } else if ("2".equals(friendAction)) {
                            System.out.println(ENTER_FRIEND_USERNAME_TO_DELETE);
                            String friendUsernameA = scanner.nextLine();
                            output.println(friendUsernameA);
                            output.flush();
                            String response = input.readLine();
                            System.out.println(response);
                        }
                        break;
                    case "3":
                        handlePostOperations(input, output, scanner);
                        break;
                    case "4":
                        System.out.println("Enter your new bio:");
                        String bio = scanner.nextLine();
                        output.println(bio);
                        output.flush();
                        break;
                    case "5":
                        System.out.println("Enter your new username:");
                        String newUsername = scanner.nextLine();
                        output.println(newUsername);
                        output.flush();
                        System.out.println("Enter your new password:");
                        String newPassword = scanner.nextLine();
                        output.println(newPassword);
                        output.flush();
                        break;
                    case "6":
                        handleCommentOperations(output, input, scanner);
                        break;
                    case "7":
                        System.out.println(LOGOUT_CONFIRMATION);
                        System.out.println("yes/no");
                        String logoutChoice = scanner.nextLine();

                        if ("yes".equalsIgnoreCase(logoutChoice)) {
                            logout = true;
                            System.out.println(GOODBYE);
                        }
                        break;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }

            }


        }
    }


    public static void handlePostOperations(BufferedReader input,
                                            PrintWriter output, Scanner scanner) throws Exception {

        // Main page for Post
        boolean continuePost = true;

        do {
            System.out.println(MENU_POST);
            String postChoice = scanner.nextLine();
            output.println(postChoice);
            output.flush();
            switch (postChoice) {
                case "1":
                    System.out.println("Enter your post content:");
                    String postContent = scanner.nextLine();
                    output.println(postContent);
                    output.flush();
                    break;
                case "2":
                    System.out.println("Enter the postID of the post you want to delete:");
                    int postID = Integer.parseInt(scanner.nextLine());
                    output.println(postID);
                    output.flush();
                    break;
                case "3":
                    System.out.println("Enter the postID of the post you want to edit:");
                    int editID = Integer.parseInt(scanner.nextLine());
                    output.println(editID);
                    output.flush();
                    System.out.println("Enter the new content for the post:");
                    String newContent = scanner.nextLine();
                    output.println(newContent);
                    output.flush();
                    break;
                case "4":
                    System.out.println("View all posts from your friends, put your friend's username:");
                    String friendUsername = scanner.nextLine();
                    output.println(friendUsername);
                    output.flush();

                    String line;
                    while ((line = input.readLine()) != null) {
                        System.out.println(line);
                        if (line.equals("end")) {
                            break;
                        }
                    }
                    break;
                case "5":
                    System.out.println("Enter the postID of the post you want to upvote or downvote:");
                    int voteID = Integer.parseInt(scanner.nextLine());
                    output.println(voteID);
                    output.flush();
                    System.out.println("1.Upvote\n2.Downvote");
                    String voteChoice = scanner.nextLine();
                    output.println(voteChoice);
                    output.flush();
                    break;
                case "6":
                    System.out.println("View what you have posted");
                    String line2;
                    while ((line2 = input.readLine()) != null) {
                        System.out.println(line2);
                        if (line2.equals("end")) {
                            break;
                        }
                    }
                    break;
                case "7":
                    continuePost = false;
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;
            }
        } while (continuePost);

    }

    public static void handleCommentOperations(PrintWriter output,
                                               BufferedReader input, Scanner scanner) throws Exception {

        boolean continueComment = true;
        System.out.println(MENU_COMMENT);
        String commentChoice = scanner.nextLine();
        output.println(commentChoice);
        output.flush();
        while (continueComment) {
            switch (commentChoice) {
                case "1":
                    System.out.println("Enter the username of the friend you want to view posts from:");
                    String user = scanner.nextLine();
                    output.println(user);
                    output.flush();
                    System.out.println("Enter the postID of the post you want to comment on:");
                    int postID = Integer.parseInt(scanner.nextLine());
                    output.println(postID);
                    output.flush();
                    System.out.println("Enter your comment:");
                    String comment = scanner.nextLine();
                    output.println(comment);
                    output.flush();
                    break;
                case "2":
                    System.out.println("Enter the username of the friend you want to delete comment from:");
                    String userA = scanner.nextLine();
                    output.println(userA);
                    output.flush();
                    System.out.println("Enter the postID of the post you want to delete a comment from:");
                    int deleteID = Integer.parseInt(scanner.nextLine());
                    output.println(deleteID);
                    output.flush();
                    System.out.println("Enter the commentID of the comment you want to delete:");
                    int commentID = Integer.parseInt(scanner.nextLine());
                    output.println(commentID);
                    output.flush();
                    break;
                case "3":
                    System.out.println("Enter the username of the friend you want to edit comment from:");
                    String userB = scanner.nextLine();
                    output.println(userB);
                    output.flush();
                    System.out.println("Enter the postID of the post you want to edit a comment from:");
                    int editID = Integer.parseInt(scanner.nextLine());
                    output.println(editID);
                    output.flush();
                    System.out.println("Enter the commentID of the comment you want to edit:");
                    int commentID2 = Integer.parseInt(scanner.nextLine());
                    output.println(commentID2);
                    output.flush();
                    System.out.println("Enter the new comment:");
                    String newComment = scanner.nextLine();
                    output.println(newComment);
                    output.flush();
                    break;
                case "4":
                    continueComment = false;
                    break;
                default:
                    System.out.println("Invalid Input");
                    break;
            }

        }
    }
}
