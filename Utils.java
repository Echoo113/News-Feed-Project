import java.util.ArrayList;

/**
 * <p>Purdue University -- CS18000 -- Spring 2024</p>
 *
 * @author Purdue CS L01 Team01
 * @version April, 2024
 */
public class Utils {
    private Utils() {
    }

    //  ArrayList<Integer>
    public static String arrayListToString(ArrayList<Integer> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    //  ArrayList<Integer>
    public static ArrayList<Integer> stringToArrayList(String str) {
        ArrayList<Integer> list = new ArrayList<>();

        str = str.replaceAll("\\[|\\]|\\s", "");
        if (str.equals("")) return list;
        String[] tokens = str.split(",");
        for (String token : tokens) {
            list.add(Integer.parseInt(token.trim()));
        }
        return list;
    }
}
