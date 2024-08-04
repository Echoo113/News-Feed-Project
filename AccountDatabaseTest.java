import java.io.*;

/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */
public class AccountDatabaseTest {

    public static void main(String[] args) {
        AccountDatabase database = new AccountDatabase("databaseOut.txt");
        try {
            FileReader fr = new FileReader("input.txt");
            BufferedReader fileReader = new BufferedReader(fr);
            String command = "";
            String content = "";
            FileWriter fw = new FileWriter("mainOut.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            while ((command = fileReader.readLine()) != null) {
                if (command.equals("register")) {
                    content = fileReader.readLine();
                    if (database.registerAccount(content))
                        bw.write("register success\n");
                    else
                        bw.write("register fail\n");
                } else if (command.equals("login")) {
                    content = fileReader.readLine();
                    String[] tokens = content.split(" ");
                    if (database.login(tokens[0], tokens[1]))
                        bw.write("login success\n");
                    else
                        bw.write("login fail\n");
                } else if (command.equals("verifyQuestion")) {
                    content = fileReader.readLine();
                    String[] tokens = content.split(" ");
                    if (database.verifyQuestion(Integer.parseInt(tokens[0]), tokens[1]))
                        bw.write("answer correct\n");
                    else
                        bw.write("answer incorrect\n");
                } else if (command.equals("addFriend")) {
                    content = fileReader.readLine();
                    String[] tokens = content.split(" ");
                    int id = Integer.parseInt(tokens[0]);
                    int id2 = Integer.parseInt(tokens[1]);
                    String res = "add friend ";
                    if (database.addFriend(id, id2))
                        bw.write(res + id + " " + id2 + " success\n");
                    else
                        bw.write(res + id + " " + id2 + " fail\n");
                } else if (command.equals("deleteFriend")) {
                    content = fileReader.readLine();
                    String[] tokens = content.split(" ");
                    int id = Integer.parseInt(tokens[0]);
                    int id2 = Integer.parseInt(tokens[1]);
                    String res = "delete friend ";
                    if (database.deleteFriend(id, id2))
                        bw.write(res + id + " " + id2 + " success\n");
                    else
                        bw.write(res + id + " " + id2 + " fail\n");
                } else if (command.equals("addBlock")) {
                    content = fileReader.readLine();
                    String[] tokens = content.split(" ");
                    int id = Integer.parseInt(tokens[0]);
                    int id2 = Integer.parseInt(tokens[1]);
                    String res = "add block ";
                    if (database.addBlock(id, id2))
                        bw.write(res + id + " " + id2 + " success\n");
                    else
                        bw.write(res + id + " " + id2 + " fail\n");
                } else if (command.equals("deleteBlock")) {
                    content = fileReader.readLine();
                    String[] tokens = content.split(" ");
                    int id = Integer.parseInt(tokens[0]);
                    int id2 = Integer.parseInt(tokens[1]);
                    String res = "delete block ";
                    if (database.deleteBlock(id, id2))
                        bw.write(res + id + " " + id2 + " success\n");
                    else
                        bw.write(res + id + " " + id2 + " fail\n");
                } else if (command.equals("isFriend")) {
                    content = fileReader.readLine();
                    String[] tokens = content.split(" ");
                    int id = Integer.parseInt(tokens[0]);
                    int id2 = Integer.parseInt(tokens[1]);
                    String res = id + "-" + id2;
                    if (database.isFriend(id, id2))
                        bw.write(res + " are friends\n");
                    else
                        bw.write(res + " are not friends\n");
                } else if (command.equals("isBlock")) {
                    content = fileReader.readLine();
                    String[] tokens = content.split(" ");
                    int id = Integer.parseInt(tokens[0]);
                    int id2 = Integer.parseInt(tokens[1]);
                    String res = id + "-" + id2;
                    if (database.isBlock(id, id2))
                        bw.write(res + " is in black list\n");
                    else
                        bw.write(res + " is not in black list\n");
                } else if (command.equals("logout")) {
                    content = fileReader.readLine();
                    int id = Integer.parseInt(content);
                    if (database.logout(id)) {
                        bw.write("logout success\n");
                    } else {
                        bw.write("logout fail\n");
                    }
                } else if (command.equals("saveData")) {
                    database.saveData();
                }
                bw.flush();
            }
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
