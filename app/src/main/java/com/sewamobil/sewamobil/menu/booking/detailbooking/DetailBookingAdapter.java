package com.sewamobil.sewamobil.menu.booking.detailbooking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.rentcar.Model.RentGeneralModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailBookingAdapter extends RecyclerView.Adapter<DetailBookingAdapter.Holder>  {

    Context context;
    List<RentGeneralModel> rentGeneralModels;

    public DetailBookingAdapter(Context context, List<RentGeneralModel> rentGeneralModels) {
        this.context = context;
        this.rentGeneralModels = rentGeneralModels;
    }

    @NonNull
    @Override
    public DetailBookingAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView;
        layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_detail_booking, parent, false);
        Holder rcv = new Holder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull DetailBookingAdapter.Holder holder, int position) {
        RentGeneralModel model = rentGeneralModels.get(position);

        holder.tv_title.setText(rentGeneralModels.get(position).getTitle());
        if(model.getType().equals("photo")){
            if(model.getKonten().equals("")){
                holder.img_confirm.setVisibility(View.GONE);
                holder.tv_konten.setText("Belum ada gambar konfirmasi");
            } else {
                holder.img_confirm.setVisibility(View.VISIBLE);
                holder.tv_konten.setVisibility(View.GONE);
                Picasso.with(context)
                        .load(model.getKonten())
                        .error(R.drawable.bg)
                        .placeholder(R.drawable.bg)
                        .into(holder.img_confirm, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {

                            }
                        });
            }
        } else {
            holder.tv_konten.setText(rentGeneralModels.get(position).getKonten());
        }

    }

    @Override
    public int getItemCount() {
        return rentGeneralModels.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_confirm)
        ImageView img_confirm;
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
