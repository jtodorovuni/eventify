package uni.fmi.eventify.ui;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import uni.fmi.eventify.R;
import uni.fmi.eventify.entity.User;
import uni.fmi.eventify.helper.RequestHelper;
import uni.fmi.eventify.helper.SQLiteHelper;

public class RegisterActivity extends AppCompatActivity {

    EditText usernameET;
    EditText emailET;
    EditText passwordET;
    EditText repeatPasswordET;
    Button okB;
    Button cancelB;

    SQLiteHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameET = findViewById(R.id.registerUsernameET);
        emailET = findViewById(R.id.registerEmailET);
        passwordET = findViewById(R.id.registerPasswordET);
        repeatPasswordET = findViewById(R.id.registerRepeatPasswordET);
        okB = findViewById(R.id.registerOkB);
        cancelB = findViewById(R.id.registerCancelB);

        dbHelper = new SQLiteHelper(this);
    }

    public void onClick(View view){

        if(view.getId() == R.id.registerOkB){
            if(usernameET.getText().length() == 0
                || emailET.getText().length() == 0
                || passwordET.getText().length() == 0
                || !passwordET.getText().toString().equals(repeatPasswordET.getText().toString())){

                Toast.makeText(this, "Please enter all fields and make sure both passwords match",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User();

            user.setEmail(emailET.getText().toString());
            user.setUsername(usernameET.getText().toString());
            user.setPassword(passwordET.getText().toString());

            new RegisterAsyncTask(user).execute();

/*            if(!dbHelper.registers(user)){
                Toast.makeText(this, "Something went caput....", Toast.LENGTH_SHORT).show();
                return;
            }*/

        }
    }

    private class RegisterAsyncTask extends AsyncTask<Void, Void, Void>{

        User user;
        boolean isSuccessful;
        ProgressDialog dialog;

        String error;

        RegisterAsyncTask(User user){
            this.user = user;
            dialog = new ProgressDialog(RegisterActivity.this);
        }

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Registering process is going...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String urlString = String.format("%s:%s/Register?password=%s&email=%s&username=%s",
                    RequestHelper.ADDRESS, RequestHelper.PORT, RequestHelper.hashPassword(user.getPassword()),
                    user.getEmail(), user.getUsername());

            HttpURLConnection urlConnection = null;

            try{
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                String result = reader.readLine();

                if(result != null){
                    JSONObject jsonOb = new JSONObject(result);

                    if(jsonOb.getBoolean("Success")){
                        isSuccessful = true;
                    }else{
                        error = jsonOb.getString("Message");
                    }

                }else{
                    error = "There was no response!";
                }

            }catch (IOException | JSONException e) {
                error = e.getMessage();
                throw new RuntimeException(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            dialog.hide();

            if(isSuccessful){
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

                intent.putExtra("newUser", true);
                intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
            }else{
                Toast.makeText(RegisterActivity.this,
                        "Register was not successful " + error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}