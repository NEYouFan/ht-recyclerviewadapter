package com.netease.hearttouch.recycleradapter.viewholder.item;

import com.netease.hearttouch.htrecyclerviewadapter.TAdapterItem;

import static com.netease.hearttouch.recycleradapter.viewholder.item.ViewItemType.VIEW_TYPE_INFO;

/**
 * Created by fgx on 2017/1/20.
 */

public class InfoViewHolderItem implements TAdapterItem<String> {

    private String mGoodInfo;

    public InfoViewHolderItem(String goodInfo) {
        mGoodInfo = goodInfo;
    }

    public int getViewType() {
        return VIEW_TYPE_INFO;
    }

    public int getId() {
        return 0;
    }

    public String getDataModel() {
        return mGoodInfo;
    }
}
