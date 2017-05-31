package com.shen.keep.app;

import com.shen.keep.engine.QuotePresenter;

/**
 * Created by edianzu on 2017/5/31.
 */
public class KeepDataManager {

    private static final KeepDataManager keepDataManager = new KeepDataManager();

    private KeepDataManager(){

    }

    public static KeepDataManager getKeepDataManager(){
        return keepDataManager;
    }

    public void startUpdateData(){

        QuotePresenter quotePresenter = new QuotePresenter(KeepApp.getAppInstance());
        quotePresenter.updateQuoteInfo();
    }

}
