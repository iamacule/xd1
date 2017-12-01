package vn.mran.xd1;

import android.content.Intent;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import vn.mran.xd1.activity.ChooserActivity;
import vn.mran.xd1.base.BaseActivity;
import vn.mran.xd1.constant.PrefValue;
import vn.mran.xd1.helper.Log;
import vn.mran.xd1.instance.Media;
import vn.mran.xd1.instance.Rules;
import vn.mran.xd1.util.MyAnimation;
import vn.mran.xd1.widget.CustomTextView;

public class SplashActivity extends BaseActivity {
    private final String TAG = getClass().getSimpleName();
    private final Handler handler = new Handler();
    private CustomTextView txtTitle;
    private LinearLayout lnMain;

    @Override
    public void initLayout() {
        txtTitle = findViewById(R.id.txtTitle);
        lnMain = (LinearLayout) findViewById(R.id.lnMain);
    }

    @Override
    public void initValue() {
//        Log.d(TAG,"Test : "+true + true);
        Rules.init(getApplicationContext());
    }

    @Override
    public void initAction() {
        txtTitle.startAnimation(MyAnimation.fadeIn(this));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = MyAnimation.fadeOut(SplashActivity.this);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        if (preferences.getBooleanValue(PrefValue.SETTING_SOUND, true)) {
                            Media.playBackgroundMusic(getApplicationContext());
                        }
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        lnMain.clearAnimation();
                        lnMain.removeAllViews();
                        Intent in = new Intent(SplashActivity.this, ChooserActivity.class);
                        startActivity(in);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                lnMain.startAnimation(animation);
            }
        }, 1500);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }
}
