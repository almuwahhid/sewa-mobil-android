package com.sewamobil.sewamobil.menu.register;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.biodata.Model.UserModel;
import com.sewamobil.sewamobil.menu.login.LoginActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.almuwahhid.Activity.ActivityGeneral;
import lib.almuwahhid.utils.LibUi;

import static lib.almuwahhid.utils.LibUi.monthName;

    public class RegisterActivity extends ActivityGeneral implements RegisterInterface.View, DatePickerDialog.OnDateSetListener {

    RegisterPresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_user_email)
    EditText edt_user_email;
    @BindView(R.id.edt_password)
    EditText edt_password;
    @BindView(R.id.edt_nama_lengkap)
    EditText edt_nama_lengkap;
    @BindView(R.id.edt_user_bdate)
    EditText edt_user_bdate;
    @BindView(R.id.edt_alamat)
    EditText edt_alamat;
    @BindView(R.id.edt_telp)
    EditText edt_telp;
    @BindView(R.id.edt_pekerjaan)
    EditText edt_pekerjaan;
    @BindView(R.id.edt_instansi)
    EditText edt_instansi;
    @BindView(R.id.edt_ktp)
    EditText edt_ktp;
    @BindView(R.id.lay_daftar)
    LinearLayout lay_daftar;
    @BindView(R.id.radiogrup)
    RadioGroup radiogrup;

    RadioButton radio;

    String email = "";

    UserModel userModel = new UserModel();

    List<Integer> forms = new ArrayList<>();
    private void setFormsToValidate(){
        forms.add(R.id.edt_user_email);
        forms.add(R.id.edt_password);
        forms.add(R.id.edt_nama_lengkap);
        forms.add(R.id.edt_user_bdate);
        forms.add(R.id.edt_alamat);
        forms.add(R.id.edt_ktp);
        forms.add(R.id.edt_pekerjaan);
        forms.add(R.id.edt_instansi);
        forms.add(R.id.edt_telp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setTitle("Register");
        setFormsToValidate();


        if(getIntent().hasExtra("data")){
            email = getIntent().getStringExtra("data");
        }
        edt_user_email.setText(email);


        presenter = new RegisterPresenter(getContext(), this);

        edt_user_bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
//                now.add(Calendar.YEAR,-7);
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        RegisterActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setMaxDate(now);
                dpd.setFirstDayOfWeek(Calendar.MONDAY);
                dpd.setAccentColor(ContextCompat.getColor(getContext(), R.color.primary));
                dpd.show(getFragmentManager(), "Tanggal Kejadian");
            }
        });

        lay_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });
    }

    @Override
    public void onSuccessRegister() {
        startActivity(new Intent(getContext(), LoginActivity.class));
        finish();
    }

    @Override
    public void onFailedRegister(String message) {
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
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        userModel.setTgl_lahir(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        edt_user_bdate.setError(null);
        edt_user_bdate.setText(dayOfMonth + " " + monthName(monthOfYear) + " " + year);
    }

    private void validateData(){
        if(LibUi.isFormValid(this, getWindow().getDecorView(), forms)){
            radio = findViewById(radiogrup.getCheckedRadioButtonId());

            HashMap<String, String> param = new HashMap<String, String>();
            param.put("username", edt_user_email.getText().toString());
            param.put("password", edt_password.getText().toString());
            param.put("no_ktp", edt_ktp.getText().toString());
            param.put("nama_lengkap", edt_nama_lengkap.getText().toString());
            param.put("alamat", edt_alamat.getText().toString());
            param.put("pekerjaan", edt_pekerjaan.getText().toString());
            param.put("instansi", edt_instansi.getText().toString());
            param.put("tgl_lahir", userModel.getTgl_lahir());
            param.put("jenis_kelamin", radio.getText().toString());
            param.put("telp", edt_telp.getText().toString());
            presenter.registerData(param);
        }
    }
}
