package com.jdeveloperapps.weather;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.jdeveloperapps.weather.customViews.MyCard;
import com.jdeveloperapps.weather.retrofit.model.WeatherRequest;

public class MainActivity extends AppCompatActivity {

    private Presenter presenter;
    private TextView city;
    private TextView temp;
    private TextView desc;
    private MyCard myCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        city = findViewById(R.id.city);
        temp = findViewById(R.id.temperature);
        desc = findViewById(R.id.description);
        myCard = findViewById(R.id.myCard);

        presenter = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(Presenter.class);
        LiveData<WeatherRequest> liveData = presenter.getData();
        liveData.observe(this, weatherRequest -> {
            opdateWeather(weatherRequest);
        });

    }

    private void opdateWeather(WeatherRequest weatherRequest) {
        city.setText(weatherRequest.city.name);
        temp.setText(prepareTemp(weatherRequest.listMassives[0].main.temp));
        desc.setText(weatherRequest.listMassives[0].weather[0].description);
        myCard.setSpeedWind(prepareSpeedWind(weatherRequest.listMassives[0].wind.speed));
        myCard.setHumidity(weatherRequest.listMassives[0].main.humidity + " %");
        myCard.setTemp(prepareTemp(weatherRequest.listMassives[0].main.feels_like));
        myCard.setPressure(preparePressure(weatherRequest.listMassives[0].main.pressure));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.requestWeatherByCity(query);
                searchView.setQuery("", false);
                searchView.clearFocus();
                searchView.setIconified(true);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private String prepareTemp(float f) {
        StringBuilder sb = new StringBuilder();
        if (f > 0) sb.append("+");
        sb.append(Math.round(f));
        sb.append(" °C");
        return sb.toString();
    }

    private String prepareSpeedWind(float f) {
        StringBuilder sb = new StringBuilder();
        sb.append(Math.round(f));
        sb.append(" m/s");
        return sb.toString();
    }

    private String preparePressure(String pressure) {
        StringBuilder sb = new StringBuilder();
        sb.append(pressure);
        sb.append(" mBar");
        return sb.toString();
    }

}
