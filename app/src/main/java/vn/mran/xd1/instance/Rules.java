package vn.mran.xd1.instance;

import android.content.Context;

import org.apache.commons.lang3.ArrayUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import vn.mran.xd1.constant.PrefValue;
import vn.mran.xd1.helper.Log;
import vn.mran.xd1.util.Preferences;

/**
 * Created by Mr An on 29/11/2017.
 */

public class Rules {

    private final String TAG = getClass().getSimpleName();

    private static Rules instance;
    private Preferences preferences;

    private int currentRule;
    private boolean[] resultArrays = new boolean[]{};

    private int num1;
    private int num2;
    private int num3;
    private int num4;

    public static Rules getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new Rules(context);
    }

    public Rules(Context context) {
        preferences = new Preferences(context);
        currentRule = preferences.getIntValue(PrefValue.RULE, PrefValue.RULE_1);
        num1 = 2;
        num2 = 3;
        num3 = 0;
        num4 = 1;
    }

    public int getCurrentRule() {
        return currentRule;
    }

    public boolean getResult() {
        return getRule1();
    }

    /**
     * Rule 1
     * Trai qua phai - Tren xuong Duoi
     * So1 = 0;
     * So2 = 1;
     * So3 = 2;
     * So4 = 3;
     * <p>
     * Cong thuc
     * <p>
     * Chan x2
     * Le x1
     * <p>
     * VD : Chan Le Le Chan
     * (So1 x 2) + So2 + So3 + (So4 x2)
     *
     * @return
     */
    private boolean getRule1() {
        int tong = 0;
        int count = 3;

        int range = 4;
        if (resultArrays.length == 0)
            return randomBoolean();
        else if (resultArrays.length >= 8) {
            range = 8;
        }
        for (int i = resultArrays.length - 1; i >= resultArrays.length - range; i--) {
            Log.d(TAG, "Result array sub : " + resultArrays[i]);
            if (resultArrays[i]) {
                tong += (getAssignNum(count) * 2);
            } else {
                tong += getAssignNum(count);
            }
            count--;
            if (count < 0) count = 3;
        }

        Log.d(TAG, "Tong : " + tong);
        return tong % 2 == 0;
    }

    public void setResultArrays(boolean[] arrays) {
        resultArrays = ArrayUtils.addAll(resultArrays, arrays);
    }

    public boolean randomBoolean() {
        return Math.random() < 0.5;
    }

    private int getAssignNum(int count) {
        switch (count) {
            case 0:
                return num1;
            case 1:
                return num2;
            case 2:
                return num3;
            default:
                return num4;
        }
    }
}
