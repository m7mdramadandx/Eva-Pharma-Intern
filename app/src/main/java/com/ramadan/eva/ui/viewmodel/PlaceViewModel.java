package com.ramadan.eva.ui.viewmodel;

import androidx.lifecycle.LiveData;

import com.ramadan.eva.data.model.Place;
import com.ramadan.eva.data.repository.PlaceRepository;

import java.util.List;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class PlaceViewModel extends ViewModel {
    private static PlaceRepository placeRepository;

    static LiveData<List<Place>> placeLiveData;

    public PlaceViewModel() {
        placeRepository = new PlaceRepository();
        placeLiveData = placeRepository.getListOfPlaces();
    }

    public LiveData<List<Place>> getPlacesLiveData() {

        return placeLiveData;
    }



//    public LiveData<Place> loadPlaces() {
//        if (placeLiveData == null) {
//            placeLiveData = new MutableLiveData<>();
//            getListOfPlaces();
//        }
//        return placeLiveData;
//    }
//
//    public void getListOfPlaces() {
//
//
//        Call<Place> itemCall = myInterface.getPlaces();
//
//        itemCall.enqueue(new Callback<Place>() {
//            @Override
//            public void onResponse(Call<Place> call, Response<Place> response) {
//                placeLiveData.setValue(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<Place> call, Throwable t) {
//                placeLiveData.postValue(null);
//            }
//
//        });
//    }
//
//    public MutableLiveData<EntityPlace> getPlaceRepository(String category, int page) {
//        placesList = loadMoviesData(category, page);
//        return placesList;
//    }
//
//    private MutableLiveData<EntityPlace> retrievePlaceData(String category, int page) {
//        return placeRepository.getListOfPlaces(category, page);
//    }

}
