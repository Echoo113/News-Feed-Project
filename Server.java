import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */
class Server implements IServer {
    private static final int PORT_NUMBER = 2001;

    public static void main(String[] args) {
        ServerSocket server = null;

        try {

            // server is listening on port 1234
            server = new ServerSocket(PORT_NUMBER);
            server.setReuseAddress(true);

            // running infinite loop for getting
            // client request
            while (true) {

                // socket object to receive incoming client
                // requests
                Socket client = server.accept();

                // Displaying that new client is connected
                // to server
                System.out.println("New client connected"
                        + client.getInetAddress()
                        .getHostAddress());

                // create a new thread object
                ClientHandler clientSock
                        = new ClientHandler(client);

                // This thread will handle the client
                // separately
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ClientHandler class
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        // Constructor
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            PrintWriter out = null;
            BufferedReader in = null;
            try {

                // get the outputstream of client
                out = new PrintWriter(
                        clientSocket.getOutputStream());

                // get the inputstream of client
                in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));
                AccountMod dataMod = new AccountMod("userFile.txt");

                System.out.println("Waiting for client input...");

                String data = in.readLine();
                System.out.println("Received from client: " + data);
                String[] temp = data.split(",");
                Account account = null;
                if (temp[0].equals("1")) {
                    while (true) {
                        account = dataMod.login(temp[1], temp[
                                2]);
                        if (account != null) {
                            out.println("Valid");
                            out.flush();
                            break;
                        }

                        out.println("Invalid");
                        out.flush();
                        data = in.readLine();
                        System.out.println("Received from client: " + data);
                        temp = data.split(",");
                    }
                }
                if (temp[0].equals("2")) {
                    while ((true)) {
                        String username = temp[1];
                        if (dataMod.getAccountWithUsername(username) == null) {
                            account = new Account(temp[1], temp[2]);
                            createAccount(account);
                            try (BufferedWriter bfr = new BufferedWriter(new FileWriter(account.getId() + ".txt", true))) {
                                bfr.write("");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            out.println("Account created");
                            out.flush();
                            break;
                        }
                        out.println("Username already exists");
                        out.flush();
                        data = in.readLine();
                        System.out.println("Received from client: " + data);
                        temp = data.split(",");
                    }
                }

                mainPage(account, in, out);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static synchronized void mainPage(Account account,
                                             BufferedReader reader, PrintWriter writer) throws IOException {

        while (true) {
            AccountMod accountMod = new AccountMod("userFile.txt");
            String actions = "";
            actions = reader.readLine();
            switch (actions) {
                case "1":
                    String user = reader.readLine();
                    account = accountMod.getAccountWithUsername(user);
                    if (account != null) {
                        writer.println("User found");
                        String name = account.getName();
                        String id = Integer.toString(account.getId());
                        String friends = account.getFriends().toString();
                        writer.println(name);
                        writer.println(id);
                        writer.println(friends);
                        writer.flush();
                    } else {
                        writer.println("User not found");
                        writer.flush();
                    }
                    break;
                case "2":
                    String friendAction = reader.readLine();
                    switch (friendAction) {
                        case "1":
                            String friendUsername = reader.readLine();
                            Account friend = accountMod.getAccountWithUsername(friendUsername);
                            if (friend != null) {
                                if (account.getFriends().contains(friend.getId())) {
                                    writer.println("User is already a friend");
                                    writer.flush();
                                    break;
                                } else if (account.getId() == friend.getId()) {
                                    writer.println("You cannot add yourself as a friend");
                                    writer.flush();
                                    break;
                                } else if (accountMod.isBlock(account.getId(), friend.getId())) {
                                    writer.println("The user is in your block list");
                                    writer.flush();
                                    break;
                                } else {
                                    writer.println("User added");
                                    writer.flush();
                                    accountMod.addFriend(account.getId(), friend.getId());
                                }
                            } else {
                                writer.println("User not found");
                                writer.flush();
                                break;
                            }
                            accountMod.saveData();
                            break;
                        case "2":
                            String deleteName = reader.readLine();
                            Account friendDelete = accountMod.getAccountWithUsername(deleteName);
                            if (friendDelete != null) {
                                if (accountMod.isFriend(account.getId(), friendDelete.getId())) {
                                    writer.println("Friend Deleted");
                                    writer.flush();
                                    accountMod.deleteFriend(account.getId(), friendDelete.getId());
                                    accountMod.saveData();
                                    break;
                                } else {
                                    writer.println("User is not your friend");
                                    writer.flush();
                                    break;
                                }
                            } else {
                                writer.println("User is not found");
                                writer.flush();
                                accountMod.saveData();
                            }
                            accountMod.saveData();
                            break;
                        default:
                            writer.println("Invalid Input");
                            writer.flush();
                            break;
                    }
                    break;
                case "3":
                    String choiceForPost = "";
                    choiceForPost = reader.readLine();
                    ArrayList<Post> myPost = initiatePosts(account);
                    account.setPosts(myPost);
                    switch (choiceForPost) {
                        //create a post
                        case "1":
                            String postContent = reader.readLine();
                            Post postA = new Post(account.getName(), account.getId(), account.postID(), postContent);
                            account.addPost(postA);
                            writeSinglePost(postA, account);
                            break;
                        //delete a post
                        case "2":

                            String deleteID = reader.readLine();
                            Post postToDelete = null;
                            //find the post to delete
                            for (Post post : account.getPosts()) {
                                if (post.getPostID() == Integer.parseInt(deleteID)) {
                                    postToDelete = post;
                                    break;
                                }
                            }

                            if (postToDelete != null) {
                                myPost.remove(postToDelete);
                                account.setPosts(myPost);
                                writer.println("Post deleted");
                                writer.flush();
                            } else {
                                writer.println("Post not found");
                                writer.flush();
                            }
                            //rewrite the file
                            flushPostFile(account);
                            break;
                        case "3":
                            //read all post from every friends' post.txt
                            String postsFromFriends = "";
                            ArrayList<Integer> friends = account.getFriends();
                            if (friends.size() == 0) {
                                writer.println("No friends");
                                writer.flush();
                                break;
                            }
                            for (int friendID : friends) {
                                Account friend = accountMod.getAccountWithID(friendID);
                                postsFromFriends = postsFromFriends + viewPosts(friend) + "\n\n";
                            }
                            writer.println(postsFromFriends);
                            writer.println("end----");
                            writer.flush();
                            break;
                        case "4":
                            //read all post from a specific friend
                            String friendUsername = reader.readLine();
                            Account friend = accountMod.getAccountWithUsername(friendUsername);
                            if (friend == null) {
                                writer.println("User not found");
                                writer.flush();
                                break;
                            }
                            if (!account.getFriends().contains(friend.getId())) {
                                writer.println("User is not your friend");
                                writer.flush();
                                break;
                            }
                            String allPosts = viewPosts(friend);
                            writer.println(allPosts);
                            writer.println("end----");
                            writer.flush();
                            break;
                        case "5":
                            //vote a post by postID
                            String friendName = reader.readLine();
                            String voteID = reader.readLine();
                            String voteChoice = reader.readLine();
                            Account friendVote = accountMod.getAccountWithUsername(friendName);
                            if (friendVote == null) {
                                writer.println("User not found");
                                writer.flush();
                                break;
                            }
                            if (!account.getFriends().contains(friendVote.getId())) {
                                writer.println("User is not your friend");
                                writer.flush();
                                break;
                            }
                            ArrayList<Post> hisPosts = initiatePosts(friendVote);
                            friendVote.setPosts(hisPosts);

                            Post postToVote = null;
                            //find the post to vote
                            int index = 0;
                            for (Post post : hisPosts) {
                                if (post.getPostID() == Integer.parseInt(voteID)) {
                                    postToVote = post;
                                    break;
                                }
                                index++;
                            }
                            if (postToVote != null) {

                                if ("1".equals(voteChoice)) {
                                    postToVote.upvotePost();
                                    writer.println("Post upvoted");
                                    writer.flush();
                                } else if ("2".equals(voteChoice)) {
                                    postToVote.downvotePost();
                                    writer.println("Post downvoted");
                                    writer.flush();
                                }
                            } else {
                                writer.println("Post not found");
                                writer.flush();
                                break;
                            }
                            //rewrite the file
                            hisPosts.set(index, postToVote);
                            friendVote.setPosts(hisPosts);
                            flushPostFile(friendVote);
                            break;
                        case "6":
                            //view your posts
                            String line2 = viewPosts(account);
                            writer.println(line2);
                            writer.println("end----");
                            writer.flush();
                            break;
                        default:
                            writer.println("Invalid Input");
                            writer.flush();
                            break;

                    }

                    break;
                case "4":
                    String choiceForBlock = "";
                    choiceForBlock = reader.readLine();
                    switch (choiceForBlock) {
                        case "1": // Block a user
                            String blockName = reader.readLine(); // Read the username to block
                            Account block = accountMod.getAccountWithUsername(blockName);
                            if (block == null) {
                                writer.println("User not found");
                            } else {
                                if (accountMod.isBlock(block.getId(), account.getId())) {
                                    writer.println("The user is already blocked by you");
                                } else {
                                    accountMod.addBlock(account.getId(), block.getId());
                                    if (accountMod.isFriend(block.getId(), account.getId())) {
                                        accountMod.deleteFriend(account.getId(), block.getId());
                                    }
                                    writer.println("User blocked");
                                }
                            }
                            break;

                        case "2": // Unblock a user
                            String unblockName = reader.readLine(); // Read the username to unblock
                            Account unblock = accountMod.getAccountWithUsername(unblockName);
                            if (unblock == null) {
                                writer.println("User not found");
                            } else {
                                if (accountMod.isBlock(unblock.getId(), account.getId())) {
                                    accountMod.deleteBlock(account.getId(), unblock.getId());
                                    writer.println("User unblocked");

                                } else {
                                    writer.println("You did not block this user");
                                }
                            }
                            break;
                        default:

                            break;
                    }
                    writer.flush(); // Ensure all data is sent back to the client

                    break;
                case "5":
                    // Change username and password
                    String newUserName = reader.readLine();
                    String newPassword = reader.readLine();
                    account.setName(newUserName);
                    account.setPassword(newPassword);
                    accountMod.updateAccount(account.getId(), account);
                    accountMod.saveData();
                    break;
                case "6":
                    //make comment, delete comment, edit comment

                    String choiceForComment = "";
                    choiceForComment = reader.readLine();
                    switch (choiceForComment) {
                        case "1":
                            //make a comment
                            String userX = reader.readLine();
                            int postID = Integer.parseInt(reader.readLine());
                            String comment = reader.readLine();
                            Account friend = accountMod.getAccountWithUsername(userX);
                            Post friendPost = null;
                            if (friend == null) {
                                writer.println("User not found");
                                writer.flush();
                                break;
                            }
                            if (account.getFriends().contains(friend.getId()) == false) {
                                writer.println("User is not your friend");
                                writer.flush();
                                break;
                            }

                            ArrayList<Post> allFriendPosts = initiatePosts(friend);
                            friend.setPosts(allFriendPosts);
                            int indexForTargetPost = 0;
                            for (Post post : friend.getPosts()) {

                                if (post.getPostID() == postID) {
                                    friendPost = post;
                                    break;
                                }
                                indexForTargetPost++;
                            }
                            //if the post is not found
                            if (friendPost == null) {
                                writer.println("Post not found");
                                writer.flush();
                                break;
                            }
                            Replies reply = new Replies(account.getName(),
                                    account.getId(), friendPost.getNewRepliesID(), comment);
                            friendPost.addReply(reply);
                            allFriendPosts.set(indexForTargetPost, friendPost);
                            friend.setPosts(allFriendPosts);
                            flushPostFile(friend);
                            writer.println("Comment added");
                            writer.flush();
                            break;
                        case "2":
                            //delete a comment
                            String userA = reader.readLine();
                            int postIDOne = Integer.parseInt(reader.readLine());
                            int commentID = Integer.parseInt(reader.readLine());

                            Account friendOne = accountMod.getAccountWithUsername(userA);
                            if (friendOne == null) {
                                writer.println("User not found");
                                writer.flush();
                                break;
                            }
                            if (account.getFriends().contains(friendOne.getId()) == false && friendOne.getId() != account.getId()) {
                                writer.println("User is not your friend nor yourself");
                                writer.flush();
                                break;
                            }
                            ArrayList<Post> allFriPost = initiatePosts(friendOne);
                            friendOne.setPosts(allFriPost);
                            Post postOne = null;

                            int indexForTarget = 0;
                            for (Post post : friendOne.getPosts()) {
                                if (post.getPostID() == postIDOne) {
                                    postOne = post;
                                    break;
                                }
                                indexForTarget++;
                            }
                            if (postOne == null) {
                                writer.println("Post not found");
                                writer.flush();
                                break;
                            }
                            Replies deleteComment = postOne.getReplyByID(commentID);
                            if (deleteComment == null) {
                                writer.println("Comment not found");
                                writer.flush();
                                break;
                            }
                            //only post owner and comment owner can delete the comment
                            if (deleteComment.getUserID() == account.getId() || friendOne.getId() == account.getId()) {
                                postOne.deleteReply(deleteComment);
                                allFriPost.set(indexForTarget, postOne);
                                friendOne.setPosts(allFriPost);
                                writer.println("Comment deleted");
                                writer.flush();
                            } else {
                                writer.println("You do not have permission to delete this comment");
                                writer.flush();
                            }
                            flushPostFile(friendOne);
                            break;
                        case "3":
                            //upvote a comment
                            String userB = reader.readLine();
                            int postIDTwo = Integer.parseInt(reader.readLine());
                            int upvoteCommentID = Integer.parseInt(reader.readLine());

                            Account friendTwo = accountMod.getAccountWithUsername(userB);
                            if (friendTwo == null) {
                                writer.println("User not found");
                                writer.flush();
                                break;
                            }
                            if (account.getFriends().contains(friendTwo.getId()) == false && friendTwo.getId() != account.getId()) {
                                writer.println("User is not your friend nor yourself");
                                writer.flush();
                                break;
                            }
                            ArrayList<Post> allFriPostTwo = initiatePosts(friendTwo);
                            friendTwo.setPosts(allFriPostTwo);
                            Post postTwo = null;
                            int indexForTargetTwo = 0;
                            for (Post post : friendTwo.getPosts()) {
                                if (post.getPostID() == postIDTwo) {
                                    postTwo = post;
                                    break;
                                }
                                indexForTargetTwo++;
                            }
                            if (postTwo == null) {
                                writer.println("Post not found");
                                writer.flush();
                                break;
                            }
                            Replies upvoteComment = postTwo.getReplyByID(upvoteCommentID);
                            if (upvoteComment == null) {
                                writer.println("Comment not found");
                                writer.flush();
                                break;
                            }
                            upvoteComment.upvotePost();
                            postTwo.renewReplies(upvoteCommentID, upvoteComment);
                            allFriPostTwo.set(indexForTargetTwo, postTwo);
                            friendTwo.setPosts(allFriPostTwo);
                            flushPostFile(friendTwo);
                            writer.println("Comment upvoted");
                            writer.flush();
                            break;
                        case "4":
                            //downvote a comment
                            String userC = reader.readLine();
                            int postIDThree = Integer.parseInt(reader.readLine());
                            int downvoteCommentID = Integer.parseInt(reader.readLine());

                            Account friendThree = accountMod.getAccountWithUsername(userC);
                            if (friendThree == null) {
                                writer.println("User not found");
                                writer.flush();
                                break;
                            }
                            if (account.getFriends().contains(friendThree.getId()) == false && friendThree.getId() != account.getId()) {
                                writer.println("User is not your friend nor yourself");
                                writer.flush();
                                break;
                            }
                            ArrayList<Post> allFriPostThree = initiatePosts(friendThree);
                            friendThree.setPosts(allFriPostThree);

                            Post postThree = null;
                            int indexForTargetThree = 0;
                            for (Post post : friendThree.getPosts()) {
                                if (post.getPostID() == postIDThree) {
                                    postThree = post;
                                    break;
                                }
                                indexForTargetThree++;
                            }
                            if (postThree == null) {
                                writer.println("Post not found");
                                writer.flush();
                                break;
                            }
                            Replies downvoteComment = postThree.getReplyByID(downvoteCommentID);
                            if (downvoteComment == null) {
                                writer.println("Comment not found");
                                writer.flush();
                                break;
                            }
                            downvoteComment.downvotePost();
                            postThree.renewReplies(downvoteCommentID, downvoteComment);
                            allFriPostThree.set(indexForTargetThree, postThree);
                            friendThree.setPosts(allFriPostThree);
                            flushPostFile(friendThree);
                            writer.println("Comment downvoted");
                            writer.flush();
                            break;
                        default:
                            writer.println("Invalid Input");
                            writer.flush();
                            break;
                    }
                    break;
                case "7":
                    accountMod.saveData();
                    break;
                default:

                    break;
            }
        }
    }


    public static void writeSinglePost(Post post, Account account) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(account.getId() + ".txt", true))) {
            writer.write(post.toString());
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String viewPosts(Account account) {
        String one = "";
        ArrayList<Post> personalPosts = initiatePosts(account);
        if (personalPosts.size() == 0) {
            return "No posts";
        }
        for (Post post : personalPosts) {
            one += post.toString() + "\n";
        }
        return one;
    }

    public static void createAccount(Account account) throws IOException {
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

    public static void flushPostFile(Account account) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(account.getId() + ".txt"))) {
            for (Post post : account.getPosts()) {
                writer.write(post.toString());
                writer.newLine();
            }
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Post> initiatePosts(Account account) {
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
                } else {

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
