package com.poepoemyintswe.specialtips.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.poepoemyintswe.specialtips.Config;
import com.poepoemyintswe.specialtips.R;
import com.poepoemyintswe.specialtips.adapter.FeedAdapter;
import com.poepoemyintswe.specialtips.models.Feed;
import com.poepoemyintswe.specialtips.service.GetFeed;
import com.poepoemyintswe.specialtips.util.NetworkConnectivityCheck;
import com.poepoemyintswe.specialtips.util.SharePref;
import java.util.ArrayList;
import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends ActionBarActivity {

  @InjectView(R.id.feed) ListView feedListView;

  @InjectView(R.id.msg) TextView msg;

  @InjectView(R.id.swipe_to_refresh_list) SwipeRefreshLayout refresh;

  ArrayList<Feed> feedItems;

  private final String TAG = MainActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
    feedItems = new ArrayList<Feed>();

    if (SharePref.getInstance(this).isFirstTime()) {
      msg.setVisibility(View.VISIBLE);
      feedListView.setVisibility(View.GONE);
      SharePref.getInstance(this).noLongerFirstTime();
    } else {
      msg.setVisibility(View.GONE);
      feedListView.setVisibility(View.VISIBLE);
      loadTips();
    }

    refresh.setColorSchemeResources(R.color.swipe_refresh_color1, R.color.swipe_refresh_color2,
        R.color.swipe_refresh_color3, R.color.swipe_refresh_color4);

    refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        loadTips();
      }
    });
  }

  public void Refreshing() {
    if (refresh != null) {
      refresh.setRefreshing(true);
    }
  }

  public void StopRefreshing() {
    if (refresh != null) {
      refresh.setRefreshing(false);
    }
  }

  public void loadTips() {
    if (NetworkConnectivityCheck.getInstance(this).isConnected()) {

      RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Config.BASE_URL)
          .setLogLevel(RestAdapter.LogLevel.BASIC)
          .build();

      GetFeed getFeed = restAdapter.create(GetFeed.class);

      getFeed.getTips(new Callback<List<Feed>>() {
        @Override
        public void success(List<Feed> feeds, Response response) {
          feedItems = (ArrayList<Feed>) feeds;
          Log.d(TAG, feeds.size() + "");
          FeedAdapter adapter = new FeedAdapter(getApplicationContext(), feedItems);
          adapter.notifyDataSetChanged();
          feedListView.setAdapter(adapter);
          msg.setVisibility(View.GONE);
          feedListView.setVisibility(View.VISIBLE);
          StopRefreshing();
        }

        @Override
        public void failure(RetrofitError error) {
          Log.d(TAG, error.toString());
          Toast.makeText(getApplicationContext(), "Please Try Again....", Toast.LENGTH_SHORT)
              .show();
          StopRefreshing();
          msg.setVisibility(View.GONE);
          feedListView.setVisibility(View.VISIBLE);
        }
      });
    } else {
      Toast.makeText(getApplicationContext(), "Please Check Your Connection....",
          Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
