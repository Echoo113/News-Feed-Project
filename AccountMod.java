import java.io.*;
import java.util.ArrayList;

/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */
public class AccountMod implements IAccountMod {
    private final ArrayList<Account> accounts;
    private final String accountFile;

    public AccountMod(String accountFile) throws IOException {
        this.accountFile = accountFile;
        this.accounts = new ArrayList<>();
        loadAccounts();
    }

    private synchronized void loadAccounts() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(accountFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                accounts.add(new Account(line));
            }
        } catch (IOException e) {
            File newFile = new File("userFile.txt");
        }
    }

    public synchronized Account login(String username, String password) {
        int i = getAccountIndex(username);
        if (i == -1) return null;
        Account account = accounts.get(i);
        if (!account.getPassword().equals(password))
            return null;
        return account;
    }

    // Logout
    public synchronized boolean logout(int id) {
        int i = getAccountIndex(id);
        if (i == -1) return false;
        Account account = accounts.get(i);

        ArrayList<Integer> friends = account.getFriends();
        for (int friendsId : friends) {
            Account friend = accounts.get(getAccountIndex(friendsId));
            friend.deleteFriend(id);
        }
        ArrayList<Integer> blacks = account.getBlocks();
        for (int blackedID : blacks) {
            Account black = accounts.get(getAccountIndex(blackedID));
            black.deleteBlock(id);
        }
        accounts.remove(i);
        return true;
    }

    // Security
    public synchronized boolean verifyQuestion(int id, String answer) {
        int i = getAccountIndex(id);
        if (i == -1) return false;
        Account account = accounts.get(i);
        return account.getSecurityAnswer().equalsIgnoreCase(answer);
    }

    // Add friend
    public synchronized boolean addFriend(int id, int id2) {
        int i = getAccountIndex(id);
        if (i == -1) return false;
        int i2 = getAccountIndex(id2);
        if (i2 == -1) return false;
        Account account = accounts.get(i);
        Account account2 = accounts.get(i2);
        account.addFriend(id2);
        account2.addFriend(id);
        return true;
    }

    // Delete friend
    public synchronized boolean deleteFriend(int id, int id2) {
        int i = getAccountIndex(id);
        if (i == -1) return false;
        int i2 = getAccountIndex(id2);
        if (i2 == -1) return false;
        Account account = accounts.get(i);
        Account account2 = accounts.get(i2);
        account.deleteFriend(id2);
        account2.deleteFriend(id);
        return true;
    }

    // Is friend
    public synchronized boolean isFriend(int id, int id2) {
        int i = getAccountIndex(id);
        if (i == -1) return false;
        Account account = accounts.get(i);
        return account.getFriends().contains(id2);
    }

    // Add block
    public synchronized boolean addBlock(int id, int id2) {
        int i = getAccountIndex(id);
        if (i == -1) return false;
        int i2 = getAccountIndex(id2);
        if (i2 == -1) return false;
        if (isFriend(id, id2))
            deleteFriend(id, id2);
        Account account = accounts.get(i);
        Account account2 = accounts.get(i2);
        account.addBlock(id2);
        account2.addBlock(id);
        return true;
    }

    // Delete block
    public synchronized boolean deleteBlock(int id, int id2) {
        int i = getAccountIndex(id);
        if (i == -1) return false;
        int i2 = getAccountIndex(id2);
        if (i2 == -1) return false;
        Account account = accounts.get(i);
        Account account2 = accounts.get(i2);
        account.deleteBlock(id2);
        account2.deleteBlock(id);
        return true;
    }

    public synchronized void updateAccount(int i, Account account) {
        accounts.set(i, account);
    }

    // Is block
    public synchronized boolean isBlock(int id, int id2) {
        int i = getAccountIndex(id);
        if (i == -1) return false;
        Account account = accounts.get(i);
        return account.getBlocks().contains(id2);
    }

    public synchronized Account getAccountWithID(int id) {
        int i = getAccountIndex(id);
        if (i == -1) return null;
        return accounts.get(i);
    }

    public synchronized Account getAccountWithUsername(String name) {
        int i = getAccountIndex(name);
        if (i == -1) {
            return null;
        }
        return accounts.get(i);
    }

    // Save data
    public synchronized boolean saveData() {
        try {
            FileWriter fw = new FileWriter(accountFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fw);
            for (Account account : accounts) {
                String resStr = account.toString() + "\n";
                bufferedWriter.write(resStr);
            }
            bufferedWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private synchronized int getAccountIndex(int id) {
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            if (account.getId() == id)
                return i;
        }
        return -1;
    }

    private synchronized int getAccountIndex(String name) {
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            if (account.getName().equals(name))
                return i;
        }
        return -1;
    }
}
