package com.netease.hearttouch.htrecyclerviewadapter.event;

/**
 * Created by zyl06 on 10/20/15.
 */
public class ItemEventHelper {
    public static boolean isClick(String eventName) {
        return ItemEventListener.clickEventName.equals(eventName);
    }

    public static boolean isLongClick(String eventName) {
        return ItemEventListener.longClickEventName.equals(eventName);
    }

    public static boolean isCheckedChanged(String eventName) {
        return ItemEventListener.checkedChangedEventName.equals(eventName);
    }

}
