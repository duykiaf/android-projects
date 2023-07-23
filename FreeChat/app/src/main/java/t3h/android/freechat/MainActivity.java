package t3h.android.freechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

import t3h.android.freechat.adapter.FragmentsAdapter;
import t3h.android.freechat.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private FragmentsAdapter fragmentsAdapter;
    private String[] tabLayoutNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        initViewPage();
    }

    private void initViewPage() {
        fragmentsAdapter = new FragmentsAdapter(this);
        binding.viewPager.setAdapter(fragmentsAdapter);
        tabLayoutNames = getResources().getStringArray(R.array.tabLayoutNames);
        TabLayout tabLayout = binding.tabLayout;
        new TabLayoutMediator(tabLayout, binding.viewPager,
                (tab, position) -> tab.setText(tabLayoutNames[position])
        ).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.groupChat:
                Toast.makeText(this, "Group chat", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                mAuth.signOut();
                startActivity(new Intent(this, SignInActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}