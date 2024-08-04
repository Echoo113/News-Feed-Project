import javax.swing.*;
import java.io.IOException;

public interface IClientGUI {
    void connectToServer();
    void sendRequest(String text) throws IOException;

    JPanel welcomePanel();
    void handleMenuAction(String actionCommand) throws IOException;
    JPanel postManagementPanel();
    void handlePostAction(String operation) throws IOException;
    JPanel addPostPanel();
    JPanel deletePostPanel();
    JPanel viewAllPostsPanel() throws IOException;
    JPanel viewFriendPostsPanel(JDialog parentDialog);
    JPanel viewYourPostsPanel(JDialog parentDialog) throws IOException;
    JPanel createVotePostPanel();
    void sendVote(String friendName, String postId, String voteType);
    JPanel blockManagePanel();
    void handleBlockUnblock(String username, boolean blockOperation, JPanel blockPanel);
    JPanel commentManagementPanel();
    void handleCommentAction(String operation) throws IOException;
    JPanel addCommentPanel();
    JPanel deleteCommentPanel();
    void confirmAndLogout() throws IOException;
    JPanel changeNamePassword();

}