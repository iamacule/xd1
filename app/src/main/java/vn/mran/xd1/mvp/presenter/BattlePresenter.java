package vn.mran.xd1.mvp.presenter;

import android.content.Context;
import android.hardware.SensorListener;
import android.hardware.SensorManager;

import vn.mran.xd1.R;
import vn.mran.xd1.helper.Log;
import vn.mran.xd1.instance.Media;
import vn.mran.xd1.instance.Rules;
import vn.mran.xd1.mvp.view.BattleView;
import vn.mran.xd1.util.Task;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by Mr An on 29/11/2017.
 */

public class BattlePresenter {
    private final String TAG = getClass().getSimpleName();

    private SensorManager sensorManager;
    private Context context;

    private BattleView view;
    private boolean isShakeEnable = true;

    public BattlePresenter(BattleView view) {
        this.context = (Context) view;
        this.view = view;
    }

    public void getResult() {
        boolean b = Rules.getInstance().getResult();
        boolean[] resultArrays;
        while (true) {
            resultArrays = (new boolean[]{Rules.getInstance().randomBoolean(),
                    Rules.getInstance().randomBoolean(),
                    Rules.getInstance().randomBoolean(),
                    Rules.getInstance().randomBoolean()});

            int tong = 0;
            for (boolean b2 : resultArrays) {
                Log.d(TAG, "" + b2);
                if (b2)
                    tong += 2;
                else
                    tong += 1;
            }
            if (b) {

                if (tong % 2 == 0)
                    break;
            } else {

                if (tong % 2 != 0)
                    break;
            }
        }
        view.setImage(resultArrays, b);
    }

    public void setMode(boolean isShake) {
        if (isShake) {
            final int[] SHAKE_THRESHOLD = {800};
            sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
            sensorManager.registerListener(new SensorListener() {
                public float last_x;
                public float last_y;
                public float last_z;
                public float x;
                public float y;
                public float z;
                public long lastUpdate;

                private Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        getResult();
                    }
                };

                @Override
                public void onSensorChanged(int sensor, float[] values) {
                    if (isShakeEnable){
                        if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
                            long curTime = System.currentTimeMillis();

                            // only allow one update every 100ms.
                            if ((curTime - lastUpdate) > 100) {
                                long diffTime = (curTime - lastUpdate);
                                lastUpdate = curTime;

                                x = values[SensorManager.DATA_X];
                                y = values[SensorManager.DATA_Y];
                                z = values[SensorManager.DATA_Z];

                                float speed = Math.abs(x + y + z - last_x - last_y - last_z) / diffTime * 10000;

                                if (speed > SHAKE_THRESHOLD[0]) {
                                    Log.d("sensor", "shake detected w/ speed: " + speed);
                                    Media.playShortSound(context, R.raw.shake);
                                    Task.removeCallBack(runnable);
                                    Task.postDelay(runnable, 500);
                                }
                                last_x = x;
                                last_y = y;
                                last_z = z;
                            }
                        }
                    }
                }

                @Override
                public void onAccuracyChanged(int i, int i1) {

                }
            }, SensorManager.SENSOR_ACCELEROMETER, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    public void setShakeEnable(boolean b) {
        isShakeEnable = b;
    }
}
