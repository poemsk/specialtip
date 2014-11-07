package com.poepoemyintswe.specialtips.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.poepoemyintswe.specialtips.Config;
import com.poepoemyintswe.specialtips.R;
import com.poepoemyintswe.specialtips.adapter.FeedAdapter;
import com.poepoemyintswe.specialtips.event.BusProvider;
import com.poepoemyintswe.specialtips.event.NetworkError;
import com.poepoemyintswe.specialtips.event.ServerError;
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
public class FeedFragment extends Fragment {

  private final String TAG = FeedFragment.class.getSimpleName();
  @InjectView(R.id.feed) ListView feedListView;
  @InjectView(R.id.swipe_to_refresh_list) SwipeRefreshLayout swipeRefreshLayout;
  private ArrayList<Feed> feedItems;

  public FeedFragment() {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Override
  public void onStart() {
    super.onStart();
    loadTips();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                     Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

    ButterKnife.inject(this, rootView);
    swipeRefreshLayout.setColorSchemeResources(R.color.swipe_refresh_color1,
                                                  R.color.swipe_refresh_color2,
                                                  R.color.swipe_refresh_color3,
                                                  R.color.swipe_refresh_color4);

    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        loadTips();
      }
    });

    return rootView;
  }

  @Override public void onResume() {
    super.onResume();
    BusProvider.getInstance().register(this);
  }

  @Override public void onPause() {
    super.onPause();
    BusProvider.getInstance().unregister(this);
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
    if (NetworkConnectivityCheck.getInstance(getActivity()).isConnected()) {
      feedItems = new ArrayList<Feed>();
      StartRefreshing();
      RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(Config.BASE_URL)
                                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                                    .build();

      GetFeed getFeed = restAdapter.create(GetFeed.class);

      getFeed.getTips(new Callback<List<Feed>>() {
        @Override
        public void success(List<Feed> feeds, Response response) {
          feedItems = (ArrayList<Feed>) feeds;
          FeedAdapter adapter = new FeedAdapter(getActivity(), feedItems);
          adapter.notifyDataSetChanged();
          feedListView.setAdapter(adapter);
          StopRefreshing();
        }

        @Override
        public void failure(RetrofitError error) {
          Log.d(TAG, error.toString());
          StopRefreshing();
          ReplaceCurrentFragment();
          BusProvider.getInstance().post(new ServerError(R.string.swipe_to_refresh));
        }
      });
    } else {
      ReplaceCurrentFragment();
      BusProvider.getInstance().post(new NetworkError(R.string.check_connection));
    }
  }

  public void ReplaceCurrentFragment() {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    SwipeFragment fragment = new SwipeFragment();
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.hide(FeedFragment.this);
    fragmentTransaction.add(R.id.container, fragment).commitAllowingStateLoss();
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.main, menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_refresh:
        loadTips();
        return true;
      case R.id.action_info:
        show();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void show() {
    WebView webView = new WebView(getActivity());
    webView.loadUrl("file:///android_asset/about.html");
    new AlertDialog.Builder(getActivity()).setTitle(R.string.action_info)
        .setView(webView)
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {
            dialog.dismiss();
          }
        })
        .create()
        .show();
  }
}
