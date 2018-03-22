# WordView
一个基于 recyclerView 实现的自定义歌词控件 
在使用时只需要使用 wordView.setList(wordList,timeList) 即可使用。
结合 seekBar 需要使用 setChangeTime(int time) 方法设置时间来使控件移动。
使用 setOnclickListener() 监听点击歌词返回的时间，从而调用 seekBar.

示例：
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
