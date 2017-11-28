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
                firebaseView.onDataChange(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });
    }
}
