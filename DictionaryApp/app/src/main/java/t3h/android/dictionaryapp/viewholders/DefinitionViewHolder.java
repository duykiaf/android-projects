package t3h.android.dictionaryapp.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import t3h.android.dictionaryapp.R;

public class DefinitionViewHolder extends RecyclerView.ViewHolder {
    private TextView textViewDefinition, textViewExample, textViewSynonyms, textViewAntonyms;

    public DefinitionViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewDefinition = itemView.findViewById(R.id.textView_definition);
        textViewExample = itemView.findViewById(R.id.textView_example);
        textViewSynonyms = itemView.findViewById(R.id.textView_synonyms);
        textViewAntonyms = itemView.findViewById(R.id.textView_antonyms);
    }

    public TextView getTextViewDefinition() {
        return textViewDefinition;
    }

    public TextView getTextViewExample() {
        return textViewExample;
    }

    public TextView getTextViewSynonyms() {
        return textViewSynonyms;
    }

    public TextView getTextViewAntonyms() {
        return textViewAntonyms;
    }
}
