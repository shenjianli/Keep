package com.shen.keep.app.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.shen.keep.R;
import com.shen.keep.app.KeepApp;
import com.shen.keep.app.adapter.QuoteAdapter;
import com.shen.keep.app.db.QuoteDao;
import com.shen.keep.core.widget.KeepTitleLayout;
import com.shen.keep.model.Quote;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowQuoteActivity extends AppCompatActivity implements KeepTitleLayout.LeftClickListener{


    LinearLayout mShowLayout;
    @Bind(R.id.show_info_rv)
    RecyclerView mShowInfoRv;
    @Bind(R.id.show_keep_info_srl)
    SwipeRefreshLayout mShowKeepInfoSrl;
    @Bind(R.id.keep_title_layout)
    KeepTitleLayout keepTitleLayout;

    private QuoteAdapter quoteAdapter;
    private List<Quote> quotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);
        keepTitleLayout.setLeftClickListener(this);
        quotes = new ArrayList<>();

        initRecylerView();

        queryQuoteInfo();

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
        quoteAdapter = new QuoteAdapter(this, quotes);
        mShowInfoRv.setAdapter(quoteAdapter);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void queryQuoteInfo() {
        QuoteDao quoteDao = KeepApp.getAppInstance().getDaoSession().getQuoteDao();
        if (null != quoteDao) {
            List<Quote> quoteList = quoteDao.loadAll();
            if (null != quoteList && quoteList.size() > 0) {
                quoteAdapter.appendList(quoteList);
                quoteAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onLeftClick() {
        finish();
    }
}
