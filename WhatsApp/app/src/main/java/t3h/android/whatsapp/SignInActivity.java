package t3h.android.whatsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import t3h.android.whatsapp.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        Objects.requireNonNull(getSupportActionBar()).hide();

        checkCurrentUser();

        setUpProgressDialog();
    }

    private void checkCurrentUser() {
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
        }
    }

    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please wait\nValidation in progress.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        signIn();
        openSignUpScreen();
    }

    private void signIn() {
        binding.signInBtn.setOnClickListener(v -> {
            email = binding.emailEdt.getText().toString().trim();
            password = binding.passwordEdt.getText().toString();
            if (!email.isEmpty() && !password.isEmpty()) {
                progressDialog.show();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        goToMainActivity();
                    } else {
                        Toast.makeText(this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Please Enter Credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void openSignUpScreen() {
        binding.signUpClickTxt.setOnClickListener(v -> startActivity(new Intent(this, SignUpActivity.class)));
    }
}