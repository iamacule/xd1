package vn.mran.xd1.mvp.presenter;

import android.content.Context;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import vn.mran.xd1.R;
import vn.mran.xd1.constant.PrefValue;
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

    public BattlePresenter(BattleView view) {
        this.context = (Context) view;
        this.view = view;
    }

    public void getResult(byte result) {
        boolean b;
        switch (result) {
            case PrefValue.RESULT_RULES:
                b = Rules.getInstance().getResult();
                break;
            case PrefValue.RESULT_OFFLINE:
                b = Rules.getInstance().getRuleOffline();
                break;
            default:
                b = (result == PrefValue.RESULT_CHAN);
                break;
        }
        boolean[] resultArrays;
        while (true) {
            resultArrays = (new boolean[]{Rules.getInstance().randomBoolean(),
                    Rules.getInstance().randomBoolean(),
                    Rules.getInstance().randomBoolean(),
                    Rules.getInstance().randomBoolean()});

            int tong = 0;
            for (boolean b2 : resultArrays) {
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

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
