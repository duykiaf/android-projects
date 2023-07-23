package t3h.android.freechat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
            binding.lastMessage.setText(user.getLastMessage());
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(Users user);
    }
}
