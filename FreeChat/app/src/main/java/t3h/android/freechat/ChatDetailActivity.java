package t3h.android.freechat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import t3h.android.freechat.adapter.ChatAdapter;
import t3h.android.freechat.databinding.ActivityChatDetailBinding;
import t3h.android.freechat.models.MessageModel;

public class ChatDetailActivity extends AppCompatActivity {
    private ActivityChatDetailBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private String receiverId, receiverName, profilePic;
    private ChatAdapter chatAdapter;
    private List<MessageModel> messageModelList;

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

        initChatAdapter(receiverId);

        sendMessage(senderId, receiverId);
    }

    private void initReceiverInfo() {
        binding.username.setText(receiverName);
        Picasso.get().load(profilePic).placeholder(R.drawable.avatar3).into(binding.profileImage);
    }

    private void initChatAdapter(String receiverId) {
        messageModelList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageModelList, this, receiverId);
        binding.messagesRcv.setAdapter(chatAdapter);
        binding.messagesRcv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void sendMessage(String senderId, String receiverId) {
        StringBuilder senderRoom = new StringBuilder();
        senderRoom.append(senderId).append(receiverId);
        StringBuilder receiverRoom = new StringBuilder();
        receiverRoom.append(receiverId).append(senderId);

        // get messages
        database.getReference().child("chats").child(senderRoom.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageModelList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    MessageModel model = data.getValue(MessageModel.class);
                    if (model != null) {
                        model.setMessageId(data.getKey());
                        messageModelList.add(model);
                    }
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // send message
        binding.sendBtn.setOnClickListener(v -> {
            String message = binding.enterMessageEdt.getText().toString();
            MessageModel messageModel = new MessageModel(senderId, message);
            messageModel.setTimestamp(new Date().getTime());
            binding.enterMessageEdt.setText("");

            // phương thức push() sẽ tạo 1 khóa ngẫu nhiên và duy nhất dưới nút senderRoom.toString()
            database.getReference().child("chats").child(senderRoom.toString()).push()
                    .setValue(messageModel).addOnSuccessListener(unused -> {
                        database.getReference().child("chats").child(receiverRoom.toString())
                                .push().setValue(messageModel);
                    });
        });
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