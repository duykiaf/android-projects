package t3h.android.whatsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import t3h.android.whatsapp.databinding.ActivitySignUpBinding;
import t3h.android.whatsapp.models.Users;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ProgressDialog progressDialog;
    private String username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseApp.initializeApp(this);

        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        Objects.requireNonNull(getSupportActionBar()).hide();

        setUpProgressDialog();
    }

    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We're creating your account.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        signUp();
        openSignInScreen();
    }

    private void signUp() {
        binding.signUpBtn.setOnClickListener(v -> {
            username = binding.usernameEdt.getText().toString().trim();
            email = binding.emailEdt.getText().toString().trim();
            password = binding.passwordEdt.getText().toString();
            if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        // save new user to real time database
                        Users user = new Users(username, email, password);
                        String getUserId = Objects.requireNonNull(task.getResult().getUser()).getUid();
//                        database.getReference().child("Users").child(getUserId).setValue(user);
                        database.getReference("Users").child(getUserId).setValue(user);

                        Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(this, "Please Enter Credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openSignInScreen() {
        binding.signInClickTxt.setOnClickListener(v -> startActivity(new Intent(this, SignInActivity.class)));
    }
}