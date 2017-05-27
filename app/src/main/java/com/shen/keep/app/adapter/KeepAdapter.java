package com.shen.keep.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.shen.keep.R;
import com.shen.keep.app.holder.KeepHolder;
import com.shen.keep.core.BaseAdapter;
import com.shen.keep.model.Keep;

import java.util.List;

/**
 * Created by shenjianli on 17/5/20.
 */
public class KeepAdapter extends BaseAdapter<Keep,KeepHolder>{

    public KeepAdapter(Context context) {
        super(context);
    }

    public KeepAdapter(Context context, List<Keep> list) {
        super(context, list);
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    @Override
    public KeepHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new KeepHolder(parent, R.layout.item_keep_layout);
    }

    @Override
    public void bindCustomViewHolder(KeepHolder holder, int position) {
        Keep keep = getItem(position);
        if(null != keep){
            if(null != holder){
                holder.mKeepNumTv.setText(String.valueOf(position + 1));
                if(position % 2 == 0){
                    holder.mKeepItemLayout.setBackgroundColor(Color.parseColor("#e67e22"));
                }
                else {
                    holder.mKeepItemLayout.setBackgroundColor(Color.parseColor("#9b59b6"));
                }
                String name = keep.getKeepName();
                if(!TextUtils.isEmpty(name)){
                    holder.mKeepItemTv.setText(keep.getKeepName());
                }
                holder.mKeepTimeTv.setText(keep.getKeepTime());
                holder.mKeepStartTimeTv.setText(keep.getStartDate());
                holder.mKeepStopTimeTv.setText(keep.getStopDate());
            }
        }

    }
}
