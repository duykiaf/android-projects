package t3h.android.freechat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import t3h.android.freechat.databinding.SampleReceiverBinding;
import t3h.android.freechat.databinding.SampleSenderBinding;
import t3h.android.freechat.models.MessageModel;

public class ChatAdapter extends RecyclerView.Adapter {
    private List<MessageModel> messageModelList;
    private Context context;
    private int SENDER_VIEW_TYPE = 1;
    private int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapter(List<MessageModel> messageModelList, Context context) {
        this.messageModelList = messageModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SENDER_VIEW_TYPE) {
            return new SenderViewHolder(SampleSenderBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent, false)
            );
        } else {
            return new ReceiverViewHolder(SampleReceiverBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent, false)
            );
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messageModelList.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_VIEW_TYPE;
        } else {
            return RECEIVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageModel messageModel = messageModelList.get(position);
        if (holder.getClass() == SenderViewHolder.class) {
            ((SenderViewHolder) holder).bindSampleSenderView(messageModel);
        } else {
            ((ReceiverViewHolder) holder).bindSampleReceiverView(messageModel);
        }
    }

    @Override
    public int getItemCount() {
        return messageModelList == null ? 0 : messageModelList.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {
        private SampleReceiverBinding sampleReceiverBinding;

        public ReceiverViewHolder(SampleReceiverBinding sampleReceiverBinding) {
            super(sampleReceiverBinding.getRoot());
            this.sampleReceiverBinding = sampleReceiverBinding;
        }

        public void bindSampleReceiverView(MessageModel messageModel) {
            sampleReceiverBinding.receiverTxt.setText(messageModel.getMessage());
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        private SampleSenderBinding sampleSenderBinding;

        public SenderViewHolder(SampleSenderBinding sampleSenderBinding) {
            super(sampleSenderBinding.getRoot());
            this.sampleSenderBinding = sampleSenderBinding;
        }

        public void bindSampleSenderView(MessageModel messageModel) {
            sampleSenderBinding.senderTxt.setText(messageModel.getMessage());
        }
    }
}
