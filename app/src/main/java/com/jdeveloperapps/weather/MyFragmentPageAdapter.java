package com.jdeveloperapps.weather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jdeveloperapps.weather.retrofit.model.ListMassive;
import com.jdeveloperapps.weather.retrofit.model.WeatherRequest;
import com.jdeveloperapps.weather.utils.PrepareUtil;

public class MyFragmentPageAdapter extends RecyclerView.Adapter<MyFragmentPageAdapter.ViewHolder> {

    private WeatherRequest weatherRequest;


    public void setWeatherRequest(WeatherRequest weatherRequest) {
        this.weatherRequest = weatherRequest;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_page, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(weatherRequest.listMassives[position]);
    }

    @Override
    public int getItemCount() {
        return weatherRequest.listMassives.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView pageWeekDay;
        private TextView descPage;
        private TextView tempPage;

        public ViewHolder(View view) {
            super(view);

            pageWeekDay = view.findViewById(R.id.pageWeekDay);
            descPage = view.findViewById(R.id.descPage);
            tempPage = view.findViewById(R.id.tempPage);
        }

        public void bind(ListMassive listMassive) {
            pageWeekDay.setText(listMassive.dt_txt);
            descPage.setText(listMassive.weather[0].description);
            tempPage.setText(PrepareUtil.prepareTemp(listMassive.main.temp));
        }
    }
}
