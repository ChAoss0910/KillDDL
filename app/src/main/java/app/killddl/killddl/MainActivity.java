package app.killddl.killddl;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.firebase.firestore.*;
import com.google.firebase.Timestamp;

public class MainActivity extends AppCompatActivity {

    LinearLayout loginLayout;
    AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //animate background
        loginLayout = (LinearLayout) findViewById(R.id.loginLayout);
        animationDrawable = (AnimationDrawable) loginLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4500);
        animationDrawable.setExitFadeDuration(4500);
        animationDrawable.start();

//        Button button = findViewById(R.id.test);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
//            }
//        });

        //login button
        //TODO connect to database
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference userRef = db.collection("User");


        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameEditText = (EditText) findViewById(R.id.login_username);
                EditText passwordEditText = (EditText) findViewById(R.id.login_password);
                TextView errorMsg = (TextView) findViewById(R.id.errorMsg);
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                System.out.println("username: " + username + " password: " + password); //TODO remove this after connection
                if(password.length() == 0){errorMsg.setText("Password cannot be empty!");}
                if(username.length() == 0){errorMsg.setText("Username cannot be empty!");}

                //Find user from db
                userRef.whereEqualTo("name", username).whereEqualTo("password", password);

                if(false){errorMsg.setText("Username/Password combination wrong!");} //TODO check database
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void SignUp(View view)
    {
        Intent intent = new Intent(MainActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    public void JumpToNotification (View view) {
        startActivity(new Intent(MainActivity.this, NotificationActivity.class));
    }
}
