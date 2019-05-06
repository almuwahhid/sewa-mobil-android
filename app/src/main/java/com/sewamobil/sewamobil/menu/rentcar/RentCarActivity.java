package com.sewamobil.sewamobil.menu.rentcar;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.booking.Model.BookingModel;
import com.sewamobil.sewamobil.menu.booking.checkbooking.DialogCheckBooking;
import com.sewamobil.sewamobil.menu.booking.detailbooking.DetailBookingActivity;
import com.sewamobil.sewamobil.menu.login.LoginActivity;
import com.sewamobil.sewamobil.menu.rentcar.Model.RentCarModel;
import com.sewamobil.sewamobil.menu.rentcar.detailrent.DetailRentCarActivity;
import com.sewamobil.sewamobil.utils.Functions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.almuwahhid.Activity.ActivityGeneral;
import lib.almuwahhid.Activity.FragmentPermission;
import lib.almuwahhid.utils.LibUi;

public class RentCarActivity extends FragmentPermission implements RentCarInterface.View{
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
    DialogCheckBooking dialogCheckBooking;

    List<RentCarModel> list;
    RentCarAdapter adapter;
    RentCarPresenter presenter;

    public static RentCarActivity newInstance() {
        RentCarActivity fragment = new RentCarActivity();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_rent_car, container, false);
        ButterKnife.bind(this, view);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);
        getActivity().invalidateOptionsMenu();

        presenter = new RentCarPresenter(getContext(), this);

        initRecyclerView();
        presenter.requestListRent();
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestListRent();
            }
        });

        return view;
    }

    private void initRecyclerView(){
        list = new ArrayList<>();
        adapter = new RentCarAdapter(getContext(), list, new RentCarAdapter.OnRentCarAdapter() {
            @Override
            public void onBookClicked(RentCarModel model) {
                startActivity(new Intent(getContext(), DetailRentCarActivity.class).putExtra("data", model));
            }
        });
        rv.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rv.setAdapter(adapter);
    }

    @Override
    public void onRequestRent(List<RentCarModel> rentCarModels) {
        list.clear();
        for (RentCarModel models: rentCarModels){
            list.add(models);
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
//        LibUi.hideLoadingDialog(getContext());
        refresh_layout.setRefreshing(false);
        helper_error.setVisibility(View.GONE);
        helper_empty.setVisibility(View.GONE);
        helper_loading.setVisibility(View.GONE);
    }

    @Override
    public void onFailed() {
        refresh_layout.setRefreshing(false);
        helper_error.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_rent, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_booking:
                if(Functions.isUserLogin(getContext())){
                    initDialogCheckBooking();
                } else {
                    LibUi.ToastShort(getContext(), "Login diperlukan");
                    startActivity(new Intent(getContext(), LoginActivity.class));
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initDialogCheckBooking(){
        if(dialogCheckBooking == null){
            dialogCheckBooking = new DialogCheckBooking(getContext(), new DialogCheckBooking.OnDialogCheckBooking() {
                @Override
                public void onSuccessCheck(BookingModel model) {
                    getContext().startActivity(new Intent(getContext(), DetailBookingActivity.class).putExtra("data", model));
                }
            });
        } else {
            dialogCheckBooking.show();
        }
    }
}
