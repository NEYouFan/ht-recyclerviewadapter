package com.netease.hearttouch.htrecyclerviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.netease.hearttouch.htrecyclerviewadapter.base.TBaseRecyclerViewHolder;


/**
 * recycle需要的viewholder
 * Created by zhengwen on 15-6-16.
 */
@TRecyclerViewHolderAnnotation()
public abstract class TRecyclerViewHolder<TDataModel>
        extends TBaseRecyclerViewHolder<View, TDataModel> {

    public TRecyclerViewHolder(View itemView, Context context, RecyclerView recyclerView) {
        super(itemView, context, recyclerView);
        inflate();
    }
}
