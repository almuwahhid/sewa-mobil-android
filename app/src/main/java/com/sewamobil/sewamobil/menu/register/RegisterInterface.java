package com.sewamobil.sewamobil.menu.register;

import com.sewamobil.sewamobil.base.BaseViewInterface;

import java.util.HashMap;

public interface RegisterInterface {
    interface Presenter{
        void registerData(HashMap<String, String> param);
    }
    interface View extends BaseViewInterface {
        void onSuccessRegister();
        void onFailedRegister(String message);
    }
}
