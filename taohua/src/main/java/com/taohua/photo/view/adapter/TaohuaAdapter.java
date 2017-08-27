package com.taohua.photo.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.taohua.photo.R;
import com.taohua.photo.contract.OnItemClickLitener;
import com.taohua.photo.model.bean.TaohuaBean;


import java.util.List;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class TaohuaAdapter extends RecyclerView.Adapter<TaohuaAdapter.ViewHolder> {

    List<TaohuaBean.TaohuaData> taohuaList;
    Context mContext;
    OnItemClickLitener mOnItemClickLitener;

    public TaohuaAdapter(Context context, List<TaohuaBean.TaohuaData> taohuaList) {
        this.mContext = context;
        this.taohuaList = taohuaList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_taohua, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (taohuaList != null) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.height = (int) (550 + Math.random() * 200);
            holder.itemView.setLayoutParams(layoutParams);

            final TaohuaBean.TaohuaData data = taohuaList.get(position);
            if (data != null) {
                if (!TextUtils.isEmpty(data.getImg())) {
//                    Glide.with(mContext).load("http://s6.sinaimg.cn/mw690/001pPXi2gy6W1B9sLE9c5&690").apply(new RequestOptions().centerCrop()).into(holder.ivImg);
                    Glide.with(mContext).load(data.getImg())
                            .transition(new DrawableTransitionOptions().crossFade(500))
                            .thumbnail(0.3f)
                            .apply(new RequestOptions().centerCrop()).into(holder.ivImg);
                }
                holder.tvTitle.setText(data.getTitle());
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.setOnItemClickLitener(view, position);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return (taohuaList != null) ? taohuaList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImg;
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImg = (ImageView) itemView.findViewById(R.id.iv_img);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
