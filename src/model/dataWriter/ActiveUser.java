package model.dataWriter;

/**
 * Created by John on 2/17/2017.
 */
public class ActiveUser {

    private static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        ActiveUser.username = username;
    }
}
