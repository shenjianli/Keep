package com.shen.keep.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.shen.keep.R;
import com.shen.keep.app.KeepApp;
import com.shen.keep.app.db.KeepDao;
import com.shen.keep.app.db.QuoteDao;
import com.shen.keep.core.CustomToast;
import com.shen.keep.core.LogUtils;
import com.shen.keep.model.Keep;
import com.shen.keep.model.Quote;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.test)
    Button test;
    @Bind(R.id.input)
    Button input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }
    
    @OnClick({R.id.test, R.id.input})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.test:
                Intent intent = new Intent(this, KeepActivity.class);
                startActivity(intent);
                break;
            case R.id.input:
                //inputQuoteInfo();
                queryKeepInfo();
                break;
        }
    }

    private void queryKeepInfo() {
        KeepDao keepDao = KeepApp.getAppInstance().getDaoSession().getKeepDao();
        if(null != keepDao){
            List<Keep> keeps = keepDao.loadAll();
            if(null != keeps && keeps.size() > 0){
                for (Keep keep: keeps ) {
                    LogUtils.i(keep.toString());
                }
            }
        }
    }

    private void inputQuoteInfo() {
        QuoteDao quoteDao= KeepApp.getAppInstance().getDaoSession().getQuoteDao();
        if(null != quoteDao){
            Quote quote = new Quote();
            quote.setTitle("撑起头顶的天");
            quote.setContent("我要养成这样的习惯： 我喜欢，驾驭着代码在风驰电掣中创造完美！我喜欢，操纵着代码在随心所欲中体验生活！我喜欢，书写着代码在时代浪潮中完成经典！每一段新的代码在我手中诞生对我来说就像观看刹那花开的感动！");
            quote.setAppName("程序员");
            quote.setAppImgUrl("");
            quote.setBgImgUrl("http://pic.qiantucdn.com/58pic/25/63/65/08b58PICt2I_1024.jpg");
            quote.setContentColor("#00ffff");
            quote.setTitleColor("#f0000f");
            quote.setCountTime(10);
            quoteDao.insert(quote);
            quoteDao.insertWithoutSettingPk(quote);
            CustomToast.show(this,"录入数据成功");
        }
    }
}
