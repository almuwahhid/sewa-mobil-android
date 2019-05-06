package com.sewamobil.sewamobil.menu.profile;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.biodata.BiodataActivity;
import com.sewamobil.sewamobil.menu.booking.listbooking.ListBookingActivity;
import com.sewamobil.sewamobil.menu.login.LoginActivity;
import com.sewamobil.sewamobil.menu.main.MainActivity;
import com.sewamobil.sewamobil.menu.register.RegisterActivity;
import com.sewamobil.sewamobil.menu.register.checkemail.DialogCheckEmail;
import com.sewamobil.sewamobil.menu.tentangaplikasi.TentangAplikasiActivity;
import com.sewamobil.sewamobil.utils.Functions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.almuwahhid.Activity.FragmentPermission;
import lib.almuwahhid.utils.LibUi;

public class ProfileActivity extends FragmentPermission {

    @BindView(R.id.lay_login)
    LinearLayout lay_login;
    @BindView(R.id.lay_profil)
    LinearLayout lay_profil;
    @BindView(R.id.lay_biodata)
    RelativeLayout lay_biodata;
    @BindView(R.id.lay_bookinghistory)
    RelativeLayout lay_bookinghistory;
    @BindView(R.id.lay_aboutapp)
    RelativeLayout lay_aboutapp;
    @BindView(R.id.card_login)
    CardView card_login;
    @BindView(R.id.card_mendaftar)
    CardView card_mendaftar;

    DialogCheckEmail dialogCheckEmail;

    public static ProfileActivity newInstance() {
        ProfileActivity fragment = new ProfileActivity();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: "+Functions.isUserLogin(getContext()));
        if(Functions.isUserLogin(getContext())){
            lay_login.setVisibility(View.GONE);
            lay_profil.setVisibility(View.VISIBLE);
        } else {
            lay_login.setVisibility(View.VISIBLE);
            lay_profil.setVisibility(View.GONE);
        }
    }

    String TAG = "ProfileActivity";
    @OnClick({ R.id.card_mendaftar, R.id.card_login, R.id.lay_biodata, R.id.lay_bookinghistory, R.id.lay_aboutapp, R.id.lay_keluar})
    public void setViewOnClickEvent(View view) {
        Log.d(TAG, "setViewOnClickEvent: "+view.getId());
        switch(view.getId())
        {
            case R.id.card_mendaftar:
                initCheckEmailDialog();
                break;
            case R.id.card_login:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
            case R.id.lay_biodata:
                startActivity(new Intent(getContext(), BiodataActivity.class).putExtra("data", Functions.getUser(getContext())));
                break;
            case R.id.lay_bookinghistory:
                startActivity(new Intent(getContext(), ListBookingActivity.class));
                break;
            case R.id.lay_aboutapp:
                startActivity(new Intent(getContext(), TentangAplikasiActivity.class));
                break;
            case R.id.lay_keluar:
                Functions.removeUserPreference(getContext());
                startActivity(new Intent(getContext(), MainActivity.class));
                break;

        }
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
