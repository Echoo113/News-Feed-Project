import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */
public class Post implements IPost {
    private int userID;
    private String username;
    private int postID;
    private String content;
    private int upvoteUsers;
    private int downvoteUsers;
    // private int viewNumbers;  DO WE NEED IT?
    private ArrayList<Replies> replies; // Updated from 'comments' to 'replies'

    // Constructors
    public Post() {
        this.username = null;
        this.userID = 0; // Default value for int
        this.postID = 0; // Default value for int
        this.content = null;
        this.upvoteUsers = 0;
        this.downvoteUsers = 0;
        this.replies = new ArrayList<>(); // Updated from 'comments' to 'replies'
    }

    public Post(String username, int userID, int postID, String content) {
        this.username = username;
        this.userID = userID;
        this.postID = postID;
        this.content = content;
        this.upvoteUsers = 0;
        this.downvoteUsers = 0;
        this.replies = new ArrayList<>(); // Updated from 'comments' to 'replies'
    }

    public Post(String username, int userID, int postID, String content,
                int upvoteUsers, int downvoteUsers, ArrayList<Replies> replies) {
        this.username = username;
        this.userID = userID;
        this.postID = postID;
        this.content = content;
        this.upvoteUsers = upvoteUsers;
        this.downvoteUsers = downvoteUsers;
        this.replies = replies; // Updated from 'comments' to 'replies'
    }

    //    public synchronized String toString() {
//        return String.format("UserID: %d | Username: %s | PostID: %d | Content: %s | UV: %d | DV: %d \nReplies[%s]",
//                userID, username, postID, content, upvoteUsers, downvoteUsers, printReplies());
//    }
    public Post(String toString) {
        String currentLine = toString;
        this.userID = Integer.parseInt(currentLine.substring(currentLine.indexOf(":") + 1, currentLine.indexOf("|")).trim());
        currentLine = currentLine.substring(currentLine.indexOf("|") + 2);
        this.username = currentLine.substring(currentLine.indexOf(":") + 1, currentLine.indexOf("|"));
        currentLine = currentLine.substring(currentLine.indexOf("P") + 2);
        this.postID = Integer.parseInt(currentLine.substring(currentLine.indexOf(":") + 1, currentLine.indexOf("|")).trim());
        currentLine = currentLine.substring(currentLine.indexOf("C") + 2);
        this.content = currentLine.substring(currentLine.indexOf(":") + 1, currentLine.indexOf("|"));
        currentLine = currentLine.substring(currentLine.indexOf("U") + 1);
        this.upvoteUsers = Integer.parseInt(currentLine.substring(currentLine.indexOf(":") + 1, currentLine.indexOf("|")).trim());
        currentLine = currentLine.substring(currentLine.indexOf("D") + 1);
        this.downvoteUsers = Integer.parseInt(currentLine.substring(currentLine.indexOf(":") + 1, currentLine.indexOf("R")).trim());

        //  Replies[replyID{content}uv#|dv#<UserID|username>~%replyID{content}uv#|dv#<userID|username>~%repeat...]
        currentLine = currentLine.substring(currentLine.indexOf("[") + 1);
        currentLine = currentLine.replace("]", "");
        if (!currentLine.contains("EMPTY")) {
            String[] allReplies = currentLine.split("~%");
            this.replies = new ArrayList<>();
            if (allReplies != null) {
                for (String amount : allReplies) {
                    Replies newReply = new Replies(amount);
                    this.replies.add(newReply);
                }
            }
        } else {
            this.replies = new ArrayList<>();
        }


    }

    // Getters and Setters
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUpvoteUsers() {
        return upvoteUsers;
    }


    public int getDownvoteUsers() {
        return downvoteUsers;
    }


    public synchronized ArrayList<Replies> getReplies() {
        return replies;
    }

    public synchronized void setReplies(ArrayList<Replies> replies) {
        this.replies = replies;
    }

    // Post interaction methods
    public synchronized void upvotePost() {
        upvoteUsers++;
    }

    public synchronized void downvotePost() {
        downvoteUsers++;
    }

    public synchronized void deletePost() {
        this.postID = 0;
        this.content = null;
        this.upvoteUsers = 0;
        this.downvoteUsers = 0;
        //  this.viewNumbers = 0;
        this.replies = null;
    }

    // Method for adding a reply to a post
    public synchronized void addReply(Replies reply) {
        replies.add(reply);
    }

    // Method for deleting a reply from a post
    public synchronized void deleteReply(Replies reply) {
        replies.remove(reply);
    }

    public void renewReplies(int repliesID, Replies one) {
        int index = 0;
        for (Replies reply : replies) {
            if (reply.getPostID() == repliesID) {
                break;
            }
            index++;
        }
        replies.set(index, one);
    }

    /**
     * @return
     */
    public synchronized int getNewRepliesID() {
        Random rand = new Random();
        int randomNum = rand.nextInt(1000) + 1;
        return randomNum;
    }

    // Printing and file writing methods
    public synchronized String printReplies() {
        // Similar logic as previous 'printComment', adapted for replies
        StringBuilder replyString = new StringBuilder("Replies[");
        for (Replies reply : replies) {
            replyString.append(reply.toString()).append("~%");
        }
        if (replies.isEmpty()) {
            replyString.append("EMPTY");
        }
        replyString.append(']');
        return replyString.toString();
    }

  public synchronized Replies getReplyByID(int replyID) {
        for (Replies reply : replies) {
            if (reply.getPostID() == replyID) {
                return reply;
            }
        }
        return null;
    }

    public synchronized void writeToFile(String filename) {
        try (BufferedWriter bfw = new BufferedWriter(new FileWriter(filename))) {
            bfw.write(this.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
//    public synchronized String toString() {
//        return String.format("UserID: %d || Username: %s\nPostID: %d\nContent: %s\nUpvotes: %d && %s || Downvotes: %d && %s\n%s",
//                userID, username, postID, content, upvoteUsers.size(), upvoteUsers, downvoteUsers.size(), downvoteUsers, printReplies());
//    }

    public synchronized String toString() {
        return String.format("UserID: %d | Username: %s | PostID: %d | Content: %s | UV: %d | DV: %d \n%s",
                userID, username, postID, content, upvoteUsers, downvoteUsers, printReplies());
    }
}