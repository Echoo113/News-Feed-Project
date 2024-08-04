/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */
public interface IAccountDatabase {
    boolean login(String username, String password);

    boolean registerAccount(String data);

    boolean logout(int id);

    boolean verifyQuestion(int id, String answer);

    boolean addFriend(int id, int id2);

    boolean deleteFriend(int id, int id2);

    boolean isFriend(int id, int id2);

    boolean addBlock(int id, int id2);

    boolean deleteBlock(int id, int id2);

    boolean isBlock(int id, int id2);

    boolean saveData();
}
