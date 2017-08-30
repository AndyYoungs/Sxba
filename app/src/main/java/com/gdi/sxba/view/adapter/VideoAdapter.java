package com.gdi.sxba.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdi.sxba.R;
import com.gdi.sxba.contract.OnItemClickLitener;
import com.gdi.sxba.model.bean.NovelBean;
import com.gdi.sxba.model.bean.VideoBean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    List<VideoBean.VideoData> sxList;
    Context mContext;
    OnItemClickLitener mOnItemClickLitener;

    public VideoAdapter(Context context, List<VideoBean.VideoData> sxList) {
        this.mContext = context;
        this.sxList = sxList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (sxList != null) {

            VideoBean.VideoData videoData = sxList.get(position);
            holder.tvClassify.setText(videoData.getClassify());
            holder.tvTitle.setText(videoData.getTitle());

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
        return (sxList != null) ? sxList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvClassify;

        public ViewHolder(View itemView) {
            super(itemView);
            tvClassify = (TextView) itemView.findViewById(R.id.tv_classify);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
