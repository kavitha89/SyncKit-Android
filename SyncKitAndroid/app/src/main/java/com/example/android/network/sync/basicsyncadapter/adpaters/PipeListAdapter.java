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
import com.example.android.network.sync.basicsyncadapter.models.Pipe;

public class PipeListAdapter extends ArrayAdapter<Pipe> {
        Context context;
        int layoutResourceId;
        List<Pipe> data = null;

public PipeListAdapter(Context context, int layoutResourceId, List<Pipe> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PipeHolder holder = null;

        if(row == null)
        {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder = new PipeHolder();
        holder.pipeLocation = (TextView)row.findViewById(R.id.text1);
        holder.pipeContent = (TextView)row.findViewById(R.id.text2);

        row.setTag(holder);
        }
        else
        {
        holder = (PipeHolder)row.getTag();
        }

        Pipe trs = (Pipe)data.get(position);
        holder.pipeLocation.setText(trs.pLocation);
        holder.pipeContent.setText(trs.pCurrentContainment);

        return row;
        }

static class PipeHolder
{
    TextView pipeLocation;
    TextView pipeContent;
}


}

