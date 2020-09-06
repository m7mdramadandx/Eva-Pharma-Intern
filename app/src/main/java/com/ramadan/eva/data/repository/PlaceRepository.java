package com.ramadan.eva.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.ramadan.eva.data.model.Place;
import com.ramadan.eva.ui.service.ApiInterface;
import com.ramadan.eva.ui.service.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceRepository {

    private static ApiInterface apiInterface;
    static final MutableLiveData<List<Place>> placesList = new MutableLiveData<>();

    private static PlaceRepository placeRepository;

    public PlaceRepository getInstance() {
        if (placeRepository == null) {
            placeRepository = new PlaceRepository();
        }
        return placeRepository;
    }

    public PlaceRepository() {
        apiInterface = RetrofitService.getRetrofitInstance().create(ApiInterface.class);
    }

    public MutableLiveData<List<Place>> getListOfPlaces() {
        apiInterface.getPlaces().enqueue(new Callback<List<Place>>() {
            @Override
            public void onResponse(Call<List<Place>> call, Response<List<Place>> response) {
                placesList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Place>> call, Throwable t) {
                placesList.postValue(null);
            }

        });
        return placesList;
    }

}
