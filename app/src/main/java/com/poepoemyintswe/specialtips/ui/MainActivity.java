package com.poepoemyintswe.specialtips.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.poepoemyintswe.specialtips.R;
import com.poepoemyintswe.specialtips.models.Feed;
import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {

  @InjectView(R.id.swipe_to_refresh_list) SwipeRefreshLayout refresh;

  ArrayList<Feed> feedItems;

  private final String TAG = MainActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.inject(this);
    feedItems = new ArrayList<Feed>();

    refresh.setColorSchemeResources(R.color.swipe_refresh_color1, R.color.swipe_refresh_color2,
        R.color.swipe_refresh_color3, R.color.swipe_refresh_color4);

    refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {

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
