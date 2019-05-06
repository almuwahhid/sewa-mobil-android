package com.sewamobil.sewamobil.menu.register.checkemail;

import com.sewamobil.sewamobil.base.BaseViewInterface;

public interface DialogCheckEmailInterface {
    interface Presenter{
        public void checkEmail(String email);
    }
    interface View extends BaseViewInterface {
        public void onCheckEmail(boolean isSuccess, String message);
    }
}
