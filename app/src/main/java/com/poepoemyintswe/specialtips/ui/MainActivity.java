package com.poepoemyintswe.specialtips.ui;

import android.os.Bundle;
import com.poepoemyintswe.specialtips.R;
import com.poepoemyintswe.specialtips.fragment.FeedFragment;

public class MainActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setActionBarIcon(R.drawable.ic_launcher);

    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction().add(R.id.container, new FeedFragment()).commit();
    }
  }

  @Override protected void setActionBarIcon(int iconRes) {
    super.setActionBarIcon(iconRes);
  }

  @Override protected int getLayoutResource() {
    return R.layout.activity_main;
  }
}
