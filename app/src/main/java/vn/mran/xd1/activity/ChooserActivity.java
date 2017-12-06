package vn.mran.xd1.activity;

import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import vn.mran.xd1.R;
import vn.mran.xd1.base.BaseActivity;
import vn.mran.xd1.instance.Media;
import vn.mran.xd1.util.ResizeBitmap;
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

    @Override
    public void initLayout() {
        imgBattle = (ImageView) findViewById(R.id.imgBattle);
        imgSetting = (ImageView) findViewById(R.id.imgSetting);
        imgExit = (ImageView) findViewById(R.id.imgExit);
        imgFlower = (ImageView) findViewById(R.id.imgFlower);
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
