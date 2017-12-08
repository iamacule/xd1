package vn.mran.xd1.mvp.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import vn.mran.xd1.constant.PrefValue;
import vn.mran.xd1.helper.Log;
import vn.mran.xd1.instance.Rules;
import vn.mran.xd1.mvp.view.BattleView;

/**
 * Created by Mr An on 29/11/2017.
 */

public class BattlePresenter {
    private final String TAG = getClass().getSimpleName();

    private Context context;

    private BattleView view;

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private Future future;

    private NetworkCheckingRunable networkCheckingRunable;

    private boolean isNetworkEnable = false;

    private boolean run = true;

    public BattlePresenter(BattleView view) {
        this.context = (Context) view;
        this.view = view;
        isNetworkEnable = isOnline();
        executor = Executors.newFixedThreadPool(1);
        networkCheckingRunable = new NetworkCheckingRunable();
        future = executor.submit(networkCheckingRunable);
    }

    public void stopCheckingNetwork() {
        run = false;
        if (future != null && !future.isDone()) {
            future.cancel(true);
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(1000, TimeUnit.MILLISECONDS)) {
                }
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public void getResult(byte result) {
        boolean b;
        switch (result) {
            case PrefValue.RESULT_RULES:
                b = Rules.getInstance().getResult();
                break;
            case PrefValue.RESULT_OFFLINE:
                b = Rules.getInstance().getRuleOffline();
                break;
            default:
                b = (result == PrefValue.RESULT_CHAN);
                break;
        }
        boolean[] resultArrays;
        while (true) {
            resultArrays = (new boolean[]{Rules.getInstance().randomBoolean(),
                    Rules.getInstance().randomBoolean(),
                    Rules.getInstance().randomBoolean(),
                    Rules.getInstance().randomBoolean()});

            int tong = 0;
            for (boolean b2 : resultArrays) {
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
        view.setImage(resultArrays, b);
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private class NetworkCheckingRunable implements Runnable {
        @Override
        public void run() {
            while (run && Thread.currentThread().isAlive()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isOnline()) {
                    if (!isNetworkEnable) {
                        isNetworkEnable = true;
                        view.onNetworkChanged(isNetworkEnable);
                    }
                } else {
                    if (isNetworkEnable) {
                        isNetworkEnable = false;
                        view.onNetworkChanged(isNetworkEnable);
                    }
                }
            }
        }
    }
}
