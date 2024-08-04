/*
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

*/
/**
 * This class is the main class that runs the program. It is responsible for creating the account and logging in the user.
 * It also contains the main menu for the user to interact with the program.
 *
 *
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */

public class MainMerge {
    //These static final variables are used to store the strings that are printed out to the console
    private final static String INVALID_PA = "This is an invalid password or this account does not exist";
    private final static String CREATE_ACCOUNT_PAGE = "This is the create your account page";
    private final static String WELCOME_MESSAGE = "Welcome to InsFabRam!";
    private final static String MENU_MAIN = "1.Search up a User\n" +
            "2.Block a User\n" +
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
                System.out.println(INVALID_PA);
            } else {

                runCode(scanner, account);
            }
        } else {
            System.out.println(CREATE_ACCOUNT_PAGE);
            Account account = logIn.makeAAccount(scanner);

            runCode(scanner, account);
        }
    }

    public static void runCode(Scanner scanner, Account account) throws IOException {
        account.setPosts(initiatePosts(account));
        AccountMod dataMod = new AccountMod("userFile.txt");
        boolean logout = false;
        while (!logout) {
            System.out.println(WELCOME_MESSAGE);
            System.out.println(MENU_MAIN);
            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    System.out.println(FRIEND_ACTION_PROMPT);
                    System.out.println(ADD_OR_DELETE);
                    System.out.println(FRIEND_ACTION_PROMPT);
                    String choice = scanner.nextLine();
                    handleFriendAction(scanner, account, dataMod, Integer.parseInt(choice));
                    break;
                case "2":
                    // Code for case 2, using another switch statement for the inner choices, similar to case "1"

                    System.out.println("Do you want to block someone or unblock someone");
                    System.out.println("1.Block");
                    System.out.println("2.Unblock");
                    choice = scanner.nextLine();
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

                    break;
                case "3":
                    modifyPost(scanner, account);

                    break;
                case "4":
                    // Case "4" seems to be empty. You may add relevant code here.
                    break;
                case "5":
                    System.out.println("Do you want to change your Username, Password, or both");
                    System.out.println("1.Username");
                    System.out.println("2.Password");
                    System.out.println("3.Both");
                    String changeChoice = scanner.nextLine();
                    handleAccountUpdate(scanner, account, dataMod, changeChoice);
                    break;
                case "6"://modify comments
                    modifyComments(scanner, account);
                    break;
                case "7":
                    System.out.println(LOGOUT_CONFIRMATION);
                    System.out.println("1.Yes");
                    System.out.println("2.No");
                    String logoutChoice = scanner.nextLine();
                    if ("1".equals(logoutChoice)) {
                        System.out.println(GOODBYE);
                        dataMod.saveData();
                        logout = true;
                        break; // This break exits the switch, you may need to adjust logic if intending to exit a loop
                    }
                    // No explicit 'break;' is necessary for the last case if there's no code after the switch.
                default:
                    System.out.println("Invalid Input");
                    break;
            }

        }
    }


    public static void modifyPost(Scanner scanner, Account account) throws IOException {
        boolean continuePost = true;
        AccountMod dataMod = new AccountMod("userFile.txt");
        do {
            System.out.println(MENU_POST);
            String postChoice = scanner.nextLine();
            switch (postChoice) {
                case "1":
                    createPost(scanner, account);
                    break;
                case "2":
                    deletePost(scanner, account);
                    break;
                case "3":
                    editPost(scanner, account);
                    break;
                case "4":
                    System.out.println("Enter the username of the friend you want to view posts from:");
                    String friendUsername = scanner.nextLine();
                    Account friend = dataMod.getAccountWithUsername(friendUsername);
                    if (friend != null) {

                        viewPosts(friend);
                    } else {
                        System.out.println("User not found");
                    }
                case "5":
                    System.out.println("Enter the postID of the post you want to upvote or downvote:");
                    int voteID = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter 1 to upvote or 2 to downvote:");
                    String voteChoice = scanner.nextLine();
                    Post postToVote = getPostByID(voteID, account);
                    if (postToVote != null) {
                        if ("1".equals(voteChoice)) {
                            postToVote.upvotePost();
                        } else if ("2".equals(voteChoice)) {
                            postToVote.downvotePost();
                        } else {
                            System.out.println("Invalid Input");
                        }
                    } else {
                        System.out.println("Post not found");
                    }
                case "6":
                    viewPosts(account);
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

    public static void modifyComments(Scanner scanner, Account account) throws IOException {
        AccountMod dataMod = new AccountMod("userFile.txt");
        boolean continueComment = true;
        System.out.println(MENU_COMMENT);
        String commentChoice = scanner.nextLine();
        while (continueComment) {
            switch (commentChoice) {
                case "1":
                    addComment(scanner, account, dataMod);
                    break;
                case "2":
                    deleteComment(scanner, account, dataMod);
                case "3":
                    editComment(scanner, account, dataMod);
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

    public static void handleFriendAction(Scanner scanner, Account account, AccountMod dataMod, int choice) {
        if (choice == 1) {
            System.out.println(FRIEND_USERNAME);
            String userToAdd = scanner.nextLine();
            Account accountToAdd = dataMod.getAccountWithUsername(userToAdd);
            if (accountToAdd != null) {
                if (dataMod.isFriend(accountToAdd.getId(), account.getId())) {
                    System.out.println(ALREADY_FRIENDS);
                } else if (dataMod.isBlock(accountToAdd.getId(), account.getId())) {
                    System.out.println(USER_BLOCK_LIST);
                } else {
                    dataMod.addFriend(accountToAdd.getId(), account.getId());
                    System.out.println("Friend Added");
                }
            } else {
                System.out.println("User Not found");
            }
        } else if (choice == 2) {
            System.out.println(ENTER_FRIEND_USERNAME_TO_DELETE);
            String userToDelete = scanner.nextLine();
            Account accountToDelete = dataMod.getAccountWithUsername(userToDelete);
            if (accountToDelete != null) {
                if (dataMod.isFriend(accountToDelete.getId(), account.getId())) {
                    dataMod.deleteFriend(accountToDelete.getId(), account.getId());
                    System.out.println("Friend Deleted");
                } else {
                    System.out.println(NOT_FRIENDS_CANT_DELETE);
                }
            } else {
                System.out.println("User Not found");
            }
        } else {
            System.out.println("Invalid Input");
        }
    }

    public static void handleAccountUpdate(Scanner scanner, Account account, AccountMod dataMod, String changeChoice) {
        if ("1".equals(changeChoice) || "3".equals(changeChoice)) {
            System.out.println("Enter your new Username");
            String newUsername = scanner.nextLine();
            account.setName(newUsername);
            // Fall through to case "2" if choice is "3"
        }
        if ("2".equals(changeChoice) || "3".equals(changeChoice)) {
            boolean passwordCheck = true;
            String newPassword = "";
            while (passwordCheck) {
                System.out.println("Enter your new Password");
                System.out.println("Include Numbers or Special Character");
                String checkPassword = scanner.nextLine();
                for (char x : checkPassword.toCharArray()) {
                    if (!Character.isLetter(x) && !Character.isWhitespace(x)) {
                        newPassword = checkPassword;
                        passwordCheck = false;
                    }
                }
                if (passwordCheck) {
                    System.out.println("Did not include numbers or special character");
                }
            }
            account.setPassword(newPassword);
        }
        if (!"1".equals(changeChoice) && !"2".equals(changeChoice) && !"3".equals(changeChoice)) {
            System.out.println("Invalid Input");
        }
        dataMod.updateAccount(account.getId(), account);
    }

    public static void createPost(Scanner scanner, Account account) {
        System.out.println("Enter your post content:");
        String postContent = scanner.nextLine();
        Post post = new Post(account.getName(), account.getId(), account.postID(), postContent);
        addPost(post, account);
        writePosts(account);
    }

    public static void deletePost(Scanner scanner, Account account) {
        System.out.println("Enter the postID of the post you want to delete:");
        int deleteID = Integer.parseInt(scanner.nextLine());
        Post postToDelete = getPostByID(deleteID, account);
        if (postToDelete != null) {
            removePost(account, deleteID);
        } else {
            System.out.println("Post not found");
        }
        writePosts(account);
    }

    public static void editPost(Scanner scanner, Account account) {
        System.out.println("Enter the postID of the post you want to edit:");
        int editID = Integer.parseInt(scanner.nextLine());
        Post postToEdit = getPostByID(editID, account);
        if (postToEdit != null) {
            System.out.println("Enter the new content for the post:");
            String newContent = scanner.nextLine();
            postToEdit.setContent(newContent);
        } else {
            System.out.println("Post not found");
        }
        writePosts(account);
    }

    public static void addComment(Scanner scanner, Account account, AccountMod dataMod) {
        System.out.println("Enter the username of the friend you want to view posts from:");
        String user = scanner.nextLine();
        Account friend = dataMod.getAccountWithUsername(user);
        System.out.println("Enter the postID of the post you want to comment on:");
        Post friendPost = getPostByID(Integer.parseInt(scanner.nextLine()), friend);
        System.out.println("Enter your comment:");
        String comment = scanner.nextLine();
        Replies reply = new Replies(account.getName(), account.getId(), friendPost.getPostID(), comment);
        friendPost.addReply(reply);
        writePosts(friend);
    }

    public static void deleteComment(Scanner scanner, Account account, AccountMod dataMod) {
        System.out.println("Enter the username of the friend you want to delete comment from:");
        String user = scanner.nextLine();
        System.out.println("Enter the postID of the post you want to delete comment from:");
        int postID = Integer.parseInt(scanner.nextLine());
        Account friend = dataMod.getAccountWithUsername(user);
        Post post = getPostByID(postID, friend);
        System.out.println("Enter the commentID of the comment you want to delete:");
        int commentID = Integer.parseInt(scanner.nextLine());
        Replies comment = post.getReplyByID(commentID);
        if (comment != null) {
            post.deleteReply(comment);
        } else {
            System.out.println("Comment not found");
        }
        writePosts(friend);
    }

    public static void editComment(Scanner scanner, Account account, AccountMod dataMod) {
        System.out.println("Enter the username of the friend you want to edit comment from:");
        String user = scanner.nextLine();
        System.out.println("Enter the postID of the post you want to edit comment from:");
        int postID = Integer.parseInt(scanner.nextLine());
        Account friend = dataMod.getAccountWithUsername(user);
        Post post = getPostByID(postID, friend);
        System.out.println("Enter the commentID of the comment you want to edit:");
        int commentID = Integer.parseInt(scanner.nextLine());
        Replies comment = post.getReplyByID(commentID);
        if (comment != null) {
            System.out.println("Enter the new content for the comment:");
            String newContent = scanner.nextLine();
            comment.setContent(newContent);
        } else {
            System.out.println("Comment not found");
        }
        writePosts(friend);
    }

    public static void writePosts(Account account) {
        try (FileWriter writer = new FileWriter(account.getId() + ".txt", false)) {
            for (Post post : account.getPosts()) {
                writer.write(post.toString());
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public static void viewPosts(Account account) {
//        try (BufferedReader reader = new BufferedReader(new FileReader(account.getId() + ".txt"))) {
//            String line = reader.readLine();
//            int count = 0;
//            while (line != null) {
//
//
//                if (count == 2) {
//                    System.out.println("-------------");
//                    count = 0;
//                }  else {
//
//                    count++;
//                }
//                System.out.println(line);
//                line = reader.readLine();
//            }
//        } catch (Exception e) {
//            System.out.println("No Posts in record");
//        }
//    }

    public static void viewPosts(Account account) {

        for (Post current:account.getPosts()) {
            System.out.println(current.toString());
            System.out.println("-------------");
        }
    }

    public static void removePost(Account account, int postID) {
        ArrayList<Post> posts = account.getPosts();
        for (Post post : posts) {
            if (post.getPostID() == postID) {
                posts.remove(post);
                return;
            }
        }

    }

    public static void addPost(Post post, Account account) {
        ArrayList<Post> posts = account.getPosts();
        posts.add(post);

    }

    public static Post getPostByID(int postID, Account account) {
        for (Post post : account.getPosts()) {
            if (post.getPostID() == postID) {
                return post;
            }
        }
        return null;
    }

    public static ArrayList<Post>  initiatePosts(Account account) {
        ArrayList<Post> recreatingPosts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(account.getId() + ".txt"))) {
            String line = reader.readLine();
            int count = 0;
            String makingPost = "";

            while (line != null) {

                makingPost = makingPost + " " + line;

                if (count == 1) {
                    System.out.println(makingPost);
                    Post newPost = new Post(makingPost);
                    recreatingPosts.add(newPost);
                    makingPost = "";
                    count = 0;
                }  else {

                    count++;
                }
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No Posts in record");
        }
        return recreatingPosts;
    }

}