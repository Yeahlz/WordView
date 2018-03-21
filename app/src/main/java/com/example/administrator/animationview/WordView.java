package com.example.administrator.animationview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Created by ${Learning_Yeachlz} on 2018-03-20.
 */

public class WordView extends RelativeLayout {
    private TextView mTvTime;
    private RelativeLayout view ;
    private View mDivderLine;
    private AutoPullRecyclerView autoPullRecyclerView;

    private AutoPullAdapter autoPullAdapter;
    private LinearLayoutManager linearLayoutManager ;

    private List<Integer> mTimeList ;
    private List<String> mWordList ;

    private Context context;

    private boolean show = false;
    private int mCurrentTime;

    private com.example.administrator.animationview.OnClickListener onClickListener;

    /** 初始化类
     * @param context
     */
    public WordView(Context context) {
        super(context);
        this.context = context;
    }

    public WordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public WordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    /** 歌词点击事件设置
     * @param onClickListener
     */
    public void setOnClickListener(com.example.administrator.animationview.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    /**初始化内容
     * @param context
     */
    private void initView(Context context) {
         View.inflate(context, R.layout.activity_atv_word_view, WordView.this);
          autoPullRecyclerView = (AutoPullRecyclerView) this.findViewById(R.id.auto_word);

          view = (RelativeLayout) this.findViewById(R.id.divide_line);
          view.setVisibility(GONE);

          mTvTime = (TextView) this.findViewById(R.id.time1);
          mDivderLine = (View) this.findViewById(R.id.divide_line1);


        List<String> wordList = new ArrayList<>();
        wordList.add("");
        wordList.add("");
        wordList.add("");
        wordList.add("");
        wordList.addAll(mWordList);
        wordList.add("");
        wordList.add("");
        wordList.add("");
        wordList.add("");


        autoPullRecyclerView.setTimeList(mTimeList, mWordList.size());
        autoPullAdapter = new AutoPullAdapter(context,wordList);
        linearLayoutManager = new LinearLayoutManager(context);
        autoPullRecyclerView.setLayoutManager(linearLayoutManager);
        autoPullRecyclerView.setAdapter(autoPullAdapter);
        autoPullRecyclerView.start();

        autoPullRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (show) {
                    showTime();
                }
            }
        });
    }


    /**
     * 简单加多几行空格让歌词中间显示
     * @return
     */


    /**
     *  根据位移显示时间
     */
    private void showTime(){
        int height = autoPullRecyclerView.getMeasuredHeight() / 9;
        int top = autoPullRecyclerView.getChildAt(1).getTop();
        int currentPosition = linearLayoutManager.findFirstVisibleItemPosition();
        int position;
        if (top > height / 2) {
            position = currentPosition;
        } else {
            position = currentPosition + 1;
        }
        String Minute = null;
        String Second = null;
        int minute = mTimeList.get(position) / 1000 / 60;      // 转换时间格式
        int second = mTimeList.get(position) / 1000 % 60;
        mCurrentTime = mTimeList.get(position);
        if (minute < 10) {
            Minute = "0" + minute;
        } else {
            Minute = minute + "";
        }
        if (second < 10) {
            Second = "0" + second;
        } else {
            Second = second + "";
        }
        mTvTime.setText(Minute + ":" + Second);
    }





    /**
     * 手指离开过一段时间消失
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            view.setVisibility(GONE);
            show = false;
        }
    };

    /**
     * 设置 歌词列表和相应的时间列表
     * @param mWordList
     * @param mTimeList
     */
    public void setList(List<String> mWordList,List<Integer> mTimeList){
        this.mWordList = mWordList;
        this.mTimeList = mTimeList;
        initView(context);
    }

    /** 设置控件背景
     * @param drawable
     */
    public void setBackGround(Drawable drawable){
        try {
            autoPullRecyclerView.setBackground(drawable);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /** 设置 歌词高亮颜色
     * @param color
     */
    public void setHighLightColor(String color){
        autoPullAdapter.setHightLightColor(color);
    }

    /** 设置普通歌词颜色
     * @param color
     */
    public void setOrdinaryColor(String color){
        autoPullAdapter.setmOrdinaryColor(color);
    }

    /** 设置 高亮字体大小
     * @param size
     */
    public void setHighLightSize(int size){
        autoPullAdapter.setmHighLightSize(size);
    }

    /**设置 普通字体大小
     * @param size
     */
    public void setOrdinarySize(int size){
        autoPullAdapter.setmOrdinarySize(size);
    }

    /** 设置中间线颜色
     * @param color
     */
    public void setDivderLineColor(String color){
        try {
            mDivderLine.setBackgroundColor(Color.parseColor(color));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /** 输入时间歌词做出相应改变
     * @param time
     */
    public void setChangeTime(int time){
        autoPullRecyclerView.setChangeTime(time);
    }

    /** 设置显示时间颜色
     * @param color
     */
    public void setTimeColor(String color){
        try {
            mTvTime.setTextColor(Color.parseColor(color));
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    /** 事件分发机制
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                performClick();
                view.setVisibility(VISIBLE);
                show = true;
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        autoPullRecyclerView.setComeToPlay();
                        onClickListener.onClickListener(mCurrentTime);
                    }
                });
                break;
            case MotionEvent.ACTION_UP:
                view.removeCallbacks(runnable);
                view.postDelayed(runnable,4000);
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}

