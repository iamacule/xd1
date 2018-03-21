package vn.mran.xd1.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vn.mran.xd1.R;
import vn.mran.xd1.base.BaseActivity;
import vn.mran.xd1.constant.PrefValue;
import vn.mran.xd1.draw.DrawBattle;
import vn.mran.xd1.draw.DrawParallaxStar;
import vn.mran.xd1.helper.Log;
import vn.mran.xd1.helper.OnDoubleClickListener;
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

    private DrawBattle drawBattle;
    private ImageView imgPlate;
    private ImageView imgStarChan;
    private ImageView imgStarLe;
    private ImageView imgAction;
    private ImageView imgV1;
    private ImageView imgV2;
    private ImageView imgV3;
    private ImageView imgV4;
    private ImageView imgSound;
    private ImageView imgBack;
    private CustomTextView txtAction;

    private BattlePresenter presenter;
    private boolean result;
    private Bitmap bpStar;
    private Bitmap bpUp;
    private Bitmap bpDown;
    private Bitmap bpSoundOn;
    private Bitmap bpSoundOff;
    private CustomTextView txtTitle;


    private boolean isLidOpened = false;
    private boolean isEnableMainRuleBySecretKey = false;

    private byte currentResult = PrefValue.RESULT_RULES;


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
        imgBack = findViewById(R.id.imgBack);
        txtAction = findViewById(R.id.txtAction);
        txtTitle = findViewById(R.id.txtTitle);
        drawBattle = findViewById(R.id.drawBattle);

        TouchEffect.addAlpha(imgSound);
        TouchEffect.addAlpha(imgAction);
        TouchEffect.addAlpha(imgBack);
    }

    @Override
    public void initValue() {
        presenter = new BattlePresenter(this);
        drawBattle.setOnDrawBattleUpdate(this);
        Bitmap plate = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.plate), screenHeight * 7 / 10);
        bpUp = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.up), screenWidth / 12);
        bpDown = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.down), screenWidth / 12);
        imgPlate.setImageBitmap(plate);
        drawBattle.setLidSize(plate.getWidth());
        ((DrawParallaxStar) findViewById(R.id.drawParallaxStar)).setStarSize(plate.getWidth() * 12 / 100);

        setBpStar();
        bpSoundOn = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.sound_on), screenWidth / 20);
        bpSoundOff = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.sound_off), screenWidth / 20);
        imgAction.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.button_background_9), screenHeight / 3));
        imgStarChan.setImageBitmap(bpStar);
        imgStarLe.setImageBitmap(bpStar);
        imgStarChan.setVisibility(View.GONE);
        imgStarLe.setVisibility(View.GONE);
        imgV1.setImageBitmap(bpUp);
        imgV2.setImageBitmap(bpDown);
        imgV3.setImageBitmap(bpUp);
        imgV4.setImageBitmap(bpDown);
        imgBack.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.back), screenWidth / 20));


        if (preferences.getBooleanValue(PrefValue.SETTING_SOUND, true)) {
            imgSound.setImageBitmap(bpSoundOn);
        } else {
            imgSound.setImageBitmap(bpSoundOff);
        }

        txtTitle.setText("Chúc các bạn chơi vui vẻ !");

        setVersion();
    }

    private void setVersion() {
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            ((TextView) findViewById(R.id.txtVersion)).setText("v" + pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void setBpStar() {
        if (preferences.getBooleanValue(PrefValue.MAIN_RULE, false)) {
            switch (Rules.getInstance().getCurrentRule()) {
                case PrefValue.RULE_1:
                    bpStar = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.star_on_rule1), screenWidth * 35 / 100);
                    break;
                case PrefValue.RULE_2:
                    bpStar = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.star_on_rule2), screenWidth * 35 / 100);
                    break;
                default:
                    bpStar = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.star_on_random), screenWidth * 35 / 100);
                    break;
            }
        } else {
            switch (Rules.getInstance().getCurrentRule()) {
                case PrefValue.RULE_1:
                    bpStar = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.star_off_rule1), screenWidth * 35 / 100);
                    break;
                case PrefValue.RULE_2:
                    bpStar = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.star_off_rule2), screenWidth * 35 / 100);
                    break;
                default:
                    bpStar = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.star_off_random), screenWidth * 35 / 100);
                    break;
            }
        }
        imgStarChan.setImageBitmap(bpStar);
        imgStarLe.setImageBitmap(bpStar);
    }

    @Override
    public void initAction() {
        findViewById(R.id.root).setOnClickListener(this);
        findViewById(R.id.btnChan).setOnClickListener(this);
        findViewById(R.id.btnLe).setOnClickListener(this);
        OnDoubleClickListener onDoubleClickListener = new OnDoubleClickListener() {
            @Override
            public void onDoubleClick(View v) {
                switch (v.getId()) {
                    case R.id.txtChan:
                        Log.d(TAG, "lnSecret clicked");
                        checkEnableMainRule();
                        break;
                }
            }
        };
        findViewById(R.id.txtChan).setOnClickListener(onDoubleClickListener);
        imgAction.setOnClickListener(this);
        imgSound.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        txtTitle.setSelected(true);

        Log.d(TAG, "Shake at first time");
        onLidChanged(false);
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
            findViewById(R.id.rlCenter).startAnimation(MyAnimation.shake(this));
            drawBattle.startAnimation(MyAnimation.shake(this));
            Media.playShortSound(getApplicationContext(), R.raw.open_close);
            txtAction.setText(getString(R.string.open));

        }
        if (isOpened) {
            presenter.getResult(currentResult);
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
                checkOfflineAndGetResult();
                break;

            case R.id.imgSound:
                Log.d(TAG, "btnSound clicked");
                switchSound();
                break;

            case R.id.imgBack:
                Log.d(TAG, "btnBack clicked");
                onBackPressed();
                break;

            case R.id.btnChan:
                Log.d(TAG, "btnChan clicked");
                if (txtAction.getText().equals(getString(R.string.open))) {
                    if (isEnableMainRuleBySecretKey) {
                        currentResult = PrefValue.RESULT_CHAN;
                        action(PrefValue.RESULT_CHAN);
                    } else {
                        checkOfflineAndGetResult();
                    }
                } else {
                    checkOfflineAndGetResult();
                }
                break;

            case R.id.btnLe:
                Log.d(TAG, "btnLe clicked");
                if (txtAction.getText().equals(getString(R.string.open))) {
                    if (isEnableMainRuleBySecretKey) {
                        currentResult = PrefValue.RESULT_LE;
                        action(PrefValue.RESULT_LE);
                    } else {
                        checkOfflineAndGetResult();
                    }
                } else {
                    checkOfflineAndGetResult();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        presenter.stopCheckingNetwork();
        super.onBackPressed();
    }

    private void checkOfflineAndGetResult() {
        currentResult = PrefValue.RESULT_RULES;
        action(PrefValue.RESULT_RULES);
    }

    private void checkEnableMainRule() {
        if (isEnableMainRuleBySecretKey) {
            isEnableMainRuleBySecretKey = false;
        } else {
            isEnableMainRuleBySecretKey = true;
        }
        Log.d(TAG, "isEnableMainRuleBySecretKey : " + isEnableMainRuleBySecretKey);
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

    private void action(final byte result) {
        Task.startAliveBackGroundThread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "isLidOpened : " + isLidOpened);
                if (txtAction.getText().equals(getString(R.string.shake))) {
                    if (isLidOpened) {
                        Log.d(TAG, "Close lid");
                        drawBattle.closeLid(result);
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

    @Override
    public void onResultUpdate(byte result) {
        currentResult = result;
    }
}
