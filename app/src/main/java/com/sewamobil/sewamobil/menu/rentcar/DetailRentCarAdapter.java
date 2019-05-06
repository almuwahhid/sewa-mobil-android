package com.sewamobil.sewamobil.menu.rentcar;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.rentcar.Model.RentGeneralModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailRentCarAdapter extends RecyclerView.Adapter<DetailRentCarAdapter.Holder> {

    List<RentGeneralModel> rentGeneralModels;

    public DetailRentCarAdapter(List<RentGeneralModel> rentGeneralModels) {
        this.rentGeneralModels = rentGeneralModels;
    }

    @NonNull
    @Override
    public DetailRentCarAdapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View layoutView;
        layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_detail_rent_car, viewGroup, false);
        DetailRentCarAdapter.Holder rcv = new DetailRentCarAdapter.Holder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailRentCarAdapter.Holder holder, int position) {
        holder.tv_title.setText(rentGeneralModels.get(position).getTitle());
        holder.tv_konten.setText(rentGeneralModels.get(position).getKonten());
        holder.img.setImageResource(rentGeneralModels.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return rentGeneralModels.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.img)
        ImageView img;
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_konten)
        TextView tv_konten;


        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
