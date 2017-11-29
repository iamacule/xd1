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
import vn.mran.xd1.helper.Log;
import vn.mran.xd1.util.ResizeBitmap;

/**
 * Created by Mr An on 28/11/2017.
 */

public class DrawBattle extends View {

    public interface OnDrawBattleUpdate {
        void onTouch();
        void onOpenLid();
    }

    private final String TAG = getClass().getSimpleName();

    private OnDrawBattleUpdate onDrawBattleUpdate;

    private Context context;
    private Bitmap lid;
    private Rect rectLid;
    private int width;
    private int height;

    private Point midPoint;
    private boolean isOpen = false;

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
        this.context = context;
        lid = BitmapFactory.decodeResource(context.getResources(), R.drawable.lid);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (midPoint == null) {
            width = canvas.getWidth();
            height = canvas.getHeight();
            midPoint = new Point(width / 2, height / 2);
        }
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
                onDrawBattleUpdate.onTouch();
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getX() > midPoint.x - lid.getWidth() / 2 &&
                        event.getX() < midPoint.x + lid.getWidth() / 2 &&
                        event.getY() > midPoint.y - lid.getHeight() / 2 &&
                        event.getY() < midPoint.y + lid.getHeight() / 2) {
                    Log.d(TAG, "Move : x,y = " + event.getX());
                    midPoint.x = (int) event.getX();
                    midPoint.y = (int) event.getY();
                    invalidate();

                    if (midPoint.x > width / 2 + (lid.getWidth() / 2) ||
                            midPoint.x < width / 2 - (lid.getWidth() / 2) ||
                            midPoint.y > height / 2 + (lid.getHeight() / 2) ||
                            midPoint.y < height / 2 - (lid.getWidth() / 2) ) {
                        if (!isOpen){
                            isOpen = true;
                            Log.d(TAG,"OPEN");
                            onDrawBattleUpdate.onOpenLid();
                        }
                    }else {
                        isOpen = false;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                break;
        }
        return true;
    }
}
