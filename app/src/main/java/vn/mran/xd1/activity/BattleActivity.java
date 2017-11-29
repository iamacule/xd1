package vn.mran.xd1.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import vn.mran.xd1.R;
import vn.mran.xd1.base.BaseActivity;
import vn.mran.xd1.constant.PrefValue;
import vn.mran.xd1.draw.DrawBattle;
import vn.mran.xd1.helper.Log;
import vn.mran.xd1.instance.Rules;
import vn.mran.xd1.mvp.presenter.BattlePresenter;
import vn.mran.xd1.mvp.view.BattleView;
import vn.mran.xd1.util.ResizeBitmap;

/**
 * Created by Mr An on 28/11/2017.
 */

public class BattleActivity extends BaseActivity implements DrawBattle.OnDrawBattleUpdate, BattleView {

    private final String TAG = getClass().getSimpleName();

    private DrawBattle drawBattle;
    private ImageView imgPlate;
    private ImageView imgStarChan;
    private ImageView imgStarLe;
    private ImageView imgV1;
    private ImageView imgV2;
    private ImageView imgV3;
    private ImageView imgV4;

    private BattlePresenter presenter;
    private boolean isShake;
    private Bitmap bpStar;
    private Bitmap bpUp;
    private Bitmap bpDown;

    @Override
    public void initLayout() {
        hideStatusBar();
        imgPlate = findViewById(R.id.imgPlate);
        imgStarChan = findViewById(R.id.imgStarChan);
        imgStarLe = findViewById(R.id.imgStarLe);
        imgV1 = findViewById(R.id.imgV1);
        imgV2 = findViewById(R.id.imgV2);
        imgV3 = findViewById(R.id.imgV3);
        imgV4 = findViewById(R.id.imgV4);
        drawBattle = findViewById(R.id.drawBattle);
    }

    @Override
    public void initValue() {
        presenter = new BattlePresenter(this);
        drawBattle.setOnDrawBattleUpdate(this);

        isShake = preferences.getBooleanValue(PrefValue.SETTING_SHAKE, true);
        if (isShake) {
            findViewById(R.id.lnBottom).setVisibility(View.GONE);
            Bitmap plate = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.plate), screenHeight * 9 / 10);
            imgPlate.setImageBitmap(plate);
            drawBattle.setLidSize(plate.getWidth() * 72 / 100);
        } else {
            Bitmap plate = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.plate), screenHeight * 8 / 10);
            imgPlate.setImageBitmap(plate);
            drawBattle.setLidSize(plate.getWidth() * 65 / 100);
        }

        bpStar = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.star), screenWidth * 35 / 100);
        bpUp = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.up), screenWidth / 10);
        bpDown = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.down), screenWidth / 10);
        imgStarChan.setImageBitmap(bpStar);
        imgV1.setImageBitmap(bpUp);
        imgV2.setImageBitmap(bpDown);
        imgV3.setImageBitmap(bpUp);
        imgV4.setImageBitmap(bpDown);
    }

    @Override
    public void initAction() {
        findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Root clicked");
                hideStatusBar();
            }
        });
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
    public void onOpenLid() {
        presenter.getResult();
    }

    @Override
    public void setImage(boolean[] imageRuleArrays) {
        Rules.getInstance().setResultArrays(imageRuleArrays);

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
}
