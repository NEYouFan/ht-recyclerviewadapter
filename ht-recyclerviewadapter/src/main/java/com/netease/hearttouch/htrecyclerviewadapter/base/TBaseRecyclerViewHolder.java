package com.netease.hearttouch.htrecyclerviewadapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.netease.hearttouch.htrecyclerviewadapter.event.ItemEventListener;
import com.netease.hearttouch.htrecyclerviewadapter.TAdapterItem;

/**
 * Created by zyl06 on 10/16/15.
 */
public abstract class TBaseRecyclerViewHolder<T extends View, TDataModel>
        extends RecyclerView.ViewHolder {
    protected T view;
    protected Context context;
    protected ItemEventListener listener;
    private RecyclerView mRecycleView;

    public TBaseRecyclerViewHolder(T itemView,
                                   Context context,
                                   RecyclerView recyclerView) {
        super(itemView);
        this.view = itemView;
        this.context = context;
        this.mRecycleView = recyclerView;

//        viewHolderProxy.inflate();
        //inflate();
    }

    public void setItemEventListener(ItemEventListener listener) {
        this.listener = listener;
    }
    T getView() {
        return view;
    }

    public abstract void inflate();
    public abstract void refresh(TAdapterItem<TDataModel> item);

    public int getScrollState() {
        return (mRecycleView != null) ? mRecycleView.getScrollState() : RecyclerView.SCROLL_STATE_IDLE;
    }
}