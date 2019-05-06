package com.sewamobil.sewamobil.menu.wisata;

import com.sewamobil.sewamobil.base.BaseViewInterface;
import com.sewamobil.sewamobil.menu.wisata.Model.WisataModel;

import java.util.List;

public interface WisataInterface {
    interface Presenter{
        void requestWisata();
    }

    interface View extends BaseViewInterface {
        void onRequestWisata(List<WisataModel> wisataModelList);
        void onEmptyData();
    }
}
