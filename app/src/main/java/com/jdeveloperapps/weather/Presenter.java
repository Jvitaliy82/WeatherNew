package com.jdeveloperapps.weather;

import android.app.Application;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
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
    private MutableLiveData<Boolean> refreshStatus = new MutableLiveData<>();

    public Presenter(@NonNull Application application) {
        super(application);
    }

    public LiveData<WeatherRequest> getWeatherData() {
        return weatherRequest;
    }

    public LiveData<Boolean> getRefreshStatus() {
        return refreshStatus;
    }

    public void requestWeatherByCity(String cityName) {
        refreshStatus.setValue(true);
        OpenWeatherRepo.getSingleton().getAPI_BY_CITY().loadWeather(cityName, "metric", "ru", API_KEY)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            weatherRequest.setValue(response.body());
                        } else {
                            showMessage("город не найден");
                        }
                        refreshStatus.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        showMessage("нет интернета");
                        refreshStatus.setValue(false);
                    }
                });
    }

    public void requestWeatherByGPS(String lat, String lon) {
        refreshStatus.setValue(true);
        OpenWeatherRepo.getSingleton().getAPI_BY_GPS().loadWeather(lat, lon, "metric", "ru", API_KEY)
                .enqueue(new Callback<WeatherRequest>() {
                    @Override
                    public void onResponse(Call<WeatherRequest> call, Response<WeatherRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            weatherRequest.setValue(response.body());
                        } else {
                            showMessage("город не найден");
                        }
                        refreshStatus.setValue(false);
                    }

                    @Override
                    public void onFailure(Call<WeatherRequest> call, Throwable t) {
                        showMessage("нет интернета");
                        refreshStatus.setValue(false);
                    }
                });
    }

    private void showMessage(String message) {
        Toast toast = Toast.makeText(getApplication().getApplicationContext(),
                message, Toast.LENGTH_LONG);

        View toastView = toast.getView();
        TextView toastMessage = toastView.findViewById(android.R.id.message);

        toastView.setBackgroundColor(Color.YELLOW);
        toastMessage.setTextColor(Color.BLACK);
        toastView.setPadding(10, 10, 10, 10);

        toast.show();
    }
}
