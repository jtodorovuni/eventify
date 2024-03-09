package uni.fmi.eventify.ui;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

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

public class LoginActivity extends AppCompatActivity {

    EditText usernameET;
    EditText passwordET;
    Button loginB;
    TextView registerTV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameET = findViewById(R.id.usernameET);
        passwordET = findViewById(R.id.passwordET);
        loginB = findViewById(R.id.loginB);
        registerTV = findViewById(R.id.registerTV);
    }

    public void onLoginClick(){

    }
}