package com.shen.keep.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shen.keep.R;
import com.shen.keep.app.KeepApp;
import com.shen.keep.app.adapter.KeepAdapter;
import com.shen.keep.app.db.KeepDao;
import com.shen.keep.core.LogUtils;
import com.shen.keep.model.Keep;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowActivity extends AppCompatActivity {


    @Bind(R.id.show_layout)
    LinearLayout mShowLayout;
    @Bind(R.id.show_info_rv)
    RecyclerView mShowInfoRv;
    @Bind(R.id.show_keep_info_srl)
    SwipeRefreshLayout mShowKeepInfoSrl;

    private KeepAdapter mKeepAdapter;
    private List<Keep> keeps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);

        mKeepAdapter = new KeepAdapter(this,keeps);
        mShowInfoRv.setAdapter(mKeepAdapter);

        queryKeepInfo();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void queryKeepInfo() {
        KeepDao keepDao = KeepApp.getAppInstance().getDaoSession().getKeepDao();
        if (null != keepDao) {
            List<Keep> keeps = keepDao.loadAll();
            if (null != keeps && keeps.size() > 0) {
                TextView textView;
                Keep keep;
                for (int i = 0; i < keeps.size(); i++) {
                    keep = keeps.get(i);
                    LogUtils.i(keep.toString());
                    textView = new TextView(this);
                    if (i % 2 == 0) {
                        textView.setTextColor(Color.RED);
                    } else {
                        textView.setTextColor(Color.GREEN);
                    }
                    textView.setText(keep.toString());
                    mShowLayout.addView(textView);
                }
            }
        }
    }

}
