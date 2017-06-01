package com.shen.keep.engine;

import android.content.Context;

import com.shen.keep.app.KeepApp;
import com.shen.keep.app.db.QuoteDao;
import com.shen.keep.core.HttpResultFunc;
import com.shen.keep.core.api.QuoteApi;
import com.shen.keep.core.base.BasePresenter;
import com.shen.keep.core.util.LogUtils;
import com.shen.keep.model.Quote;
import com.shen.netclient.NetClient;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by edianzu on 2016/9/12.
 */
public class QuotePresenter extends BasePresenter<QuoteView> {

    Context context;
    private Subscriber<Quote> quoteSubscriber;

    public QuotePresenter(Context context) {
        this.context = context;
    }

    @Override
    public void attachView(QuoteView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        quoteSubscriber = null;
    }

    public void loadQuoteInfo() {

        if (null != mMvpView) {
            QuoteDao quoteDao = KeepApp.getAppInstance().getDaoSession().getQuoteDao();
            if (null != quoteDao) {
                LogUtils.i("加载数据" +
                        quoteDao.count() +
                        quoteDao.getAllColumns() +
                        quoteDao.getDatabase() +
                        quoteDao.getTablename() +
                        quoteDao.loadAll() +
                        quoteDao.queryBuilder());

                QueryBuilder<Quote> qb = quoteDao.queryBuilder().orderDesc(QuoteDao.Properties.Id);
                List<Quote> list = qb.list();
                if (null != list && list.size() > 0) {
                    mMvpView.updateQuoteView(list.get(0));
                    return;
                }
            }
            mMvpView.updateQuoteView(null);
        }
    }

    public void loadQuoteInfoFromNet() {

        if (null != mMvpView) {
            quoteSubscriber = new Subscriber<Quote>() {
                @Override
                public void onCompleted() {
                    LogUtils.i("表求完成");
                }

                @Override
                public void onError(Throwable e) {
                    LogUtils.i(e.getStackTrace().toString());
                    mMvpView.showError(e.getMessage());
                    mMvpView.hideLoading();
                }

                @Override
                public void onNext(Quote data) {
                    LogUtils.i("获取数据成功");
                    mMvpView.updateQuoteView(data);
                    mMvpView.hideLoading();
                }
            };
            QuoteApi quoteApi = NetClient.retrofit().create(QuoteApi.class);
            quoteApi.queryQuoteInfo().map(new HttpResultFunc<Quote>())
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(quoteSubscriber);
        }
    }


    public void updateQuoteInfo() {


        Subscriber<Boolean> quoteSubscriber = new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                LogUtils.i("表求完成");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.i(e.getStackTrace().toString());
                if(null != mMvpView){
                    mMvpView.showError(e.getMessage());
                    mMvpView.hideLoading();
                }
            }

            @Override
            public void onNext(Boolean data) {
                LogUtils.i("获取数据成功");
                Thread thread = Thread.currentThread();
                com.shen.netclient.util.LogUtils.i("当前线程名：" + thread.getName());
                if(null != mMvpView){
                    mMvpView.hideLoading();
                }
            }
        };
        QuoteApi quoteApi = NetClient.retrofit().create(QuoteApi.class);
        quoteApi.queryQuoteInfo().map(new HttpResultFunc<Quote>())
                .map(new Func1<Quote, Boolean>() {
                    @Override
                    public Boolean call(Quote quote) {
                        if (null != quote) {
                            LogUtils.i("从网络上接收到数据" + quote.toString());
                            QuoteDao quoteDao = KeepApp.getAppInstance().getDaoSession().getQuoteDao();
                            if (null != quoteDao) {
                                //与数据库进行比较更新数据
                                List<Quote> queryDatas = quoteDao.queryBuilder().orderDesc(QuoteDao.Properties.Id)
                                        .where(QuoteDao.Properties.Content.eq(quote.getContent())).list();
                                if (null == queryDatas || queryDatas.size() <= 0) {
                                    //插入数据
                                    LogUtils.i("数据库不存在数据-----插入数据");
                                    quoteDao.insertWithoutSettingPk(quote);
                                } else {
                                    if (!quote.equals(queryDatas.get(0))) {
                                        //更新数据
                                        LogUtils.i("数据库中存在数据-----内容，标题不同----更新数据");
                                        quoteDao.update(quote);
                                    } else {
                                        //更新数据
                                        LogUtils.i("数据库中存在数据-----内容，标题相同-----不更新内容");
                                    }
                                }
                                Thread thread = Thread.currentThread();
                                com.shen.netclient.util.LogUtils.i("当前线程名：" + thread.getName());
                                return true;
                            }
                        }
                        return false;
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(quoteSubscriber);

    }

    /**
     * 查询用户列表
     */
    public List<Quote> queryUserList() {

        QuoteDao quoteDao = KeepApp.getAppInstance().getDaoSession().getQuoteDao();
        QueryBuilder<Quote> qb = quoteDao.queryBuilder();
        List<Quote> list = qb.list();
        return list;
    }

    /**
     * 查询用户列表
     */
    public List<Quote> queryUserList(String title) {
        QuoteDao quoteDao = KeepApp.getAppInstance().getDaoSession().getQuoteDao();
        QueryBuilder<Quote> qb = quoteDao.queryBuilder();
        qb.where(QuoteDao.Properties.Title.gt(title)).orderAsc(QuoteDao.Properties.Id);
        List<Quote> list = qb.list();
        return list;
    }
}
