package com.sewamobil.sewamobil.menu.booking.listbooking;

import com.sewamobil.sewamobil.base.BaseViewInterface;
import com.sewamobil.sewamobil.menu.booking.Model.BookingModel;

import java.util.List;

public interface ListBookingInterface {
    interface Presenter{
        void requestList();
    }

    interface View extends BaseViewInterface {
        void onRequestList(List<BookingModel> models);
        void onEmptyData();
    }
}
