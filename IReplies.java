public interface IReplies {
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
    void upvotePost();
    void downvotePost();
    void deletePost();
    boolean compareTo(Object o);
    String toString();
}
