package com.sewamobil.sewamobil.menu.biodata.DialogChangePassword;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sewamobil.sewamobil.R;

import java.util.ArrayList;
import java.util.List;

import lib.almuwahhid.utils.DialogBuilder;
import lib.almuwahhid.utils.LibUi;

public class DialogChangePassword extends DialogBuilder implements ChangePasswordInterface.View {

    EditText edt_password_lama, edt_password;
    LinearLayout btn_ubah;
    RelativeLayout lay_changepassword;
    DialogChangePasswordPresenter presenter;

    public DialogChangePassword(Context context) {
        super(context, R.layout.dialog_change_password);

        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                btn_ubah = dialog.findViewById(R.id.btn_ubah);
                edt_password = dialog.findViewById(R.id.edt_password);
                edt_password_lama = dialog.findViewById(R.id.edt_password_lama);
                lay_changepassword = dialog.findViewById(R.id.lay_changepassword);
            }
        });
        setFormsToValidate();

        presenter = new DialogChangePasswordPresenter(getContext(), this);
        setAnimation(R.style.DialogBottomAnimation);
        setGravity(Gravity.BOTTOM);
        setFullWidth(lay_changepassword);

        btn_ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LibUi.isFormValid(getContext(), getDialog().getWindow().getDecorView(), forms)){
                    presenter.changePassword(edt_password_lama.getText().toString(), edt_password.getText().toString());
                }
            }
        });
    }

    @Override
    public void onSuccessChange() {
        dismiss();
        LibUi.ToastShort(getContext(), "Berhasil mengubah password");
    }

    @Override
    public void onFailedChange(String message) {
        LibUi.ToastShort(getContext(), message);
    }

    @Override
    public void onLoading() {
        LibUi.showLoadingDialog(getContext(), R.drawable.logo_rent);
    }

    @Override
    public void onHideLoading() {
        LibUi.hideLoadingDialog(getContext());
    }

    @Override
    public void onFailed() {
        LibUi.ToastShort(getContext(), "Bermasalah dengan server");
    }

    @Override
    public void show() {
        super.show();
        edt_password.setText("");
        edt_password_lama.setText("");
    }

    List<Integer> forms = new ArrayList<>();
    private void setFormsToValidate(){
        forms.add(R.id.edt_password);
        forms.add(R.id.edt_password_lama);
    }
}
