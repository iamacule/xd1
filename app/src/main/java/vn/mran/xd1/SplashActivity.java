package vn.mran.xd1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vn.mran.xd1.activity.ChooserActivity;
import vn.mran.xd1.util.MyAnimation;
import vn.mran.xd1.util.ScreenUtil;
import vn.mran.xd1.widget.CustomTextView;

public class SplashActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private final Handler handler = new Handler();
    private CustomTextView txtTitle;
    private LinearLayout lnMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        txtTitle = findViewById(R.id.txtTitle);
        lnMain = (LinearLayout)findViewById(R.id.lnMain);
        txtTitle.startAnimation(MyAnimation.fadeIn(this));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = MyAnimation.fadeOut(SplashActivity.this);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        lnMain.clearAnimation();
                        lnMain.removeAllViews();
                        Intent in = new Intent(SplashActivity.this, ChooserActivity.class);
                        startActivity(in);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                lnMain.startAnimation(animation);
            }
        }, 1500);
    }
}
