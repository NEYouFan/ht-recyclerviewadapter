package com.netease.hearttouch.htrecyclerviewadapter.event;

import android.view.View;
import android.widget.CompoundButton;

/**
 * Created by zyl06 on 10/19/15.
 */
public class SimpleItemEventListener implements ItemEventListener {

    protected boolean onClick(View v, int position) {
        return true;
    }

    protected boolean onLongClick(View v, int position) {
        return true;
    }

    protected boolean onCheckedChanged(CompoundButton buttonView,
                                       boolean isChecked,
                                       int position) {
        return true;
    }

    @Override
    public final boolean onEventNotify(String eventName, View v, int position, Object... values) {
        if (ItemEventHelper.isClick(eventName)) {
            return onClick(v, position);
        } else if (ItemEventHelper.isLongClick(eventName)) {
            return onLongClick(v, position);
        } else if (ItemEventHelper.isCheckedChanged(eventName)) {
            return onCheckedChanged((CompoundButton)v, (Boolean)values[0], position);
        }

        return true;
    }
}
