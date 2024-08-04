import java.util.ArrayList;

/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */
public interface IAccount {
    // Getters
    public String getName();

    public int getId();

    public String getPassword();

    public boolean isSecurityProtection();

    public String getSecurityQuestion();

    public String getSecurityAnswer();

    public ArrayList<Integer> getFriends();

    public ArrayList<Integer> getBlocks();

    // Setters
    public void setName(String name);

    public void setId(int id);

    public void setPassword(String password);

    public void setSecurityProtection(boolean securityProtection);

    public void setSecurityQuestion(String securityQuestion);

    public void setSecurityAnswer(String securityAnswer);

    public void setFriends(ArrayList<Integer> friends);

    public void setBlocks(ArrayList<Integer> blocks);

    // Functions
    public void addFriend(int id);

    public void deleteFriend(int id);

    public void addBlock(int block);

    public void deleteBlock(int id);

    public String toString();
}