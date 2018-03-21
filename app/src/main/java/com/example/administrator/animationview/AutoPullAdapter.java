package com.example.administrator.animationview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/** 歌词适配器
 * Created by ${Learning_Yeachlz} on 2018-03-15.
 */

public class AutoPullAdapter extends RecyclerView.Adapter<AutoPullAdapter.ViewHolder> {
    private List<String> mWordList ;
    private Context mContext;
    private ViewGroup group ;
    private int mHighLightPosition;
    private String mOrdinaryColor = "#696969";
    private int mOrdinarySize = 14;
    private int mHighLightSize = 16;
    private String mHighLightColor = "#FF3030";

    public AutoPullAdapter(Context context,List<String> wordList){
            mWordList = wordList;
            mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        group = parent;
         View view = LayoutInflater.from(mContext).inflate(R.layout.item_ry_word,parent,false);
         ViewGroup.LayoutParams lp =  view.getLayoutParams();
         lp.height = getItemHeight();
         return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String word = mWordList.get(position);
        holder.textView.setText(word);

        try {
            if (!isHighLight(position)) {
                holder.textView.setTextSize(mOrdinarySize);
                holder.textView.setTextColor(Color.parseColor(mOrdinaryColor));

            } else if (isHighLight(position)) {
                holder.textView.setTextSize(mHighLightSize);
                holder.textView.setTextColor(Color.parseColor(mHighLightColor));
            }
        }catch ( Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return mWordList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_rv_tv_word);
        }
    }


    /** 获取歌词高度
     * @return
     */
    private int  getItemHeight(){
        int itemHeight = group.getMeasuredHeight()/9;
        return  itemHeight;
    }

    /** 判断是否高亮
     * @param position
     * @return
     */
    private boolean isHighLight(int position){
        return mHighLightPosition == position;
    }


    /** 改变歌词高亮位置
     * @param position
     */
    public void hightLightItem(int position){
         mHighLightPosition = position;
         notifyItemChanged(position-1);
         notifyItemChanged(position);
    }


    /** 改变歌词高亮的位置
     * @param lastPostion
     * @param currentPosition
     */
    public void changeToHighLight(int lastPostion,int currentPosition){
        mHighLightPosition = currentPosition;
        notifyItemChanged(lastPostion);
        notifyItemChanged(currentPosition);
    }

    public void setmHighLightSize(int size){
        mHighLightSize = size;
    }

    public void setmOrdinarySize(int size){
        mOrdinarySize = size;
    }

    public void setHightLightColor(String color){
        mHighLightColor = color;
    }

    public void setmOrdinaryColor(String color){
        mOrdinaryColor = color;
    }
}
