package com.jdeveloperapps.weather;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.jdeveloperapps.weather.retrofit.model.ListMassive;
import com.jdeveloperapps.weather.utils.PrepareUtil;

public class PageFragment extends Fragment {
    private static final String ARG_PAGE_LIST = "pageList";

    private ListMassive list;

    public PageFragment() {
        // Required empty public constructor
    }

    public static PageFragment newInstance(ListMassive list) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PAGE_LIST, list);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.list = (ListMassive) getArguments().getSerializable(ARG_PAGE_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_page, container, false);
        TextView date = v.findViewById(R.id.pageWeekDay);
        TextView desc = v.findViewById(R.id.descPage);
        TextView temp = v.findViewById(R.id.tempPage);
        date.setText(PrepareUtil.prepareDate(list.dt_txt));
        desc.setText(list.weather[0].description);
        temp.setText(PrepareUtil.prepareTemp(list.main.temp));

        return v;
    }
}
