package vn.mran.xd1.mvp.presenter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.mran.xd1.helper.Log;
import vn.mran.xd1.mvp.view.FirebaseView;

/**
 * Created by Mr An on 27/11/2017.
 */

public class FirebasePresenter {
    private final String TAG = getClass().getSimpleName();
    private FirebaseView firebaseView;

    public FirebasePresenter(FirebaseView view) {
        firebaseView = view;
        FirebaseDatabase.getInstance().getReference("message").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Receive : " + data);
                Log.d(TAG, "Convert rule: " + data.split(" ")[0]);
                Log.d(TAG, "Convert number: " + data.split(" ")[1]);

                if (data.substring(0, 1).equals("R")) {
                    firebaseView.onRuleChanged(data.split(" ")[0]);
                    firebaseView.onNumOfRuleChanged(Integer.parseInt(data.split(" ")[1]));
                } else {
                    if (data.split(" ")[1].equals("true")) {
                        firebaseView.onMainRuleChanged(data.split(" ")[0], true);
                        firebaseView.onNumOfMainRuleChanged(Integer.parseInt(data.split(" ")[2]));
                    } else {
                        firebaseView.onMainRuleChanged(data.split(" ")[0], false);
                        firebaseView.onNumOfMainRuleChanged(0);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
    }
}
