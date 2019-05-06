package com.sewamobil.sewamobil.menu.rentcar;

import com.sewamobil.sewamobil.base.BaseViewInterface;
import com.sewamobil.sewamobil.menu.rentcar.Model.RentCarModel;

import java.util.List;

public interface RentCarInterface {
    interface Presenter{
        void requestListRent();
    }
    interface View extends BaseViewInterface {
        void onRequestRent(List<RentCarModel> rentCarModels);
        void onEmptyData();
    }
}
