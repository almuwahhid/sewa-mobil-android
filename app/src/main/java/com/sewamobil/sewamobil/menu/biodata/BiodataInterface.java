package com.sewamobil.sewamobil.menu.biodata;

import com.sewamobil.sewamobil.base.BaseViewInterface;
import com.sewamobil.sewamobil.menu.biodata.Model.UserModel;

import java.util.HashMap;

public interface BiodataInterface {
    interface Presenter{
        void changeProfil(HashMap<String, String> param);
    }

    interface View extends BaseViewInterface {
        void onChangeProfil(UserModel model);
    }
}
