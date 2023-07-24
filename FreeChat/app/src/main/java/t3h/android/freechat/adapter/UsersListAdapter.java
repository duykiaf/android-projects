package t3h.android.freechat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import t3h.android.freechat.R;
import t3h.android.freechat.databinding.ItemLayoutBinding;
import t3h.android.freechat.models.Users;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UserViewHolder> {
    private List<Users> usersList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public UsersListAdapter(List<Users> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Users getUser = usersList.get(position);
        holder.bindView(getUser);
    }

    @Override
    public int getItemCount() {
        return usersList == null ? 0 : usersList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private ItemLayoutBinding binding;

        public UserViewHolder(ItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.itemLayout.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClicked(usersList.get(getAdapterPosition()));
                }
            });
        }

        public void bindView(Users user) {
            Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.avatar3).into(binding.profileImage);
            binding.username.setText(user.getUsername());
            // get last message
            StringBuilder sb = new StringBuilder();
            String chatRoomId = sb.append(FirebaseAuth.getInstance().getUid()).append(user.getUserId()).toString();
            FirebaseDatabase.getInstance().getReference().child("chats")
                    .child(chatRoomId).orderByChild("timestamp").limitToLast(1)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // DataSnapshot chứa dữ liệu tại địa chỉ đang truy cập
                            if (snapshot.hasChildren()) {
                                for (DataSnapshot data : snapshot.getChildren()) {
                                    binding.lastMessage.setText(data.child("message").getValue().toString());
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(Users user);
    }
}
