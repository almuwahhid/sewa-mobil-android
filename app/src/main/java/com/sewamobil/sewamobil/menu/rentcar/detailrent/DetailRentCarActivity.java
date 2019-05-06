package com.sewamobil.sewamobil.menu.rentcar.detailrent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.booking.BookingActivity;
import com.sewamobil.sewamobil.menu.login.LoginActivity;
import com.sewamobil.sewamobil.menu.rentcar.DetailRentCarAdapter;
import com.sewamobil.sewamobil.menu.rentcar.Model.RentCarModel;
import com.sewamobil.sewamobil.menu.rentcar.RentCarHelper;
import com.sewamobil.sewamobil.utils.Functions;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.almuwahhid.Activity.ActivityGeneral;
import lib.almuwahhid.utils.LibUi;

public class DetailRentCarActivity extends ActivityGeneral implements DetailRentCarInterface.View {

    DetailRentCarAdapter adapter;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lay_book)
    LinearLayout lay_book;
    @BindView(R.id.lay_empty)
    LinearLayout lay_empty;

    RentCarModel model;
    DetailRentCarPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rent_car);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Detail Kendaraan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new DetailRentCarPresenter(this, this);

        if(getIntent().hasExtra("data")){
            model = (RentCarModel) getIntent().getSerializableExtra("data");

            rv.setLayoutManager(new LinearLayoutManager(this));
            adapter = new DetailRentCarAdapter(RentCarHelper.detail(model));
            rv.setAdapter(adapter);

            lay_book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    startActivity(new Intent(getContext(), BookingActivity.class).putExtra("data", model));
                    if(Functions.isUserLogin(getContext())){
                        new BookingActivity(getContext(), model).show();
                    } else {
                        LibUi.ToastShort(getContext(), "Login diperlukan");
                        startActivity(new Intent(getContext(), LoginActivity.class));
                    }

                }
            });

            Log.d("test", "onCreate: "+model.isAvailable());
            if(model.isAvailable()){
                lay_book.setVisibility(View.VISIBLE);
                lay_empty.setVisibility(View.GONE);

            } else {
                lay_book.setVisibility(View.GONE);
                lay_empty.setVisibility(View.VISIBLE);
            }
        } else {
            finish();
        }
    }

    @Override
    public void onSuccessBookingKendaraan(String id_booking) {

    }

    @Override
    public void onFailBookingKendaran(String message) {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onHideLoading() {

    }

    @Override
    public void onFailed() {

    }
}