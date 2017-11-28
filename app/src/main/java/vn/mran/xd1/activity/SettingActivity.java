package vn.mran.xd1.activity;

import android.graphics.BitmapFactory;
import android.widget.ImageView;

import vn.mran.xd1.R;
import vn.mran.xd1.base.BaseActivity;
import vn.mran.xd1.util.ResizeBitmap;

/**
 * Created by Mr An on 28/11/2017.
 */

public class SettingActivity extends BaseActivity {
//    private ImageView imgTogSound;
//    private ImageView imgTogShake;
    @Override
    public void initLayout() {
//        imgTogSound = findViewById(R.id.imgTogSound);
//        imgTogShake = findViewById(R.id.imgTogShake);
//        imgTogSound.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.toggle_on), screenWidth / 6));
//        imgTogShake.setImageBitmap(ResizeBitmap.resize(BitmapFactory.decodeResource(getResources(), R.drawable.toggle_off), screenWidth / 6));
    }

    @Override
    public void initValue() {

    }

    @Override
    public void initAction() {

    }

    @Override
    public int setLayout() {
        return R.layout.activity_setting;
    }
}
