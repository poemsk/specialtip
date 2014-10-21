package com.poepoemyintswe.specialtips.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.poepoemyintswe.specialtips.R;
import com.poepoemyintswe.specialtips.async.LoadTips;

/**
 * Created by poepoe on 10/21/14.
 */
public class FeedFragment extends Fragment {

  @InjectView(R.id.feed) ListView feedListView;
  @InjectView(R.id.swipe_to_refresh_list) SwipeRefreshLayout refresh;

  private LoadTips loadTips;

  public FeedFragment() {

  }

  @Override
  public void onStart() {
    super.onStart();
    fetchFeed();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

    ButterKnife.inject(this, rootView);
    refresh.setColorSchemeResources(R.color.swipe_refresh_color1, R.color.swipe_refresh_color2,
        R.color.swipe_refresh_color3, R.color.swipe_refresh_color4);

    refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        fetchFeed();
      }
    });

    return rootView;
  }

  private void fetchFeed() {
    loadTips = new LoadTips(getActivity(), feedListView, refresh);
    loadTips.loadTips();
  }
}
