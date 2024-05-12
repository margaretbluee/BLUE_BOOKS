package com.example.blue_books;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
EditText username;
EditText password;
EditText email;
ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        email=findViewById(R.id.email);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();


    }

    public void signupButtonClicked(View view){
String txtUsername= username.getText().toString().trim();
String txtPassword = password.getText().toString().trim();
String txtemail = email.getText().toString().trim();
      if(txtUsername.isEmpty() && txtPassword.isEmpty() && txtemail.isEmpty()){
          Toast.makeText(SignUpActivity.this,"Παρακαλώ, εισάγετε τα στοιχέια σας.",Toast.LENGTH_LONG).show();
      }

       if(txtUsername.isEmpty() ) {
            username.setError("Παρακαλώ εισάγετε Όνομα Χρήστη.");
            username.requestFocus();
        }


if(txtPassword.isEmpty() || txtPassword.length() <6 ) {
    password.setError("Παρακαλώ εισάγετε κωδικό με τουλάχιστον 6 χαρακτήρες.");
    password.requestFocus();
}

        if(txtemail.isEmpty() ) {
            email.setError("Παρακαλώ εισάγετε ένα έγκυρο Email.");
            email.requestFocus();
        }
progressBar.setVisibility(View.VISIBLE);

mAuth.createUserWithEmailAndPassword(txtemail,txtPassword)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                 User user = new User(txtUsername,txtPassword,txtemail);
                    FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignUpActivity.this,"Εγγραφήκατε Επιτυχώς!",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                    else{
                                        Toast.makeText(SignUpActivity.this,"ΑΠΟΤΥΧΙΑ ΕΓΓΡΑΦΗΣ.",Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });




                }else{
                    Toast.makeText(SignUpActivity.this,"ΑΠΟΤΥΧΙΑ ΕΓΓΡΑΦΗΣ.",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


    }

}