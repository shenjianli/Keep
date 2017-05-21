package com.shen.keep.app.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.shen.keep.R;
import com.shen.keep.app.KeepApp;
import com.shen.keep.app.adapter.KeepAdapter;
import com.shen.keep.app.db.KeepDao;
import com.shen.keep.model.Keep;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowActivity extends AppCompatActivity {


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

        keeps = new ArrayList<>();

        initRecylerView();

        queryKeepInfo();

    }

    private void initRecylerView() {
        // 线性布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        // 设置布局管理器
        mShowInfoRv.setLayoutManager(linearLayoutManager);
//        DividerDecoration decoration = new DividerDecoration(this, DividerDecoration.VERTICAL_LIST);
//        Drawable drawable = getResources().getDrawable(R.drawable.divider_single);
//        decoration.setDivider(drawable);
//
////        decoration.getItemOffsets();
//        flyRecyclerView.addItemDecoration(decoration);
        //recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        // recyclerView.addItemDecoration(new DividerDecoration(this, DividerDecoration.VERTICAL_LIST));
        mKeepAdapter = new KeepAdapter(this,keeps);
        mShowInfoRv.setAdapter(mKeepAdapter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void queryKeepInfo() {
        KeepDao keepDao = KeepApp.getAppInstance().getDaoSession().getKeepDao();
        if (null != keepDao) {
            List<Keep> keepList = keepDao.loadAll();
            if (null != keepList && keepList.size() > 0) {
                mKeepAdapter.appendList(keepList);
                mKeepAdapter.notifyDataSetChanged();
            }
        }
    }

}
