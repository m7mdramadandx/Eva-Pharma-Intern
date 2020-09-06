package com.ramadan.eva.ui.service;

import com.ramadan.eva.data.model.Place;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("v2/everything/")
    Call<List<Place>> getPlaces();
}
