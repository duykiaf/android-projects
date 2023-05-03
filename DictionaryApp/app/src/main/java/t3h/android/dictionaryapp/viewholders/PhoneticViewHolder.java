package t3h.android.dictionaryapp.viewholders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import t3h.android.dictionaryapp.R;

public class PhoneticViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewPhonetic;

    private ImageButton imageButtonAudio;

    public PhoneticViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewPhonetic = itemView.findViewById(R.id.textView_phonetic);
        imageButtonAudio = itemView.findViewById(R.id.imageButton_audio);
    }

    public TextView getTextViewPhonetic() {
        return textViewPhonetic;
    }

    public ImageButton getImageButtonAudio() {
        return imageButtonAudio;
    }
}
