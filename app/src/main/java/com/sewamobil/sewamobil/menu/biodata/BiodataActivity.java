package com.sewamobil.sewamobil.menu.biodata;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.biodata.DialogChangePassword.DialogChangePassword;
import com.sewamobil.sewamobil.menu.biodata.Model.UserModel;
import com.sewamobil.sewamobil.utils.Functions;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.almuwahhid.Activity.ActivityGeneral;
import lib.almuwahhid.utils.LibUi;

import static lib.almuwahhid.utils.LibUi.monthName;

public class BiodataActivity extends ActivityGeneral implements BiodataInterface.View, DatePickerDialog.OnDateSetListener {

    BiodataPresenter presenter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_user_email)
    EditText edt_user_email;
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
    @BindView(R.id.radiogrup)
    RadioGroup radiogrup;
    @BindView(R.id.lay_daftar)
    LinearLayout lay_daftar;

    DialogChangePassword dialogChangePassword;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ubahpassword, menu);
        return super.onCreateOptionsMenu(menu);
    }

    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biodata);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setTitle("Biodata");
        presenter = new BiodataPresenter(getContext(), this);

        if(getIntent().hasExtra("data")){
            userModel = (UserModel) getIntent().getSerializableExtra("data");

            edt_user_email.setText(userModel.getUsername());
            edt_alamat.setText(userModel.getAlamat());
            edt_instansi.setText(userModel.getInstansi());
            edt_ktp.setText(userModel.getNo_ktp());
            edt_nama_lengkap.setText(userModel.getNama_lengkap());
            edt_pekerjaan.setText(userModel.getPekerjaan());
            edt_telp.setText(userModel.getTelp());

            switch (userModel.getJenis_kelamin()){
                case "Laki - Laki":
                    radiogrup.check(R.id.radiolaki);
                    break;
                case "Perempuan":
                    radiogrup.check(R.id.radiowanita);
                    break;
                default:
                    radiogrup.check(R.id.radiolaki);
                    break;
            }

            try{
                edt_user_bdate.setText(userModel.getTgl_lahir().split("-")[2]+" "+ LibUi.monthName(Integer.valueOf(userModel.getTgl_lahir().split("-")[1])-1)+ " "+userModel.getTgl_lahir().split("-")[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();
                edt_user_bdate.setText("");
            }

            lay_daftar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    validateData();
                }
            });

            edt_user_bdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar now = Calendar.getInstance();
//                now.add(Calendar.YEAR,-7);
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            BiodataActivity.this,
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


        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_ubahpassword:
                initDialogChangePassword();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChangeProfil(UserModel model) {
        Toast.makeText(getContext(), "Berhasil update profil", Toast.LENGTH_SHORT).show();
        Functions.saveUserPreferenece(getContext(), model);
    }

    @Override
    public void onLoading() {
        LibUi.showLoadingDialog(getContext(), R.drawable.logo_rent);
    }

    @Override
    public void onHideLoading() {
        LibUi.hideLoadingDialog(getContext());
    }

    private void validateData(){
        RadioButton radio = findViewById(radiogrup.getCheckedRadioButtonId());

        HashMap<String, String> param = new HashMap<String, String>();
        param.put("username", edt_user_email.getText().toString());
        param.put("no_ktp", edt_ktp.getText().toString());
        param.put("nama_lengkap", edt_nama_lengkap.getText().toString());
        param.put("alamat", edt_alamat.getText().toString());
        param.put("pekerjaan", edt_pekerjaan.getText().toString());
        param.put("instansi", edt_instansi.getText().toString());
        param.put("tgl_lahir", userModel.getTgl_lahir());
        param.put("jenis_kelamin", radio.getText().toString());
        param.put("telp", edt_telp.getText().toString());

        presenter.changeProfil(param);
    }



    @Override
    public void onFailed() {
        Toast.makeText(getContext(), "Bermasalah dengan server", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        userModel.setTgl_lahir(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        edt_user_bdate.setError(null);
        edt_user_bdate.setText(dayOfMonth + " " + monthName(monthOfYear) + " " + year);
    }

    private void initDialogChangePassword(){
        if(dialogChangePassword == null){
            dialogChangePassword = new DialogChangePassword(getContext());
            dialogChangePassword.show();
        } else {
            dialogChangePassword.show();
        }
    }
}
