package uni.fmi.eventify.helper;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

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

    public static Date convertLongAsDate(long inputDate){
        inputDate = inputDate * 1000;
        Date date = new Date(inputDate);

        return date;
    }

    public static String convertLongAsDateString(long inputDate){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

            return dateFormat.format(convertLongAsDate(inputDate));
        }catch (Exception e){
            Log.wtf("boom", e.getMessage());
        }
        return "error";
    }

    public static String convertLongAsTimeString(long inputDate){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");

            return dateFormat.format(convertLongAsDate(inputDate));
        }catch (Exception e){
            Log.wtf("boom", e.getMessage());
        }
        return "error";
    }

    public static String convertLongAsDateTimeString(long inputDate){
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");

            return dateFormat.format(convertLongAsDate(inputDate));
        }catch (Exception e){
            Log.wtf("boom", e.getMessage());
        }
        return "error";
    }

}
