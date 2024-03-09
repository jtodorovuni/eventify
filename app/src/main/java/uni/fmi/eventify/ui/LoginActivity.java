package uni.fmi.eventify.ui;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import uni.fmi.eventify.R;
import uni.fmi.eventify.databinding.ActivityLoginBinding;
import uni.fmi.eventify.helper.SQLiteHelper;

public class LoginActivity extends AppCompatActivity {

    EditText usernameET;
    EditText passwordET;
    Button loginB;
    TextView registerTV;

    SQLiteHelper dbHelper;

    int count = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        loginB = findViewById(R.id.loginB);
        registerTV = findViewById(R.id.registerTV);

        dbHelper = new SQLiteHelper(LoginActivity.this);
    }

    public void onLoginClick(View view){
        if(usernameET.getText().length() == 0 || passwordET.getText().length() == 0){
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }
        count--;

        String username = usernameET.getText().toString();
        String password = passwordET.getText().toString();

        if(dbHelper.login(username, password) != null){
            //Loging in...
        }else{
            Toast.makeText(this, "Invalid username or password!", Toast.LENGTH_SHORT).show();
            if(count == 0){
                finish();
            }
        }
    }

    public void onRegisterClick(View view){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}