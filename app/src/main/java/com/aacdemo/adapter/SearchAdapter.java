package com.aacdemo.adapter;

import android.arch.persistence.room.util.StringUtil;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aacdemo.R;
import com.aacdemo.entity.Location;
import com.aacdemo.view.SearchClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/30.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{
    private Context mContext;
    private List<Location> mList;
    private SearchClickListener listener;

    public void setSearchClickListener(SearchClickListener listener) {
        this.listener = listener;
    }

    public SearchAdapter(Context mContext, List<Location> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false);
        final SearchViewHolder viewHolder = new SearchViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.favoriteImg, (Integer) v.getTag());
            }
        });
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        Location location = mList.get(position);
        holder.cityName.setText(location.getName());
        holder.cityAddress.setText(location.getPath());
        if(TextUtils.isEmpty(location.getTimezone_offset())){
            holder.cityTimeZone.setVisibility(View.GONE);
        } else {
            holder.cityTimeZone.setVisibility(View.VISIBLE);
            holder.cityTimeZone.setText(mContext.getString(R.string.format_timezone, location.getTimezone_offset()));
        }
        holder.favoriteImg.setSelected(location.isFavorite());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.city_name)
        TextView cityName;
        @BindView(R.id.city_address)
        TextView cityAddress;
        @BindView(R.id.city_timezone)
        TextView cityTimeZone;
        @BindView(R.id.favorite_img)
        ImageView favoriteImg;

        public SearchViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
