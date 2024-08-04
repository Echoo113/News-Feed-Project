import java.util.ArrayList;

/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */
public class Replies implements IReplies{

    private int userID;
    private String username;
    private int postID;
    private String content;
    private int upvoteUsers;
    private int thumbsdownUsers;

    public Replies() {
        this.postID = 0;
        this.content = null;
        this.upvoteUsers = 0;
        this.thumbsdownUsers = 0;
    }

    public Replies(String username, int userID, int postID, String content) {
        this.username = username;
        this.userID = userID;
        this.postID = postID;
        this.content = content;
        this.upvoteUsers = 0;
        this.thumbsdownUsers = 0;
    }

    public Replies(String username, int userID, int postID, String content,
                   int upvoteUsers, int thumbsdownUsers) {
        this.username = username;
        this.userID = userID;
        this.postID = postID;
        this.content = content;
        this.upvoteUsers = upvoteUsers;
        this.thumbsdownUsers = thumbsdownUsers;
    }

    public Replies(String toString) {
        //        replyID{content}uv#|dv#<UserID|username>
        String line = toString;
        this.postID = Integer.parseInt(line.substring(0, line.indexOf('{')).trim());
        line = line.substring(line.indexOf('{'));
        this.content = line.substring(line.indexOf('{') + 1, line.indexOf('}'));

        //UV: 0 | DV: 0 <0|amy>~%]
        line= line.substring(line.indexOf('U') );
       this.upvoteUsers = Integer.parseInt(line.substring(line.indexOf('V') +3 , line.indexOf('|')-1).trim());
        line = line.substring(line.indexOf('|'));
        this.thumbsdownUsers = Integer.parseInt(line.substring(line.indexOf('V') + 3, line.indexOf('<')-1).trim());
        line = line.substring(line.indexOf('<'));
        this.userID = Integer.parseInt(line.substring(line.indexOf('<') + 1, line.indexOf('|')).trim());
        this.username = line.substring(line.indexOf('|') + 1, line.indexOf('>'));
    }

    public synchronized void setUserID(int userID) {
        this.userID = userID;
    }

    public synchronized void setUsername(String username) {
        this.username = username;
    }

    public synchronized void setPostID(int postID) {
        this.postID = postID;
    }

    public synchronized void setContent(String content) {
        this.content = content;
    }

    public synchronized void setUpvoteUsers(int upvoteUsers) {
        this.upvoteUsers = upvoteUsers;
    }

    public synchronized void setDownvoteUsers(int thumbsdownUsers) {
        this.thumbsdownUsers = thumbsdownUsers;
    }

    public synchronized int getUserID() {
        return userID;
    }

    public synchronized String getUsername() {
        return username;
    }

    public synchronized int getPostID() {
        return postID;
    }

    public synchronized String getContent() {
        return content;
    }

    public synchronized int getUpvoteUsers() {
        return upvoteUsers;
    }

    public synchronized int getDownvoteUsers() {
        return thumbsdownUsers;
    }

    public synchronized void upvotePost() {
        upvoteUsers++;
    }

    public synchronized void downvotePost() {
        thumbsdownUsers++;
    }

    public synchronized void deletePost() {
        this.postID = 0;
        this.content = null;
        this.upvoteUsers = 0;
        this.thumbsdownUsers = 0;
    }

    public synchronized boolean compareTo(Object o) {
        if (o instanceof Replies) {
            if ((((Replies) o).getPostID() == (this.postID)) && ((Replies) o).getUserID() == (this.userID)) {
                return true;
            }
        }
        return false;
    }

    public synchronized String toString() {
        String output = "";
        if (this.postID == 0 && this.content == null && this.upvoteUsers == 0 && this.thumbsdownUsers == 0) {
            output = "Deleted";
            return output;
        }

//        [replyID{content}uv#|dv#<UserID|username>,replyID{content}uv#|dv#<userID|username>,repeat...]
        output = String.format("%d{%s} UV: %d | DV: %d <%d|%s>",
                this.postID, this.content, this.upvoteUsers, this.thumbsdownUsers, this.userID, this.username);
        return output;
    }
}
