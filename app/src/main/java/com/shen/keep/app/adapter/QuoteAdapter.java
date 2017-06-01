package com.shen.keep.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.shen.keep.R;
import com.shen.keep.app.holder.QuoteHolder;
import com.shen.keep.core.adapter.BaseAdapter;
import com.shen.keep.model.Quote;

import java.util.List;

/**
 * Created by shenjianli on 17/5/20.
 */
public class QuoteAdapter extends BaseAdapter<Quote,QuoteHolder>{

    public QuoteAdapter(Context context) {
        super(context);
    }

    public QuoteAdapter(Context context, List<Quote> list) {
        super(context, list);
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    @Override
    public QuoteHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new QuoteHolder(parent, R.layout.item_quote_layout);
    }

    @Override
    public void bindCustomViewHolder(QuoteHolder holder, int position) {
        Quote quote = getItem(position);
        if(null != quote){
            if(null != holder){
                if(position % 2 == 0){
                    holder.quoteItemLayout.setBackgroundColor(Color.parseColor("#e67e22"));
                }
                else {
                    holder.quoteItemLayout.setBackgroundColor(Color.parseColor("#9b59b6"));
                }
                String title = quote.getTitle();
                if(!TextUtils.isEmpty(title)){
                    holder.quoteNameTv.setText(title);
                }
                holder.quoteContentTv.setText(quote.getContent());
                holder.quoteImageIv.setBackgroundResource(R.drawable.leak_canary_icon);
            }
        }

    }
}
