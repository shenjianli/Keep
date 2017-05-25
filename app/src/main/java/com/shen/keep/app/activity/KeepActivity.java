package com.shen.keep.app.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shen.keep.R;
import com.shen.keep.app.KeepApp;
import com.shen.keep.app.db.KeepDao;
import com.shen.keep.core.CustomToast;
import com.shen.keep.core.LogUtils;
import com.shen.keep.core.SharedPreUtil;
import com.shen.keep.model.Keep;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jerry shen on 2017/5/18.
 */
public class KeepActivity extends AppCompatActivity{

    @Bind(R.id.keep_quote)
    TextView keepQuote;
    @Bind(R.id.keep_time)
    TextView keepTime;
    @Bind(R.id.continue_btn)
    Button continueBtn;
    @Bind(R.id.pause_btn)
    Button pauseBtn;
    @Bind(R.id.stop_btn)
    Button stopBtn;

    private long startTime;
    private boolean isPause = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keep);
        ButterKnife.bind(this);
        startTime = SharedPreUtil.get(this, "start_cnt", 0L);
        long exitTime = SharedPreUtil.get(this, "exit_time", 0L);
        if( startTime > 0){
            if(0 != exitTime){
                long secCnt = (System.currentTimeMillis() - exitTime)/1000;
                if(secCnt > 0 && 0 != startTime ){
                    startTime += secCnt;
                }
            }
        }
        else {
            SharedPreUtil.put(this, "start_time", System.currentTimeMillis());
        }
        keepTime.setText(getTimerStrByCount(startTime));
        timer.schedule(timerTask, 1000, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreUtil.put(this,"start_cnt", startTime);

        if(!isPause){
            SharedPreUtil.put(this,"exit_time", System.currentTimeMillis());
        }

        if(null != stopDialog){
            stopDialog.dismiss();
            stopDialog = null;
        }

        if(null != handler){
            handler.removeCallbacksAndMessages(null);
            handler.clearTimer();
            handler = null;
        }

        if(null != timer){
            timer.cancel();
            timer = null;
        }

        if(null != timerTask){
            timerTask = null;
        }

    }

    @OnClick({R.id.continue_btn, R.id.pause_btn, R.id.stop_btn})
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.continue_btn:
                continueKeep();
                break;
            case R.id.pause_btn:
                isPause = true;
                continueBtn.setVisibility(View.VISIBLE);
                stopBtn.setVisibility(View.VISIBLE);
                pauseBtn.setVisibility(View.GONE);

                break;
            case R.id.stop_btn:
                showStopDialog();
                break;
        }
    }

    private void continueKeep() {
        isPause = false;
        continueBtn.setVisibility(View.GONE);
        stopBtn.setVisibility(View.GONE);
        pauseBtn.setVisibility(View.VISIBLE);
    }


    AlertDialog stopDialog;
    private void showStopDialog() {

        LayoutInflater inflater = LayoutInflater.from(this) ;

        View view = inflater.inflate(R.layout.dialog_stop_layout, null) ;


        stopDialog  = new AlertDialog.Builder(this)
                .setView(view).create();
        view.findViewById(R.id.dialog_continue_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueKeep();
                stopDialog.dismiss();

            }
        });

        view.findViewById(R.id.dialog_stop_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveKeepInfo();
                stopDialog.dismiss();
                finish();
            }
        });


        stopDialog.show();
        stopDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);



    }


    private void saveKeepInfo(){
        long start_time = SharedPreUtil.get(this, "start_time", 0L);
        long currTime = System.currentTimeMillis();
        String startDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(start_time));
        String stopDate = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(currTime));

        long pauseTime = (currTime - start_time)/1000 - startTime;

        Keep keep = new Keep();
        KeepDao keepDao = KeepApp.getAppInstance().getDaoSession().getKeepDao();

        if(null != keepDao){
            keep.setKeepName("Keep");
            keep.setKeepTime(getTimerStrByCount(startTime));
            keep.setStartTime(start_time);
            keep.setStopTime(currTime);
            keep.setStartDate(startDate);
            keep.setStopDate(stopDate);
            keep.setPauseTime(getTimerStrByCount(pauseTime));
            keep.setKeepSecNum(startTime);
            keepDao.insert(keep);
            LogUtils.i("保存坚持时间" + keep.toString());
        }
        isPause = true;
        startTime = 0L;
        SharedPreUtil.put(this,"start_cnt", 0L);
        SharedPreUtil.put(this,"exit_time", 0L);
        if(null != timer){
            timer.cancel();
        }
    }

    private  Timer timer = new Timer();
    private TimerHandler handler = new TimerHandler(this);
    private CustomTimerTask timerTask = new CustomTimerTask(handler);

    private static class CustomTimerTask extends TimerTask{

        private WeakReference<TimerHandler> weakReference;

        public CustomTimerTask(TimerHandler timerHandler){
            weakReference = new WeakReference<>(timerHandler);
        }
        @Override
        public void run() {
            TimerHandler timerHandler = weakReference.get();
            if(null != timerHandler){
                Message message = timerHandler.obtainMessage();
                message.what = 1;
                timerHandler.sendMessage(message);
            }
        }
    }
    private static class TimerHandler extends Handler{

        private WeakReference<KeepActivity> weakReference;
        KeepActivity keepActivity;
        public TimerHandler(KeepActivity keepActivity){
            weakReference = new WeakReference<>(keepActivity);
        }

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            if(null == keepActivity){
                keepActivity = weakReference.get();
            }

            if(null != keepActivity){
                if(!keepActivity.isPause){
                    keepActivity.startTime ++;
                    keepActivity.keepTime.setText(keepActivity.getTimerStrByCount(keepActivity.startTime));
                }
            }
        }

        public void clearTimer(){
            weakReference = null;
            keepActivity = null;
        }
    }

    private String getTimerStrByCount(long  lapseTime){

        StringBuilder result = new StringBuilder();

        int second = (int) Math.floor(lapseTime % 60); // 计算秒
        int minite = (int) Math.floor((lapseTime / 60) % 60); // 计算分
        int day = (int) Math.floor((lapseTime / 3600) / 24); // 计算天
        int hour1 = (int) (Math.floor(lapseTime / 3600) - day * 24); // 计算小时详情页

        if(hour1 < 10){
            result.append("0" + hour1);
        }else {
            result.append(hour1);
        }
        result.append(":");

        if(minite < 10){
            result.append("0" + minite);
        }else {
            result.append(minite);
        }
        result.append(":");

        if(second < 10){
            result.append("0" + second);
        }else {
            result.append(second);
        }

        return  result.toString();
    }

    @Override
    public void onBackPressed() {
        if(isPause){
            saveKeepInfo();
            CustomToast.show(this,"完成坚持活动，下次再来喽！");
        }
        else {
            SharedPreUtil.put(this,"start_cnt", startTime);
            SharedPreUtil.put(this,"exit_time", System.currentTimeMillis());
        }
        super.onBackPressed();
    }
}
