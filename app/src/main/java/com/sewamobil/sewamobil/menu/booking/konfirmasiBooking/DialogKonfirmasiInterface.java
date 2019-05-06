package com.sewamobil.sewamobil.menu.booking.konfirmasiBooking;

import com.sewamobil.sewamobil.base.BaseViewInterface;

public interface DialogKonfirmasiInterface {
    interface Presenter{
        void konfirmasiBooking(String id, String img);
    }
    interface View extends BaseViewInterface {
        void onKonfirmasiBooking();
    }

}
