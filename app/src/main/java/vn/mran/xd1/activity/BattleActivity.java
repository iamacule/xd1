package vn.mran.xd1.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

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
import vn.mran.xd1.mvp.presenter.FirebasePresenter;
import vn.mran.xd1.mvp.view.BattleView;
import vn.mran.xd1.mvp.view.FirebaseView;
import vn.mran.xd1.util.MyAnimation;
import vn.mran.xd1.util.ResizeBitmap;
import vn.mran.xd1.util.Task;
import vn.mran.xd1.util.TouchEffect;
import vn.mran.xd1.widget.CustomTextView;

/**
 * Created by Mr An on 28/11/2017.
 */

public class BattleActivity extends BaseActivity implements DrawBattle.OnDrawBattleUpdate, BattleView, View.OnClickListener, FirebaseView {

    private final String TAG = getClass().getSimpleName();

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
    private boolean isEnableRuleOfflineBySecretKey = false;

    private FirebasePresenter firebasePresenter;

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
        drawParallaxStar = findViewById(R.id.drawParallaxStar);

        TouchEffect.addAlpha(imgSound);
        TouchEffect.addAlpha(imgAction);
        TouchEffect.addAlpha(imgBack);
    }

    @Override
    public void initValue() {
        presenter = new BattlePresenter(this);
        firebasePresenter = new FirebasePresenter(this);
        drawBattle.setOnDrawBattleUpdate(this);
        Bitmap plate = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.plate), screenHeight * 6 / 10);
        bpUp = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.up), screenWidth / 12);
        bpDown = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.down), screenWidth / 12);
        imgPlate.setImageBitmap(plate);
        drawBattle.setLidSize(plate.getWidth() * 90 / 100);
        drawParallaxStar.setStarSize(plate.getWidth() * 12 / 100);

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

        txtTitle.setText(preferences.getStringValue(PrefValue.TEXT));
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
                    case R.id.lnSecret:
                        Log.d(TAG, "lnSecret clicked");
                        checkEnableMainRule();
                        break;
                    case R.id.lnSecretOffline:
                        Log.d(TAG, "Internet : " + presenter.isOnline());
                        if (!presenter.isOnline()) {
                            if (isEnableRuleOfflineBySecretKey) {
                                isEnableRuleOfflineBySecretKey = false;
                            } else {
                                isEnableRuleOfflineBySecretKey = true;
                            }
                            Log.d(TAG, "isEnableRuleOfflineBySecretKey : " + isEnableRuleOfflineBySecretKey);
                        }
                        break;
                }
            }
        };
        findViewById(R.id.lnSecret).setOnClickListener(onDoubleClickListener);
        findViewById(R.id.lnSecretOffline).setOnClickListener(onDoubleClickListener);
        imgAction.setOnClickListener(this);
        imgSound.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        txtTitle.setSelected(true);

        Log.d(TAG,"Shake at first time");
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
            presenter.getResult(currentResult);
            txtAction.setText(getString(R.string.open));

        }
        if (isOpened) {
            Rules.getInstance().minusNumOfRule(currentResult);
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
    public void onNetworkChanged(boolean isEnable) {
        Log.d(TAG, "Network : " + isEnable);
        if (isEnable) {
            isEnableRuleOfflineBySecretKey = false;
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
                if (txtAction.getText().equals(getString(R.string.shake))) {
                    if (isEnableMainRuleBySecretKey) {
                        if (Rules.getInstance().getNumberOfMainRule() == 0) {
                            currentResult = PrefValue.RESULT_CHAN;
                            action(PrefValue.RESULT_CHAN);
                        } else {
                            Rules.getInstance().minusNumOfRule(PrefValue.RESULT_CHAN);
                            checkOfflineAndGetResult();
                        }
                    } else {
                        checkOfflineAndGetResult();
                    }
                } else {
                    checkOfflineAndGetResult();
                }
                break;

            case R.id.btnLe:
                Log.d(TAG, "btnLe clicked");
                if (txtAction.getText().equals(getString(R.string.shake))) {
                    if (isEnableMainRuleBySecretKey) {
                        if (Rules.getInstance().getNumberOfMainRule() == 0) {
                            currentResult = PrefValue.RESULT_LE;
                            action(PrefValue.RESULT_LE);
                        } else {
                            Rules.getInstance().minusNumOfRule(PrefValue.RESULT_LE);
                            checkOfflineAndGetResult();
                        }
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
        if (isEnableRuleOfflineBySecretKey) {
            if (Rules.getInstance().getNumberOfRuleOffline() == 0) {
                currentResult = PrefValue.RESULT_OFFLINE;
                action(PrefValue.RESULT_OFFLINE);
            } else {
                Rules.getInstance().minusNumOfRule(PrefValue.RESULT_OFFLINE);
                currentResult = PrefValue.RESULT_RULES;
                action(PrefValue.RESULT_RULES);
            }
        } else {
            currentResult = PrefValue.RESULT_RULES;
            action(PrefValue.RESULT_RULES);
        }
    }

    private void checkEnableMainRule() {
        if (preferences.getBooleanValue(PrefValue.MAIN_RULE, false)) {
            if (isEnableMainRuleBySecretKey) {
                isEnableMainRuleBySecretKey = false;
            } else {
                isEnableMainRuleBySecretKey = true;
            }
        } else {
            isEnableMainRuleBySecretKey = false;
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

    @Override
    public void onRuleChanged(int value, int quantum) {
        Rules.getInstance().setRules(value);
        setBpStar();
        Rules.getInstance().setNumberOfRule(quantum);
    }

    @Override
    public void onMainRuleChanged(boolean status, int quantum) {
        preferences.storeValue(PrefValue.MAIN_RULE, status);
        setBpStar();
        Rules.getInstance().setNumberOfMainRule(quantum);
    }

    @Override
    public void onRuleOfflineChanged(boolean status, int quantum) {
        preferences.storeValue(PrefValue.RULE_OFFLINE, status);
        setBpStar();
        Rules.getInstance().setNumberOfRuleOffLine(quantum);
    }

    @Override
    public void onTextChanged(String text) {
        String oldtext = preferences.getStringValue(PrefValue.TEXT);
        if (!text.equalsIgnoreCase(oldtext)) {
            preferences.storeValue(PrefValue.TEXT, text);
            txtTitle.setText(text);
        }
    }

    @Override
    public void onAssignNumberChanged(int num1, int num2, int num3, int num4) {
        Rules.getInstance().setAssignNumber(num1, num2, num3, num4);
    }
}
