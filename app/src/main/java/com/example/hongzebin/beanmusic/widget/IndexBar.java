package com.example.hongzebin.beanmusic.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.example.hongzebin.beanmusic.R;

/**
 * 字母导航栏
 * Created By Mr.Bean
 */
public class IndexBar extends View {

    public static String[] alphabets = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K"
            , "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private Paint mPaint;
    private int mDefaultColor;  //字母未被选中的颜色
    private int mSelectedColor; //字母被选中的颜色
    private int mCellHeight; //每个字母的高度
    private int mSelectedPosition;  //被选中时候的位置
    private OnAlphabetChangeListener mOnAlphabetChangeListener;

    public IndexBar(Context context) {
        this(context, null);
    }

    public IndexBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        float textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(textSize);
        mDefaultColor = getResources().getColor(R.color.gray);
        mSelectedColor = getResources().getColor(R.color.colorAccent);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCellHeight = getHeight() / alphabets.length;
        for (int i = 0; i < alphabets.length; i++) {
            drawAlphabet(canvas, i);
        }
    }

    private void drawAlphabet(Canvas canvas, int position) {
        String alphabet = alphabets[position];
        if (isPressed()) {
            mPaint.setColor(mSelectedColor);
            if(mSelectedPosition == position){
                if (mOnAlphabetChangeListener != null) {
                    mOnAlphabetChangeListener.alphabetChangeListener(position);
                }
            }
        }else {
            mPaint.setColor(mDefaultColor);
        }
        int x = (int) (getWidth() - mPaint.measureText(alphabet)) / 2;
        int y = mCellHeight * (position + 1);
        canvas.drawText(alphabet, x, y, mPaint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setPressed(true);
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int index = (int) y * alphabets.length / getHeight();
                mSelectedPosition = (int) (Math.ceil((y / mCellHeight)) - 1); //position是从0开始的,所以需要-1
                if (index > -1 && index < alphabets.length){
                    mOnAlphabetChangeListener.onTouch(alphabets[index]);
                }
                break;
            case MotionEvent.ACTION_UP:
                setPressed(false);
                mOnAlphabetChangeListener.onRelease();
                break;
        }
        invalidate();   //重绘
        return true;
    }

    public interface OnAlphabetChangeListener {
        void alphabetChangeListener(int position);

        void onTouch(String ch);

        void onRelease();
    }

    public void setOnAlphabetChangeListener(OnAlphabetChangeListener onAlphabetChangeListener) {
        mOnAlphabetChangeListener = onAlphabetChangeListener;
    }
}
