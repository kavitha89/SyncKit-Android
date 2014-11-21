package com.example.android.network.sync.basicsyncadapter.adpaters;

import java.lang.String;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.SimpleAdapter;

import com.example.android.network.sync.basicsyncadapter.models.Transformer;

public class TransformerListAdapter extends SimpleAdapter {
	private List<Transformer> cars;

	public TransformerListAdapter(Context context, List<? extends Map<String, String>> cars, int resource, String[] from,
                                  int[] to) {
		super(context, cars, resource, from, to);
		this.cars = (List<Transformer>) cars;
	}


}
