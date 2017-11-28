package vn.mran.xd1.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;

import vn.mran.xd1.R;
import vn.mran.xd1.base.BaseActivity;
import vn.mran.xd1.constant.PrefValue;
import vn.mran.xd1.draw.DrawBattle;
import vn.mran.xd1.util.ResizeBitmap;

/**
 * Created by Mr An on 28/11/2017.
 */

public class BattleActivity extends BaseActivity {
    private DrawBattle drawBattle;
    private ImageView imgPlate;
    private boolean isShake;

    @Override
    public void initLayout() {
        hideStatusBar();
        imgPlate = findViewById(R.id.imgPlate);
        drawBattle = findViewById(R.id.drawBattle);
    }

    @Override
    public void initValue() {
        isShake = preferences.getBooleanValue(PrefValue.SETTING_SHAKE, true);
        if (isShake){
            findViewById(R.id.lnBottom).setVisibility(View.GONE);
            Bitmap plate = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.plate), screenHeight * 9 / 10);
            imgPlate.setImageBitmap(plate);
            drawBattle.setLidSize(plate.getWidth() * 72 / 100);
        }else {
            Bitmap plate = ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.plate), screenHeight * 8 / 10);
            imgPlate.setImageBitmap(plate);
            drawBattle.setLidSize(plate.getWidth() * 65 / 100);
        }
    }

    @Override
    public void initAction() {

    }

    @Override
    public int setLayout() {
        return R.layout.activity_battle;
    }
}
