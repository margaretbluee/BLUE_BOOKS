package com.example.blue_books;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText fpEmail;
    Button fpButton;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forgot_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        fpEmail=findViewById(R.id.fp_email);
        fpButton=findViewById(R.id.fp_button);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.fp_progBar);
    }

    public void onResetPasswordClicked(View view){

        resetPassword();
    }

    private void resetPassword(){
  String txtEmail = fpEmail.getText().toString().trim();
 if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail).matches()){
     fpEmail.setError("Καταχωρήστε ένα έγκυρο Email.");
     fpEmail.requestFocus();
     return;
 }
 progressBar.setVisibility(View.VISIBLE);

 mAuth.sendPasswordResetEmail(txtEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
     @Override
     public void onComplete(@NonNull Task<Void> task) {
         if(task.isSuccessful()){
             Toast.makeText(ForgotPasswordActivity.this,"ΠΑΡΑΚΑΛΩ ΕΛΕΓΞΤΕ ΤΟ EMAIL ΣΑΣ.",Toast.LENGTH_LONG).show();
Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
startActivity(intent);
progressBar.setVisibility(View.GONE);
         }else{

             Toast.makeText(ForgotPasswordActivity.this,"ΑΠΟΤΥΧΙΑ ΕΠΑΝΑΦΟΡΑΣ.",Toast.LENGTH_LONG).show();
          //   progressBar.setVisibility(View.GONE);
         }
     }
 });

    }
}