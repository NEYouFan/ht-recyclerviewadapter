package com.netease.hearttouch.recycleradapter.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.hearttouch.htrecyclerviewadapter.TAdapterItem;
import com.netease.hearttouch.htrecyclerviewadapter.TRecyclerViewHolder;
import com.netease.hearttouch.htrecyclerviewadapter.TRecyclerViewHolderAnnotation;
import com.netease.hearttouch.recycleradapter.R;

/**
 * Created by fgx on 2017/1/20.
 */
@TRecyclerViewHolderAnnotation(resId = R.layout.item_info)
public class InfoViewHolder extends TRecyclerViewHolder<String> {
    private ImageView mImgPic;
    private TextView mTvName;
    private TextView mTvPrice;

    public InfoViewHolder(View itemView, Context context, RecyclerView recyclerView) {
        super(itemView, context, recyclerView);
    }

    public void inflate() {
        mImgPic = (ImageView) view.findViewById(R.id.img_pic);
        mTvName = (TextView) view.findViewById(R.id.tv_name);
        mTvPrice = (TextView) view.findViewById(R.id.tv_price);
    }

    public void refresh(TAdapterItem<String> tAdapterItem) {
        if (tAdapterItem == null || tAdapterItem.getDataModel() == null) return;
        String model = tAdapterItem.getDataModel();
        mTvName.setText(model);
        mTvPrice.setText(String.format("¥%d", 100));
        mImgPic.setImageResource(R.mipmap.ic_launcher);
//        if (!TextUtils.isEmpty(goodInfo.getPic())) {
//            NosGlideUtil.setImageViewTarget(context, mImgPic, goodInfo.getPic());
//        }
    }
}
