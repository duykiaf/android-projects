package t3h.android.paintapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.widget.ImageButton;

import t3h.android.paintapp.view.DrawerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerView mDrawerView;
    ImageButton mBtnClear;
    ImageButton mBtnUndo;
    ImageButton mBtnRedo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerView = findViewById(R.id.drawer);
        mBtnClear = findViewById(R.id.btn_clear);
        mBtnUndo = findViewById(R.id.btn_undo);
        mBtnRedo = findViewById(R.id.btn_redo);

        mBtnClear.setOnClickListener(this);
        mBtnUndo.setOnClickListener(this);
        mBtnRedo.setOnClickListener(this);

        // trả về thông tin người dùng hiện tại
        UserHandle userHandle = UserHandle.getUserHandleForUid(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_clear:
                mDrawerView.clearAll();
                break;
            case R.id.btn_undo:
                mDrawerView.undo();
                break;
            case R.id.btn_redo:
                mDrawerView.redo();
                break;
        }
    }
}