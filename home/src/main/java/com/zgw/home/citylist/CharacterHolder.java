package com.zgw.home.citylist;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.zgw.home.R;

public class CharacterHolder extends RecyclerView.ViewHolder {

    public TextView mCharater;

    public CharacterHolder(View itemView) {
        super(itemView);
        mCharater = (TextView) itemView.findViewById(R.id.character);
    }
}
