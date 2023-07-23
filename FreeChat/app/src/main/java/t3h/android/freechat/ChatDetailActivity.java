package t3h.android.freechat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import t3h.android.freechat.databinding.ActivityChatDetailBinding;

public class ChatDetailActivity extends AppCompatActivity {
    private ActivityChatDetailBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private String receiverId, receiverName, profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        final String senderId = auth.getUid();
        receiverId = getIntent().getStringExtra("userId");
        receiverName = getIntent().getStringExtra("username");
        profilePic = getIntent().getStringExtra("profileImage");
        initReceiverInfo();
    }

    private void initReceiverInfo() {
        binding.username.setText(receiverName);
        Picasso.get().load(profilePic).placeholder(R.drawable.avatar3).into(binding.profileImage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.backIc.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}