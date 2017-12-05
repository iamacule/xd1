package vn.mran.xd1.instance;

import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private boolean isNewResult = false;

    private String currentRule;
    private int numberOfRule;
    private int numberOfMainRule;
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
        numberOfMainRule = preferences.getIntValue(PrefValue.NUMBER_OF_MAIN_RULE, PrefValue.DEFAULT_NUMBER_OF_MAIN_RULES);
        numberOfRule = preferences.getIntValue(PrefValue.NUMBER_OF_RULE, PrefValue.DEFAULT_NUMBER_OF_RULES);
    }

    public void setRules(String rule) {
        currentRule = rule;
        preferences.storeValue(PrefValue.RULE, rule);
    }

    public String getCurrentRule(){
        return currentRule;
    }

    public void setNumberOfRule(int num) {
        this.numberOfRule = num;
        preferences.storeValue(PrefValue.NUMBER_OF_RULE, num);
    }

    public void setNumberOfMainRule(int num) {
        this.numberOfMainRule = num;
        preferences.storeValue(PrefValue.NUMBER_OF_MAIN_RULE, num);
    }

    public int getNumberOfMainRule() {
        return numberOfMainRule;
    }

    public boolean getResult() {
        if (numberOfRule > 0) {
            isNewResult = true;
            switch (currentRule) {
                case PrefValue.RULE_1:
                    Log.d(TAG, "Rule 1");
                    return getRule1();
                case PrefValue.RULE_2:
                    Log.d(TAG, "Rule 2");
                    return getRule2();
                default:
                    return getRule1();
            }
        } else {
            setRules(PrefValue.RULE_RANDOM);
            return randomBoolean();
        }
    }

    public void minusNumOfRule(byte currentResult) {
        android.util.Log.d(TAG, "minusNumOfRule current result : " + currentResult);
        switch (currentResult) {
            case PrefValue.RESULT_RULES:
                Log.d(TAG, "Number of rules : " + numberOfRule);
                if (numberOfRule > 0) {
                    if (isNewResult) {
                        isNewResult = false;
                        numberOfRule = numberOfRule - 1;
                        preferences.storeValue(PrefValue.NUMBER_OF_RULE, numberOfRule);
                    }
                }
                break;

            default:
                Log.d(TAG, "Number of main rules : " + numberOfMainRule);
                if (numberOfMainRule > 0) {
                    numberOfMainRule = numberOfMainRule - 1;
                    preferences.storeValue(PrefValue.NUMBER_OF_MAIN_RULE, numberOfMainRule);
                }
                break;
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
     * (So1 x 2) + So2 + So3 + (So4 x2) + Number of minute
     *
     * @return
     */
    private boolean getRule2() {
        int tong = 0;
        for (int i = 0; i < resultArrays.length; i++) {
            if (resultArrays[i]) {
                tong += (i * 2);
            } else {
                tong += i;
            }
        }

        Log.d(TAG, "Tong 1 : " + tong);

        int min = Integer.parseInt((new SimpleDateFormat("mm").format(new Date())).toString());

        if (min < 10)
            tong += 1;
        if (min >= 10 && min < 20)
            tong += 2;
        if (min >= 20 && min < 30)
            tong += 3;
        if (min >= 30 && min < 40)
            tong += 4;
        if (min >= 40 && min < 50)
            tong += 5;
        if (min >= 50 && min < 60)
            tong += 6;

        Log.d(TAG, "Tong 2 : " + tong);
        return tong % 2 == 0;
    }

    public void setResultArrays(boolean[] arrays) {
        resultArrays = arrays;
    }

    public boolean randomBoolean() {
        return Math.random() < 0.5;
    }
}
