package vn.mran.xd1.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import vn.mran.xd1.R;
import vn.mran.xd1.util.EventUtil;
import vn.mran.xd1.util.MyAnimation;
import vn.mran.xd1.util.ResizeBitmap;
import vn.mran.xd1.util.ScreenUtil;

/**
 * Created by MrAn PC on 13-Feb-16.
 */
public class ChooserActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "ChooserActivity";
    private ImageView imgPlay;
    private ImageView imgBattle;
    private ImageView imgSetting;
    private ImageView imgRanking;
    private LinearLayout lnChoose;
    private LinearLayout lnImgSplash;
    private float screenWidth;
    private final Activity chooserActivity = this;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
        screenWidth = ScreenUtil.getScreenWidth(getWindowManager());
        initLayout();
        setOnClick();
    }

    private void setOnClick() {
        imgPlay.setOnClickListener(this);
        imgBattle.setOnClickListener(this);
        imgSetting.setOnClickListener(this);
        imgRanking.setOnClickListener(this);
    }

    private void initLayout() {
        imgPlay = (ImageView) findViewById(R.id.imgPlay);
        imgBattle = (ImageView) findViewById(R.id.imgBattle);
        imgSetting = (ImageView) findViewById(R.id.imgSetting);
        imgRanking = (ImageView) findViewById(R.id.imgRanking);
        lnChoose = (LinearLayout) findViewById(R.id.lnChoose);
        lnImgSplash = (LinearLayout) findViewById(R.id.lnImgSplash);
        startAnimation();
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
        imgPlay.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background), screenWidth / 2));
        imgBattle.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background), screenWidth / 2));
        imgSetting.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background), screenWidth / 2));
        imgRanking.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background), screenWidth / 2));
    }

    @Override
    public void onBackPressed() {
        EventUtil.backPressExitApp(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgPlay:
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.imgBattle:
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.imgSetting:
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.imgRanking:
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
    }
}
