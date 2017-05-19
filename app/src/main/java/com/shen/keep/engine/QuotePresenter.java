package com.shen.keep.engine;

import android.content.Context;

import com.shen.keep.app.KeepApp;
import com.shen.keep.app.db.QuoteDao;
import com.shen.keep.core.BasePresenter;
import com.shen.keep.core.LogUtils;
import com.shen.keep.model.Quote;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by edianzu on 2016/9/12.
 */
public class QuotePresenter extends BasePresenter<QuoteView> {

    Context context;

    public QuotePresenter(Context context){
        this.context = context;
    }

    @Override
    public void attachView(QuoteView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
    }

    public void loadQuoteInfo() {

        if(null != mMvpView){
            QuoteDao quoteDao= KeepApp.getAppInstance().getDaoSession().getQuoteDao();
            if(null != quoteDao){
                LogUtils.i("加载数据" +
                quoteDao.count() +
                quoteDao.getAllColumns() +
                quoteDao.getDatabase() +
                quoteDao.getTablename() +
                quoteDao.loadAll() +
                quoteDao.queryBuilder());

                QueryBuilder<Quote> qb = quoteDao.queryBuilder();
                List<Quote> list = qb.list();
                if(null != list && list.size() >0 ){
                    mMvpView.updateQuoteView(list.get(0));
                    return;
                }
            }
            mMvpView.updateQuoteView(null);
        }
    }

    /**
     * 查询用户列表
     */
    public List<Quote> queryUserList() {

        QuoteDao quoteDao= KeepApp.getAppInstance().getDaoSession().getQuoteDao();
        QueryBuilder<Quote> qb = quoteDao.queryBuilder();
        List<Quote> list = qb.list();
        return list;
    }

    /**
     * 查询用户列表
     */
    public List<Quote> queryUserList(String title) {
        QuoteDao quoteDao= KeepApp.getAppInstance().getDaoSession().getQuoteDao();
        QueryBuilder<Quote> qb = quoteDao.queryBuilder();
        qb.where(QuoteDao.Properties.Title.gt(title)).orderAsc(QuoteDao.Properties.Id);
        List<Quote> list = qb.list();
        return list;
    }
}
