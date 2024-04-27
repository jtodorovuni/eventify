package uni.fmi.eventify.helper;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RequestHelper {

    public static final String ADD_SCORE = "AddGameScore?token=%s&score=%s";
    public static final String GET_CODES = "GetMyCodes?token=%s";
    public static final String GET_EVENTS = "AllEvents?token=%s";

    public static final String ADDRESS = "http://78.130.187.186";
    public static final String PORT = "8989";
    public static String token;
    public static void requestService(String urlString, ResponseListener listener){
        new Thread(() ->
        {
            HttpURLConnection connection = null;

            try{
                URL url = new URL(urlString);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");

                InputStreamReader stream  = new InputStreamReader(connection.getInputStream());

                BufferedReader reader = new BufferedReader(stream);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = reader.readLine()) != null){
                    sb.append(line);
                }

                reader.close();
                String response = sb.toString();

                listener.onResponse(response);


            }catch (Exception e){
                Log.wtf("Went Boom", e.getMessage());
                listener.onError(e.getMessage());
            }finally {
                if(connection != null)
                    connection.disconnect();
            }

        }).start();
    }



}
