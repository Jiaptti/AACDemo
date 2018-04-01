package com.aacdemo.adapter;

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
import com.aacdemo.entity.WeatherEntity;
import com.aacdemo.utils.DateUtil;
import com.aacdemo.view.MyLongClickListener;
import com.aacdemo.view.MyClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/28.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private static final int INVALID_ID = -1;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_NORMAL = 3;

    private int mHeaderRes = INVALID_ID;
    private int mFooterRes = INVALID_ID;
    private Context mContext;
    private List<WeatherEntity> mList;
    private View mHeaderView;
    private View mFooterView;

    private MyClickListener mClickListener;
    private MyLongClickListener mLongClickListener;

    public MainAdapter(Context context, List<WeatherEntity> mList) {
        this.mContext = context;
        this.mList = mList;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (mHeaderRes != INVALID_ID && viewType == TYPE_HEADER) {
            view = LayoutInflater.from(mContext).inflate(mHeaderRes, parent, false);
            mHeaderView = view;
        } else if (mFooterRes != INVALID_ID && viewType == TYPE_FOOTER) {
            view = LayoutInflater.from(mContext).inflate(mFooterRes, parent, false);
            mFooterView = view;
        } else {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_main, parent, false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onClick(v, (int) v.getTag());
                    }
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mLongClickListener != null) {
                        mLongClickListener.onLongClick(v, (int) v.getTag());
                        return true;
                    }
                    return false;
                }
            });
        }
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_HEADER) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            String week = DateUtil.getWeek(mContext, calendar);
            int noonResId;
            if (hour < 12) {
                noonResId = R.string.morning;
            } else if (hour == 12) {
                noonResId = R.string.noon;
            } else if (hour > 12 && hour < 18) {
                noonResId = R.string.afternoon;
            } else {
                noonResId = R.string.night;
            }
            holder.date.setText(mContext.getString(R.string.format_header, mContext.getString(noonResId), year, month, day, week));
        } else if (type == TYPE_NORMAL) {
            WeatherEntity entity;
            if (mHeaderRes == INVALID_ID) {
                entity = mList.get(position);
                holder.itemView.setTag(position);
            } else {
                entity = mList.get(position - 1);
                holder.itemView.setTag(position - 1);
            }
            if (entity.getLocation() != null) {
                holder.name.setText(entity.getLocation().getName());
                holder.addr.setText(entity.getLocation().getPath());
            } else {
                holder.name.setText(R.string.default_name);
                holder.addr.setText(R.string.default_text);
            }
            if (entity.getNow() != null) {
                holder.desc.setText(entity.getNow().getText());
                holder.temperature.setText(entity.getNow().getTemperature() + "℃");
                String imgRes = "ic_weather_" + entity.getNow().getCode();
                int resId = mContext.getResources().getIdentifier(imgRes, "mipmap", mContext.getPackageName());
                Glide.with(mContext).load(imgRes)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .placeholder(R.mipmap.ic_weather_99)
                        .error(R.mipmap.ic_weather_99)
                        .into(holder.image);
            } else {
                holder.desc.setText(R.string.default_text);
                holder.temperature.setText(R.string.default_temperature);
                holder.image.setImageResource(R.mipmap.ic_weather_99);
            }
            if (!TextUtils.isEmpty(entity.getLast_update())) {
                String last_update = entity.getLast_update();
                holder.time.setText(mContext.getString(R.string.format_update, last_update.substring(11, 16)));
            } else {
                holder.time.setText(mContext.getString(R.string.format_update, mContext.getString(R.string.default_time)));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mHeaderRes == INVALID_ID && mFooterRes == INVALID_ID) {
            return mList.size();
        } else if (mFooterRes == INVALID_ID) {
            return mList.size() + 1;
        } else if (mHeaderRes == INVALID_ID) {
            return mList.size() + 1;
        } else {
            return mList.size() + 2;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderRes == INVALID_ID && mFooterRes == INVALID_ID) {
            return TYPE_NORMAL;
        } else if (mFooterRes == INVALID_ID) {//header
            if (position == 0) {
                return TYPE_HEADER;
            } else {
                return TYPE_NORMAL;
            }
        } else if (mHeaderRes == INVALID_ID) {
            if (position == getItemCount() - 1) {//footer
                return TYPE_FOOTER;
            } else {
                return TYPE_NORMAL;
            }
        } else {//header 和 footer
            if (position == 0) {
                return TYPE_HEADER;
            } else if (position == getItemCount() - 1) {
                return TYPE_FOOTER;
            } else {
                return TYPE_NORMAL;
            }
        }
    }

    public void setHeaderRes(int mHeaderRes) {
        this.mHeaderRes = mHeaderRes;
        notifyItemInserted(0);
    }

    public void setFooterRes(int mFooterRes) {
        this.mFooterRes = mFooterRes;
        notifyItemInserted(getItemCount() - 1);
    }

    public void setClickListener(MyClickListener mClickListener) {
        this.mClickListener = mClickListener;
    }

    public void setLongClickListener(MyLongClickListener mLongClickListener) {
        this.mLongClickListener = mLongClickListener;
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView addr;
        TextView desc;
        TextView temperature;
        TextView time;
        ImageView image;
        TextView date;

        MainViewHolder(View view) {
            super(view);
            if (itemView == mHeaderView) {
                date = (TextView) view.findViewById(R.id.header_date);
                return;
            }
            if (itemView == mFooterView) {
                return;
            }
            name = (TextView) view.findViewById(R.id.weather_location);
            addr = (TextView) view.findViewById(R.id.weather_address);
            desc = (TextView) view.findViewById(R.id.weather_desc);
            time = (TextView) view.findViewById(R.id.update_time);
            temperature = (TextView) view.findViewById(R.id.temperature);
            image = (ImageView) view.findViewById(R.id.weather_img);
        }
    }
}
