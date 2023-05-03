package t3h.android.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import t3h.android.dictionaryapp.adapters.MeaningsAdapter;
import t3h.android.dictionaryapp.adapters.PhoneticsAdapter;
import t3h.android.dictionaryapp.models.APIResponse;

public class MainActivity extends AppCompatActivity {
    private SearchView searchView;
    private TextView textViewWord;
    private RecyclerView recyclerPhonetics, recyclerMeanings;
    private ProgressDialog progressDialog;
    private PhoneticsAdapter phoneticsAdapter;
    private MeaningsAdapter meaningsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.search_view);
        textViewWord = findViewById(R.id.textView_word);
        recyclerPhonetics = findViewById(R.id.recycler_phonetics);
        recyclerMeanings = findViewById(R.id.recycler_meanings);
        progressDialog = new ProgressDialog(this);

        // khi mới mở app lên thì sẽ load dữ liệu của từ "hello"
        progressDialog.setTitle("Loading...");
        progressDialog.show();
        RequestManager requestManager = new RequestManager(MainActivity.this);
        requestManager.getWordMeaning(listener, "hello");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                progressDialog.setTitle("Fetching response for '" + query + "'");
                progressDialog.show();
                RequestManager requestManager = new RequestManager(MainActivity.this);
                requestManager.getWordMeaning(listener, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private final OnFetchDataListener listener = new OnFetchDataListener() {
        @Override
        public void onFetchData(APIResponse apiResponse, String message) {
            progressDialog.dismiss();
            if (apiResponse == null) {
                Toast.makeText(MainActivity.this, "No data found!!!", Toast.LENGTH_SHORT).show();
                return;
            }
            showData(apiResponse);
        }

        @Override
        public void onError(String message) {
            progressDialog.dismiss();
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    private void showData(APIResponse apiResponse) {
        textViewWord.setText("Word: " + apiResponse.getWord());
        recyclerPhonetics.setHasFixedSize(true);
        recyclerPhonetics.setLayoutManager(new GridLayoutManager(this, 1));
        phoneticsAdapter = new PhoneticsAdapter(this, apiResponse.getPhonetics());
        recyclerPhonetics.setAdapter(phoneticsAdapter);

        recyclerMeanings.setHasFixedSize(true);
        recyclerMeanings.setLayoutManager(new GridLayoutManager(this, 1));
        meaningsAdapter = new MeaningsAdapter(this, apiResponse.getMeanings());
        recyclerMeanings.setAdapter(meaningsAdapter);
    }
}