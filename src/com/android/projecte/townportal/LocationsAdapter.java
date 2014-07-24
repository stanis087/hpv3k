package com.android.projecte.townportal;

import java.util.Vector;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// creating custom arrayadapter: http://www.ezzylearning.com/tutorial.aspx?tid=1763429

public class LocationsAdapter extends ArrayAdapter<Location> {

	Context context;
	int layoutResourceId;
	Vector<Location> locations;

	public LocationsAdapter(Context context, int layoutResourceId,
			Vector<Location> locations) {
		super(context, layoutResourceId, locations);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.locations = locations;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {

			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(layoutResourceId, null);

		}

		Location p = getItem(position);
		if (p != null) {

			TextView tt = (TextView) v.findViewById(R.id.rowTextView);

			if (tt != null) {
				tt.setText(p.getName());
			}
		}

		return v;
	}

}