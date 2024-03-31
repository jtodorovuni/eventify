package uni.fmi.eventify.ui;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import uni.fmi.eventify.databinding.ActivityLoginBinding;
import uni.fmi.eventify.entity.User;
import uni.fmi.eventify.helper.RequestHelper;
import uni.fmi.eventify.helper.SQLiteHelper;

public class LoginActivity extends AppCompatActivity {

    EditText usernameET;
    EditText passwordET;
    Button loginB;
    TextView registerTV;
    CheckBox rememberMeCB;

    SQLiteHelper dbHelper;

    SharedPreferences sharedPref;
    boolean newUser = false;
    int count = 3;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        loginB = findViewById(R.id.loginB);
        registerTV = findViewById(R.id.registerTV);
        rememberMeCB = findViewById(R.id.rememberMeCB);

        if(getIntent().getExtras() != null){
            newUser = getIntent().getExtras().getBoolean("newUser", false);
        }
        dbHelper = new SQLiteHelper(LoginActivity.this);

        sharedPref = getPreferences(Context.MODE_PRIVATE);
        String lastUsername = sharedPref.getString("username", "");
        String lastPassword = sharedPref.getString("password", "");

        if(lastUsername.length() > 0 && lastPassword.length() > 0 && !newUser){
           login(lastUsername, lastPassword);
        }


        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void login(String username, String password){
        //User user = dbHelper.login(username, password);

        new LoginAsyncTask(username, password).execute();
    }

    public void onLoginClick(View view){
        if(usernameET.getText().length() == 0 || passwordET.getText().length() == 0){
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }
        count--;

        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        login(username, RequestHelper.hashPassword(password));
    }

    public void onRegisterClick(View view){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    private class LoginAsyncTask extends AsyncTask<Void, Void, Void>{

        boolean isSuccessful;
        String token;
        String errorMessage;
        String username;
        String password;

        ProgressDialog dialog;

        LoginAsyncTask(String username, String password){
            this.username = username;
            this.password = password;
            dialog = new ProgressDialog(LoginActivity.this);
        }

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Loging in...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Login?username={username}&password={password}

            String urlString = String.format("%s:%s/Login?username=%s&password=%s",
                    RequestHelper.ADDRESS, RequestHelper.PORT, username, password);

            HttpURLConnection urlConnection = null;

            try{
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

                String result = reader.readLine();

                if(result != null){

                    JSONObject resultOb = new JSONObject(result);

                    if(resultOb.getBoolean("Success")){
                        isSuccessful = true;
                        token = resultOb.getString("Data");
                    }else{
                        errorMessage = resultOb.getString("Message");
                    }
                }

            } catch (Exception e) {
                errorMessage = e.getMessage();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            dialog.hide();

            if(isSuccessful){

                if(rememberMeCB.isChecked()){
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.apply();
                }

                RequestHelper.token = token;

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

                if(count == 0){
                    finish();
                }
            }
        }
    }
}