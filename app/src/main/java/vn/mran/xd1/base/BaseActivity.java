package vn.mran.xd1.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import vn.mran.xd1.util.ScreenUtil;

/**
 * Created by Mr An on 28/11/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected float screenWidth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(setLayout());
        initBaseValue();
        initLayout();
        initValue();
        initAction();
    }

    private void initBaseValue() {
        screenWidth = ScreenUtil.getScreenWidth(getWindowManager());
    }

    public abstract void initLayout();

    public abstract void initValue();

    public abstract void initAction();

    public abstract int setLayout();

    protected void startActivity(Class clazz){
        startActivity(new Intent(this,clazz));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
