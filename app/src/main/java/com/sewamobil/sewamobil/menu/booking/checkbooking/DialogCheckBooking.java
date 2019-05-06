package com.sewamobil.sewamobil.menu.booking.checkbooking;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.booking.Model.BookingModel;
import com.sewamobil.sewamobil.menu.booking.detailbooking.DetailBookingActivity;
import com.sewamobil.sewamobil.menu.booking.listbooking.ListBookingAdapter;

import java.util.ArrayList;
import java.util.List;

import lib.almuwahhid.utils.DialogBuilder;
import lib.almuwahhid.utils.LibUi;

public class DialogCheckBooking extends DialogBuilder implements DialogCheckInterface.View{

    DialogCheckBookingPresenter presenter;

    ImageView img_back;
    EditText edt_kodebooking;
    RelativeLayout lay_checkbooking;
    RecyclerView recycler_view;
    List<BookingModel> list = new ArrayList<>();
    ListBookingAdapter adapter;

    public DialogCheckBooking(Context context, OnDialogCheckBooking onDialogCheckBooking) {
        super(context, R.layout.dialog_check_booking);
        presenter = new DialogCheckBookingPresenter(getContext(), this);
        this.onDialogCheckBooking = onDialogCheckBooking;

        initComponent(new OnInitComponent() {
            @Override
            public void initComponent(Dialog dialog) {
                img_back = dialog.findViewById(R.id.img_back);
                edt_kodebooking = dialog.findViewById(R.id.edt_kodebooking);
                lay_checkbooking = dialog.findViewById(R.id.lay_checkbooking);
                recycler_view = dialog.findViewById(R.id.recycler_view);
            }
        });

        setFullScreen(lay_checkbooking);
        setGravity(Gravity.BOTTOM);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edt_kodebooking.getText().toString().equals("")){
                    edt_kodebooking.setText("");
                } else {
                    dismiss();
                }
            }
        });

        edt_kodebooking.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    presenter.checkBookingModel(edt_kodebooking.getText().toString());
                    return true;
                }
                return false;
            }
        });

        adapter = new ListBookingAdapter(getContext(), list, new ListBookingAdapter.OnListBookingAdapter() {
            @Override
            public void onListClick(BookingModel model) {
                getContext().startActivity(new Intent(getContext(), DetailBookingActivity.class).putExtra("data", model));
            }
        });
        recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        recycler_view.setAdapter(adapter);

        show();
    }

    @Override
    public void onSuccessCheck(BookingModel model) {
//        onDialogCheckBooking.onSuccessCheck(model);

        list.clear();
        list.add(model);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onFailedCheck(String message) {
        LibUi.ToastShort(getContext(), message);
    }

    @Override
    public void show() {
        super.show();
        list.clear();
        adapter.notifyDataSetChanged();
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
    public void dismiss() {
        super.dismiss();
        edt_kodebooking.setText("");
    }

    OnDialogCheckBooking onDialogCheckBooking;
    public interface OnDialogCheckBooking{
        void onSuccessCheck(BookingModel model);
    }
}
