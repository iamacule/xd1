package vn.mran.xd1.mvp.view;

/**
 * Created by Mr An on 27/11/2017.
 */

public interface FirebaseView {
    void onRuleChanged(String value);

    void onNumOfRuleChanged(int numberOfRule);

    void onMainRuleChanged(String value,boolean enable);

    void onNumOfMainRuleChanged(int numberOfRule);
}
