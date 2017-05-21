package com.shen.keep.app.adapter;

import android.content.Context;
import android.view.ViewGroup;

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
        return new KeepHolder(parent);
    }

    @Override
    public void bindCustomViewHolder(KeepHolder holder, int position) {
        Keep keep = getItem(position);

    }
}
