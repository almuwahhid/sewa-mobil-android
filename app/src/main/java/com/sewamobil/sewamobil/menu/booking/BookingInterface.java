package com.sewamobil.sewamobil.menu.booking;

import com.sewamobil.sewamobil.base.BaseViewInterface;
import com.sewamobil.sewamobil.menu.booking.Model.BookingModel;

import java.util.Map;

public interface BookingInterface {
    interface Presenter{
        void requestBooking(Map<String, String> param);
    }

    interface View extends BaseViewInterface {
        void onRequestBooking(BookingModel model);
    }
}
