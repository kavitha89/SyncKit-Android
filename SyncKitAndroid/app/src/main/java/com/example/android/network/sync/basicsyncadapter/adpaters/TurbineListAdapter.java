package com.example.android.network.sync.basicsyncadapter.adpaters;

import java.lang.String;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.SimpleAdapter;

import com.example.android.network.sync.basicsyncadapter.models.Turbine;


public class TurbineListAdapter extends SimpleAdapter {
    private List<Turbine> turbines;

    public TurbineListAdapter(Context context, List<? extends Map<String, String>> turbineArray, int resource, String[] from,
                             int[] to) {
        super(context, turbineArray, resource, from, to);
        this.turbines = (List<Turbine>) turbineArray;
    }


}
