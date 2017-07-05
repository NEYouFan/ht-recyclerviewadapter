package com.netease.hearttouch.htrecyclerviewadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.netease.hearttouch.htrecyclerviewadapter.util.LogUtil;
import com.netease.hearttouch.htrecyclerviewadapter.base.TBaseRecyclerViewAdapter;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 * 用于RecycleView的adatper
 * Created by zhengwen on 15-6-16.
 */
public class TRecyclerViewAdapter<TRealViewHolder extends TRecyclerViewHolder, TDataModel>
        extends TBaseRecyclerViewAdapter<TRealViewHolder, TDataModel> {

//    private static final int LOADMORE = 10;
//    private static final int BLANK = 11;
//    private static final int FOOT = 6;
//    private int mLimits;

    public TRecyclerViewAdapter(Context context,
                                SparseArray<Class<TRealViewHolder>> viewHolders,
                                List<TAdapterItem<TDataModel>> items){

        super(context, viewHolders, items);
    }

    @Override
    public void onBindViewHolder(TRecyclerViewHolder holder, int position) {
        TAdapterItem<TDataModel> item = mItems.get(position);
        holder.refresh(item);
    }

    @Override
    public TRealViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TRealViewHolder recycleViewHolder = null;
        try{
            Class viewHolder = mViewHolders.get(viewType);
            TRecyclerViewHolderAnnotation annotation = (TRecyclerViewHolderAnnotation) viewHolder.getAnnotation(TRecyclerViewHolderAnnotation.class);
            int resId = annotation.resId();
            View v = mInflater.inflate(resId, parent, false);
            Constructor constructor = viewHolder.getConstructor(android.view.View.class, android.content.Context.class, RecyclerView.class);
            recycleViewHolder = (TRealViewHolder) (constructor.newInstance(v, mContext, (RecyclerView)parent));
            recycleViewHolder.setItemEventListener(this);
        }catch(Exception e){
            LogUtil.logE(e.getMessage());
        }

        return recycleViewHolder;
    }
}
