import java.util.ArrayList;

public interface IPost {
    int getUserID();
    void setUserID(int userID);
    String getUsername();
    void setUsername(String username);
    int getPostID();
    void setPostID(int postID);
    String getContent();
    void setContent(String content);
    int getUpvoteUsers();
    int getDownvoteUsers();
    ArrayList<Replies> getReplies();
    void setReplies(ArrayList<Replies> replies);
    void upvotePost();
    void downvotePost();
    void deletePost();
    void addReply(Replies reply);
    void deleteReply(Replies reply);
    int getNewRepliesID();
    String printReplies();

    void writeToFile(String filename);
    String toString();
}
