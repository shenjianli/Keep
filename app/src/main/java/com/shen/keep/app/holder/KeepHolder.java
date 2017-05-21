package com.shen.keep.app.holder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shen.keep.R;
import com.shen.keep.core.BaseHolder;

import butterknife.Bind;

/**
 * Created by shenjianli on 17/5/20.
 */
public class KeepHolder extends BaseHolder {
    @Bind(R.id.keep_num_tv)
    public TextView mKeepNumTv;
    @Bind(R.id.keep_item_tv)
    public TextView mKeepItemTv;
    @Bind(R.id.keep_time_tv)
    public TextView mKeepTimeTv;
    @Bind(R.id.keep_start_time_tv)
    public TextView mKeepStartTimeTv;
    @Bind(R.id.keep_stop_time_tv)
    public TextView mKeepStopTimeTv;
    @Bind(R.id.keep_item_layout)
    public LinearLayout mKeepItemLayout;

    public KeepHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
    }

    public KeepHolder(View view) {
        super(view);
    }

}
