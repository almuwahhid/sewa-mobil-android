package com.sewamobil.sewamobil.menu.register.checkemail;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sewamobil.sewamobil.R;

import lib.almuwahhid.utils.DialogBuilder;
import lib.almuwahhid.utils.LibUi;

public class DialogCheckEmail extends DialogBuilder implements DialogCheckEmailInterface.View {

    EditText edt_mendaftar;
    RelativeLayout lay_checkemail;
    LinearLayout lay_mendaftar;
    DialogCheckEmailPresenter presenter;
    OnDialogCheckEmail onDialogCheckEmail;

    public DialogCheckEmail(Context context, final OnDialogCheckEmail onDialogCheckEmail) {
        super(context, R.layout.dialog_check_email);
        this.onDialogCheckEmail = onDialogCheckEmail;

        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                lay_checkemail = dialog.findViewById(R.id.lay_checkemail);
                lay_mendaftar = dialog.findViewById(R.id.lay_mendaftar);
                edt_mendaftar = dialog.findViewById(R.id.edt_mendaftar);
            }
        });

        setFullWidth(lay_checkemail);
        setGravity(Gravity.BOTTOM);
        setAnimation(R.style.DialogBottomAnimation);
        presenter = new DialogCheckEmailPresenter(getContext(), this);

        lay_mendaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_mendaftar.getText().toString().equals("")){
                    edt_mendaftar.setError("Isi terlebih dahulu emailnya");
                } else {
                    presenter.checkEmail(edt_mendaftar.getText().toString());
                }
            }
        });

        show();
    }

    @Override
    public void onCheckEmail(boolean isSuccess, String message) {
        dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        if(isSuccess)
            onDialogCheckEmail.onSuccessCheck(edt_mendaftar.getText().toString());
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

    }

    public interface OnDialogCheckEmail{
        void onSuccessCheck(String email);
    }

    @Override
    public void show() {
        super.show();
        edt_mendaftar.setText("");
    }
}
