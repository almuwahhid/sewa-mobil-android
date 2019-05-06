package com.sewamobil.sewamobil.menu.booking.checkbooking;

import com.sewamobil.sewamobil.base.BaseViewInterface;
import com.sewamobil.sewamobil.menu.booking.Model.BookingModel;

public interface DialogCheckInterface {
    interface Presenter{
        void checkBookingModel(String kodebooking);
    }

    interface View extends BaseViewInterface {
        void onSuccessCheck(BookingModel model);
        void onFailedCheck(String message);

    }
}
