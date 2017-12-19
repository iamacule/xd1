package vn.mran.xd1.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import vn.mran.xd1.R;
import vn.mran.xd1.util.ResizeBitmap;
import vn.mran.xd1.util.Task;

/**
 * Created by Mr An on 28/11/2017.
 */

public class DrawBattle extends View {

    public interface OnDrawBattleUpdate {
        void onTouch();

        void onLidChanged(boolean isOpened);

        void onResultUpdate(byte result);
    }

    private final String TAG = getClass().getSimpleName();

    private OnDrawBattleUpdate onDrawBattleUpdate;

    private Bitmap lid;

    private Rect rectLid;
    private int width;
    private int height;

    private Point midPoint;
    private boolean isLidOpened = false;

    public DrawBattle(Context context) {
        super(context);
        init(context);
    }

    public DrawBattle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setOnDrawBattleUpdate(OnDrawBattleUpdate onDrawBattleUpdate) {
        this.onDrawBattleUpdate = onDrawBattleUpdate;
    }

    private void init(Context context) {
        lid = BitmapFactory.decodeResource(context.getResources(), R.drawable.lid);
        setFocusable(false);
        setFocusableInTouchMode(false);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
        midPoint = new Point(width / 2, height / 2);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Draw
        rectLid = new Rect(midPoint.x - lid.getWidth() / 2, midPoint.y - lid.getHeight() / 2, midPoint.x + lid.getWidth() / 2, midPoint.y + lid.getHeight() / 2);
        canvas.drawBitmap(lid, null, rectLid, null);
    }

    public void setLidSize(int w) {
        lid = ResizeBitmap.resize(lid, w);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (onDrawBattleUpdate != null)
                    onDrawBattleUpdate.onTouch();
                break;
        }
        return false;
    }

    public void closeLid(final byte result) {
        for (int i = 0 - lid.getHeight() / 2; i <= height / 2; i += 3) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            midPoint.y = i;
            postInvalidate();
            if (i >= (height / 2) - 3) {
                isLidOpened = false;
                Task.runOnUIThread(new Runnable() {
                    @Override
                    public void run() {
                        onDrawBattleUpdate.onResultUpdate(result);
                        onDrawBattleUpdate.onLidChanged(isLidOpened);
                    }
                });
            }
        }
    }

    public void openLid() {
        isLidOpened = true;
        Task.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                onDrawBattleUpdate.onLidChanged(isLidOpened);
            }
        });
        for (int i = height / 2; i >= 0 - lid.getHeight() / 2; i -= 3) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            midPoint.y = i;
            postInvalidate();
        }
    }
}
