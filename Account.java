import java.util.ArrayList;
import java.util.Random;

/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */
public class Account implements IAccount {
    private int id;
    private String name;
    private String password;
    private boolean securityProtection;
    private String securityQuestion;
    private String securityAnswer;
    private ArrayList<Integer> friends;
    private ArrayList<Integer> blocks;
    private ArrayList<Post> posts;


    public static final String SPLITTER = "-=-";

    public Account(String data) {

        String[] tokens = data.split(SPLITTER);
        this.id = Integer.parseInt(tokens[0]);
        this.name = tokens[1];
        this.password = tokens[2];
        this.securityProtection = Boolean.parseBoolean(tokens[3]);
        this.securityQuestion = tokens[4];
        this.securityAnswer = tokens[5];
        this.friends = Utils.stringToArrayList(tokens[6]);
        this.blocks = Utils.stringToArrayList(tokens[7]);
        this.posts = new ArrayList<>();

    }

    public Account(String name, String password, boolean securityProtection,
                   String securityQuestion, String securityAnswer) {
        this.id = 0;
        this.name = name;
        this.password = password;
        this.securityProtection = securityProtection;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.friends = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.posts = new ArrayList<>();

    }

    public Account(int id, String name, String password, boolean securityProtection,
                   String securityQuestion, String securityAnswer) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.securityProtection = securityProtection;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.friends = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public Account(String name, String password) {
        this.id = 0;
        this.name = name;
        this.password = password;
        this.securityProtection = false;
        this.securityQuestion = null;
        this.securityAnswer = null;
        this.friends = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public synchronized ArrayList<Post> getPosts() {
        return posts;
    }

    public synchronized void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public synchronized void addPost(Post newPost) {
        posts.add(newPost);
    }

    public synchronized void removePost(Post target) {
        posts.remove(target);
    }

    public synchronized int postID() {
        Random rand = new Random();
        int randomNum = rand.nextInt(10000) + 1;
        return randomNum;

    }

    public synchronized String getName() {
        return name;
    }

    public synchronized String getPassword() {
        return password;
    }

    public synchronized boolean isSecurityProtection() {
        return securityProtection;
    }

    public synchronized String getSecurityQuestion() {
        return securityQuestion;
    }

    public synchronized String getSecurityAnswer() {
        return securityAnswer;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized void setPassword(String password) {
        this.password = password;
    }

    public synchronized void setSecurityProtection(boolean securityProtection) {
        this.securityProtection = securityProtection;
    }

    public synchronized void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public synchronized void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public synchronized ArrayList<Integer> getFriends() {
        return friends;
    }

    public synchronized void setFriends(ArrayList<Integer> friends) {
        this.friends = friends;
    }

    public synchronized void addFriend(int idOne) {
        friends.add(idOne);
    }

    public synchronized void deleteFriend(int idO) {
        Integer idObj = idO;
        friends.remove(idObj);
    }

    public synchronized void addBlock(int block) {
        this.blocks.add(block);
    }

    public synchronized void deleteBlock(int idX) {
        Integer idObj = idX;
        blocks.remove(idObj);
    }

    public synchronized ArrayList<Integer> getBlocks() {
        return blocks;
    }

    public synchronized void setBlocks(ArrayList<Integer> blocks) {
        this.blocks = blocks;
    }

    public synchronized int getId() {
        return id;
    }

    public synchronized void setId(int id) {
        this.id = id;
    }

    public synchronized String toString() {
        String res = "";
        res = id + SPLITTER + name + SPLITTER + password + SPLITTER
                + securityProtection + SPLITTER +
                securityQuestion + SPLITTER + securityAnswer + SPLITTER +
                Utils.arrayListToString(friends) + SPLITTER + Utils.arrayListToString(blocks);
        //+ SPLITTER + Utils.arrayListToString(posts);
        return res;
    }
}