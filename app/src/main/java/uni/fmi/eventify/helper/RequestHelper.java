package uni.fmi.eventify.helper;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RequestHelper {
    public static final String ADDRESS = "http://78.130.187.186";
    public static final String PORT = "8989";
    public static String token;


    public static String hashPassword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            md.update(password.getBytes());

            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();

            for(byte myByte : bytes){
                sb.append(Integer.toString(
                        (myByte & 0xff) + 0x100, 16)
                        .substring(1));
            }

            return sb.toString();
        }catch (NoSuchAlgorithmException e) {
            Log.e("hashing problem...", e.getMessage());
            return "";
        }
    }
}
