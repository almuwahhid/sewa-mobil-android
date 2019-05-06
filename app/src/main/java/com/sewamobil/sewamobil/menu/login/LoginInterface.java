package com.sewamobil.sewamobil.menu.login;

import com.sewamobil.sewamobil.base.BaseViewInterface;
import com.sewamobil.sewamobil.menu.biodata.Model.UserModel;

public interface LoginInterface {
    interface Presenter{
        void requestLogin(String username, String password);
    }

    interface View extends BaseViewInterface {
        void onSuccessLogin(UserModel model);
        void onFailedLogin();
    }
}
