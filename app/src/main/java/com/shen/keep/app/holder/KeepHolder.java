package com.shen.keep.app.holder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.shen.keep.core.BaseHolder;

/**
 * Created by shenjianli on 17/5/20.
 */
public class KeepHolder extends BaseHolder{

    public KeepHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
    }

    public KeepHolder(View view) {
        super(view);
    }

}
