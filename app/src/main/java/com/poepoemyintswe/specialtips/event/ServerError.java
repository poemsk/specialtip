package com.poepoemyintswe.specialtips.event;

import retrofit.RetrofitError;

/**
 * Created by poepoe on 11/7/14.
 */
public class ServerError {
  public RetrofitError error;

  public ServerError(RetrofitError e) {
    this.error = e;
  }
}
