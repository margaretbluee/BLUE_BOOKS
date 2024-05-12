package com.example.blue_books;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.thirdparty.publicsuffix.PublicSuffixPatterns;

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {
EditText email, password;
TextView forgotP, register;
Pattern pattern;
ProgressBar progressBar;
FirebaseAuth mAuth;
    private sharedPreferenceConfig sharedPreferenceConfig;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    CheckBox checkbox;
    boolean savelogin;
 Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        forgotP=findViewById(R.id.login_forgotPass);
        register=findViewById(R.id.login_register);
        progressBar= findViewById(R.id.progressBar_login);
        mAuth=FirebaseAuth.getInstance();
       checkbox=findViewById(R.id.checkBox);

        sharedPreferenceConfig = new sharedPreferenceConfig(getApplicationContext());

        sharedPreferences = getSharedPreferences("loginRef", MODE_PRIVATE);
        editor=sharedPreferences.edit();
        savelogin=sharedPreferences.getBoolean("savelogin", true);
        if(savelogin==true){
            email.setText(sharedPreferences.getString("username",null));
            password.setText(sharedPreferences.getString("password",null));

        }
    }

    public void onForgotPasswordClicked(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }

    public void onRegisterClicked(View view) {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void onLoginClicked(View view) {
String Email = email.getText().toString().trim();
String Password = password.getText().toString().trim();

if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
    email.setError("Παρακαλώ βάλτε ενα έγκυρο Email.");
}
        if( password.length() <6 ) {
            password.setError("Παρακαλώ εισάγετε κωδικό με τουλάχιστον 6 χαρακτήρες.");
            password.requestFocus();
        }
progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"EΠΙΤΥΧΙΑ.",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                    Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent1);
                    sharedPreferenceConfig.writeLoginStatus(true);
                    if(checkbox.isChecked()){
                        editor.putBoolean("savelogin",true);
                        editor.putString("username", Email);
                        editor.putString("password", Password);
                        editor.commit();

                    }

                }else{
                    Toast.makeText(LoginActivity.this,"ΑΠΟΤΥΧΙΑ.",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}