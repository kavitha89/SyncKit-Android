package com.example.android.network.sync.basicsyncadapter.adpaters;

import java.lang.String;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.SimpleAdapter;

import com.example.android.network.sync.basicsyncadapter.models.Boiler;

public class BoilerListAdapter extends SimpleAdapter {
    private List<Boiler> boilers;

    public BoilerListAdapter(Context context, List<? extends Map<String, String>> boilerArray, int resource, String[] from,
                             int[] to) {
        super(context, boilerArray, resource, from, to);
        this.boilers = (List<Boiler>) boilerArray;
    }


}
