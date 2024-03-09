package uni.fmi.eventify.ui;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import uni.fmi.eventify.R;
import uni.fmi.eventify.databinding.ActivityLoginBinding;
import uni.fmi.eventify.entity.User;
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
    }

    public void login(String username, String password){
        User user = dbHelper.login(username, password);

        if(user != null){

            if(rememberMeCB.isChecked()){
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("username", username);
                editor.putString("password", password);
                editor.apply();
            }

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);

            intent.putExtra("currentUser", user);

            startActivity(intent);
        }else{
            Toast.makeText(this, "Invalid username or password!", Toast.LENGTH_SHORT).show();
            if(count == 0){
                finish();
            }
        }
    }

    public void onLoginClick(View view){
        if(usernameET.getText().length() == 0 || passwordET.getText().length() == 0){
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }
        count--;

        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        login(username, password);
    }

    public void onRegisterClick(View view){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}