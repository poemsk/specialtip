package com.poepoemyintswe.specialtips.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.poepoemyintswe.specialtips.R;

/**
 * Created by poepoe on 10/21/14.
 */
public class SwipeFragment extends Fragment {
  @InjectView(R.id.swipe_to_refresh_text) SwipeRefreshLayout refreshLayout;
  @InjectView(R.id.msg) TextView msg;

  public SwipeFragment() {

  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.fragment_swipe, container, false);
    ButterKnife.inject(this, rootView);

    msg.setText(R.string.swipe_to_refresh);

    refreshLayout.setColorSchemeResources(R.color.swipe_refresh_color1,
        R.color.swipe_refresh_color2, R.color.swipe_refresh_color3, R.color.swipe_refresh_color4);

    refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        FeedFragment fragment = new FeedFragment();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.hide(SwipeFragment.this);
        fragmentTransaction.add(R.id.container, fragment).commit();
      }
    });

    return rootView;
  }
}
