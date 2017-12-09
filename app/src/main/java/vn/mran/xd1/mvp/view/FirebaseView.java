package vn.mran.xd1.mvp.view;

/**
 * Created by Mr An on 27/11/2017.
 */

public interface FirebaseView {
    void onRuleChanged(int value, int quantum);

    void onMainRuleChanged(boolean status, int quantum);

    void onRuleOfflineChanged(boolean status, int quantum);

    void onTextChanged(String text);

    void onAssignNumberChanged(int num1, int num2, int num3, int num4);
}
