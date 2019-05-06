package com.sewamobil.sewamobil.menu.booking;

import android.content.Context;

import com.google.gson.Gson;
import com.sewamobil.sewamobil.menu.booking.Model.BookingModel;
import com.sewamobil.sewamobil.utils.Endpoints;
import com.sewamobil.sewamobil.utils.ProjectConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import lib.almuwahhid.utils.LibConstant;
import lib.almuwahhid.utils.UiLibRequest;

public class BookingPresenter implements BookingInterface.Presenter {
    Context context;
    BookingInterface.View view;
    Gson gson;

    public BookingPresenter(Context context, BookingInterface.View view) {
        this.context = context;
        this.view = view;
        this.gson = new Gson();
    }

    @Override
    public void requestBooking(final Map<String, String> param) {
        UiLibRequest.POST(Endpoints.stringBooking(), context, new UiLibRequest.OnPostRequest() {
            @Override
            public void onPreExecuted() {
                view.onLoading();
            }

            @Override
            public void onSuccess(JSONObject response) {
                view.onHideLoading();
                try {
                    if(response.getInt("status")== LibConstant.CODE_SUCCESS){
                        view.onRequestBooking(gson.fromJson(response.getJSONObject("data").toString(), BookingModel.class));
                    }else {
                        view.onFailed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {
                view.onHideLoading();
                view.onFailed();
            }

            @Override
            public Map<String, String> requestParam() {
                return param;
            }

            @Override
            public Map<String, String> requestHeaders() {
                Map<String, String> header_param = new HashMap<String, String>();
                return header_param;
            }
        });
    }
}
