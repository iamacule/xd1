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
    private boolean isNewResult = false;

    private int currentRule;
    private int numberOfRule;
    private int numberOfMainRule;
    private int numberOfRuleOffline;
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
        numberOfMainRule = preferences.getIntValue(PrefValue.NUMBER_OF_MAIN_RULE, PrefValue.DEFAULT_NUMBER_OF_MAIN_RULES);
        numberOfRule = preferences.getIntValue(PrefValue.NUMBER_OF_RULE, PrefValue.DEFAULT_NUMBER_OF_RULES);
        numberOfRuleOffline = preferences.getIntValue(PrefValue.NUMBER_OF_RULE_OFFLINE, PrefValue.DEFAULT_NUMBER_OF_RULES_OFFLINE);
        num1 = preferences.getIntValue(PrefValue.ASSIGN_NUM_1);
        num2 = preferences.getIntValue(PrefValue.ASSIGN_NUM_2);
        num3 = preferences.getIntValue(PrefValue.ASSIGN_NUM_3);
        num4 = preferences.getIntValue(PrefValue.ASSIGN_NUM_4);
    }

    public void setRules(int rule) {
        currentRule = rule;
        preferences.storeValue(PrefValue.RULE, rule);
    }

    public int getCurrentRule() {
        return currentRule;
    }

    public void setNumberOfRule(int num) {
        this.numberOfRule = num;
        preferences.storeValue(PrefValue.NUMBER_OF_RULE, num);
    }

    public void setNumberOfRuleOffLine(int num) {
        this.numberOfRuleOffline = num;
        preferences.storeValue(PrefValue.NUMBER_OF_RULE_OFFLINE, num);
    }

    public void setNumberOfMainRule(int num) {
        this.numberOfMainRule = num;
        preferences.storeValue(PrefValue.NUMBER_OF_MAIN_RULE, num);
    }

    public int getNumberOfMainRule() {
        return numberOfMainRule;
    }

    public int getNumberOfRuleOffline() {
        return numberOfRuleOffline;
    }

    public boolean getResult() {
        Log.d(TAG, "Current rule : " + currentRule);
        if (numberOfRule == 0) {
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
            isNewResult = true;
            return randomBoolean();
        }
    }

    public void minusNumOfRule(byte currentResult) {
        switch (currentResult) {
            case PrefValue.RESULT_RULES:
                Log.d(TAG, "Number of rules : " + numberOfRule);
                if (numberOfRule > 0) {
                    if (isNewResult) {
                        isNewResult = false;
                        numberOfRule = numberOfRule - 1;
                    }
                }
                break;
            case PrefValue.RESULT_OFFLINE:
                Log.d(TAG, "Number of rules offline : " + numberOfRuleOffline);
                if (numberOfRuleOffline > 0) {
                    numberOfRuleOffline = numberOfRuleOffline - 1;
                }
                break;

            default:
                Log.d(TAG, "Number of main rules : " + numberOfMainRule);
                if (numberOfMainRule > 0) {
                    numberOfMainRule = numberOfMainRule - 1;
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

    /**
     * Rule 2
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
     * (So1 x 2) + So2 + So3 + (So4 x2) + Don vi thoi gian (0->6)
     *
     * @return
     */
    private boolean getRule2() {
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

    /**
     * Rule Offline
     * Trai qua phai - Tren xuong Duoi
     * So1 = 0;
     * So2 = 1;
     * So3 = 2;
     * So4 = 3;
     * <p>
     * Cong thuc
     * <p>
     * Chan x1
     * Le x2
     * <p>
     * VD : Chan Le Le Chan
     * (So1) + (So2 x2) + (So3 x2) + (So4)
     *
     * @return
     */
    public boolean getRuleOffline() {
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
            if (!resultArrays[i]) {
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

    public void setAssignNumber(int num1, int num2, int num3, int num4) {
        this.num1 = num1;
        this.num2 = num2;
        this.num3 = num3;
        this.num4 = num4;

        preferences.storeValue(PrefValue.ASSIGN_NUM_1, num1);
        preferences.storeValue(PrefValue.ASSIGN_NUM_2, num2);
        preferences.storeValue(PrefValue.ASSIGN_NUM_3, num3);
        preferences.storeValue(PrefValue.ASSIGN_NUM_4, num4);
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
