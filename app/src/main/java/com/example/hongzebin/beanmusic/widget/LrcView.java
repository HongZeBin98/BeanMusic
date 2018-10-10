package com.example.hongzebin.beanmusic.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.hongzebin.beanmusic.R;
import com.example.hongzebin.beanmusic.music.bean.LrcBean;

import java.util.List;

import static com.example.hongzebin.beanmusic.util.Constant.DISPLAY_MODE_NORMAL;
import static com.example.hongzebin.beanmusic.util.Constant.DISPLAY_MODE_SEEK;

public class LrcView extends View implements ILrcView {

    /**
     * 歌词拖动时候的监听类
     */
    public interface ILrcViewListener {
        /**
         * 当歌词被用户上下拖动的时候回调该方法
         */
        void onLrcSeek(int newPosition, LrcBean lrcBean);
    }

    private Paint mPaint;
    private List<LrcBean> mLrcBeans;
    private ILrcViewListener mLrcViewListener;
    //歌词的当前展示模式
    private int mDisplayMode;
    //最小移动的距离，当拖动歌词时如果小于该距离不做处理
    private int mMinSeekOffset;
    //当前高亮歌词的行数
    private int mHighLightRow;
    //当前高亮歌词的字体颜色
    private int mHighLightRowColor;
    //不高亮歌词的字体颜色为白色
    private int mNormalRowColor;
    //歌词字体大小默认值
    private int mLrcFontSize;
    //歌词字体大小高光值
    private int mLrcHighLightFontSize;
    //两行歌词之间的间距
    private int mPaddingY;
    //高光歌词上下歌词的间距
    private int mHighLightPaddingY;
    //当没有歌词的时候展示的内容
    private String mLoadingLrcTip;
    //手指第一次按下时的位置
    private float mLastMotionY;

    public LrcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHighLightRowColor = getResources().getColor(R.color.colorAccent);
        mNormalRowColor = getResources().getColor(R.color.white);
        mMinSeekOffset = 10;
        mHighLightRow = 0;
        mLrcFontSize = 65;
        mPaddingY = 50;
        mLrcHighLightFontSize = 90;
        mHighLightPaddingY = 100;
        mDisplayMode = DISPLAY_MODE_NORMAL;
        mLoadingLrcTip = "Downloading lrc fail";
        mPaint.setTextSize(mLrcFontSize);
    }

    @Override
    public void setLrc(List<LrcBean> lrcBeans) {
        mLrcBeans = lrcBeans;
        mHighLightRow = 0;
        invalidate();
    }

    @Override
    public void seekLrcToTime(long time) {
        if (mLrcBeans == null || mLrcBeans.size() == 0) {
            return;
        }
        if (mDisplayMode != DISPLAY_MODE_NORMAL) {
            return;
        }
        for (int i = 0; i < mLrcBeans.size(); i++) {
            LrcBean current = mLrcBeans.get(i);
            LrcBean next = i + 1 == mLrcBeans.size() ? null : mLrcBeans.get(i + 1);
            //正在播放的时间大于current行的歌词的时间而小于next行歌词的时间， 设置要高亮的行为current行
            //正在播放的时间大于current行的歌词，而current为最后一句歌词时，设置要高亮的行为current行
            if ((time >= current.getTime() && next != null && time < next.getTime())
                    || (time > current.getTime() && next == null)) {
                seekLrc(i, false);
                return;
            }
        }
    }

    @Override
    public void setListener(ILrcViewListener listener) {
        mLrcViewListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int height = getHeight();
        final int width = getWidth();
        //当没有歌词的时候
        if (mLrcBeans == null || mLrcBeans.size() == 0) {
            if (mLoadingLrcTip != null) {
                mPaint.setColor(mHighLightRowColor);
                mPaint.setTextSize(100);
                mPaint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText(mLoadingLrcTip, width / 2, height / 2 - mLrcFontSize, mPaint);
            }
            return;
        }
        int rowY;
        final int rowX = width / 2;
        int rowNum;
        //高亮地画出正在要高亮的的那句歌词
        String highlightText = mLrcBeans.get(mHighLightRow).getContent();
        int highlightRowY = height / 2 - mLrcFontSize;
        mPaint.setColor(mHighLightRowColor);
        mPaint.setTextSize(mLrcHighLightFontSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(highlightText, rowX, highlightRowY, mPaint);
        //画出正在播放的那句歌词的上面可以展示出来的歌词
        mPaint.setColor(mNormalRowColor);
        mPaint.setTextSize(mLrcFontSize);
        mPaint.setTextAlign(Paint.Align.CENTER);
        rowNum = mHighLightRow - 1;
        rowY = highlightRowY - mHighLightPaddingY - mLrcFontSize;
        while (rowY > -mLrcFontSize && rowNum >= 0) {
            String text = mLrcBeans.get(rowNum).getContent();
            canvas.drawText(text, rowX, rowY, mPaint);
            rowY -= (mPaddingY + mLrcFontSize);
            rowNum--;
        }
        //画出正在播放的那句歌词的下面的可以展示出来的歌词
        rowNum = mHighLightRow + 1;
        rowY = highlightRowY + mHighLightPaddingY + mLrcFontSize;
        while (rowY < height && rowNum < mLrcBeans.size()) {
            String text = mLrcBeans.get(rowNum).getContent();
            canvas.drawText(text, rowX, rowY, mPaint);
            rowY += (mPaddingY + mLrcFontSize);
            rowNum++;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mLrcBeans == null || mLrcBeans.size() == 0) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
            //手指按下
            case MotionEvent.ACTION_DOWN:
                mLastMotionY = event.getY();
                invalidate();
                break;
            //手指移动
            case MotionEvent.ACTION_MOVE:
                //如果一个手指按下，在屏幕上移动的话，拖动歌词上下
                doSeek(event);
                break;
            case MotionEvent.ACTION_CANCEL:
                //手指抬起
            case MotionEvent.ACTION_UP:
                if (mDisplayMode == DISPLAY_MODE_SEEK) {
                    //高亮手指抬起时的歌词并播放从该句歌词开始播放
                    seekLrc(mHighLightRow, true);
                }
                mDisplayMode = DISPLAY_MODE_NORMAL;
                invalidate();
                break;
        }
        return true;
    }

    /**
     * 设置要高亮的歌词为第几行歌词
     *
     * @param position 要高亮的歌词行数
     * @param cb       是否是手指拖动后要高亮的歌词
     */
    public void seekLrc(int position, boolean cb) {
        if (mLrcBeans == null || position < 0 || position > mLrcBeans.size()) {
            return;
        }
        LrcBean lrcBean = mLrcBeans.get(position);
        mHighLightRow = position;
        invalidate();
        //如果是手指拖动歌词后
        if (mLrcViewListener != null && cb) {
            //回调onLrcSeek方法，将音乐播放器播放的位置移动到高亮歌词的位置
            mLrcViewListener.onLrcSeek(position, lrcBean);
        }
    }

    /**
     * 处理单指在屏幕移动时，歌词上下滚动
     */
    private void doSeek(MotionEvent event) {
        float y = event.getY();//手指当前位置的y坐标
        float offsetY = y - mLastMotionY; //第一次按下的y坐标和目前移动手指位置的y坐标之差
        //如果移动距离小于10，不做任何处理
        if (Math.abs(offsetY) < mMinSeekOffset) {
            return;
        }
        //将模式设置为拖动歌词模式
        mDisplayMode = DISPLAY_MODE_SEEK;
        int rowOffset = Math.abs((int) offsetY / mLrcFontSize); //歌词要滚动的行数
        if (offsetY < 0) {
            //手指向上移动，歌词向下滚动
            mHighLightRow += rowOffset;//设置要高亮的歌词为 当前高亮歌词 向下滚动rowOffset行后的歌词
        } else if (offsetY > 0) {
            //手指向下移动，歌词向上滚动
            mHighLightRow -= rowOffset;//设置要高亮的歌词为 当前高亮歌词 向上滚动rowOffset行后的歌词
        }
        //设置要高亮的歌词为0和mHighLightRow中的较大值，即如果mHighLightRow < 0，mHighLightRow=0
        mHighLightRow = Math.max(0, mHighLightRow);
        //设置要高亮的歌词为0和mHighLightRow中的较小值，即如果mHighLightRow > mLrcBeans.size()-1，mHighLightRow=mLrcRows.size()-1
        mHighLightRow = Math.min(mHighLightRow, mLrcBeans.size() - 1);
        //如果歌词要滚动的行数大于0，则重画LrcView
        if (rowOffset > 0) {
            mLastMotionY = y;
            invalidate();
        }
    }
}
