package vn.mran.xd1.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Mr An on 28/11/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(setLayout());
        initLayout();
        initValue();
        initAction();
    }

    public abstract void initLayout();

    public abstract void initValue();

    public abstract void initAction();

    public abstract int setLayout();
}
