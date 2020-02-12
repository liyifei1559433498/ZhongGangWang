package com.zgw.base.component.marker;

import android.content.Context;

import com.github.mikephil.charting.components.MarkerView;
import com.zgw.base.R;


public class PositionMarker extends MarkerView {

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     */
    public PositionMarker(Context context) {
        super(context, R.layout.item_chart_post);
    }
}
