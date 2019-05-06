package com.sewamobil.sewamobil.menu.wisata;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.wisata.Model.WisataModel;
import com.sewamobil.sewamobil.menu.wisata.detailwisata.DetailWisataActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lib.almuwahhid.Activity.FragmentPermission;

public class WisataActivity extends FragmentPermission implements WisataInterface.View{

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.helper_error)
    LinearLayout helper_error;
    @BindView(R.id.helper_empty)
    LinearLayout helper_empty;
    @BindView(R.id.helper_loading)
    LinearLayout helper_loading;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refresh_layout;

    List<WisataModel> list;
    WisataAdapter adapter;
    WisataPresenter presenter;

    public static WisataActivity newInstance() {
        WisataActivity fragment = new WisataActivity();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_wisata, container, false);
        ButterKnife.bind(this, view);

        presenter = new WisataPresenter(getContext(), this);

        initRecyclerView();
        presenter.requestWisata();
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestWisata();
            }
        });
        return view;
    }

    private void initRecyclerView(){
        list = new ArrayList<>();
        adapter = new WisataAdapter(getContext(), list, new WisataAdapter.OnWisataAdapter() {
            @Override
            public void onWisataClick(WisataModel model) {
                startActivity(new Intent(getContext(), DetailWisataActivity.class).putExtra("data", model));
            }
        });
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_view.setAdapter(adapter);
    }

    @Override
    public void onRequestWisata(List<WisataModel> wisataModelList) {
        list.clear();
        for (WisataModel models: wisataModelList){
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
}
