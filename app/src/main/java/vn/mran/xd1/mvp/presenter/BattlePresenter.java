package vn.mran.xd1.mvp.presenter;

import vn.mran.xd1.helper.Log;
import vn.mran.xd1.instance.Rules;
import vn.mran.xd1.mvp.view.BattleView;

/**
 * Created by Mr An on 29/11/2017.
 */

public class BattlePresenter {
    private final String TAG = getClass().getSimpleName();

    private BattleView view;

    public BattlePresenter(BattleView view) {
        this.view = view;
        view.setImage(new boolean[]{Rules.getInstance().randomBoolean(),
                Rules.getInstance().randomBoolean(),
                Rules.getInstance().randomBoolean(),
                Rules.getInstance().randomBoolean()});
    }

    public void getResult() {
        boolean b = Rules.getInstance().getResult();
        boolean[] resultArrays;
        while (true) {
            resultArrays = (new boolean[]{Rules.getInstance().randomBoolean(),
                    Rules.getInstance().randomBoolean(),
                    Rules.getInstance().randomBoolean(),
                    Rules.getInstance().randomBoolean()});

            int tong = 0;
            for (boolean b2 : resultArrays) {
                Log.d(TAG, "" + b2);
                if (b2)
                    tong += 2;
                else
                    tong += 1;
            }
            if (b) {

                if (tong % 2 == 0)
                    break;
            } else {

                if (tong % 2 != 0)
                    break;
            }
        }
        view.setImage(resultArrays);
    }
}
