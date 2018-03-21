package com.example.administrator.animationview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;
import java.util.List;

/** 自动滑动的 recyclerView
 * Created by ${Learning_Yeachlz} on 2018-03-15.
 */

public class AutoPullRecyclerView extends RecyclerView {

    private List<Integer> timeList ;

    private static WeakReference<AutoPullRecyclerView> weakReference;
    private AutoPullWork autoPullWork;
    private AutoBackWork autoBackWork;

    private int currentWord = 4 ;
    private static int height;
    private static int type = 0;
    private int wordLength;
    private int lastWord ;

    private boolean isDownAndMove  = true;
    private boolean comeToPlay = false;
    private boolean running = true;
    private boolean canRun = true;


    private Context context ;


    public AutoPullRecyclerView(Context context) {
        super(context);
        this.context = context;
        autoPullWork = new AutoPullWork(this);
        autoBackWork =  new AutoBackWork();
    }



    public AutoPullRecyclerView(Context context, @Nullable AttributeSet attrs) throws NoSuchFieldException, IllegalAccessException {
        super(context, attrs);
        this.context = context;
        autoPullWork = new AutoPullWork(this);
        autoBackWork =  new AutoBackWork();
    }


    public AutoPullRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        autoPullWork = new AutoPullWork(this);
        autoBackWork =  new AutoBackWork();
    }


    /**
     *  歌词自动滑动到特定位置任务
     */
    private static class AutoBackWork implements Runnable{

        @Override
        public void run() {

            AutoPullRecyclerView autoPullRecyclerView = weakReference.get();
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) autoPullRecyclerView.getLayoutManager();
            int firtPosition = linearLayoutManager.findFirstVisibleItemPosition();
            int lastPosition = linearLayoutManager.findLastVisibleItemPosition();

            if (firtPosition>autoPullRecyclerView.currentWord){
                autoPullRecyclerView.smoothScrollBy(0, -(firtPosition - autoPullRecyclerView.currentWord + 5) * height);
                Log.d("kkk2","滑动1");
            }else if(firtPosition+9>autoPullRecyclerView.currentWord){
                if (firtPosition+3>autoPullRecyclerView.currentWord){
                    int  top = autoPullRecyclerView.getChildAt(autoPullRecyclerView.currentWord-firtPosition).getTop();
                    autoPullRecyclerView.smoothScrollBy(0, -(4*height-top)); //--
                    Log.d("kkk2","滑动2");
                }else{
                    int  top = autoPullRecyclerView.getChildAt(autoPullRecyclerView.currentWord-firtPosition).getTop();
                    autoPullRecyclerView.smoothScrollBy(0,top-(4*height)); //++
                    Log.d("kkk2","滑动3");
                }
            }else {
                autoPullRecyclerView.smoothScrollBy(0, (autoPullRecyclerView.currentWord - lastPosition + 5) * height);
                Log.d("kkk2","滑动4");
            }

            if (type==2){
                autoPullRecyclerView.currentWord = autoPullRecyclerView.currentWord+1;
            }
            if (type!=2) {
                type = 1;
            }
        }


    }


    /**
     *  歌词按照时间自动滑动
     */
    private static class AutoPullWork implements Runnable {
        public AutoPullWork(AutoPullRecyclerView autoPullRecyclerView) {
            weakReference = new WeakReference<AutoPullRecyclerView>(autoPullRecyclerView);
        }
        @Override
        public void run() {
            AutoPullRecyclerView autoPullRecyclerView = weakReference.get();
            if (autoPullRecyclerView.currentWord + 1 < autoPullRecyclerView.wordLength+6||type==3||type==2) {
                Log.d("uuu",autoPullRecyclerView.currentWord+":"+autoPullRecyclerView.wordLength);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager)autoPullRecyclerView.getLayoutManager();
                int firtPosition = linearLayoutManager.findFirstVisibleItemPosition();
                int top = autoPullRecyclerView.getChildAt(0).getTop();
                if (type==1||type==3||type==2) {
                    if (autoPullRecyclerView != null && autoPullRecyclerView.running && autoPullRecyclerView.canRun) {
                        height = autoPullRecyclerView.getMeasuredHeight() / 9;
                        Log.d("ooo", height + "");
                        AutoPullAdapter autoPullAdapter = (AutoPullAdapter) autoPullRecyclerView.getAdapter();

                        if (type==1) {
                            autoPullRecyclerView.smoothScrollBy(0, autoPullRecyclerView.getMeasuredHeight() / 9);
                            Log.d("mmm","type==1&&!autoPullRecyclerView.comeToPlay");
                        }

                        if (type==3&&autoPullRecyclerView.comeToPlay){
                            type = 1;
                            if (-top>height/2){
                                autoPullAdapter.changeToHighLight(autoPullRecyclerView.lastWord,firtPosition+5);
                                autoPullRecyclerView.currentWord = firtPosition+5;
                            }else {
                                autoPullAdapter.changeToHighLight(autoPullRecyclerView.lastWord,firtPosition+4);
                                autoPullRecyclerView.currentWord = firtPosition+4;
                            }
                            autoPullRecyclerView.comeToPlay = false;
                            Log.d("mmm1",type+"这里3");
                        }else if (type==2){
                            autoPullAdapter.changeToHighLight(autoPullRecyclerView.lastWord,autoPullRecyclerView.currentWord);
                            type=1;
                            Log.d("mmm1",type+"这里2");
                        }else {
                            autoPullAdapter.changeToHighLight(autoPullRecyclerView.currentWord-1,autoPullRecyclerView.currentWord);
                            Log.d("mmm1",type+"这里1");
                        }


                        autoPullRecyclerView.currentWord = autoPullRecyclerView.currentWord + 1;
                           Log.d("mmm","putong");

                        if(autoPullRecyclerView.currentWord>=autoPullRecyclerView.wordLength+4){
                            autoPullRecyclerView.postDelayed(autoPullRecyclerView.autoPullWork,2000);
                            Log.d("uuu",autoPullRecyclerView.currentWord+"");
                        }else {
                            autoPullRecyclerView.postDelayed(autoPullRecyclerView.autoPullWork, autoPullRecyclerView.timeList.get(autoPullRecyclerView.currentWord - 4) - autoPullRecyclerView.timeList.get(autoPullRecyclerView.currentWord - 5));

                            Log.d("uuu", autoPullRecyclerView.timeList.get(autoPullRecyclerView.currentWord - 4) + ":" + autoPullRecyclerView.timeList.get(autoPullRecyclerView.currentWord - 5) + "");
                            int a = autoPullRecyclerView.currentWord - 3;
                            int b = autoPullRecyclerView.currentWord - 4;

                            Log.d("uuu", autoPullRecyclerView.timeList.size() + ":" + a + ":" + b);
                        }
                    }
                }
            }
        }
    }


    /**
     *  开始滑动
     */
    public void start(){
        if (running){
            stop();
        }
        canRun =true;
        running = true;
        type = 1;
        postDelayed(autoPullWork,timeList.get(0));
    }

    public void stop(){
        running = false;
        removeCallbacks(autoPullWork);
    }



    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (isDownAndMove) {
                    type=3;
                    isDownAndMove = false;
                    Log.d("kkkk","recyclerview+down");
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d("kkkk","recyclerview+up");
                removeCallbacks(autoBackWork);
                postDelayed(autoBackWork,4000);
                isDownAndMove = true;
                break;
            default:
                break;
        }
        return super.onTouchEvent(e);
    }

    /**
     *  点击歌词滑动
     */
    public void setComeToPlay(){
        type =3;
        comeToPlay = true;
        lastWord = currentWord-1;
        removeCallbacks(autoPullWork);
        post(autoPullWork);
    }

    /** 设置歌词时间相应歌词滑动
     * @param time
     */
    public void setChangeTime(int time){
        type =2;
        if (time<=timeList.get(0)){
            removeCallbacks(autoPullWork);
            removeCallbacks(autoBackWork);
            lastWord = currentWord;
            currentWord = 3;
            post(autoBackWork);
            postDelayed(autoPullWork,timeList.get(0)-time);
        }else if (time>=timeList.get(timeList.size()-1)){
            removeCallbacks(autoPullWork);
            removeCallbacks(autoBackWork);
            lastWord = currentWord;
            currentWord = wordLength+3;
            post(autoPullWork);
            postDelayed(autoBackWork,2000);
        }else {
            removeCallbacks(autoPullWork);
            removeCallbacks(autoBackWork);
            int position = 0;
            for (int i=0;i<timeList.size()-1;i++){
                if (time>timeList.get(i)&&time<timeList.get(i+1)){
                    position =i;
                    break;
                }
            }
            int a = timeList.get(currentWord-3)-time;
            lastWord = currentWord-1;
            currentWord = position+4;
            post(autoBackWork);
            postDelayed(autoPullWork,timeList.get(currentWord-3)-time);
        }
    }

    /** 设置 歌词和时间列表
     * @param timeList
     * @param
     */
    public void setTimeList(List<Integer> timeList,int wordLength){
        this.timeList = timeList;
        this.wordLength = wordLength;
    }

}


