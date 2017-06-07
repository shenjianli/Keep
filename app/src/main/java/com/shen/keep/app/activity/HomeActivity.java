package com.shen.keep.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shen.keep.R;
import com.shen.keep.app.KeepApp;
import com.shen.keep.app.KeepDataManager;
import com.shen.keep.app.db.KeepDao;
import com.shen.keep.app.db.QuoteDao;
import com.shen.keep.core.util.CustomToast;
import com.shen.keep.core.util.SharedPreUtil;
import com.shen.keep.core.util.TimeUtils;
import com.shen.keep.model.Keep;
import com.shen.keep.model.Quote;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.query_keep_btn)
    Button queryKeepBtn;
    @Bind(R.id.start_keep_btn)
    Button startKeepBtn;
    @Bind(R.id.keep_sum_time_tv)
    TextView keepSumTimeTv;
    @Bind(R.id.keep_name_tv)
    TextView keepNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        ButterKnife.bind(this);
        initView();
        queryKeepTime();
        updateKeepData();

    }

    private void updateKeepData() {
        KeepDataManager.getKeepDataManager().startUpdateData();
    }

    /**
     * 是否在没有提示情况下进入
     */
    private boolean isEnter = false;
    private void initView() {
        long startTime = SharedPreUtil.get(this, "start_cnt", 0L);
        long exitTime = SharedPreUtil.get(this, "exit_time", 0L);
        if( startTime > 0){
            if(0 != exitTime){
                startKeepBtn.setText("进入");
                isEnter = true;
            }
        }
        if(0 == startTime && 0 == exitTime){
            startKeepBtn.setText("开始");
            isEnter = false;
            queryKeepTime();
        }
    }

    private void queryKeepTime() {
        KeepDao keepDao = KeepApp.getAppInstance().getDaoSession().getKeepDao();
        if (null != keepDao) {
            List<Keep> keepList = keepDao.loadAll();
            long keepTime = 0;
            for (Keep keep : keepList) {
                keepTime += keep.getKeepSecNum();
            }
            keepSumTimeTv.setText(TimeUtils.getTimerStrBySecNum(keepTime));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != startDialog){
            startDialog.dismiss();
            startDialog = null;
        }
    }

    @OnClick({R.id.query_keep_btn, R.id.start_keep_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_keep_btn:
                if(isEnter){
                    enterKeepPage();
                }
                else {
                    showStartDialog();
                }
                break;
            case R.id.query_keep_btn:
                //inputQuoteInfo();
                //queryKeepInfo();
                queryQuoteInfo();//
                break;
        }
    }


    AlertDialog startDialog;
    private void showStartDialog() {

        LayoutInflater inflater = LayoutInflater.from(this) ;

        View view = inflater.inflate(R.layout.dialog_start_layout, null) ;

        startDialog = new AlertDialog.Builder(this)
                .setView(view).create();

        view.findViewById(R.id.dialog_start_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterKeepPage();
                CustomToast.show(HomeActivity.this,"GO！祝你成功！");
                startDialog.dismiss();
            }
        });

        view.findViewById(R.id.dialog_cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomToast.showLong(HomeActivity.this,"希望下次，你可以有一颗坚持之心");
                startDialog.dismiss();
            }
        });


        startDialog.show();
        if(null != startDialog){
            startDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        }
    }

    private void enterKeepPage() {
        Intent intent = new Intent(HomeActivity.this, KeepActivity.class);
        startActivity(intent);
    }

    private void queryKeepInfo() {
        Intent intent = new Intent(this, ShowActivity.class);
        startActivity(intent);
    }

    private void queryQuoteInfo() {
        Intent intent = new Intent(this, ShowQuoteActivity.class);
        startActivity(intent);
    }

    private void inputQuoteInfo() {
        QuoteDao quoteDao = KeepApp.getAppInstance().getDaoSession().getQuoteDao();
        if (null != quoteDao) {
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
            CustomToast.show(this, "录入数据成功");
        }
    }
}
