package com.poepoemyintswe.specialtips.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.poepoemyintswe.specialtips.R;
import com.poepoemyintswe.specialtips.fragment.FeedFragment;

public class MainActivity extends ActionBarActivity {

  private final String TAG = MainActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction().add(R.id.container, new FeedFragment()).commit();
    }
  }
}
