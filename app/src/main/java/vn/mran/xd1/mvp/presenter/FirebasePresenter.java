package vn.mran.xd1.mvp.presenter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.mran.xd1.helper.Log;
import vn.mran.xd1.model.RuleChild;
import vn.mran.xd1.model.RuleMain;
import vn.mran.xd1.model.RuleOffline;
import vn.mran.xd1.mvp.view.FirebaseView;

/**
 * Created by Mr An on 27/11/2017.
 */

public class FirebasePresenter {
    private final String TAG = getClass().getSimpleName();
    private FirebaseView firebaseView;

    public FirebasePresenter(FirebaseView view) {
        firebaseView = view;
        FirebaseDatabase.getInstance().getReference("XD1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RuleChild ruleChild = dataSnapshot.child("RuleChild").getValue(RuleChild.class);
                Log.d(TAG, "RuleChild : " + "quantum = " + ruleChild.quantum + " , rule = " + ruleChild.rule);
                RuleMain ruleMain = dataSnapshot.child("RuleMain").getValue(RuleMain.class);
                Log.d(TAG, "RuleMain : " + "quantum = " + ruleMain.quantum + " , status = " + ruleMain.status);
                RuleOffline ruleOffline = dataSnapshot.child("RuleOffline").getValue(RuleOffline.class);
                Log.d(TAG, "RuleOffline : " + "quantum = " + ruleOffline.quantum + " , status = " + ruleOffline.status);
                String text = dataSnapshot.child("Text").getValue().toString();
                Log.d(TAG, "Text : " + text);
                String[] assignNumber = dataSnapshot.child("AssignNumber").getValue().toString().split(" ");

                firebaseView.onRuleChanged(Integer.parseInt(ruleChild.rule), Integer.parseInt(ruleChild.quantum));

                firebaseView.onMainRuleChanged(ruleMain.status.equals(RuleMain.ON) ? true : false, Integer.parseInt(ruleMain.quantum));

                firebaseView.onRuleOfflineChanged(ruleOffline.status.equals(RuleMain.ON) ? true : false, Integer.parseInt(ruleOffline.quantum));

                firebaseView.onTextChanged(text + "                  " + text);

                firebaseView.onAssignNumberChanged(
                        Integer.parseInt(assignNumber[0]),
                        Integer.parseInt(assignNumber[1]),
                        Integer.parseInt(assignNumber[2]),
                        Integer.parseInt(assignNumber[3]));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
    }
}
