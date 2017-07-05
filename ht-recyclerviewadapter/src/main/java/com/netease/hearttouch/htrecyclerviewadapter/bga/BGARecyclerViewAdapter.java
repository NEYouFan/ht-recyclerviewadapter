package com.netease.hearttouch.htrecyclerviewadapter.bga;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.netease.hearttouch.htrecyclerviewadapter.R;
import com.netease.hearttouch.htrecyclerviewadapter.TAdapterItem;
import com.netease.hearttouch.htrecyclerviewadapter.base.TBaseRecyclerViewAdapter;
import com.netease.hearttouch.htrecyclerviewadapter.util.LogUtil;

import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by zyl06 on 10/15/15.
 */
public class BGARecyclerViewAdapter<TDataModel>
        extends TBaseRecyclerViewAdapter<BGARecyclerViewHolder, TDataModel>
        implements BGASwipeItemLayout.BGASwipeItemLayoutDelegate {

    /**
     * 当前处于打开状态的item
     */

    public final int INVALID_POSITION = -1;
    protected int mOpenPosition = INVALID_POSITION;
    private Mode mode = Mode.Single;
    protected final Set<Integer> mOpenPositions = new HashSet<>();
    protected final Set<BGASwipeItemLayout> mShownLayouts = new HashSet<>();

    private BGASwipeItemLayout.BGASwipeItemLayoutDelegate itemDelegate;

    public BGARecyclerViewAdapter(Context context,
                                  SparseArray<Class<BGARecyclerViewHolder>> viewHolders,
                                  List<TAdapterItem<TDataModel>> items) {

        super(context, viewHolders, items);
    }

    @Override
    public void onBindViewHolder(BGARecyclerViewHolder holder, int position) {
        TAdapterItem<TDataModel> item = mItems.get(position);
        holder.refresh(item);
    }

    @Override
    public BGARecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BGARecyclerViewHolder recycleViewHolder = null;
        try {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            BGASwipeItemLayout swipeItemLayout = (BGASwipeItemLayout) inflater.inflate(R.layout.item_gba_recycleview_container, parent, false);

            Class<? extends BGARecyclerViewHolder> viewHolder = mViewHolders.get(viewType);
            BGARecyclerViewHolderAnnotation annotation = viewHolder.getAnnotation(BGARecyclerViewHolderAnnotation.class);
            View leftContent = inflater.inflate(annotation.leftLayoutId(), null);
            View rightContent = inflater.inflate(annotation.rightLayoutId(), null);

            Constructor constructor = viewHolder.getConstructor(BGASwipeItemLayout.class, View.class, View.class, Context.class, RecyclerView.class);
            recycleViewHolder = (BGARecyclerViewHolder) (constructor.newInstance(swipeItemLayout, leftContent, rightContent, parent.getContext(), (RecyclerView) parent));
            recycleViewHolder.setItemEventListener(this);
            recycleViewHolder.setSwipeItemDelegate(this);

            initSwipeItemLayout(swipeItemLayout, recycleViewHolder);

            mShownLayouts.add(swipeItemLayout);

        } catch (Exception e) {
            LogUtil.logE(e.getMessage());
            e.printStackTrace();
        }
        return recycleViewHolder;
    }

    private void initSwipeItemLayout(BGASwipeItemLayout swipeItemLayout,
                                     BGARecyclerViewHolder viewHolder) {
        swipeItemLayout.setSwipeable(viewHolder.getSwipeable());
        swipeItemLayout.setSwipeDirection(viewHolder.getSwipeDirection());
        swipeItemLayout.setBottomModel(viewHolder.getBottomModel());
        swipeItemLayout.setSpringDistance(viewHolder.getSpringDistance());
        swipeItemLayout.setFadeInOrOut(viewHolder.getFadeInOrOut());

        LinearLayout leftView = (LinearLayout) swipeItemLayout.findViewById(R.id.swipeitemlayout_left_container);
        LinearLayout rightView = (LinearLayout) swipeItemLayout.findViewById(R.id.swipeitemlayout_right_container);
        initLayoutParams(leftView, rightView, viewHolder.getSwipeDirection());
    }

    private void initLayoutParams(ViewGroup leftView,
                                  ViewGroup rightView,
                                  BGASwipeItemLayout.SwipeDirection swipeDirection) {
        final int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;
        final int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;

        if (swipeDirection.equals(BGASwipeItemLayout.SwipeDirection.Left)) {
            ViewGroup.LayoutParams leftLP = leftView.getLayoutParams();
            leftLP.width = matchParent;
            leftLP.height = matchParent;
            leftView.setLayoutParams(leftLP);

            ViewGroup.LayoutParams rightLP = rightView.getLayoutParams();
            rightLP.width = wrapContent;
            rightLP.height = matchParent;
            rightView.setLayoutParams(rightLP);
        } else if (swipeDirection.equals(BGASwipeItemLayout.SwipeDirection.Right)) {
            ViewGroup.LayoutParams leftLP = leftView.getLayoutParams();
            leftLP.width = wrapContent;
            leftLP.height = matchParent;
            leftView.setLayoutParams(leftLP);

            ViewGroup.LayoutParams rightLP = rightView.getLayoutParams();
            rightLP.width = matchParent;
            rightLP.height = matchParent;
            rightView.setLayoutParams(rightLP);
        }
    }

    public void setItemDelegate(BGASwipeItemLayout.BGASwipeItemLayoutDelegate delegate) {
        this.itemDelegate = delegate;
    }

    @Override
    public void onBGASwipeItemLayoutOpened(BGASwipeItemLayout swipeItemLayout, int position) {

        if (mode == Mode.Multiple) {
            if (!mOpenPositions.contains(position)) {
                mOpenPositions.add(position);
            }
        } else {
            mOpenPosition = position;
            closeAllExcept(swipeItemLayout);
        }
    }

    @Override
    public void onBGASwipeItemLayoutClosed(BGASwipeItemLayout swipeItemLayout, int position) {

        if (mode == Mode.Multiple) {
            mOpenPositions.remove(position);
        } else {
            if (mOpenPosition == position) {
                mOpenPosition = INVALID_POSITION;
            }
        }
    }

    @Override
    public void onBGASwipeItemLayoutStartOpen(BGASwipeItemLayout swipeItemLayout, int position) {
        if (mode == Mode.Single) {
            closeAllExcept(swipeItemLayout);
        }
    }


    public void removeShownLayouts(BGASwipeItemLayout layout) {
        mShownLayouts.remove(layout);
    }

    public void closeAllExcept(BGASwipeItemLayout layout) {
        for (BGASwipeItemLayout s : mShownLayouts) {
            if (s != layout)
                s.closeWithAnim();
        }
    }

    public void closeAllItemsWithAnim() {
        if (mode == Mode.Multiple) {
            mOpenPositions.clear();
        } else {
            mOpenPosition = INVALID_POSITION;
        }
        for (BGASwipeItemLayout s : mShownLayouts) {
            s.closeWithAnim();
        }
    }

    public void closeAllItems() {
        if (mode == Mode.Multiple) {
            mOpenPositions.clear();
        } else {
            mOpenPosition = INVALID_POSITION;
        }
        for (BGASwipeItemLayout s : mShownLayouts) {
            s.close();
        }
    }

    /**
     *设置左滑控件的模式:单个滑动或者多个滑动
     * @param mode Single or Multiple
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public enum Mode {
        Single, Multiple
    }
}

