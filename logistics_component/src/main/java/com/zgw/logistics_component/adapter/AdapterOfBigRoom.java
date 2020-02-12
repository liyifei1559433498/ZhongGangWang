package com.zgw.logistics_component.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zgw.logistics_component.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by  李逸飞 on 2020/2/12  15:38.
 */
public class AdapterOfBigRoom extends RecyclerView.Adapter<AdapterOfBigRoom.MViewHolder> {
    private Context context;

    public AdapterOfBigRoom(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_of_logistics, null);
        return new MViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 10;
    }

    class MViewHolder extends RecyclerView.ViewHolder {

        public MViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
