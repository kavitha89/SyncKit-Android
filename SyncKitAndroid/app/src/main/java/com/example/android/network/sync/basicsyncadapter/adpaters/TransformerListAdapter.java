package com.example.android.network.sync.basicsyncadapter.adpaters;

import java.lang.String;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.example.android.network.sync.basicsyncadapter.R;

import com.example.android.network.sync.basicsyncadapter.models.Transformer;

public class TransformerListAdapter extends ArrayAdapter<Transformer> {

    Context context;
    int layoutResourceId;
    List<Transformer> data = null;

    public TransformerListAdapter(Context context, int layoutResourceId, List<Transformer> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TransformerHolder holder = null;

        if(row == null)
        {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TransformerHolder();
            holder.transformerName = (TextView)row.findViewById(R.id.text1);
            holder.transformerLocation = (TextView)row.findViewById(R.id.text2);

            row.setTag(holder);
        }
        else
        {
            holder = (TransformerHolder)row.getTag();
        }

        Transformer trs = (Transformer)data.get(position);
        holder.transformerName.setText(trs.trsName);
        holder.transformerLocation.setText(trs.trsLocation);

        return row;
    }

    static class TransformerHolder
    {
        TextView transformerName;
        TextView transformerLocation;
    }

	/*private List<Transformer> cars;

	public TransformerListAdapter(Context context, List<? extends Map<String, String>> cars, int resource, String[] from,
                                  int[] to) {
		super(context, cars, resource, from, to);
		this.cars = (List<Transformer>) cars;
	}*/


}
