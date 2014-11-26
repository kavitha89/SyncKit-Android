package com.example.android.network.sync.basicsyncadapter.adpaters;

import java.lang.String;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.android.network.sync.basicsyncadapter.R;
import com.example.android.network.sync.basicsyncadapter.models.Boiler;
import com.example.android.network.sync.basicsyncadapter.models.Transformer;

public class BoilerListAdapter extends ArrayAdapter<Boiler> {

    Context context;
    int layoutResourceId;
    List<Boiler> data = null;
    private List<Boiler> boilers;

    public BoilerListAdapter(Context context, int layoutResourceId, List<Boiler> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        BoilerHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new BoilerHolder();
            holder.boilerName = (TextView)row.findViewById(R.id.text1);
            holder.boilerLocation = (TextView)row.findViewById(R.id.text2);

            row.setTag(holder);
        }
        else
        {
            holder = (BoilerHolder)row.getTag();
        }

        Boiler trs = (Boiler)data.get(position);
        holder.boilerName.setText(trs.trsName);
        holder.boilerLocation.setText(trs.trsLocation);

        return row;
    }

    static class BoilerHolder
    {
        TextView boilerName;
        TextView boilerLocation;
    }


}
