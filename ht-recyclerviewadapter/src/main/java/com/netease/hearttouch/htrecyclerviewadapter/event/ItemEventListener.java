package com.netease.hearttouch.htrecyclerviewadapter.event;

import android.view.View;

/**
 * Created by zyl06 on 10/19/15.
 */
public interface ItemEventListener {
    String clickEventName = "onClick";
    String longClickEventName = "onLongClick";
    String checkedChangedEventName = "onCheckedChanged";

    /**
     * 事件（点击，长按等）发生时的回调函数
     * @param eventName  事件的名称
     * @param view 发生事件的view
     * @param position 发生事件的item的位置
     * @param values 事件回调的参数
     * @return boolean 事件是否被消化
     */
    boolean onEventNotify(String eventName, View view, int position, Object... values);
}
