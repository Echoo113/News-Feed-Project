import java.util.ArrayList;

/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */
public class AccountTest {
    private int id;
    private String name;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    private ArrayList<Integer> friends;
    private ArrayList<Integer> blocks;
    //private ArrayList<Integer> posts;


    public static final String SPLITTER = "_";

    public AccountTest(String data) throws BadDataException {
        try {
            String[] tokens = data.split(SPLITTER);
            if (tokens.length != 7)
                throw new Exception();
            this.id = Integer.parseInt(tokens[0]);
            this.name = tokens[1];
            this.password = tokens[2];
            this.securityQuestion = tokens[3];
            this.securityAnswer = tokens[4];
            this.friends = Utils.stringToArrayList(tokens[5]);
            this.blocks = Utils.stringToArrayList(tokens[6]);
        } catch (Exception e) {
            throw new BadDataException(("Account" + data + ": Bad Data"));
        }
    }

    public AccountTest(String name, String password, String securityQuestion, String securityAnswer) {
        this.name = name;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }

    public AccountTest(String name, String password) {
        this.name = name;
        this.password = password;
        this.securityQuestion = null;
        this.securityAnswer = null;
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized String getPassword() {
        return password;
    }

    public synchronized void setPassword(String password) {
        this.password = password;
    }

    public synchronized ArrayList<Integer> getFriends() {
        return friends;
    }

    public synchronized void setFriends(ArrayList<Integer> friends) {
        this.friends = friends;
    }

    public synchronized ArrayList<Integer> getBlocks() {
        return blocks;
    }

    public synchronized void setBlocks(ArrayList<Integer> blocks) {
        this.blocks = blocks;
    }


    public synchronized void addFriend(int idS) {
        friends.add(idS);
    }

    public synchronized void deleteFriend(int idX) {
        Integer idObj = idX;
        friends.remove(idObj);
    }

    public synchronized void addBlock(int idSome) {
        blocks.add(idSome);
    }

    public synchronized void deleteBlock(int idOne) {
        Integer idObj = idOne;
        blocks.remove(idObj);
    }

    public synchronized String getSecurityAnswer() {
        return securityAnswer;
    }

    public synchronized String toString() {
        String res = "";
        res = Integer.toString(id) + SPLITTER + name + SPLITTER + password + SPLITTER +
                securityQuestion + SPLITTER + securityAnswer + SPLITTER +
                Utils.arrayListToString(friends) + SPLITTER + Utils.arrayListToString(blocks);
        //+ SPLITTER + Utils.arrayListToString(posts);
        return res;
    }
}