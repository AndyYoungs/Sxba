package com.taohua.photo.view.adapter;

import android.content.Context;
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

import uk.co.senab.photoview.PhotoView;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class TaohuaPhotoAdapter extends RecyclerView.Adapter<TaohuaPhotoAdapter.ViewHolder> {

    List<String> taohuaList;
    Context mContext;
    OnItemClickLitener mOnItemClickLitener;

    public TaohuaPhotoAdapter(Context context, List<String> taohuaList) {
        this.mContext = context;
        this.taohuaList = taohuaList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_taohua_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (taohuaList != null) {

            Glide.with(mContext).load(taohuaList.get(position))
                    .transition(new DrawableTransitionOptions().crossFade(500))
                    .thumbnail(0.3f)
                    .apply(new RequestOptions().centerCrop()).into(holder.ivImg);
//            Glide.with(mContext).load("http://s6.sinaimg.cn/mw690/001pPXi2gy6W1B9sLE9c5&690").apply(new RequestOptions().centerCrop()).into(holder.ivImg);

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

        public ViewHolder(View itemView) {
            super(itemView);
            ivImg = (ImageView) itemView.findViewById(R.id.iv_photo);
        }
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
