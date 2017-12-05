package vn.mran.xd1.activity;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vn.mran.xd1.R;
import vn.mran.xd1.base.BaseActivity;
import vn.mran.xd1.instance.Media;
import vn.mran.xd1.util.EventUtil;
import vn.mran.xd1.util.MyAnimation;
import vn.mran.xd1.util.ResizeBitmap;
import vn.mran.xd1.util.ScreenUtil;
import vn.mran.xd1.util.TouchEffect;

/**
 * Created by MrAn PC on 13-Feb-16.
 */
public class ChooserActivity extends BaseActivity implements View.OnClickListener {
    private final String TAG = "ChooserActivity";
    private ImageView imgBattle;
    private ImageView imgSetting;
    private ImageView imgFlower;
    private ImageView imgExit;
    private LinearLayout lnChoose;
    private LinearLayout lnImgSplash;
    private final Activity chooserActivity = this;

    @Override
    public void initLayout() {
        imgBattle = (ImageView) findViewById(R.id.imgBattle);
        imgSetting = (ImageView) findViewById(R.id.imgSetting);
        imgExit = (ImageView) findViewById(R.id.imgExit);
        imgFlower = (ImageView) findViewById(R.id.imgFlower);
        lnChoose = (LinearLayout) findViewById(R.id.lnChoose);
        lnImgSplash = (LinearLayout) findViewById(R.id.lnImgSplash);
    }

    @Override
    public void initValue() {
        TouchEffect.addAlpha(imgBattle);
        TouchEffect.addAlpha(imgSetting);
        TouchEffect.addAlpha(imgExit);
    }

    @Override
    public void initAction() {
        startAnimation();
        imgBattle.setOnClickListener(this);
        imgSetting.setOnClickListener(this);
        imgExit.setOnClickListener(this);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_chooser;
    }

    private void startAnimation() {
        lnImgSplash.startAnimation(MyAnimation.fadeIn(chooserActivity));
        Animation animation = MyAnimation.sliceInToTopLong(chooserActivity);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                lnChoose.clearAnimation();
                Log.d(TAG, "Clear Animations");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        lnChoose.startAnimation(animation);
        imgBattle.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background), screenWidth / 2));
        imgSetting.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background), screenWidth / 2));
        imgExit.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background), screenWidth / 2));
        imgFlower.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.ochna_tree), screenWidth));
    }

    @Override
    public void onBackPressed() {
        finish();
        Media.stopBackgroundMusic();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgBattle:
                startActivity(BattleActivity.class);
                break;
            case R.id.imgSetting:
                startActivity(SettingActivity.class);
                break;
            case R.id.imgExit:
                finish();
                Media.stopBackgroundMusic();
                break;
        }
    }
}
