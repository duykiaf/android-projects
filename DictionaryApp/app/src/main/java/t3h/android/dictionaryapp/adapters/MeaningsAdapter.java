package t3h.android.dictionaryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import t3h.android.dictionaryapp.R;
import t3h.android.dictionaryapp.models.Meanings;
import t3h.android.dictionaryapp.viewholders.MeaningViewHolder;

public class MeaningsAdapter extends RecyclerView.Adapter<MeaningViewHolder> {
    private Context context;

    private List<Meanings> meaningsList;

    public MeaningsAdapter(Context context, List<Meanings> meaningsList) {
        this.context = context;
        this.meaningsList = meaningsList;
    }

    @NonNull
    @Override
    public MeaningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MeaningViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.item_meaning_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MeaningViewHolder holder, int position) {
        holder.getTextViewPartsOfSpeech().setText("Parts of Speech: " + meaningsList.get(position).getPartOfSpeech());
        holder.getRecyclerDefinitions().setHasFixedSize(true);
        holder.getRecyclerDefinitions().setLayoutManager(new GridLayoutManager(context, 1));
        DefinitionsAdapter definitionsAdapter = new DefinitionsAdapter(context, meaningsList.get(position).getDefinitions());
        holder.getRecyclerDefinitions().setAdapter(definitionsAdapter);
    }

    @Override
    public int getItemCount() {
        return meaningsList.size();
    }
}
