package t3h.android.dictionaryapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import t3h.android.dictionaryapp.R;
import t3h.android.dictionaryapp.models.Phonetics;
import t3h.android.dictionaryapp.viewholders.PhoneticViewHolder;

public class PhoneticsAdapter extends RecyclerView.Adapter<PhoneticViewHolder> {
    private Context context;
    private List<Phonetics> phoneticsList;

    public PhoneticsAdapter(Context context, List<Phonetics> phoneticsList) {
        this.context = context;
        this.phoneticsList = phoneticsList;
    }

    @NonNull
    @Override
    public PhoneticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhoneticViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.item_phonetics_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneticViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.getTextViewPhonetic().setText(phoneticsList.get(position).getText());
        holder.getImageButtonAudio().setOnClickListener(view -> {
            Log.d("AUDIO", phoneticsList.get(position).getAudio());
            MediaPlayer mediaPlayer = new MediaPlayer();
            try{
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource("https:" + phoneticsList.get(position).getAudio());
                mediaPlayer.prepare();
                mediaPlayer.start();
                mediaPlayer.release();
            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(context, "Couldn't play audio", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return phoneticsList.size();
    }
}
