package com.netease.hearttouch.recycleradapter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;

import com.netease.hearttouch.htrecyclerviewadapter.TAdapterItem;
import com.netease.hearttouch.htrecyclerviewadapter.TRecyclerViewAdapter;
import com.netease.hearttouch.htrecyclerviewadapter.TRecyclerViewHolder;
import com.netease.hearttouch.recycleradapter.viewholder.InfoViewHolder;
import com.netease.hearttouch.recycleradapter.viewholder.item.InfoViewHolderItem;
import com.netease.hearttouch.recycleradapter.viewholder.item.ViewItemType;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final SparseArray<Class<? extends TRecyclerViewHolder>> viewHolders = new SparseArray<Class<? extends TRecyclerViewHolder>>() {
        {
            put(ViewItemType.VIEW_TYPE_INFO, InfoViewHolder.class);
        }
    };

    private final List<TAdapterItem> mTAdapterItems = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TRecyclerViewAdapter mRecycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_container);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleViewAdapter = new TRecyclerViewAdapter(this, viewHolders, mTAdapterItems);
        bindData();
        mRecyclerView.setAdapter(mRecycleViewAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
    }

    private void bindData() {
        for (int i = 0; i < 20; i++) {
            mTAdapterItems.add(new InfoViewHolderItem(String.valueOf(i) + "item"));
        }
    }

}
