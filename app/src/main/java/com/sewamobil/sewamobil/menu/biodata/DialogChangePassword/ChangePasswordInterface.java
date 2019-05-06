package com.sewamobil.sewamobil.menu.biodata.DialogChangePassword;

import com.sewamobil.sewamobil.base.BaseViewInterface;

public interface ChangePasswordInterface {
    interface Presenter{
        void changePassword(String p1, String p2);
    }

    interface View extends BaseViewInterface {
        void onSuccessChange();
        void onFailedChange(String message);
    }
}
