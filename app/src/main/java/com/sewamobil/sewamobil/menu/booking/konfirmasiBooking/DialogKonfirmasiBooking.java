package com.sewamobil.sewamobil.menu.booking.konfirmasiBooking;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sewamobil.sewamobil.R;

import lib.almuwahhid.utils.DialogBuilder;
import lib.almuwahhid.utils.LibUi;

public class DialogKonfirmasiBooking extends DialogBuilder implements DialogKonfirmasiInterface.View{

    DialogKonfirmasiBookingPresenter presenter;
    OnDialogKonfirmasiBooking onDialogKonfirmasiBooking;

    public DialogKonfirmasiBooking(Context context, OnDialogKonfirmasiBooking onDialogKonfirmasiBooking) {
        super(context, R.layout.activity_dialog_konfirmasi_booking);
        this.onDialogKonfirmasiBooking = onDialogKonfirmasiBooking;
        presenter = new DialogKonfirmasiBookingPresenter(getContext(), this);

    }

    @Override
    public void onKonfirmasiBooking() {
        LibUi.ToastShort(getContext(), "Upload berhasil");
        onDialogKonfirmasiBooking.onAfterKonfirmas();
    }

    @Override
    public void onLoading() {
        LibUi.showLoadingDialog(getContext(), R.drawable.logo_rent);
    }

    @Override
    public void onHideLoading() {
        LibUi.hideLoadingDialog(getContext());
    }

    @Override
    public void onFailed() {
        LibUi.ToastShort(getContext(), "Server bermasalah");
    }

    interface OnDialogKonfirmasiBooking{
        public void onAfterKonfirmas();
    }
}
