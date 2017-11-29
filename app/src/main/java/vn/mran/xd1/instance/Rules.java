package vn.mran.xd1.instance;

import android.content.Context;

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
    private boolean isNewResult = false;

    private String currentRule;
    private int numberOfRule;
    private boolean[] resultArrays = new boolean[4];

    public static Rules getInstance() {
        return instance;
    }

    public static void init(Context context) {
        instance = new Rules(context);
    }

    public Rules(Context context) {
        preferences = new Preferences(context);
        currentRule = preferences.getStringValue(PrefValue.RULE, PrefValue.RULE_1);
        numberOfRule = preferences.getIntValue(PrefValue.NUMBER_OF_RULE, PrefValue.DEFAULT_NUMBER_OF_RULES);
    }

    public boolean getResult() {
        if (numberOfRule > 0) {
            isNewResult = true;
            switch (currentRule) {
                case PrefValue.RULE_1:
                    return getRule1();
                default:
                    return getRule1();
            }
        } else
            return randomBoolean();
    }

    public void minusNumOfRule() {
        if (numberOfRule > 0) {
            if (isNewResult) {
                isNewResult = false;
                numberOfRule = numberOfRule - 1;
                Log.d(TAG, "Number of rules : " + numberOfRule);
            }
        }
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
        for (int i = 0; i < resultArrays.length; i++) {
            if (resultArrays[i]) {
                tong += (i * 2);
            } else {
                tong += i;
            }
        }

        Log.d(TAG, "Tong : " + tong);
        return tong % 2 == 0;
    }

    public void setResultArrays(boolean[] arrays) {
        resultArrays = arrays;
    }

    public boolean randomBoolean() {
        return Math.random() < 0.5;
    }

    public void setNewResult(boolean newResult) {
        isNewResult = newResult;
    }
}
