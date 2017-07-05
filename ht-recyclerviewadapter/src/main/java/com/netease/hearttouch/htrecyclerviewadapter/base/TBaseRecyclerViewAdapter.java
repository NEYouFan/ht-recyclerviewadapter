package com.netease.hearttouch.htrecyclerviewadapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

import com.netease.hearttouch.htrecyclerviewadapter.event.ItemEventListener;
import com.netease.hearttouch.htrecyclerviewadapter.TAdapterItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by zyl06 on 10/16/15.
 */
public abstract class TBaseRecyclerViewAdapter<T extends TBaseRecyclerViewHolder, TDataModel>
        extends RecyclerView.Adapter<T>
        implements ItemEventListener {

    protected List<TAdapterItem<TDataModel>> mItems;
    protected SparseArray<Class<T>> mViewHolders;
    protected Context mContext;
    protected LayoutInflater mInflater;

    /**
     * 当前页面，Fragment or Activity
     */
    protected ItemEventListener mItemEventListener;


    public TBaseRecyclerViewAdapter(Context context,
                                    SparseArray<Class<T>> viewHolders,
                                    List<TAdapterItem<TDataModel>> items){
        this.mViewHolders = viewHolders;
        this.mItems = items;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public void onViewRecycled(T holder) {
        super.onViewRecycled(holder);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    @Override
    public long getItemId(int position) {
        return mItems.get(position).hashCode();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        int count = (mItems==null ? 0 : mItems.size());
        return count;
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public TDataModel getItemDataModel(int position) {
        TDataModel dataModel = (mItems==null ? null : mItems.get(position).getDataModel());
        return dataModel;
    }

    /**
     * 在集合尾部添加更多数据集合（上拉从服务器获取更多的数据集合，例如新浪微博列表上拉加载更晚时间发布的微博数据）
     *
     * @param items
     */
    public void addMoreItems(List<TAdapterItem<TDataModel>> items) {
        if (items != null && items.size() > 0) {
            int positionInsert = mItems.size();
            mItems.addAll(positionInsert, items);
            notifyItemRangeInserted(positionInsert, items.size());
        }
    }

    /**
     * 设置全新的数据集合，如果传入null，则清空数据列表（第一次从服务器加载数据，或者下拉刷新当前界面数据表）
     *
     * @param items
     */
    public void setItems(List<TAdapterItem<TDataModel>> items) {
        if (items != null) {
            mItems= items;
        } else {
            mItems.clear();
        }
        notifyDataSetChanged();
    }

    /**
     * 清空数据列表
     */
    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    /**
     * 删除指定索引数据条目
     *
     * @param position
     */
    public void removeItem(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 删除指定数据条目
     *
     * @param item
     */
    public void removeItem(TAdapterItem<TDataModel> item) {
        removeItem(mItems.indexOf(item));
    }

    /**
     * 在指定位置添加数据条目
     *
     * @param position
     * @param item
     */
    public void addItem(int position, TAdapterItem<TDataModel> item) {
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * 在集合头部添加数据条目
     *
     * @param item
     */
    public void addFirstItem(TAdapterItem<TDataModel> item) {
        addItem(0, item);
    }

    /**
     * 在集合末尾添加数据条目
     *
     * @param item
     */
    public void addLastItem(TAdapterItem<TDataModel> item) {
        addItem(mItems.size(), item);
    }

    /**
     * 替换指定索引的数据条目
     *
     * @param location
     * @param item
     */
    public void setItem(int location, TAdapterItem<TDataModel> item) {
        mItems.set(location, item);
        notifyItemChanged(location);
    }

    /**
     * 替换指定数据条目
     *
     * @param oldItem
     * @param newItem
     */
    public void setItem(TAdapterItem<TDataModel> oldItem, TAdapterItem<TDataModel> newItem) {
        setItem(mItems.indexOf(oldItem), newItem);
    }

    /**
     * 交换两个数据条目的位置
     *
     * @param fromPosition
     * @param toPosition
     */
    public void moveItem(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }


    public void setItemEventListener(ItemEventListener listener) {
        this.mItemEventListener = listener;
    }

    @Override
    public boolean onEventNotify(String eventName, View view, int position, Object... values) {
        if (mItemEventListener != null) {
            return mItemEventListener.onEventNotify(eventName, view, position, values);
        }
        return true;
    }
}
