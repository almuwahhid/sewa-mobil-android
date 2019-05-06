package com.sewamobil.sewamobil.menu.booking.listbooking;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.booking.Model.BookingModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.almuwahhid.utils.LibUi;

public class ListBookingAdapter extends RecyclerView.Adapter<ListBookingAdapter.Holder> {
    Context context;
    List<BookingModel> bookingModels;
    OnListBookingAdapter onListBookingAdapter;

    public ListBookingAdapter(Context context, List<BookingModel> bookingModels, OnListBookingAdapter onListBookingAdapter) {
        this.context = context;
        this.bookingModels = bookingModels;
        this.onListBookingAdapter = onListBookingAdapter;
    }

    @NonNull
    @Override
    public ListBookingAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView;
        layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_booking, parent, false);
        ListBookingAdapter.Holder rcv = new ListBookingAdapter.Holder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull ListBookingAdapter.Holder holder, int position) {
        final BookingModel bookingModel = bookingModels.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListBookingAdapter.onListClick(bookingModel);
            }
        });

        String begin = bookingModel.getBegin_date().split(" ")[0];
        String due = bookingModel.getDue_date().split(" ")[0];

      holder.tv_date.setText(begin.split("-")[2]+" "+ LibUi.monthName(Integer.valueOf(begin.split("-")[1])-1)+ " "+begin.split("-")[0]+
                            " - "+
                            due.split("-")[2]+" "+ LibUi.monthName(Integer.valueOf(due.split("-")[1])-1)+ " "+due.split("-")[0]);

      holder.tv_merk.setText(bookingModel.getMerk());
      holder.tv_kodebooking.setText("Kode Booking : "+bookingModel.getKode_booking());

      if(bookingModel.getConfirmed().equals("Y")){
          if(!bookingModel.getDelete().equals("")){
              holder.tv_status.setText("Dibatalkan");
              holder.tv_status.setTextColor(context.getResources().getColor(R.color.red_500));
          } else {
              holder.tv_status.setText("Dikonfirmasi");
              holder.tv_status.setTextColor(context.getResources().getColor(R.color.green_500));
          }
      } else if(bookingModel.getConfirmed().equals("N") && !bookingModel.getDelete().equals("")){
          holder.tv_status.setText("Ditolak");
          holder.tv_status.setTextColor(context.getResources().getColor(R.color.red_500));
      } else {
          holder.tv_status.setText("Menunggu konfirmasi");
          holder.tv_status.setTextColor(context.getResources().getColor(R.color.grey_500));
      }

    }

    @Override
    public int getItemCount() {
        return bookingModels.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.lay_booking)
        RelativeLayout lay_booking;
        @BindView(R.id.tv_kodebooking)
        TextView tv_kodebooking;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_merk)
        TextView tv_merk;
        @BindView(R.id.tv_status)
        TextView tv_status;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnListBookingAdapter{
        void onListClick(BookingModel model);
    }
}
