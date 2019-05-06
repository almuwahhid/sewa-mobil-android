package com.sewamobil.sewamobil.menu.rentcar.detailrent;

import android.content.Context;

import java.util.HashMap;

public class DetailRentCarPresenter implements DetailRentCarInterface.Presenter{
    Context context;
    DetailRentCarInterface.View view;

    public DetailRentCarPresenter(Context context, DetailRentCarInterface.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void bookingKendaraan(HashMap<String, String> params) {

    }
}
