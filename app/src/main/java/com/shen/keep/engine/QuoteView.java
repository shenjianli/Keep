package com.shen.keep.engine;


import com.shen.keep.core.MvpView;
import com.shen.keep.model.Quote;

public interface QuoteView extends MvpView {

    void updateQuoteView(Quote data);
}
