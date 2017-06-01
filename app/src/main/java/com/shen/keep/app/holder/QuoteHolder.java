package com.shen.keep.app.holder;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shen.keep.R;
import com.shen.keep.core.base.BaseHolder;

import butterknife.Bind;

/**
 * Created by shenjianli on 17/5/20.
 */
public class QuoteHolder extends BaseHolder {


    @Bind(R.id.quote_image_iv)
    public ImageView quoteImageIv;
    @Bind(R.id.quote_name_tv)
    public TextView quoteNameTv;
    @Bind(R.id.quote_content_tv)
    public TextView quoteContentTv;
    @Bind(R.id.quote_item_layout)
    public LinearLayout quoteItemLayout;

    public QuoteHolder(ViewGroup parent, @LayoutRes int resId) {
        super(parent, resId);
    }

    public QuoteHolder(View view) {
        super(view);
    }

}
