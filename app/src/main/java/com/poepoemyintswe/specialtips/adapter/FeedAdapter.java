package com.poepoemyintswe.specialtips.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.poepoemyintswe.specialtips.R;
import com.poepoemyintswe.specialtips.models.Feed;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by poepoe on 10/7/14.
 */
public class FeedAdapter extends BaseAdapter {
    Context context;
    ArrayList<Feed> feedList;

    public FeedAdapter(Context context, ArrayList<Feed> feedList) {
        this.context = context;
        this.feedList = feedList;
    }

    public View getView(int position, View convertView, ViewGroup paramViewGroup) {
        final ViewHolder holder;
        View view = convertView;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_item, null, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        final Feed feedItem = (Feed) getItem(position);
        holder.title.setText(feedItem.title);
        holder.date.setText(feedItem.date);
        holder.content.setText(feedItem.content);

        return view;
    }

    @Override
    public int getCount() {
        return feedList.size();
    }

    @Override
    public Object getItem(int position) {
        return feedList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return feedList.indexOf(getItem(position));
    }

    public static class ViewHolder {

        @InjectView(R.id.title)
        TextView title;
        @InjectView(R.id.date)
        TextView date;
        @InjectView(R.id.content)
        TextView content;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
