package t3h.android.freechat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import t3h.android.freechat.databinding.ActivitySignInBinding;
import t3h.android.freechat.models.Users;

public class SignInActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 999;
    private ActivitySignInBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private String email, password;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        Objects.requireNonNull(getSupportActionBar()).hide();

        setUpProgressDialog();

        setUpGoogleSignInOptions();

        checkCurrentUser();
    }

    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please wait\nValidation in progress.");
    }

    private void setUpGoogleSignInOptions() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void checkCurrentUser() {
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(SignInActivity.this, MainActivity.class));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        signIn();
        openSignUpScreen();
        signInWithGoogle();
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

    private void signInWithGoogle() {
        binding.googleBtn.setOnClickListener(v -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.e("DNV", account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.e("DNV", "Google sign in failed");
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        Log.e("DNV", "firebaseAuthWithGoogle");
        progressDialog.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            progressDialog.dismiss();
           if (task.isSuccessful()) {
               Log.e("DNV", "is successful");
               FirebaseUser user = mAuth.getCurrentUser();
               if (user != null) {
                   Log.e("DNV", user.toString());
                   Users userInfo = new Users();
                   userInfo.setUserId(user.getUid());
                   userInfo.setEmail(user.getEmail());
                   userInfo.setUsername(user.getDisplayName());
                   userInfo.setProfilePic(user.getPhotoUrl().toString());
                   Log.e("DNV", userInfo.toString());
                   databaseReference.child("users").child(userInfo.getUserId()).setValue(userInfo);
               }
               goToMainActivity();
               Toast.makeText(this, "Sign with Google", Toast.LENGTH_LONG).show();
           } else {
               Toast.makeText(this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
           }
        });
    }
}