package com.example.android.network.sync.basicsyncadapter.adpaters;

import java.lang.String;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.SimpleAdapter;

import com.example.android.network.sync.basicsyncadapter.models.Pipe;

public class PipeListAdapter extends SimpleAdapter {
    private List<Pipe> pipes;

    public PipeListAdapter(Context context, List<? extends Map<String, String>> pipesArray, int resource, String[] from,
                             int[] to) {
        super(context, pipesArray, resource, from, to);
        this.pipes = (List<Pipe>) pipesArray;
    }


}
