package com.shen.keep.app.activity;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shen.keep.R;
import com.shen.keep.core.CountTimer;
import com.shen.keep.core.util.LogUtils;
import com.shen.keep.engine.QuotePresenter;
import com.shen.keep.engine.QuoteView;
import com.shen.keep.model.Quote;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class QuoteActivity extends AppCompatActivity implements CountTimer.CountDownTimerListener, QuoteView {

    @Bind(R.id.fly_motivational_quotes_enter_time_tv)
    TextView flyMotivationalQuotesEnterTimeTv;
    @Bind(R.id.fly_app_logo_info)
    TextView flyAppLogoInfo;
    @Bind(R.id.fly_motivational_quotes_title_tv)
    TextView flyMotivationalQuotesTitleTv;
    @Bind(R.id.fly_motivational_quotes_content_tv)
    TextView flyMotivationalQuotesContentTv;
    @Bind(R.id.fly_motivational_quotes_sum_layout)
    RelativeLayout flyMotivationalQuotesSumLayout;

    private CountTimer countTimer;
    private QuotePresenter quotePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);
        ButterKnife.bind(this);

        quotePresenter = new QuotePresenter(this);
        quotePresenter.attachView(this);
        quotePresenter.loadQuoteInfo();
    }


    private void updateViewByData(Quote quoteInfo) {
        long countTime = 5;

        if (null != quoteInfo) {
            LogUtils.i("收到名言警句请求数据：" + quoteInfo.toString());
            String title = quoteInfo.getTitle();
            if (!TextUtils.isEmpty(title)) {
                flyMotivationalQuotesTitleTv.setText(title);
            }
            String content = quoteInfo.getContent();
            if (!TextUtils.isEmpty(content)) {
                flyMotivationalQuotesContentTv.setText(content);
            }

            String appName = quoteInfo.getAppName();
            if (!TextUtils.isEmpty(appName)) {
                flyAppLogoInfo.setText(appName);
            }

            String titleColor = quoteInfo.getTitleColor();
            if (!TextUtils.isEmpty(titleColor)) {
                flyMotivationalQuotesTitleTv.setTextColor(Color.parseColor(titleColor));
            }

            String contentColor = quoteInfo.getContentColor();
            if (!TextUtils.isEmpty(contentColor)) {
                flyMotivationalQuotesContentTv.setTextColor(Color.parseColor(contentColor));
            }

//            String bgImgUrl = quoteInfo.getBgImgUrl();
//            if(!TextUtils.isEmpty(bgImgUrl)){
//                Drawable d = Drawable.createFromPath(bgImgUrl);
//                flyMotivationalQuotesSumLayout.setBackground(d);
//                Uri uri = Uri.parse(bgImgUrl);
//                downLoadImg(uri);
//            }

            if (countTime > 1) {
                countTime = quoteInfo.getCountTime();
            }
        }

        countTimer = new CountTimer((countTime + 1) * 1000, 1000);
        countTimer.setCountDownTimerListener(this);
        countTimer.start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick(R.id.fly_motivational_quotes_enter_time_tv)
    public void onClick() {
        enterMainPage();
    }

    private void enterMainPage() {
        if(null != countTimer){
            countTimer.setCountDownTimerListener(null);
            countTimer.cancel();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if (null != countTimer) {
            countTimer.setCountDownTimerListener(null);
            countTimer.cancel();
            countTimer = null;
        }

        if (null != quotePresenter) {
            quotePresenter.detachView();
            quotePresenter = null;
        }
    }

    @Override
    public void updateCountDownTime(String day, String hour, String min, String second) {
        LogUtils.i("倒计时：" + day + ":" + hour + ":" + min + ":" + second);
        if (null != flyMotivationalQuotesEnterTimeTv) {
            flyMotivationalQuotesEnterTimeTv.setText(second);
        }
        if( TextUtils.equals("1",second)){
            enterMainPage();
        }
    }

    @Override
    public void countDownTimerFinish() {
        LogUtils.i("倒计时结束");
    }

    @Override
    public void updateQuoteView(Quote quoteInfo) {

        updateViewByData(quoteInfo);
//        String bgImgUrl = quoteInfo.getBgImgUrl();
//        if(!TextUtils.isEmpty(bgImgUrl)){
//            Uri uri = Uri.parse(bgImgUrl);
//            downLoadImg(uri);
//        }
    }

    @Override
    public void startLoading() {
        LogUtils.i("名言警句开始请求数据...");
    }

    @Override
    public void hideLoading() {
        LogUtils.i("名言警句结束请求数据...");
    }

    @Override
    public void showError(String msg) {
        LogUtils.i("名言警句显示错误信息" + msg);
    }


    private void downLoadImg(Uri uri) {

    }
}
