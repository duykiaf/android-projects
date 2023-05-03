package t3h.android.dictionaryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import t3h.android.dictionaryapp.R;
import t3h.android.dictionaryapp.models.Definitions;
import t3h.android.dictionaryapp.viewholders.DefinitionViewHolder;

public class DefinitionsAdapter extends RecyclerView.Adapter<DefinitionViewHolder> {
    private Context context;

    private List<Definitions> definitionsList;

    public DefinitionsAdapter(Context context, List<Definitions> definitionsList) {
        this.context = context;
        this.definitionsList = definitionsList;
    }

    @NonNull
    @Override
    public DefinitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DefinitionViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.item_definition_layout, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewHolder holder, int position) {
        holder.getTextViewDefinition().setText(definitionsList.get(position).getDefinition());
        holder.getTextViewExample().setText(definitionsList.get(position).getExample());

        StringBuilder synonyms = new StringBuilder();
        StringBuilder antonyms = new StringBuilder();

        synonyms.append(definitionsList.get(position).getSynonyms());
        antonyms.append(definitionsList.get(position).getAntonyms());

        holder.getTextViewSynonyms().setSelected(true);
        holder.getTextViewAntonyms().setSelected(true);
    }

    @Override
    public int getItemCount() {
        return definitionsList.size();
    }
}
