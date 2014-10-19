package com.poepoemyintswe.specialtips.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.poepoemyintswe.specialtips.R;
import com.poepoemyintswe.specialtips.models.Feed;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
    holder.date.setText(changeDateFormat(feedItem.date));
    holder.content.setText(feedItem.content);
    holder.share.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Share(feedItem.permalink);
      }
    });

    return view;
  }

  //Share intent inside BaseAdapter
  public void Share(String url) {
    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
    sharingIntent.setType("text/plain");
    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, url);
    Intent createChooser = Intent.createChooser(sharingIntent, "Share via");
    createChooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(createChooser);
  }

  public String changeDateFormat(String d) {
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
    try {
      Date date = sdf1.parse(d);
      String formatted_date = sdf.format(date);
      return formatted_date;
    } catch (ParseException e) {
      return d;
    }
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

    @InjectView(R.id.title) TextView title;
    @InjectView(R.id.date) TextView date;
    @InjectView(R.id.content) TextView content;

    @InjectView(R.id.share_btn) Button share;

    public ViewHolder(View view) {
      ButterKnife.inject(this, view);
    }
  }
}
