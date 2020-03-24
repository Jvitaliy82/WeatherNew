package com.jdeveloperapps.weather;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.jdeveloperapps.weather.customViews.MyCard;
import com.jdeveloperapps.weather.gpsData.GpsCoordinator;
import com.jdeveloperapps.weather.retrofit.model.WeatherRequest;
import com.jdeveloperapps.weather.utils.PrepareUtil;

public class MainActivity extends AppCompatActivity {

    private final String LAST_CITY = "lastCity";

    private Presenter presenter;
    private SharedPreferences sharedPreferences;
    private SwipeRefreshLayout swipeContainer;
    private TextView city;
    private TextView temp;
    private TextView desc;
    private MyCard myCard;

    private ViewPager viewPager;
    private MyFragmentPageAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeContainer = findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(this::getLastCity);

        city = findViewById(R.id.city);
        temp = findViewById(R.id.temperature);
        desc = findViewById(R.id.description);
        myCard = findViewById(R.id.myCard);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPageAdapter(getSupportFragmentManager());

        viewPager.setClipToPadding(false);
        viewPager.setPadding(60, 0, 60, 0);

        presenter = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication()))
                .get(Presenter.class);
        LiveData<WeatherRequest> weatherData = presenter.getWeatherData();
        weatherData.observe(this, this::updateWeather);

        LiveData<Boolean> refreshStatus = presenter.getRefreshStatus();
        refreshStatus.observe(this, this::setRefreshStatus);

        getLastCity();
    }

    private void updateWeather(WeatherRequest weatherRequest) {
        saveLastCity(weatherRequest.city.name);
        city.setText(weatherRequest.city.name);
        temp.setText(PrepareUtil.prepareTemp(weatherRequest.listMassives[0].main.temp));
        desc.setText(weatherRequest.listMassives[0].weather[0].description);
        myCard.setSpeedWind(PrepareUtil.prepareSpeedWind(weatherRequest.listMassives[0].wind.speed));
        myCard.setHumidity(weatherRequest.listMassives[0].main.humidity + " %");
        myCard.setTemp(PrepareUtil.prepareTemp(weatherRequest.listMassives[0].main.feels_like));
        myCard.setPressure(PrepareUtil.preparePressure(weatherRequest.listMassives[0].main.pressure));
        updatePageView(weatherRequest);
    }

    private void updatePageView(WeatherRequest weather){
        pagerAdapter.setWeatherRequest(weather);
        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(pagerAdapter);
        }
        viewPager.setCurrentItem(0);

    }

    private void setRefreshStatus(boolean status) {
        swipeContainer.setRefreshing(status);
        myCard.setVisibility(status ? View.INVISIBLE : View.VISIBLE);
        viewPager.setVisibility(status ? View.INVISIBLE : View.VISIBLE);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.gpsButton:
                GpsCoordinator gpsCoordinator = new GpsCoordinator(presenter);
                gpsCoordinator.getLatLot(this);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getLastCity() {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        String city = sharedPreferences.getString(LAST_CITY, "Moscow");
        presenter.requestWeatherByCity(city);
    }

    private void saveLastCity(String city) {
        sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(LAST_CITY, city);
        ed.apply();
    }

}
