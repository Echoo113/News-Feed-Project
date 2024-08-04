import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class tests the methods and make sure they work
 * So we can implement it in the AccountDatabase
 *
 * @author Team 01  lab sec 1
 * @version Apr 1 2024
 */
public class AccountDatabase implements IAccountDatabase {
    private ArrayList<AccountTest> accounts;
    private String accountFile;

    public AccountDatabase(String accountFile) {
        this.accountFile = accountFile;
        this.accounts = new ArrayList<AccountTest>();
    }

    //login
    public synchronized boolean login(String username, String password) {
        int i = getAccountIndex(username);
        if (i == -1) return false;
        AccountTest account = accounts.get(i);
        return account.getPassword().equals(password);

    }

    //register
    public synchronized boolean registerAccount(String data) {
        //assign id
        int id = 1;
        if (!accounts.isEmpty()) {
            int last = accounts.size() - 1;
            id = accounts.get(last).getId() + 1;
        }
        data = Integer.toString(id) + AccountTest.SPLITTER + data;
        AccountTest account = null;
        try {
            account = new AccountTest(data);
        } catch (BadDataException e) {
            return false;
        }
        //uniqueness of username
        int i = getAccountIndex(account.getName());
        if (i != -1) return false;
        accounts.add(account);
        return true;
    }

    //log out
    public synchronized boolean logout(int id) {
        int i = getAccountIndex(id);
        if (i == -1) return false;
        AccountTest account = accounts.get(i);

        ArrayList<Integer> friends = account.getFriends();
        for (int friendId : friends) {
            AccountTest friend = accounts.get(getAccountIndex(friendId));
            friend.deleteFriend(id);
        }
        ArrayList<Integer> blocks = account.getBlocks();
        for (int blocksId : blocks) {
            AccountTest block = accounts.get(getAccountIndex(blocksId));
            block.deleteBlock(id);
        }
        //to do : post


        accounts.remove(i);
        return true;
    }

    //security
    public synchronized boolean verifyQuestion(int id, String answer) {
        int i = getAccountIndex(id);
        if (i == -1) return false;
        AccountTest account = accounts.get(i);
        return account.getSecurityAnswer().equalsIgnoreCase(answer);

    }


    //add friend
    public synchronized boolean addFriend(int id, int id2) {
        int i = getAccountIndex(id);
        if (i == -1) return false;
        int i2 = getAccountIndex(id2);
        if (i2 == -1) return false;
        AccountTest account = accounts.get(i);
        AccountTest account2 = accounts.get(i2);
        account.addFriend(id2);
        account2.addFriend(id);
        return true;
    }

    //delete friend
    public synchronized boolean deleteFriend(int id, int id2) {
        int i = getAccountIndex(id);
        if (i == -1) return false;
        int i2 = getAccountIndex(id2);
        if (i2 == -1) return false;
        AccountTest account = accounts.get(i);
        AccountTest account2 = accounts.get(i2);
        account.deleteFriend(id2);
        account2.deleteFriend(id);
        return true;
    }

    //
    public synchronized boolean isFriend(int id, int id2) {
        int i = getAccountIndex(id);
        if (i == -1) return false;
        AccountTest account = accounts.get(i);
        return account.getFriends().contains(id2);

    }

    //
    public synchronized boolean addBlock(int id, int id2) {
        int i = getAccountIndex(id);
        if (i == -1) return false;
        int i2 = getAccountIndex(id2);
        if (i2 == -1) return false;
        if (isFriend(id, id2))
            deleteFriend(id, id2);
        AccountTest account = accounts.get(i);
        AccountTest account2 = accounts.get(i2);
        account.addBlock(id2);
        account2.addBlock(id);
        return true;
    }

    //
    public synchronized boolean deleteBlock(int id, int id2) {
        int i = getAccountIndex(id);
        if (i == -1) return false;
        int i2 = getAccountIndex(id2);
        if (i2 == -1) return false;
        AccountTest account = accounts.get(i);
        AccountTest account2 = accounts.get(i2);
        account.deleteBlock(id2);
        account2.deleteBlock(id);
        return true;
    }

    //
    public synchronized boolean isBlock(int id, int id2) {
        int i = getAccountIndex(id);
        if (i == -1) return false;
        AccountTest account = accounts.get(i);
        return account.getBlocks().contains(id2);

    }


    /**/
    public synchronized boolean saveData() {
        try {
            FileWriter fw = new FileWriter(accountFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fw);
            for (AccountTest account : accounts) {
                String resStr = account.toString();
                resStr += "\n";
                bufferedWriter.write(resStr);
                bufferedWriter.flush();
            }
            bufferedWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /***/
    private synchronized int getAccountIndex(int id) {
        for (int i = 0; i < accounts.size(); i++) {
            AccountTest account = accounts.get(i);
            if (account.getId() == id)
                return i;
        }
        return -1;
    }

    private synchronized AccountTest getAccount(int id) {
        return null;
    }

    private synchronized int getAccountIndex(String name) {
        for (int i = 0; i < accounts.size(); i++) {
            AccountTest account = accounts.get(i);
            if (account.getName().equals(name))
                return i;
        }
        return -1;
    }


}
