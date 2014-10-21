package com.poepoemyintswe.specialtips.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Add this method for this fragment to handle the menu
    setHasOptionsMenu(true);
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
        ReplaceCurrentFragment();
      }
    });

    return rootView;
  }

  private void ReplaceCurrentFragment() {
    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
    FeedFragment fragment = new FeedFragment();
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.hide(SwipeFragment.this);
    fragmentTransaction.add(R.id.container, fragment).commit();
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
        ReplaceCurrentFragment();
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
