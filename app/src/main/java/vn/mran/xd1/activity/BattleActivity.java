package vn.mran.xd1.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import vn.mran.xd1.R;
import vn.mran.xd1.base.BaseActivity;
import vn.mran.xd1.constant.PrefValue;
import vn.mran.xd1.draw.DrawBattle;
import vn.mran.xd1.draw.DrawParallaxStar;
import vn.mran.xd1.helper.Log;
import vn.mran.xd1.instance.Media;
import vn.mran.xd1.instance.Rules;
import vn.mran.xd1.mvp.presenter.BattlePresenter;
import vn.mran.xd1.mvp.view.BattleView;
import vn.mran.xd1.util.MyAnimation;
import vn.mran.xd1.util.ResizeBitmap;
import vn.mran.xd1.util.Task;
import vn.mran.xd1.util.TouchEffect;
import vn.mran.xd1.widget.CustomTextView;

/**
 * Created by Mr An on 28/11/2017.
 */

public class BattleActivity extends BaseActivity implements DrawBattle.OnDrawBattleUpdate, BattleView, View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private AdView mAdView;

    private DrawBattle drawBattle;
    private DrawParallaxStar drawParallaxStar;
    private ImageView imgPlate;
    private ImageView imgStarChan;
    private ImageView imgStarLe;
    private ImageView imgAction;
    private ImageView imgV1;
    private ImageView imgV2;
    private ImageView imgV3;
    private ImageView imgV4;
    private ImageView imgSound;
    private CustomTextView txtAction;

    private BattlePresenter presenter;
    private boolean result;
    private Bitmap bpStar;
    private Bitmap bpUp;
    private Bitmap bpDown;
    private Bitmap bpSoundOn;
    private Bitmap bpSoundOff;
    private boolean isLidOpened = false;

    @Override
    public void initLayout() {
        hideStatusBar();
        imgPlate = findViewById(R.id.imgPlate);
        imgStarChan = findViewById(R.id.imgStarChan);
        imgStarLe = findViewById(R.id.imgStarLe);
        imgAction = findViewById(R.id.imgAction);
        imgV1 = findViewById(R.id.imgV1);
        imgV2 = findViewById(R.id.imgV2);
        imgV3 = findViewById(R.id.imgV3);
        imgV4 = findViewById(R.id.imgV4);
        imgSound = findViewById(R.id.imgSound);
        txtAction = findViewById(R.id.txtAction);
        drawBattle = findViewById(R.id.drawBattle);
        drawParallaxStar = findViewById(R.id.drawParallaxStar);

        TouchEffect.addAlpha(imgAction);
        TouchEffect.addAlpha(imgSound);
    }

    @Override
    public void initValue() {
        presenter = new BattlePresenter(this);
        drawBattle.setOnDrawBattleUpdate(this);
        Bitmap plate = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.plate), screenHeight * 6 / 10);
        bpUp = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.up), screenWidth / 18);
        bpDown = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.down), screenWidth / 18);
        imgPlate.setImageBitmap(plate);
        drawBattle.setLidSize(plate.getWidth() * 90 / 100);
        drawParallaxStar.setStarSize(plate.getWidth() * 12 / 100);

        bpStar = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.star), screenWidth * 35 / 100);
        bpSoundOn = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.sound_on), screenWidth / 20);
        bpSoundOff = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.sound_off), screenWidth / 20);
        imgAction.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background), screenHeight / 4));
        imgStarChan.setImageBitmap(bpStar);
        imgStarLe.setImageBitmap(bpStar);
        imgStarChan.setVisibility(View.GONE);
        imgStarLe.setVisibility(View.GONE);
        imgV1.setImageBitmap(bpUp);
        imgV2.setImageBitmap(bpDown);
        imgV3.setImageBitmap(bpUp);
        imgV4.setImageBitmap(bpDown);


        if (preferences.getBooleanValue(PrefValue.SETTING_SOUND, true)) {
            imgSound.setImageBitmap(bpSoundOn);
        } else {
            imgSound.setImageBitmap(bpSoundOff);
        }
    }

    @Override
    public void initAction() {
        findViewById(R.id.root).setOnClickListener(this);
        imgAction.setOnClickListener(this);
        imgSound.setOnClickListener(this);

        //Load AD
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_battle;
    }

    @Override
    public void onTouch() {
        hideStatusBar();
    }

    @Override
    public void onLidChanged(boolean isOpened) {
        Log.d(TAG, "onLidChanged");
        isLidOpened = isOpened;
        if (isOpened) {
            txtAction.setText(getString(R.string.shake));
        } else {
            findViewById(R.id.frameRootCenter).startAnimation(MyAnimation.shake(this));
            drawBattle.startAnimation(MyAnimation.shake(this));
            Media.playShortSound(getApplicationContext(), R.raw.open_close);
            presenter.getResult();
            txtAction.setText(getString(R.string.open));

        }
        if (isOpened) {
            Rules.getInstance().minusNumOfRule();
            if (result) {
                imgStarChan.setVisibility(View.VISIBLE);
                imgStarLe.setVisibility(View.GONE);
            } else {
                imgStarChan.setVisibility(View.GONE);
                imgStarLe.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void setImage(final boolean[] imageRuleArrays, final boolean result) {
        Rules.getInstance().setResultArrays(imageRuleArrays);
        BattleActivity.this.result = result;

        if (imageRuleArrays[0]) {
            imgV1.setImageBitmap(bpUp);
        } else {
            imgV1.setImageBitmap(bpDown);
        }
        if (imageRuleArrays[1]) {
            imgV2.setImageBitmap(bpUp);
        } else {
            imgV2.setImageBitmap(bpDown);
        }
        if (imageRuleArrays[2]) {
            imgV3.setImageBitmap(bpUp);
        } else {
            imgV3.setImageBitmap(bpDown);
        }
        if (imageRuleArrays[3]) {
            imgV4.setImageBitmap(bpUp);
        } else {
            imgV4.setImageBitmap(bpDown);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.root:
                Log.d(TAG, "Root clicked");
                hideStatusBar();
                break;

            case R.id.imgAction:
                Log.d(TAG, "btnAction clicked");
                action();
                break;

            case R.id.imgSound:
                Log.d(TAG, "btnSound clicked");
                switchSound();
                break;
        }
    }

    private void switchSound() {
        Log.d(TAG, "switchSound");
        final boolean isPlaySound = preferences.getBooleanValue(PrefValue.SETTING_SOUND, true);
        Runnable switchSoundRunnable = new Runnable() {
            @Override
            public void run() {
                Task.startNewBackGroundThread(new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!isPlaySound) {
                            Media.playBackgroundMusic(getApplicationContext());
                        } else {
                            Media.stopBackgroundMusic();
                        }
                    }
                }));
            }
        };

        if (!isPlaySound) {
            imgSound.setImageBitmap(bpSoundOn);
            preferences.storeValue(PrefValue.SETTING_SOUND, true);
        } else {
            imgSound.setImageBitmap(bpSoundOff);
            preferences.storeValue(PrefValue.SETTING_SOUND, false);
        }
        Task.removeCallBack(switchSoundRunnable);
        Task.postDelay(switchSoundRunnable, 500);
    }

    private void action() {
        Task.startAliveBackGroundThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "isLidOpened : " + isLidOpened);
                if (txtAction.getText().equals(getString(R.string.shake))) {
                    if (isLidOpened) {
                        Log.d(TAG, "Close lid");
                        drawBattle.closeLid();
                    }
                } else {
                    if (!isLidOpened) {
                        Media.playShortSound(getApplicationContext(), R.raw.open_close);
                        drawBattle.openLid();
                        Log.d(TAG, "Open lid");
                    }
                }
            }
        });
    }
}
