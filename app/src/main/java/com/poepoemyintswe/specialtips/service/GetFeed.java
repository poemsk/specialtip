package com.poepoemyintswe.specialtips.service;

import com.poepoemyintswe.specialtips.Config;
import com.poepoemyintswe.specialtips.models.Feed;
import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by poepoe on 10/7/14.
 */
public interface GetFeed {
  @GET(Config.FEED_URL) void getTips(Callback<List<Feed>> callback);
}
