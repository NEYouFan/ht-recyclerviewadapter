package com.netease.hearttouch.htrecyclerviewadapter.bga;

import com.netease.hearttouch.htrecyclerviewadapter.R;

/**
 * Created by zyl06 on 10/20/15.
 */
public class BGAHelper {

    public static boolean isSwipeItemLayout(int id) {
        return R.id.item_swipeitemlayout_container == id;
    }

    public static boolean isSwipeItemLayoutLeftView(int id) {
        return R.id.swipeitemlayout_left_container == id;
    }

    public static boolean isSwipeItemLayoutRightView(int id) {
        return R.id.swipeitemlayout_right_container == id;
    }
}
