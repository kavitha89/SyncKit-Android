package com.example.android.network.sync.basicsyncadapter.adpaters;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.android.network.sync.basicsyncadapter.R;
import com.example.android.network.sync.basicsyncadapter.models.Turbine;


public class TurbineListAdapter extends ArrayAdapter<Turbine> {
    Context context;
    int layoutResourceId;
    List<Turbine> data = null;

    public TurbineListAdapter(Context context, int layoutResourceId, List<Turbine> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TurbineHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TurbineHolder();
            holder.turbineName = (TextView)row.findViewById(R.id.text1);
            holder.turbineLocation = (TextView)row.findViewById(R.id.text2);

            row.setTag(holder);
        }
        else
        {
            holder = (TurbineHolder)row.getTag();
        }

        Turbine trs = (Turbine)data.get(position);
        holder.turbineName.setText(trs.trbName);
        holder.turbineLocation.setText(trs.trbLocation);

        return row;
    }

    static class TurbineHolder
    {
        TextView turbineName;
        TextView turbineLocation;
    }


}
