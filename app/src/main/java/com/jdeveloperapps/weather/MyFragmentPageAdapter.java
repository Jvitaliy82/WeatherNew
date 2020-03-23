package com.jdeveloperapps.weather;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jdeveloperapps.weather.retrofit.model.ListMassive;
import com.jdeveloperapps.weather.retrofit.model.WeatherRequest;

public class MyFragmentPageAdapter extends FragmentPagerAdapter {

    private WeatherRequest weatherRequest;

    public MyFragmentPageAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        ListMassive list = weatherRequest.listMassives[position];
        return PageFragment.newInstance(list);
    }

    @Override
    public int getCount() {
        return weatherRequest.listMassives.length;
    }

    public void setWeatherRequest(WeatherRequest weatherRequest) {
        this.weatherRequest = weatherRequest;
    }
}
