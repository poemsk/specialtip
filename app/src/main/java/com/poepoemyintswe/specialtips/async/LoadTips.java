package com.poepoemyintswe.specialtips.async;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;
import com.poepoemyintswe.specialtips.Config;
import com.poepoemyintswe.specialtips.adapter.FeedAdapter;
import com.poepoemyintswe.specialtips.models.Feed;
import com.poepoemyintswe.specialtips.service.GetFeed;
import com.poepoemyintswe.specialtips.util.NetworkConnectivityCheck;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by poepoe on 10/21/14.
 */
public class LoadTips {

  private final String TAG = LoadTips.class.getSimpleName();

  private ArrayList<Feed> feedItems;

  private Context mContext;

  private ListView feedListView;
  private SwipeRefreshLayout swipeRefreshLayout;

  public LoadTips(Context mContext, ListView feedListView, SwipeRefreshLayout swipeRefreshLayout) {
    this.mContext = mContext;
    this.feedListView = feedListView;
    this.swipeRefreshLayout = swipeRefreshLayout;
    feedItems = new ArrayList<Feed>();
  }

  public void StartRefreshing() {
    if (swipeRefreshLayout != null) {
      swipeRefreshLayout.setRefreshing(true);
    }
  }

  public void StopRefreshing() {
    if (swipeRefreshLayout != null) {
      swipeRefreshLayout.setRefreshing(false);
    }
  }

  public void loadTips() {
    if (NetworkConnectivityCheck.getInstance(mContext).isConnected()) {
      StartRefreshing();

      RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Config.BASE_URL)
          .setLogLevel(RestAdapter.LogLevel.BASIC)
          .build();

      GetFeed getFeed = restAdapter.create(GetFeed.class);

      getFeed.getTips(new Callback<List<Feed>>() {
        @Override
        public void success(List<Feed> feeds, Response response) {
          feedItems = (ArrayList<Feed>) feeds;
          Log.d(TAG, feeds.size() + "");
          FeedAdapter adapter = new FeedAdapter(mContext, feedItems);
          adapter.notifyDataSetChanged();
          feedListView.setAdapter(adapter);
          StopRefreshing();
        }

        @Override
        public void failure(RetrofitError error) {
          Log.d(TAG, error.toString());
          Toast.makeText(mContext, "Please Try Again....", Toast.LENGTH_SHORT).show();
          StopRefreshing();
        }
      });
    } else {
      Toast.makeText(mContext, "Please Check Your Connection....", Toast.LENGTH_SHORT).show();
    }
  }
}
