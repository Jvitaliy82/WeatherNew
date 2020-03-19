package com.jdeveloperapps.weather;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.jdeveloperapps.weather.retrofit.interfaces.OpenWeatherRepo;
import com.jdeveloperapps.weather.retrofit.model.WeatherRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Presenter extends AndroidViewModel {

    private static final String API_KEY = "c0d574c3889b1c91410749a8a2302d5a";

    private MutableLiveData<WeatherRequest> weatherRequest = new MutableLiveData<>();

    public Presenter(@NonNull Application application) {
        super(application);
    }

    public LiveData<WeatherRequest> getData() {
        return weatherRequest;
    }

    public void requestWeatherByCity(String cityName) {
        OpenWeatherRepo.getSingleton().getAPI_BY_CITY().loadWeather(cityName, "metric", "ru", API_KEY)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            weatherRequest.setValue(response.body());
                        } else {
                            shoeMessage("город не найден");
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        shoeMessage("нет интернета");
                    }
                });
    }

    public void requestWeatherByGPS(String lat, String lon) {
        OpenWeatherRepo.getSingleton().getAPI_BY_GPS().loadWeather(lat, lon, "metric", "ru", API_KEY)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            weatherRequest.setValue(response.body());
                        } else {
                            shoeMessage("город не найден");
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        Toast.makeText(getApplication().getApplicationContext(),
                                "нет интернета", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void shoeMessage(String message) {
        Toast.makeText(getApplication().getApplicationContext(),
                message, Toast.LENGTH_SHORT).show();
    }
}
