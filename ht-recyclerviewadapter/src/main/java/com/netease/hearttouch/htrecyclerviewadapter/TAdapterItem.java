package com.netease.hearttouch.htrecyclerviewadapter;

/**
 * Created by zhengwen on 15-6-10.
 */
public interface TAdapterItem<T> {
    public int getViewType();
    public int getId();
    public T getDataModel();
}
