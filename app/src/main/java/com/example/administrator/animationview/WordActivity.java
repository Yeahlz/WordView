package com.example.administrator.animationview;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Learning_Yeachlz} on 2018-03-15.
 */

public class WordActivity extends Activity {
    private WordView worldRelativeLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

          SeekBar seekBar = (SeekBar)findViewById(R.id.seekbar);
          seekBar.setMax(46265);

        worldRelativeLayout = (WordView) findViewById(R.id.wordview);

        List<String> mWordList = new ArrayList<>();
        mWordList.add("许嵩"); //790
        mWordList.add("半城烟沙"); //4200
        mWordList.add("词/曲/制作人/演唱：许嵩"); //6420
        mWordList.add("和声编写/和声：许嵩");  //8620
        mWordList.add("录音/混音：许嵩");    //10960
        mWordList.add("有些爱像断线纸鸢 结局悲余手中线"); //12240
        mWordList.add("有些恨像是一个圈 冤冤相报不了结");  //14570
        mWordList.add("只为了完成一个夙愿 还将付出几多鲜血"); //16940
        mWordList.add("忠义之言 自欺欺人的谎言"); //18990
        mWordList.add("有些情入苦难回绵 窗间月夕夕成玦"); //21270
        mWordList.add("有些仇心藏却无言 腹化风雪为刀剑"); //24550
        mWordList.add("只为了完成一个夙愿 荒乱中邪正如何辨"); //28970
        mWordList.add("飞沙狼烟将乱我 徒有悲添"); //31941
        mWordList.add("半城烟沙 兵临池下");  //32337
        mWordList.add("金戈铁马 替谁争天下"); //34753
        mWordList.add("一将成 万骨枯 多少白发送走黑发"); //36587
        mWordList.add("半城烟沙 随风而下");  //39998
        mWordList.add("手中还有 一缕牵挂");  //40261
        mWordList.add("只盼归田卸甲 还能捧回你沏的茶 "); //43261



        List<Integer> mTimeList = new ArrayList<>();
        mTimeList.add(790);
        mTimeList.add(4200);
        mTimeList.add(6420);
        mTimeList.add(8620);
        mTimeList.add(10960);
        mTimeList.add(12240);
        mTimeList.add(14570);
        mTimeList.add(16940);
        mTimeList.add(18990);
        mTimeList.add(21270);
        mTimeList.add(24550);
        mTimeList.add(28970);
        mTimeList.add(31941);
        mTimeList.add(32337);
        mTimeList.add(34753);
        mTimeList.add(36587);
        mTimeList.add(39998);
        mTimeList.add(43261);
        mTimeList.add(46261);

        worldRelativeLayout.setList(mWordList, mTimeList);
        worldRelativeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClickListener(int time) {
                Log.d("ppp","点击");
                Toast.makeText(WordActivity.this,time+":",Toast.LENGTH_SHORT).show();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();       // 获取当前进度
                worldRelativeLayout.setChangeTime(progress);
            }
        });

    }
}

