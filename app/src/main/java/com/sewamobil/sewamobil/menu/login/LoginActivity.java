package com.sewamobil.sewamobil.menu.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.biodata.Model.UserModel;
import com.sewamobil.sewamobil.menu.main.MainActivity;
import com.sewamobil.sewamobil.menu.register.RegisterActivity;
import com.sewamobil.sewamobil.menu.register.checkemail.DialogCheckEmail;
import com.sewamobil.sewamobil.utils.Functions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.almuwahhid.Activity.ActivityGeneral;
import lib.almuwahhid.utils.LibUi;

public class LoginActivity extends ActivityGeneral implements LoginInterface.View {
    @BindView(R.id.btn_login)
    LinearLayout btn_login;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.edt_username)
    EditText edt_username;
    @BindView(R.id.tv_daftar)
    TextView tv_daftar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    LoginPresenter presenter;
    DialogCheckEmail dialogCheckEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_HOME_AS_UP);
//        getSupportActionBar().setTitle("Register");

        presenter = new LoginPresenter(getContext(), this);
        setFormsToValidate();

        if(Functions.isUserLogin(getContext())){
            startActivity(new Intent(getContext(), MainActivity.class));
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        tv_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCheckEmailDialog();
            }
        });
    }

    private void validate(){
        if(LibUi.isFormValid(this, getWindow().getDecorView(), forms)){
            presenter.requestLogin(edt_username.getText().toString(), edt_password.getText().toString());
        }
    }

    List<Integer> forms = new ArrayList<>();
    private void setFormsToValidate(){
        forms.add(R.id.edt_password);
        forms.add(R.id.edt_username);
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
        LibUi.ToastShort(getContext(), "Bermasalah dengan Server");
    }

    @Override
    public void onSuccessLogin(UserModel model) {
        Functions.saveUserPreferenece(getContext(), model);
        startActivity(new Intent(getContext(), MainActivity.class));
    }

    @Override
    public void onFailedLogin() {
        LibUi.ToastShort(getContext(), "Login gagal, username / password salah");
    }

    private void initCheckEmailDialog(){
        if(dialogCheckEmail == null) {
            dialogCheckEmail = new DialogCheckEmail(getContext(), new DialogCheckEmail.OnDialogCheckEmail() {
                @Override
                public void onSuccessCheck(String email) {
                    startActivity(new Intent(getContext(), RegisterActivity.class).putExtra("data", email));
                }
            });
        } else {
            dialogCheckEmail.show();
        }
    }
}
