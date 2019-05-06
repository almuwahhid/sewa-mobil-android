package com.sewamobil.sewamobil.menu.booking.listbooking;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.booking.Model.BookingModel;
import com.sewamobil.sewamobil.menu.booking.detailbooking.DetailBookingActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.almuwahhid.Activity.ActivityGeneral;
import lib.almuwahhid.utils.LibUi;

public class ListBookingActivity extends ActivityGeneral implements ListBookingInterface.View{

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.helper_error)
    LinearLayout helper_error;
    @BindView(R.id.helper_empty)
    LinearLayout helper_empty;
    @BindView(R.id.helper_loading)
    LinearLayout helper_loading;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refresh_layout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ListBookingPresenter presenter;
    List<BookingModel> list = new ArrayList<>();
    ListBookingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_booking);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayOptions(
                ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setTitle("Riwayat Booking");

        presenter = new ListBookingPresenter(getContext(), this);

        adapter = new ListBookingAdapter(getContext(), list, new ListBookingAdapter.OnListBookingAdapter() {
            @Override
            public void onListClick(BookingModel model) {
                startActivity(new Intent(getContext(), DetailBookingActivity.class).putExtra("data", model));
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        presenter.requestList();
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestList();
            }
        });

    }

    @Override
    public void onRequestList(List<BookingModel> models) {
        list.clear();
        for (BookingModel model: models){
            list.add(model);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onEmptyData() {
        helper_empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoading() {
        helper_loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideLoading() {
        helper_loading.setVisibility(View.GONE);
        refresh_layout.setRefreshing(false);
    }

    @Override
    public void onFailed() {
        helper_error.setVisibility(View.VISIBLE);
        LibUi.ToastShort(getContext(), "Bermasalah dengan server");
    }
}
