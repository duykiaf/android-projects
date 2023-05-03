package t3h.android.dictionaryapp.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import t3h.android.dictionaryapp.R;

public class MeaningViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewPartsOfSpeech;

    private RecyclerView recyclerDefinitions;

    public MeaningViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewPartsOfSpeech = itemView.findViewById(R.id.textView_partsOfSpeech);
        recyclerDefinitions = itemView.findViewById(R.id.recycler_definitions);
    }

    public TextView getTextViewPartsOfSpeech() {
        return textViewPartsOfSpeech;
    }

    public RecyclerView getRecyclerDefinitions() {
        return recyclerDefinitions;
    }
}
