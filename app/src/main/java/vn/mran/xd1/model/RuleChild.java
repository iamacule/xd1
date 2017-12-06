package vn.mran.xd1.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Mr An on 06/12/2017.
 */

@IgnoreExtraProperties
public class RuleChild {
    public String quantum;
    public String rule;

    public RuleChild() {
    }

    public RuleChild(String quantum, String rule) {
        this.quantum = quantum;
        this.rule = rule;
    }
}
