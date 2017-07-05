package com.netease.hearttouch.htrecyclerviewadapter.bga;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.netease.hearttouch.htrecyclerviewadapter.R;
import com.netease.hearttouch.htrecyclerviewadapter.base.TBaseRecyclerViewHolder;
import com.netease.hearttouch.htrecyclerviewadapter.bga.BGASwipeItemLayout.BottomModel;
import com.netease.hearttouch.htrecyclerviewadapter.bga.BGASwipeItemLayout.SwipeDirection;

/**
 * Created by zyl06 on 10/15/15.
 */

@BGARecyclerViewHolderAnnotation()
public abstract class BGARecyclerViewHolder<TDataModel>
        extends TBaseRecyclerViewHolder<BGASwipeItemLayout, TDataModel>
        implements BGASwipeItemLayout.BGASwipeItemLayoutDelegate {

    protected LinearLayout leftView;
    protected LinearLayout rightView;
    private BGASwipeItemLayout.BGASwipeItemLayoutDelegate itemLayoutDelegate;

    public BGARecyclerViewHolder(BGASwipeItemLayout itemView,
                                 View leftContent,
                                 View rightContent,
                                 Context context,
                                 RecyclerView recyclerView) {
        super(itemView, context, recyclerView);

        view.setDelegate(this);
        leftView = (LinearLayout) itemView.findViewById(R.id.swipeitemlayout_left_container);
        leftView.addView(leftContent, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        rightView = (LinearLayout) itemView.findViewById(R.id.swipeitemlayout_right_container);
        rightView.addView(rightContent, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

        inflate();
    }

    /**
     * 通过重写修改类型
     * 判断是否支持滑动
     *
     * @return boolean
     * true : 支持滑动
     * false : 不支持滑动
     */
    public boolean getSwipeable() {
        return false;
    }

    /**
     * 通过重写修改类型
     * 判断滑动方向，默认左滑
     *
     * @return SwipeDirection
     * SwipeDirection.Left : 左滑
     * SwipeDirection.Right : 右滑
     */
    public SwipeDirection getSwipeDirection() {
        return SwipeDirection.Left;
    }


    /**
     * 移动过程中,底部视图时候附带淡入淡出效果
     * @return
     */
    public boolean getFadeInOrOut() {
        return true;
    }

    /**
     * 通过重写修改类型
     * 移动过程中，底部视图的移动方式，默认是拉出
     *
     * @return SwipeDirection
     * BottomModel.PullOut : 拉出
     * SwipeDirection.LayDown : 被顶部视图遮住
     */
    public BottomModel getBottomModel() {
        return BottomModel.PullOut;
    }

    /**
     * 通过重写修改类型
     * 拖动的弹簧距离，默认为0
     *
     * @return int
     */
    public int getSpringDistance() {
        return 0;
    }

    void setSwipeItemDelegate(BGASwipeItemLayout.BGASwipeItemLayoutDelegate delegate) {
        itemLayoutDelegate = delegate;
    }

    @Override
    public void onBGASwipeItemLayoutOpened(BGASwipeItemLayout swipeItemLayout, int position) {
        if (itemLayoutDelegate != null) {
            itemLayoutDelegate.onBGASwipeItemLayoutOpened(swipeItemLayout, getAdapterPosition());
        }
    }

    @Override
    public void onBGASwipeItemLayoutClosed(BGASwipeItemLayout swipeItemLayout, int position) {
        if (itemLayoutDelegate != null) {
            itemLayoutDelegate.onBGASwipeItemLayoutClosed(swipeItemLayout, getAdapterPosition());
        }
    }

    @Override
    public void onBGASwipeItemLayoutStartOpen(BGASwipeItemLayout swipeItemLayout, int position) {
        if (itemLayoutDelegate != null) {
            itemLayoutDelegate.onBGASwipeItemLayoutStartOpen(swipeItemLayout, getAdapterPosition());
        }
    }

    public void setOnDragRadioChangedListener(OnDragRadioChangedListener listener) {
        view.setOnDragRadiaoChangedListener(listener);
    }
}
