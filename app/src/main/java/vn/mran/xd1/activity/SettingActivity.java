package vn.mran.xd1.activity;

import android.widget.CompoundButton;
import android.widget.ToggleButton;

import vn.mran.xd1.R;
import vn.mran.xd1.base.BaseActivity;
import vn.mran.xd1.constant.PrefValue;
import vn.mran.xd1.instance.Media;
import vn.mran.xd1.util.Task;

/**
 * Created by Mr An on 28/11/2017.
 */

public class SettingActivity extends BaseActivity {
    private ToggleButton togSound;
    private ToggleButton togShake;

    private boolean playSound;
    private Runnable togSoundRunnable = new Runnable() {
        @Override
        public void run() {
            if (playSound) {
                Media.playBackgroundMusic(getApplicationContext());
            } else {
                Media.stopBackgroundMusic();
            }
        }
    };

    @Override
    public void initLayout() {
        togSound = findViewById(R.id.togSound);
        togShake = findViewById(R.id.togShake);
    }

    @Override
    public void initValue() {
        playSound = preferences.getBooleanValue(PrefValue.SETTING_SOUND, true);
        togSound.setChecked(playSound);
        togShake.setChecked(preferences.getBooleanValue(PrefValue.SETTING_SHAKE, true));
    }

    @Override
    public void initAction() {
        togSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                preferences.storeValue(PrefValue.SETTING_SOUND, b);
                playSound = b;
                Task.removeCallBack(togSoundRunnable);
                Task.postDelay(togSoundRunnable,500);
            }
        });

        togShake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            }
        });
    }

    @Override
    public int setLayout() {
        return R.layout.activity_setting;
    }
}
