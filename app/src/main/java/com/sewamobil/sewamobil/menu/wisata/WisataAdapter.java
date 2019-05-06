package com.sewamobil.sewamobil.menu.wisata;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.wisata.Model.WisataModel;
import com.sewamobil.sewamobil.utils.Functions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.Holder>  {

    Context context;
    List<WisataModel> modelList;
    OnWisataAdapter onWisataAdapter;

    public WisataAdapter(Context context, List<WisataModel> modelList, OnWisataAdapter onWisataAdapter) {
        this.context = context;
        this.modelList = modelList;
        this.onWisataAdapter = onWisataAdapter;
    }

    @NonNull
    @Override
    public WisataAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView;
        layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_wisata, parent, false);
        WisataAdapter.Holder rcv = new WisataAdapter.Holder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull WisataAdapter.Holder holder, int position) {
        final WisataModel wisataModel = modelList.get(position);
        holder.tv_harga.setText(Functions.rupiahFormat(Float.valueOf(wisataModel.getBiaya())));
        holder.tv_title.setText(wisataModel.getNama_wisata());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onWisataAdapter.onWisataClick(wisataModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tv_title;
        @BindView(R.id.tv_harga)
        TextView tv_harga;
        @BindView(R.id.card)
        CardView card;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnWisataAdapter{
        void onWisataClick(WisataModel model);
    }
}
