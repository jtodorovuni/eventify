package uni.fmi.eventify.ui;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uni.fmi.eventify.R;
import uni.fmi.eventify.entity.User;
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

            if(!dbHelper.registers(user)){
                Toast.makeText(this, "Something went caput....", Toast.LENGTH_SHORT).show();
                return;
            }

        }

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}