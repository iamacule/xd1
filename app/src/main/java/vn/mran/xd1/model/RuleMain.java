package vn.mran.xd1.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Mr An on 06/12/2017.
 */

@IgnoreExtraProperties
public class RuleMain {
    public static final String ON = "on";
    public static final String OFF = "off";


    public String quantum;
    public String status = OFF;

    public RuleMain() {
    }

    public RuleMain(String quantum, String status) {
        this.quantum = quantum;
        this.status = status;
    }
}
